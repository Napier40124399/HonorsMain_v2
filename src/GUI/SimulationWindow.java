package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SimulationWindow
{

	private JFrame frame;

	public SimulationWindow()
	{
		initialize();
	}


	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
