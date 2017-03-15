package GUI;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Components.Colors;
import Components.CompBuilder;

public class MainWindow
{
	//Components
	private JFrame frame;
	private JComboBox comboType;
	private JTextField txtCellQ;
	private JTextField txtMutation;
	private JTextField txtDistance;
	private JTextField txtGenerations;
	private JTextField txtIterations;
	private JTextField txtThreads;
	private JTextField txtDelay;
	private JCheckBox boxTaurus;
	//Labels
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	//Instances
	private CompBuilder build;
	private Colors c = new Colors();

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
		initialize();
	}

	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(c.getDarkBlue());
		frame.setLayout(null);
		
		//Components
		comboType = new JComboBox();
		comboType.addItem("Hardcoded");
		comboType.addItem("Tit for tat");
		comboType.addItem("Neural network");
		comboType.addItem("Mixed");
		build.buildComboField(comboType);
		frame.getContentPane().add(comboType);
		
		txtCellQ = new JTextField();
		build.buildTxtForm(txtCellQ);
		frame.getContentPane().add(txtCellQ);
		
		txtMutation = new JTextField();
		build.buildTxtForm(txtMutation);
		frame.getContentPane().add(txtMutation);
		
		txtDistance = new JTextField();
		build.buildTxtForm(txtDistance);
		frame.getContentPane().add(txtDistance);
		
		txtGenerations = new JTextField();
		build.buildTxtForm(txtGenerations);
		frame.getContentPane().add(txtGenerations);
		
		txtIterations = new JTextField();
		build.buildTxtForm(txtIterations);
		frame.getContentPane().add(txtIterations);
		
		txtThreads = new JTextField();
		build.buildTxtForm(txtThreads);
		frame.getContentPane().add(txtThreads);
		
		txtDelay = new JTextField();
		build.buildTxtForm(txtDelay);
		frame.getContentPane().add(txtDelay);
		
		boxTaurus = new JCheckBox();
		build.buildComboField(boxTaurus, true);
		frame.getContentPane().add(boxTaurus);
		
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
		
		for(JLabel lbl : labels)
		{
			build.buildLabel(lbl, frame);
		}
		
		place();
	}
	
	private void place()
	{
		int left = 180;
		int pos = 10;
		int skip = 50;
		int width = 220;
		for(JLabel lbl : labels)
		{
			lbl.setBounds(20, pos, 160, 40);
			pos += skip;
		}
		
		pos = 10;
		
		comboType.setBounds(left, pos, width, 40);
		pos += skip;
		txtCellQ.setBounds(left, pos, width, 40);
		pos += skip;
		txtMutation.setBounds(left, pos, width, 40);
		pos += skip;
		txtDistance.setBounds(left, pos, width, 40);
		pos += skip;
		txtGenerations.setBounds(left, pos, width, 40);
		pos += skip;
		txtIterations.setBounds(left, pos, width, 40);
		pos += skip;
		txtThreads.setBounds(left, pos, width, 40);
		pos += skip;
		txtDelay.setBounds(left, pos, width, 40);
		pos += skip;
		boxTaurus.setBounds(left,  pos,  200,  40);
	}

}
