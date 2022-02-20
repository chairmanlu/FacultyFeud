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
	private Driver.Updater updater;
	private ResponsePanel[] responseBtns;
	private static final JLabel title = new JLabel("Faculty Feud",SwingConstants.CENTER);
	private static JLabel question = new JLabel("",SwingConstants.CENTER);
	private static JLabel strikes = new JLabel("",SwingConstants.CENTER);
	private static JButton nextQuestion, addStrike, assignScore1, assignScore2, revealQuestion;
	private int stage = 0;

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

	public ModeratorPanel(Game g){
		super();
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(null);
		this.width = (int) screenSize.getWidth() / 2;
		this.height = (int) screenSize.getHeight() / 2;
		this.game = g;
		this.responseBtns = new ResponsePanel[8];
		this.add(title);
		this.add(question);
		this.setPreferredSize(new Dimension(width,height));
		this.setVisible(true);
		this.requestFocus();

		nextQuestion = new JButton("Next Question");
		nextQuestion.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				game.nextQuestion();
				for(int i=0;i<responseBtns.length;i++){
					responseBtns[i].enableBtn();
					addStrike.setEnabled(true);
				}
				stage = 0;
				updater.unreveal();
				updater.hideQuestion();
				updater.update();
			}
		});

		revealQuestion = new JButton("Reveal Question");
		revealQuestion.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				updater.revealQuestion();
				updater.update();
			}
		});

		addStrike = new JButton("Add Strike");
		addStrike.addActionListener(new ActionListener(){
			//@Override
			public void actionPerformed(ActionEvent e){
				if(stage == 0){
					if(game.addStrike()){
						JButton source = (JButton) e.getSource();
//						source.setEnabled(false);
						stage = 1;
						source.setText("Steal Failed");
					}
				}
				else if(stage == 1){
					addStrike.setEnabled(false);
					addStrike.setText("Add Strike");
					stage = 2;
				}
				updater.strike();
				updater.update();
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

	public void setUpdater(Driver.Updater u){
		this.updater = u;
		for(int i=0;i<8;i++){
			ResponsePanel rp = new ResponsePanel(game, new Question.Response("",0), PanelMode.MODERATOR, i, updater);
			responseBtns[i] = rp;
			this.add(rp);
		}
		renderPanel();
	}

	public void renderPanel(){
//		this.removeAll();

		title.setBounds(0, 0, width, height / 8);
		setLabelFont(title, 0.8);

		//Question Board
		Question q = game.getCurrentQuestion();

//		question = new JLabel(q.getQuestionText(), SwingConstants.CENTER);
		question.setText(q.getQuestionText());
		question.setBounds(0,height / 8, width, height / 8); 
		setLabelFont(question, 0.5);
		int h = height / 4;
		//Column 1
		for(int i=0;i<4;i++){
			ResponsePanel rp = responseBtns[i];
			if(i < q.numResponses()){
				rp.setResponse(q.getResponse(i));
				rp.setStage(stage);
			}
			else{
				rp.setResponse(new Question.Response("",0));
				rp.setStage(stage);
			}
			rp.setBounds(0, h + i * height / 8, width / 2, height / 8);
		}

		//Column 2
		for(int i=0;i<4;i++){
			ResponsePanel rp = responseBtns[i + 4];
			if(i + 4 < q.numResponses()){
				rp.setResponse(q.getResponse(i + 4));
				rp.setStage(stage);
			}
			else{
				rp.setResponse(new Question.Response("",0));
				rp.setStage(stage);
			}
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
