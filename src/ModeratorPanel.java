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

	ModeratorPanel p;
	ContestantPanel c;

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

	public ModeratorPanel(Game g, ContestantPanel c){
		super();
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(null);
		this.width = (int) screenSize.getWidth() / 2;
		this.height = (int) screenSize.getHeight() / 2;
		this.game = g;
		this.c = c;
		this.p = this;
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
		setLabelFont(title, 0.8);

		//Question Board
		Question q = game.getCurrentQuestion();
		System.out.println(q);
		ArrayList<Question.Response> responses = new ArrayList<>();
//		responses.add(new Question.Response("Response 1", 25));
//		responses.add(new Question.Response("Response 2", 25));
//		responses.add(new Question.Response("Response 3", 25));
//		responses.add(new Question.Response("Response 4", 25));
//		responses.add(new Question.Response("Response 5", 25));
//		Question q = new Question("Question Test", responses);

		JLabel question = new JLabel(q.getQuestionText(), SwingConstants.CENTER);
		this.add(question);
		question.setBounds(0,height / 8, width, height / 8);

		int h = height / 4;
		//Column 1
		for(int i=0;i<4;i++){
			ResponsePanel rp;
			if(i < q.numResponses()){
				rp = new ResponsePanel(game, q.getResponse(i), PanelMode.MODERATOR, i);
			}
			else{
				rp = new ResponsePanel(game, new Question.Response("",0), PanelMode.MODERATOR, i);
			}
			this.add(rp);
			rp.setBounds(0, h + i * height / 8, width / 2, height / 8);
		}

		//Column 2
		for(int i=0;i<4;i++){
			ResponsePanel rp;
			if(i + 4< q.numResponses()){
				rp = new ResponsePanel(game, q.getResponse(i + 4), PanelMode.MODERATOR, i + 4);
			}
			else{
				rp = new ResponsePanel(game, new Question.Response("",0), PanelMode.MODERATOR, i + 4);
			}
			this.add(rp);
			rp.setBounds(width / 2, h + i * height / 8, width / 2, height / 8);
		}

		//Scoreboard
		JButton nextQuestion = new JButton("Next Question");
		nextQuestion.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.nextQuestion();
				p.renderPanel();
				c.renderPanel();
			}
		});

		JButton addStrike = new JButton("Add Strike");
		JButton assignScore1 = new JButton("Score for Team 1");
		JButton assignScore2 = new JButton("Score for Team 2");
		JLabel strikes = new JLabel("X", SwingConstants.CENTER);

		addStrike.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.addStrike();
				p.renderPanel();
				c.renderPanel();
			}
		});

		assignScore1.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.assignScore(0);
				p.renderPanel();
				c.renderPanel();
			}
		});

		assignScore2.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.assignScore(1);
				p.renderPanel();
				c.renderPanel();
			}
		});

		String striketext = "";
		for(int i=0;i<game.getStrikes();i++){
			striketext += "X";
		}
		strikes.setText(striketext);

		this.add(nextQuestion);
		this.add(addStrike);
		this.add(assignScore1);
		this.add(assignScore2);
		this.add(strikes);

		h = 3 * (height / 4);
		nextQuestion.setBounds(0, h, width / 3, height / 8);
		addStrike.setBounds(width / 3, h, width / 3, height / 8);
		h += height / 8;
		assignScore1.setBounds(0, h, width / 3, height / 8);
		strikes.setBounds(width / 3, h, width / 3, height / 8);
		assignScore2.setBounds(2 * width / 3, h, width / 3, height / 8);

		setLabelFont(strikes,0.75);
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);

	}
}
