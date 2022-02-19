import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

@SuppressWarnings("serial")
public class ContestantPanel extends JPanel{
	public ContestantPanel(){
		super();
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLayout(null);
		this.setPreferredSize(new Dimension((int) (screenSize.getWidth())
		                     ,(int) (screenSize.getHeight())));
//		this.setPreferredSize(new Dimension(720,480));
		this.setVisible(true);
		this.requestFocus();
	}
}
