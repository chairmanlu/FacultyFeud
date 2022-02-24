import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ContestantPanel extends JPanel{
	private int width;
	private int height;
	private Game game;
	private JLabel largeX = new JLabel("X", SwingConstants.CENTER);
	private JLabel title = new JLabel("Faculty Feud",SwingConstants.CENTER);
	private JLabel question;
	private ResponsePanel[] responseLbls;
	private JLabel team1;
	private JLabel team2;
	private JLabel team1score;
	private JLabel team2score;
	private JLabel currentscore;
	private JLabel strikes;

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

	private void setLabelFont(JLabel l, double factor, int fonttype){
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
		l.setFont(new Font(labelFont.getName(), fonttype, fontSizeToUse));
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

		this.responseLbls = new ResponsePanel[8];
		largeX.setVisible(false);
		largeX.setForeground(Driver.STRIKE_COLOR);

		this.add(largeX);
		this.setBackground(Driver.BG_COLOR);

		for(int i=0;i<8;i++){
			ResponsePanel rp = new ResponsePanel(game, i, PanelMode.CONTESTANT, null);
			responseLbls[i] = rp;
			rp.setBorder(BorderFactory.createLineBorder(Driver.TEXT_COLOR, 2));
			this.add(rp);
		}

		question = new JLabel("", SwingConstants.CENTER);
		team1 = new JLabel(game.getTeamName(0), SwingConstants.CENTER);
		team2 = new JLabel(game.getTeamName(1), SwingConstants.CENTER);
		team1score = new JLabel("", SwingConstants.CENTER);
		team2score = new JLabel("", SwingConstants.CENTER);
		currentscore = new JLabel("", SwingConstants.CENTER);
		strikes = new JLabel("", SwingConstants.CENTER);

		question.setForeground(Driver.TEXT_COLOR);
		currentscore.setForeground(Driver.TEXT_COLOR);
		team1.setForeground(Driver.TEXT_COLOR);
		team2.setForeground(Driver.TEXT_COLOR);
		team1score.setForeground(Driver.TEXT_COLOR);
		team2score.setForeground(Driver.TEXT_COLOR);
		title.setForeground(Driver.TITLE_COLOR);
		strikes.setForeground(Driver.STRIKE_COLOR);

		this.add(title);
		this.add(question);
		this.add(team1);
		this.add(team2);
		this.add(team1score);
		this.add(team2score);
		this.add(currentscore);
		this.add(strikes);
		
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
	}

	public void showStrike(){
		largeX.setVisible(true);
		ContestantPanel c = this;
		renderPanel();
		Timer timer = new Timer(1000 , new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				largeX.setVisible(false);
				c.renderPanel();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	public void renderPanel(){

		title.setBounds(0, 0, width, height / 8);
		setLabelFont(title, 0.8, Font.BOLD);

		//Question Board
		Question q = game.getCurrentQuestion();

		question.setText(q.getQuestionText());
		question.setBounds(0,height / 8, width, height / 8);
		setLabelFont(question, 0.5);
		question.setVisible(game.questionRevealed());

		int h = height / 4;
		//Column 1
		for(int i=0;i<4;i++){
			ResponsePanel rp = responseLbls[i];
			rp.setBounds(0, h + i * height / 8, width / 2, height / 8);
		}

		//Column 2
		for(int i=0;i<4;i++){
			ResponsePanel rp = responseLbls[i + 4];
			rp.setBounds(width / 2, h + i * height / 8, width / 2, height / 8);
		}

		//Scoreboard

		team1score.setText(String.valueOf(game.getScore(0)));
		team2score.setText(String.valueOf(game.getScore(1)));
		currentscore.setText(String.valueOf(game.currentScore()));
		String striketext = "";
		for(int i=0;i<game.getStrikes();i++){
			striketext += "X";
		}
		strikes.setText(striketext);

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

		largeX.setBounds(width / 4, height / 4, width / 2, height / 2);
		setLabelFont(largeX,1.0);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

	}
}
