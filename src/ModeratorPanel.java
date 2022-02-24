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
	private Updater updater;
	private ResponsePanel[] responseBtns;
	private static final JLabel title = new JLabel("Faculty Feud",SwingConstants.CENTER);
	private static JLabel question = new JLabel("",SwingConstants.CENTER);
	private static JLabel strikes = new JLabel("",SwingConstants.CENTER);
	private static JButton nextQuestion, addStrike, assignScore1, assignScore2, revealQuestion;
	private int stage = 0;

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

		public void showStrike(){
			c.showStrike();
		}
	}

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
		this.updater = new Updater(c, this);;
		this.responseBtns = new ResponsePanel[8];
		this.add(title);
		this.add(question);
		this.setPreferredSize(new Dimension(width,height));
		this.setVisible(true);
		this.requestFocus();

		for(int i=0;i<8;i++){
			ResponsePanel rp = new ResponsePanel(game, i, PanelMode.MODERATOR, updater);
			responseBtns[i] = rp;
			this.add(rp);
		}

		nextQuestion = new JButton("Next Question");
		nextQuestion.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.nextQuestion();
				for(int i=0;i<responseBtns.length;i++){
					addStrike.setEnabled(true);
				}
				updater.update();
			}
		});

		revealQuestion = new JButton("Reveal Question");
		revealQuestion.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.revealQuestion();
				updater.update();
			}
		});

		addStrike = new JButton("Strike");
		addStrike.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.addStrike();
				updater.update();
				updater.showStrike();
			}
		});

		assignScore1 = new JButton("Score for Team 1");
		assignScore1.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.assignScore(0);
				updater.update();
			}
		});

		assignScore2 = new JButton("Score for Team 2");
		assignScore2.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.assignScore(1);
				updater.update();
			}
		});

		this.add(nextQuestion);
		this.add(revealQuestion);
		this.add(addStrike);
		this.add(assignScore1);
		this.add(assignScore2);
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

	public void renderPanel(){

		title.setBounds(0, 0, width, height / 8);
		setLabelFont(title, 0.8);

		//Question Board
		Question q = game.getCurrentQuestion();

		question.setText(q.getQuestionText());
		question.setBounds(0,height / 8, width, height / 8); 
		setLabelFont(question, 0.5);

		revealQuestion.setEnabled(!game.questionRevealed());

		int h = height / 4;
		//Column 1
		for(int i=0;i<4;i++){
			ResponsePanel rp = responseBtns[i];
			rp.setBounds(0, h + i * height / 8, width / 2, height / 8);
		}

		//Column 2
		for(int i=0;i<4;i++){
			ResponsePanel rp = responseBtns[i + 4];
			rp.setBounds(width / 2, h + i * height / 8, width / 2, height / 8);
		}

		//Scoreboard

		String striketext = "";
		for(int i=0;i<game.getStrikes();i++){
			striketext += "X";
		}
		strikes.setText(striketext);

		h = 3 * (height / 4);
		nextQuestion.setBounds(0, h, width / 3, height / 8);
		revealQuestion.setBounds(2 * width / 3, h, width / 3, height / 8);
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
