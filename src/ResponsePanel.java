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

	public ResponsePanel(Game game, Question.Response response, PanelMode mode, int index){
		this.setVisible(true);
		this.setLayout(null);
		this.response = response;
		this.mode = mode;

		if(mode == PanelMode.CONTESTANT){
			text = new JLabel(response.getResponse(), SwingConstants.CENTER);
		}
		else{
			text = new JButton(response.getResponse());
			((JButton) text).addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					game.revealResponse(index);
				}
			});
		}
		if(response.getResponse().equals("")){
			score = new JLabel("");
		}
		else{
			score = new JLabel(String.valueOf(response.getScore()), SwingConstants.CENTER);
		}

		this.add(text);
		this.add(score);

		text.setBounds(0,0,this.getWidth() - this.getHeight(),this.getHeight());
		score.setBounds(this.getWidth() - this.getHeight(), 0, this.getHeight(), this.getHeight());
	}

	@Override
	public void setBounds(int x, int y, int width, int height){
		super.setBounds(x, y, width, height);
		text.setBounds(0,0,this.getWidth() - this.getHeight(),this.getHeight());
		score.setBounds(this.getWidth() - this.getHeight(), 0, this.getHeight(), this.getHeight());

//		setLabelFont(text,0.5);
		setLabelFont(score,0.5);
	}
}
