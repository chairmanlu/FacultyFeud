import javax.swing.*;
import java.io.*;

public class Driver{
	public static void main(String[] args) throws FileNotFoundException, IOException{
		Game g = new Game("../data/faculty_feud_2022.csv");
		ContestantPanel p = new ContestantPanel(g);
		JFrame frame = new JFrame();
		frame.add(p);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ModeratorPanel p2 = new ModeratorPanel(g, p);
		JFrame frame2 = new JFrame();
		frame2.add(p2);
		frame2.pack();
		frame2.setVisible(true);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
