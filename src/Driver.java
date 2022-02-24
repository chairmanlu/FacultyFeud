import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Driver{
	static Color BG_COLOR = Color.BLUE;
	static Color TEXT_COLOR = Color.WHITE;
	static Color TITLE_COLOR = Color.YELLOW;
	static Color STRIKE_COLOR = Color.RED;

	public static void main(String[] args) throws FileNotFoundException, IOException{
		Game g = new Game("../data/csv_practice.csv");
//		Game g = new Game("../data/test.csv");
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
