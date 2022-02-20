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
	}
}
