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
	private boolean revealed = false;
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

	public ResponsePanel(Game game, Question.Response response, PanelMode mode, int index, Driver.Updater updater){
		this.setVisible(true);
		this.setLayout(null);
		this.response = response;
		this.mode = mode;

		if(mode == PanelMode.CONTESTANT){
			if(response.getResponse().equals("")){
				text = new JLabel("", SwingConstants.CENTER);
			}
			else{
				text = new JLabel(String.valueOf(index + 1), SwingConstants.CENTER);
			}
			score = new JLabel("", SwingConstants.CENTER);
		}
		else{
			text = new JButton(response.getResponse());
			((JButton) text).addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					if(stage == 0){
						game.revealResponse(index);
					}
					((JButton) text).setEnabled(false);
					JButton source = (JButton) e.getSource();
					source.setEnabled(false);
					updater.reveal(index);
					updater.update();
				}
			});
			if(response.getResponse().equals("")){
				score = new JLabel("", SwingConstants.CENTER);
			}
			else{
				score = new JLabel(String.valueOf(response.getScore()), SwingConstants.CENTER);
			}
		}

		this.add(text);
		this.add(score);

		text.setBounds(0,0,this.getWidth() - this.getHeight(),this.getHeight());
		score.setBounds(this.getWidth() - this.getHeight(), 0, this.getHeight(), this.getHeight());
	}

	public void reveal(){
		this.revealed = true;
		((JLabel) text).setText(response.getResponse());
		score.setText(String.valueOf(response.getScore()));
		setLabelFont((JLabel) text, 0.4);
	}

	public void setStage(int stage){
		this.stage = stage;
	}

	public void setResponse(Question.Response r){
		this.response = r;
		if(mode == PanelMode.CONTESTANT){
			((JLabel) text).setText(r.getResponse());
		}
		else{
			((JButton) text).setText(r.getResponse());
		}
		if(r.getResponse().equals("")){
			score.setText("");
		}
		else{
			score.setText(String.valueOf(r.getScore()));
		}
	}

	public void enableBtn(){
		((JButton) text).setEnabled(true);
	}

	@Override
	public void setBounds(int x, int y, int width, int height){
		super.setBounds(x, y, width, height);
		text.setBounds(0,0,this.getWidth() - this.getHeight(),this.getHeight());
		score.setBounds(this.getWidth() - this.getHeight(), 0, this.getHeight(), this.getHeight());

		if(mode == PanelMode.CONTESTANT){
			setLabelFont((JLabel) text,0.5);
		}
		setLabelFont(score,0.5);
	}
}
