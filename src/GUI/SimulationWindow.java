package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Components.Colors;

public class SimulationWindow
{
	private JFrame frame;
	private JPanel simPanel;
	private JPanel compPanel;
	private JButton btnPanel;
	
	//Components
	//PD vars
	private JTextField txtT;
	private JTextField txtR;
	private JTextField txtP;
	private JTextField txtS;
	private JCheckBox boxDynamicTopology;
	private JTextField txtMaxNodes;
	//Sim vars
	private JTextField txtItPerGen;
	private JTextField txtMutation;
	//Visual vars
	private JComboBox comboColorMode;
	private JTextField txtDrawDelay;
	private JTextField txtDrawScale;
	//Optimizations
	private JTextField txtDelay;
	private JTextField txtSerDelay;
	private JTextField txtThreads;
	
	//Component Variables
	private boolean settingsShowing = true;
	
	//Settings
	private int cellType;
	private int cellQ;
	private int drawScale;
	private int size;
	//Instances
	private Colors c = new Colors();

	public SimulationWindow(String[] settings)
	{
		cellType = Integer.parseInt(settings[0]);
		cellQ = Integer.parseInt(settings[1]);
		drawScale = Integer.parseInt(settings[10]);
		size = cellQ*drawScale;
		initialize();
	}


	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, size+24, size+100);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setLayout(null);
		frame.getContentPane().setBackground(c.getDarkBlue());
		
		compPanel = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				g.setColor(new Color(255,255,255,160));
				g.fillRect(0, 0, compPanel.getWidth(), compPanel.getHeight());
			}
		};
		compPanel.setBackground(Color.yellow);
		compPanel.setBounds(0,size-200, size, 250);
		frame.getContentPane().add(compPanel);
		
		simPanel = new JPanel();
		simPanel.setBounds(0,0,size, size);
		simPanel.setBackground(Color.black);
		simPanel.setOpaque(false);
		frame.getContentPane().add(simPanel);
		
		buildSettingsPanel();
		addActionListeners();
	}
	
	private void addActionListeners()
	{
		compPanel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				if(settingsShowing)
				{
					compPanel.setBounds(0, size, size, 250);
					settingsShowing = false;
				}else
				{
					compPanel.setBounds(0,size-200, size, 250);
					settingsShowing = true;
				}
			}
		});
	}
	
	private void buildSettingsPanel()
	{
		pdVarsPanel();
		visualsPanel();
	}
	
	private void pdVarsPanel()
	{
		
	}
	
	private void visualsPanel()
	{
		
	}
}
