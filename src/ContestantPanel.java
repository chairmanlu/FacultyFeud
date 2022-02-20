import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class ContestantPanel extends JPanel{
	private int width;
	private int height;
	private Game game;
	private Driver.Updater updater;
	private boolean revealed[] = new boolean[8];

	private void setLabelFont(JLabel l, double factor){
		Font labelFont = l.getFont();
		String labelText = l.getText();

		//Code Snippet from Stackoverflow user "coobird"
		int stringWidth = l.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = l.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = l.getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, (int) (componentHeight * factor));

		// Set the label's font size to the newly determined size.
		l.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	}

	public ContestantPanel(Game g){
		super();
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(null);
		this.width = (int) screenSize.getWidth() / 2;
		this.height = (int) screenSize.getHeight() / 2;
		this.game = g;
		this.setPreferredSize(new Dimension(width,height));
		this.setVisible(true);
		this.requestFocus();

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("Resized to " + e.getComponent().getSize());
				Dimension d = e.getComponent().getSize();
				width = (int) d.getWidth();
				height = (int) d.getHeight();
				renderPanel();
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				System.out.println("Moved to " + e.getComponent().getLocation());
			}
		});

		renderPanel();
	}

	public void setUpdater(Driver.Updater u){
		this.updater = u;
	}

	public void unreveal(){
		for(int i=0;i<revealed.length;i++){
			revealed[i] = false;
		}
	}

	public void reveal(int index){
		revealed[index] = true;
	}

	public void renderPanel(){
		this.removeAll();

		JLabel title = new JLabel("Faculty Feud",SwingConstants.CENTER);
		this.add(title);

		title.setBounds(0, 0, width, height / 8);
		setLabelFont(title, 0.8);

		//Question Board
		Question q = game.getCurrentQuestion();
//		for(int i=0;i<q.numResponses();i++){
//			System.out.println(q.getResponse(i).getResponse());
//		}

		JLabel question = new JLabel(q.getQuestionText(), SwingConstants.CENTER);
		this.add(question);
		question.setBounds(0,height / 8, width, height / 8);

		int h = height / 4;
		//Column 1
		for(int i=0;i<4;i++){
			ResponsePanel rp;
			if(i < q.numResponses()){
				rp = new ResponsePanel(game, q.getResponse(i), PanelMode.CONTESTANT, i, updater);
			}
			else{
				rp = new ResponsePanel(game, new Question.Response("",0), PanelMode.CONTESTANT, i, updater);
			}
			this.add(rp);
			rp.setBounds(0, h + i * height / 8, width / 2, height / 8);
			if(revealed[i]){
				rp.reveal();
			}
		}

		//Column 2
		for(int i=0;i<4;i++){
			ResponsePanel rp;
			if(i + 4< q.numResponses()){
				rp = new ResponsePanel(game, q.getResponse(i + 4), PanelMode.CONTESTANT, i + 4, updater);
			}
			else{
				rp = new ResponsePanel(game, new Question.Response("",0), PanelMode.CONTESTANT, i + 4, updater);
			}
			this.add(rp);
			rp.setBounds(width / 2, h + i * height / 8, width / 2, height / 8);
			if(revealed[i + 4]){
				rp.reveal();
			}
		}

		//Scoreboard
		JLabel team1 = new JLabel("Team 1", SwingConstants.CENTER);
		JLabel team2 = new JLabel("Team 2", SwingConstants.CENTER);
		JLabel team1score = new JLabel("12", SwingConstants.CENTER);
		JLabel team2score = new JLabel("15", SwingConstants.CENTER);
		JLabel currentscore = new JLabel("36", SwingConstants.CENTER);
		JLabel strikes = new JLabel("X", SwingConstants.CENTER);

		team1score.setText(String.valueOf(game.getScore(0)));
		team2score.setText(String.valueOf(game.getScore(1)));
		currentscore.setText(String.valueOf(game.currentScore()));
		String striketext = "";
		for(int i=0;i<game.getStrikes();i++){
			striketext += "X";
		}
		strikes.setText(striketext);

		this.add(team1);
		this.add(team2);
		this.add(team1score);
		this.add(team2score);
		this.add(currentscore);
		this.add(strikes);

		h = 3 * (height / 4);
		team1.setBounds(0, h, width / 3, height / 8);
		currentscore.setBounds(width / 3, h, width / 3, height / 8);
		team2.setBounds(2 * width / 3, h, width / 3, height / 8);
		h += height / 8;
		team1score.setBounds(0, h, width / 3, height / 8);
		strikes.setBounds(width / 3, h, width / 3, height / 8);
		team2score.setBounds(2 * width / 3, h, width / 3, height / 8);

		setLabelFont(team1,0.8);
		setLabelFont(team2,0.8);
		setLabelFont(team1score,0.75);
		setLabelFont(team2score,0.75);
		setLabelFont(currentscore,1.0);
		setLabelFont(strikes,0.75);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

	}
}
