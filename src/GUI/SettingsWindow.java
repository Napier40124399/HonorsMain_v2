package GUI;

import java.awt.Point;

import javax.swing.JFrame;

import Components.Colors;

public class SettingsWindow
{

	private JFrame frame;
	//Interface variables
	private int frameWidth = 400;
	private int frameHeight = 250;
	//Instances
	private Colors c = new Colors();
	
	public SettingsWindow()
	{
		initialize();
	}

	private void initialize()
	{
		frame = new JFrame();
		frame.setTitle("Settings");
		frame.setResizable(false);
		frame.getContentPane().setBackground(c.getDarkBlue());
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
	}
	
	public void setVisible(Boolean visibility, Point p)
	{
		frame.setVisible(visibility);
		frame.setBounds(p.x, p.y, frameWidth, frameHeight);
	}
}
