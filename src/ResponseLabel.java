import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ResponseLabel extends JLabel{
	private Question.Response response;

	public ResponseLabel(Question.Response response){
		super(response.getResponse(),SwingConstants.CENTER);
		this.setVisible(true);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.response = response;
//		this.addMouseListener(new MouseAdapter(){
//			public void mouseReleased(MouseEvent m){		
//				selected=!selected;
//				if(selected){
//					t.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.RED));
//				}
//				else{
//					t.setBorder(BorderFactory.createRaisedBevelBorder());
//				}
//			}
//
//			public void mouseEntered(MouseEvent m){
//				t.requestFocus();
//			}
//		});
	}
}
