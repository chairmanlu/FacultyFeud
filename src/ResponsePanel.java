import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ResponsePanel extends JPanel{
	private Question.Response response;
	private JComponent text;
	private JLabel score;
	private PanelMode mode;
	private int index;
	private ModeratorPanel.Updater updater;
	private Game game;

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

	public ResponsePanel(Game game, int index, PanelMode mode, ModeratorPanel.Updater updater){
		this.setVisible(true);
		this.setLayout(null);
		this.game = game;
		this.mode = mode;
		this.index = index;
		this.updater = updater;

		score = new JLabel("", SwingConstants.CENTER);
		if(mode == PanelMode.CONTESTANT){
			text = new JLabel("", SwingConstants.CENTER);
			this.setBackground(Driver.BG_COLOR);
			text.setBackground(Driver.BG_COLOR);
			score.setBackground(Driver.BG_COLOR);
			text.setForeground(Driver.TEXT_COLOR);
			score.setForeground(Driver.TEXT_COLOR);
		}
		else{
			text = new JButton("");
			((JButton) text).addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					game.revealResponse(index);
					updater.update();

					Driver.playSound("ding.wav");
				}
			});
		}

		this.add(text);
		this.add(score);
	}

	@Override
	public void setBounds(int x, int y, int width, int height){
		super.setBounds(x, y, width, height);
		text.setBounds(0,0,this.getWidth() - this.getHeight(),this.getHeight());
		score.setBounds(this.getWidth() - this.getHeight(), 0, this.getHeight(), this.getHeight());

		setLabelFont(score,0.5);

		Question q = game.getCurrentQuestion();

		if(mode == PanelMode.CONTESTANT){
			JLabel textLbl = (JLabel) text;
			setLabelFont(textLbl, 0.5);

			if(index < q.numResponses()){
				Question.Response response = q.getResponse(index);
				if(game.isRevealed(index)){
					textLbl.setText(response.getResponse());
					score.setText(String.valueOf(response.getScore()));

					text.setBorder(BorderFactory.createLineBorder(Driver.TEXT_COLOR, 1));
					score.setBorder(BorderFactory.createLineBorder(Driver.TEXT_COLOR, 1));
				}
				else{
					textLbl.setText(String.valueOf(index + 1));
					score.setText("");
				}
			}
			else{
				textLbl.setText("");
				score.setText("");
			}
		}
		else{
			JButton textBtn = (JButton) text;

			if(index < q.numResponses()){
				Question.Response response = q.getResponse(index);

				textBtn.setText(response.getResponse());
				score.setText(String.valueOf(response.getScore()));
				textBtn.setEnabled(!game.isRevealed(index));
			}
			else{
				textBtn.setText("");
				score.setText("");
				textBtn.setEnabled(false);
			}
		}
	}
}
