package net.Zein.Mp3Converter;

import java.awt.Desktop;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;



public class Converter{
	
	//Song identities
	private String songName;
	private String songURL;
	private String songTime;
	private String youtubeSongName;
	private int acualSongTime;
	
	//Icon
	private final ImageIcon icon;
	
	/**
	 * Sets the song name and calls the convert method
	 * @param songName The song Name
	 */
	public Converter(String songName){
		this.songName = songName;
		icon = new ImageIcon();
		
		try {
			Image image = ImageIO.read(getClass().getResourceAsStream("/Icon.png"));
			icon.setImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		convert();
	}
	

	/**
	 * Searches on Youtube with the Song Name
	 * Acquires the link for the first recommended video
	 * Gets the time for the first recommended video
	 */
	public void convert() {	
		
		String[] spaces = songName.split(" ");
		URL youtubeUrl;
		String songNameYoutube = "";
		for (int i = 0; i < spaces.length; i++) {	
				if(i == 0){
					songNameYoutube += spaces[i];
				}
				else{
					songNameYoutube += "%20" + spaces[i];
				}		
		}
		
		try {
		
			if(OptionsWindow.avoidMusic)
				youtubeUrl = new URL("https://www.youtube.com/results?q=" + songNameYoutube + "%20lyrics");
			else
				youtubeUrl = new URL("https://www.youtube.com/results?q=" + songNameYoutube);
			
			 URLConnection conn = youtubeUrl.openConnection();
			  

	            // open the stream and put it into BufferedReader
	            BufferedReader br = new BufferedReader(
	                               new InputStreamReader(conn.getInputStream()));
	     
	           boolean read = true;
	  
	            while(read){
	            	String text = br.readLine();
	            
	            	if(text.contains("<span class=\"video-time\">") ){
	            		String songTimeStage1 = text.split("\"video-time\">")[1];
	            		songTime = songTimeStage1.split("</span>")[0];
	            	
	            		if(!songTime.equals("__length_seconds__")){
	            	    String regx = ":";
	            	    String actualSongTime = songTime;
	            	    char[] ca = regx.toCharArray();
	            	    for (char c : ca) {
	            	    	actualSongTime = actualSongTime.replace(""+c, "");
	            	    }
	            	    
	            	    this.acualSongTime = Integer.parseInt(actualSongTime);
	            		
	            		}
	            	}
	            	
	            	System.out.println(text);
	            	if(text.contains("<h3 class=\"yt-lockup-title\">")){
	            		if(!(text.contains("<h3 class=\"yt-lockup-title\"><a class=\"yt-uix-sessionlink yt-uix-tile-link  spf-link  yt-ui-ellipsis yt-ui-ellipsis-2\" dir=\"ltr\" title=\"__title__\""))){
	            	
	            			//Get URL
	            			String songURLStage1 = text.split("href=\"")[1];
	            			String songURLState2 = songURLStage1.split("\"")[0];
	            			songURL = "http://www.youtube.com" + songURLState2;
	            			
	            			if(songURLState2.contains("/user")) {
	            				//Getting a channel not a song
	            				continue;
	            			}
	            			
	            			//Get Youtube song name
	            			String youtubeSongNameStage1 = text.split("dir=\"ltr\">")[1];
	            			System.out.println(youtubeSongName);
	            			youtubeSongName = youtubeSongNameStage1.split("</a>")[0];
	            			read = false;
	            			}
	            		}
	            		
	            	}
	            
	        	if(this.acualSongTime >= 2000){
    				JOptionPane.showMessageDialog(null, youtubeSongName  + " Time: " + songTime + " is too long to download!", "Error" , JOptionPane.ERROR_MESSAGE);
    				return;
    			}
	        	
	            br.close();
	            
	            int proceed = -1;
	            if(OptionsWindow.showInfo){
	            	MainWindow.frame.requestFocus();
	            	proceed = JOptionPane.showConfirmDialog(null, "Song Name: " + youtubeSongName + " Song Time: " + songTime, "Do you want to proceed?", JOptionPane.INFORMATION_MESSAGE);
	            }
	            else
	            	download(getSongDownloadLink(songURL));
	            
	            if(proceed == JOptionPane.YES_OPTION)
	          	 download(getSongDownloadLink(songURL));
			
		} catch (Exception e) {
			e.printStackTrace();
		     JOptionPane.showMessageDialog(null, "There was an error downloading the music. The song might be too long", "Error", JOptionPane.ERROR_MESSAGE, icon);
		}

	}
	
	/**
	 * Opens the computers default browser and goes to the download link
	 * @param songURL The download link
	 */
	public void download(String songURL){
		try{
			if(Desktop.isDesktopSupported())
			{
			  Desktop.getDesktop().browse(new URI(songURL));
			}
		} catch(Exception e){
		     JOptionPane.showMessageDialog(null, "There was an error downloading the music. The song might be too long", "Error", JOptionPane.ERROR_MESSAGE, icon);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the songs download URL
	 * @param songURl the Youtube song URL
	 * @return Returns the link
	 */
	public  String getSongDownloadLink(String songURl){
		String name = null;
		URL songNameURL;
	
		try {
			songNameURL = new URL("http://youtubeinmp3.com/download/?video=" + songURl);
			URLConnection conn = songNameURL.openConnection();

	        // open the stream and put it into BufferedReader
	        BufferedReader br = new BufferedReader(
	                           new InputStreamReader(conn.getInputStream()));
	    
	        boolean read = true;
	        String text = null;

            while(read){
            	text = br.readLine();
            	if(text == null) break;
            	if(text.contains("<meta itemprop='contentURL' content=")){
            		String songNameStage1 = text.split("content='http")[1];
            		String songNameStage2 = songNameStage1.split("'")[0];
            		name = "http" + songNameStage2;
            	}
            }
                   	
            br.close();
		} catch (Exception e) {
		     JOptionPane.showMessageDialog(null, "There was an error downloading the music. The song might be too long", "Error", JOptionPane.ERROR_MESSAGE, icon);
			e.printStackTrace();
		}
	
		return name;
	}
	
}
