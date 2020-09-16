package net.Zein.Mp3Converter;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	public static MainWindow frame;
	private JTextArea songs;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Youtube To Mp3 - Zein");
		setName("Youtube to Mp3");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/Icon.png")));
		setResizable(false);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		setLocationRelativeTo(null);
		setBounds(100, 100, 269, 453); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		  
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 239, 338);
		contentPane.add(scrollPane);
		
		songs = new JTextArea();
		songs.setBackground(SystemColor.text);
		songs.setForeground(SystemColor.desktop);
	 	songs.setFont(new Font("Arial", Font.PLAIN, 12));
	 	scrollPane.setViewportView(songs);
	 	
		JButton download = new JButton("Download");
		download.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				String[] lines = songs.getText().split("\\n");
            	if(lines.length == 1 && lines[0].equals("")) JOptionPane.showMessageDialog(null, "You must input a song name in the text box", "Error" , JOptionPane.ERROR_MESSAGE);
            	for(int i = 0; i < lines.length; i++){
            		if(!lines[i].equals(""))	
            			new Converter(lines[i]);
            	}
			}
			
		});
		
		download.setBounds(10, 385, 239, 23);
		contentPane.add(download);
		
		JLabel songLabel = new JLabel("Songs:");
		songLabel.setBounds(10, 11, 46, 14);
		contentPane.add(songLabel);
		
		JButton option = new JButton("Options");
		option.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new OptionsWindow();
			}
		});
		
		option.setBounds(160, 2, 89, 23);
		contentPane.add(option);
		
	
	
	}


}
