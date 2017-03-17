package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Components.Colors;
import Components.CompBuilder;
import Components.FileChooser;
import FileIO.LoadDefaults;
import MultiThreading.SplitTask;

public class MainWindow
{
	//Components
	private JFrame frame;
	private JButton btnStart;
	//Global Settings
	private JComboBox comboType;
	private JTextField txtCellQ;
	private JTextField txtMutation;
	private JTextField txtDistance;
	private JTextField txtGenerations;
	private JTextField txtIterations;
	private JTextField txtThreads;
	private JTextField txtDelay;
	private JCheckBox boxTaurus;
	//Menu
	private JMenuItem mntmSettings;
	private JMenuItem mntmSaveSettings;
	private JMenuItem mntmLoadSettings;
	//Draw Settings
	private JPanel separator1;
	private JTextField txtDrawDelay;
	private JTextField txtDrawScale;
	//Serialization Settings
	private JPanel separator2;
	private JCheckBox boxSerialize;
	private JTextField txtSerializeDelay;
	private JTextField txtSerializePath;
	//Labels
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	//Instances
	private CompBuilder build;
	private Colors c = new Colors();
	private ArrayList<SimulationWindow> simulations = new ArrayList<SimulationWindow>();
	private LoadDefaults load = new LoadDefaults();
	private SettingsWindow settingsWindow;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow()
	{
		build = new CompBuilder();
		settingsWindow = new SettingsWindow();
		initialize();
	}

	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 980);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(c.getDarkBlue());
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setTitle("Launcher");
		
		//MENU
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(c.getDarkBlue().brighter().brighter());
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Verdana", Font.PLAIN, 20));
		mnFile.setForeground(Color.white);
		menuBar.add(mnFile);
		
		mntmLoadSettings = new JMenuItem("Load Settings");
		mntmLoadSettings.setFont(new Font("Verdana", Font.PLAIN, 20));
		mnFile.add(mntmLoadSettings);
		
		mntmSaveSettings = new JMenuItem("Save Settings");
		mntmSaveSettings.setFont(new Font("Verdana", Font.PLAIN, 20));
		mnFile.add(mntmSaveSettings);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setFont(new Font("Verdana", Font.PLAIN, 20));
		mnEdit.setForeground(Color.white);
		menuBar.add(mnEdit);
		
		mntmSettings = new JMenuItem("Settings");
		mntmSettings.setFont(new Font("Verdana", Font.PLAIN, 20));
		mnEdit.add(mntmSettings);
		
		//Components
		comboType = new JComboBox();
		comboType.addItem("Hardcoded");
		comboType.addItem("Tit for tat");
		comboType.addItem("Neural network");
		comboType.addItem("Mixed");
		build.buildComboField(comboType, frame);
		
		txtCellQ = new JTextField();
		build.buildTxtForm(txtCellQ, frame);
		
		txtMutation = new JTextField();
		build.buildTxtForm(txtMutation, frame);
		
		txtDistance = new JTextField();
		build.buildTxtForm(txtDistance, frame);
		
		txtGenerations = new JTextField();
		build.buildTxtForm(txtGenerations, frame);
		
		txtIterations = new JTextField();
		build.buildTxtForm(txtIterations, frame);
		
		txtThreads = new JTextField();
		build.buildTxtForm(txtThreads, frame);
		
		txtDelay = new JTextField();
		build.buildTxtForm(txtDelay, frame);
		
		boxTaurus = new JCheckBox();
		build.buildCheckBox(boxTaurus, true, frame);
		
		separator1 = new JPanel();
		build.buildSeparator(separator1, frame);
		
		txtDrawDelay = new JTextField();
		build.buildTxtForm(txtDrawDelay, frame);
		
		txtDrawScale = new JTextField();
		build.buildTxtForm(txtDrawScale, frame);
		
		separator2 = new JPanel();
		build.buildSeparator(separator2, frame);
		
		boxSerialize = new JCheckBox();
		build.buildCheckBox(boxSerialize, false, frame);
		
		txtSerializePath = new JTextField();
		build.buildTxtForm(txtSerializePath, frame);
		
		txtSerializeDelay = new JTextField();
		build.buildTxtForm(txtSerializeDelay, frame);
		
		//Labels
		labels.add(new JLabel("Cell Type"));
		labels.add(new JLabel("Cell Quantity"));
		labels.add(new JLabel("Mutation"));
		labels.add(new JLabel("Distance"));
		labels.add(new JLabel("Generations"));
		labels.add(new JLabel("Iterations"));
		labels.add(new JLabel("Threads"));
		labels.add(new JLabel("Delay"));
		labels.add(new JLabel("Taurus"));
		labels.add(new JLabel("Draw Delay"));
		labels.add(new JLabel("Scale"));
		labels.add(new JLabel("Save"));
		labels.add(new JLabel("Save Delay"));
		labels.add(new JLabel("Save Location"));
			//Special labels
		labels.add(new JLabel("Serialization Settings"));
		labels.add(new JLabel("Draw Settings"));
		labels.add(new JLabel("Global Settings"));

		labels.get(0).setToolTipText("Defines IPD logic for cell.");
		labels.get(1).setToolTipText("Square root  of the quantity of cells in the simulation.");
		labels.get(2).setToolTipText("Mutation quantity, value will be multiplied by 0,001.");
		labels.get(3).setToolTipText("Distance over which cells are considered neighboors.");
		labels.get(4).setToolTipText("Amount of generations to run the simulation for.");
		labels.get(5).setToolTipText("Iterations per generation.");
		labels.get(6).setToolTipText("Amount of threads the simulation will use.");
		labels.get(7).setToolTipText("Delay between each generation. May allow clearer visuals.");
		labels.get(8).setToolTipText("Activate or deactivate taurus logic.");
		labels.get(9).setToolTipText("Draw the lattice state every X generations.");
		labels.get(10).setToolTipText("Amplify lattice by this value when drawing.\nEffectively makes simulation window equal to scale * cell quantity.");
		labels.get(11).setToolTipText("Activate or deactivatethe save functionality. This will output serliazed data.");
		labels.get(12).setToolTipText("Save every X generations.");
		labels.get(13).setToolTipText("Save file location.");
		
		for(JLabel lbl : labels)
		{
			build.buildLabel(lbl, frame);
		}
		
		btnStart = new JButton();
		btnStart.setText("Start Simulation");
		build.buildButton(btnStart, frame);
		
		place();
		placeHolders();
		actionListeners();
	}
	
	private void actionListeners()
	{
		boxSerialize.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				FileChooser fc = new FileChooser();
				txtSerializePath.setText(fc.getFolder("").getPath());
			}
		});
		
		btnStart.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String[] settings = new String[14];
				settings[0] = comboType.getSelectedIndex()+"";
				settings[1] = txtCellQ.getText();
				settings[2] = txtMutation.getText();
				settings[3] = txtDistance.getText();
				settings[4] = txtGenerations.getText();
				settings[5] = txtIterations.getText();
				settings[6] = txtThreads.getText();
				settings[7] = txtDelay.getText();
				settings[8] = boxTaurus.isSelected()+"";
				settings[9] = txtDrawDelay.getText();
				settings[10] = txtDrawScale.getText();
				settings[11] = boxSerialize.isSelected()+"";
				settings[12] = txtSerializeDelay.getText();
				settings[13] = txtSerializePath.getText();
				
				simulations.add(new SimulationWindow(settings, new Point(frame.getX()+frame.getWidth(), frame.getY())));
			}
		});
		
		//Menu
		mntmSettings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				settingsWindow.setVisible(true, new Point(frame.getX()+frame.getWidth(), frame.getY()));
			}
		});
	}
	
	private void placeHolders()
	{
		comboType.setSelectedIndex(0);
		txtCellQ.setText("500");
		txtMutation.setText("0.01");
		txtDistance.setText("1");
		txtGenerations.setText("0");
		txtIterations.setText("1");
		txtThreads.setText((Runtime.getRuntime().availableProcessors()-1)+"");
		txtDelay.setText("10");
		txtDrawDelay.setText("2");
		txtDrawScale.setText("3");
		txtSerializeDelay.setText("10");
		txtSerializePath.setText("Not saving");
	}
	
	private void place()
	{
		int left = 200;
		int pos = 60;
		int skip = 50;
		int width = 220;
		int height = 40;
		int verify = 0;
		for(JLabel lbl : labels)
		{
			lbl.setBounds(20, pos, 160, 40);
			pos += skip;
			verify++;
			if(verify == 9 || verify == 11){pos += skip;}
		}
		
		pos = 10;
		labels.get(labels.size()-1).setBounds(130, pos-5, 200, height);
		pos += skip;
		comboType.setBounds(left, pos, width, height);
		pos += skip;
		txtCellQ.setBounds(left, pos, width, height);
		pos += skip;
		txtMutation.setBounds(left, pos, width, height);
		pos += skip;
		txtDistance.setBounds(left, pos, width, height);
		pos += skip;
		txtGenerations.setBounds(left, pos, width, height);
		pos += skip;
		txtIterations.setBounds(left, pos, width, height);
		pos += skip;
		txtThreads.setBounds(left, pos, width, height);
		pos += skip;
		txtDelay.setBounds(left, pos, width, height);
		pos += skip;
		boxTaurus.setBounds(left,  pos,  200,  height);
		pos += skip;
		separator1.setBounds(30, pos, 370, 3);
		labels.get(labels.size()-2).setBounds(130, pos, 200, height);
		pos += skip;
		txtDrawDelay.setBounds(left,  pos,  width,  height);
		pos += skip;
		txtDrawScale.setBounds(left, pos, width, height);
		pos += skip;
		separator2.setBounds(30, pos, 370, 3);
		labels.get(labels.size()-3).setBounds(90, pos, 240, height);
		pos += skip;
		boxSerialize.setBounds(left, pos, width, height);
		pos += skip;
		txtSerializeDelay.setBounds(left, pos, width, height);
		pos += skip;
		txtSerializePath.setBounds(left, pos, width, height);
		btnStart.setBounds(-2, pos+55, 450, height);
	}
}
