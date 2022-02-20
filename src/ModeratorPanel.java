import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class ModeratorPanel extends JPanel{
	private int width;
	private int height;
	private Game game;

	private void setLabelFont(JLabel l){
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
		int fontSizeToUse = Math.min(newFontSize, componentHeight);

		// Set the label's font size to the newly determined size.
		l.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	}

	public ModeratorPanel(Game g){
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

	public void renderPanel(){
		this.removeAll();

		JLabel title = new JLabel("Faculty Feud",SwingConstants.CENTER);
		this.add(title);

		title.setBounds(0, 0, width, height / 8);
		setLabelFont(title);

		//Question Board
//		Question q = game.getCurrentQuestion();
		ArrayList<Question.Response> responses = new ArrayList<>();
		responses.add(new Question.Response("Response 1", 25));
		responses.add(new Question.Response("Response 2", 25));
		responses.add(new Question.Response("Response 3", 25));
		responses.add(new Question.Response("Response 4", 25));
		responses.add(new Question.Response("Response 5", 25));
		Question q = new Question("Question Test", responses);

		JLabel question = new JLabel(q.getQuestionText(), SwingConstants.CENTER);
		this.add(question);
		question.setBounds(0,height / 8, width, height / 8);

		int h = height / 4;
		//Column 1
		for(int i=0;i<4;i++){
			ResponseLabel l;
			if(i < q.numResponses()){
				l = new ResponseLabel(q.getResponse(i));
			}
			else{
				l = new ResponseLabel(new Question.Response("",0));
			}
			this.add(l);
			l.setBounds(0, h + i * height / 8, width / 2, height / 8);
		}

		//Column 2
		for(int i=0;i<4;i++){
			ResponseLabel l;
			if(i + 4< q.numResponses()){
				l = new ResponseLabel(q.getResponse(i + 4));
			}
			else{
				l = new ResponseLabel(new Question.Response("",0));
			}
			this.add(l);
			l.setBounds(width / 2, h + i * height / 8, width / 2, height / 8);
		}

		//Scoreboard
		JLabel team1 = new JLabel("Team 1", SwingConstants.CENTER);
		JLabel team2 = new JLabel("Team 2", SwingConstants.CENTER);
		JLabel team1score = new JLabel("12", SwingConstants.CENTER);
		JLabel team2score = new JLabel("15", SwingConstants.CENTER);
		JLabel currentscore = new JLabel("36", SwingConstants.CENTER);
		JLabel strikes = new JLabel("X", SwingConstants.CENTER);

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

		setLabelFont(team1);
		setLabelFont(team2);
		setLabelFont(team1score);
		setLabelFont(team2score);
		setLabelFont(currentscore);
		setLabelFont(strikes);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

	}
}
