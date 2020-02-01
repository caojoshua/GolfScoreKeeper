//frame of the main panel

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

	//create the frame
	public MainFrame(){
		setTitle("Golf Scores");
		setSize(800, 250);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerFrame(this);
		
		MainPanel panel = new MainPanel();
		this.add(panel);
	}
	
	//used to center the window
	public void centerFrame(Window w){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		setLocation( (d.width-w.getWidth())/2, (d.height-w.getHeight())/2);
	}
}