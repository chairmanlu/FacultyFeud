import javax.swing.*;
import java.io.*;

public class Driver{
	static class Updater{
		private ContestantPanel c;
		private ModeratorPanel m;
		public Updater(ContestantPanel c, ModeratorPanel m){
			this.c = c;
			this.m = m;
		}

		public void update(){
			c.renderPanel();
			m.renderPanel();
		}

		public void strike(){
			c.showStrike();
		}

		public void reveal(int index){
			c.reveal(index);
		}

		public void unreveal(){
			c.unreveal();
		}

		public void revealQuestion(){
			c.revealQuestion();
		}

		public void hideQuestion(){
			c.hideQuestion();
		}
	}

	public static void main(String[] args) throws FileNotFoundException, IOException{
//		Game g = new Game("../data/faculty_feud_2022.csv");
		Game g = new Game("../data/test.csv");
		ContestantPanel p = new ContestantPanel(g);
		ModeratorPanel p2 = new ModeratorPanel(g);
		Updater u = new Updater(p,p2);
		p.setUpdater(u);
		p2.setUpdater(u);

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
