import javax.swing.*;

public class Driver{
	public static void main(String[] args){
		ContestantPanel p = new ContestantPanel();
		JFrame frame = new JFrame();
		frame.add(p);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
