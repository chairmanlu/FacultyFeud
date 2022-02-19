import javax.swing.*;

public class Driver{
	public static void main(String[] args){
		Panel p=new Panel();
		JFrame frame = new JFrame();
		frame.add(p);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
