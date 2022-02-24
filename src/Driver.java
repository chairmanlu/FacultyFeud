import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.sound.sampled.*;

public class Driver{
	static Color BG_COLOR = Color.BLUE;
	static Color TEXT_COLOR = Color.WHITE;
	static Color TITLE_COLOR = Color.YELLOW;
	static Color STRIKE_COLOR = Color.RED;

	public static synchronized void playSound(final String url) {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run(){
				try{
					Clip clip = AudioSystem.getClip();
					File f = new File("sounds",url);
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());
					clip.open(inputStream);
					clip.start(); 
				}
				catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

	public static void main(String[] args) throws FileNotFoundException, IOException{
		Game g = new Game("data/faculty_feud_2022.csv");
		ContestantPanel p = new ContestantPanel(g);
		ModeratorPanel p2 = new ModeratorPanel(g, p);

		JFrame frame = new JFrame();
		frame.add(p);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFrame frame2 = new JFrame();
		frame2.add(p2);
		frame2.pack();
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
