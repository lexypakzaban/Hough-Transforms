import java.awt.GridLayout;

import javax.swing.JFrame;

public class HoughFrame extends JFrame
{
	public HoughFrame()
	{
		super("Hough");
		setSize(1000,800);
		setResizable(true);
		getContentPane().setLayout(new GridLayout(1,1));
		getContentPane().add(new HoughPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
