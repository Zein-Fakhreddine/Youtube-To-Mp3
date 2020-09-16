package net.Zein.Mp3Converter;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OptionsWindow extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JCheckBox showMusicAndTime;
	private JCheckBox avoidMusicVideo;
	
	//Is static to make the code nicer
	public static boolean showInfo = false;
	public static boolean avoidMusic = false;
	
	/**
	 * Create the frame.
	 */
	public OptionsWindow() {
		setTitle("Options");
		setVisible(true);
		setBounds(100, 100, 344, 203);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Icon.png")));
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(MainWindow.frame);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		showMusicAndTime = new JCheckBox("Display song name and time before download");
		avoidMusicVideo = new JCheckBox("Avoid music video downloads ");
		
		if(showInfo) showMusicAndTime.setSelected(true);
		if(avoidMusic) avoidMusicVideo.setSelected(true);
		
		showMusicAndTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(showMusicAndTime.isSelected())
					showInfo = true;
				else
					showInfo = false;
			}
		});
		
		showMusicAndTime.setBounds(6, 34, 275, 23);
		contentPane.add(showMusicAndTime);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btnNewButton.setBounds(10, 109, 306, 23);
		contentPane.add(btnNewButton);
		
		JButton info = new JButton("Info");
		info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "The program starts by searching the name of the song on Youtube then taking the first link "
						+ "and downloading the mp3 file. INFO: The audio from the video might need to be converted in which case the program wont work.  Program by Zein Fkahreddine "
						, "Error" , JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		info.setBounds(247, 11, 89, 23);
		contentPane.add(info);
	
		avoidMusicVideo.setBounds(6, 60, 178, 23);
		contentPane.add(avoidMusicVideo);

		avoidMusicVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(avoidMusicVideo.isSelected())
					avoidMusic = true;
				else
					avoidMusic = false;
			}
		});
		
	}
}
