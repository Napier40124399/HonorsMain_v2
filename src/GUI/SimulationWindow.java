package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import Components.Colors;
import Components.CompBuilder;

public class SimulationWindow
{
	private JFrame frame;
	private JPanel simPanel;
	private JPanel compPanel;
	private JButton btnPanel;

	// Components
	private JButton btnUpdate;
	private JButton btnplay;
	private JButton btnPause;
	private JButton btnStop;
	private JLabel lblGenCount;
	// PD vars
	private JTextField txtT;
	private JTextField txtR;
	private JTextField txtP;
	private JTextField txtS;
	private JCheckBox boxDynamicTopology;
	private JTextField txtMaxNodes;
	// Sim vars
	private JTextField txtItPerGen;
	private JTextField txtMutation;
	// Visual vars
	private JComboBox comboColorMode;
	private JTextField txtDrawDelay;
	private JTextField txtDrawScale;
	// Optimizations
	private JTextField txtDelay;
	private JTextField txtSerDelay;
	private JTextField txtThreads;

	// Component Variables
	private boolean settingsShowing = true;
	private int panelSize = 440;
	private int panelWidth = 390;
	private int panelPos;

	// Settings
	private int cellType;
	private int cellQ;
	private int drawScale;
	private int size;
	// Instances
	private Colors c = new Colors();
	private CompBuilder builder = new CompBuilder();

	public SimulationWindow(String[] settings)
	{
		cellType = Integer.parseInt(settings[0]);
		cellQ = Integer.parseInt(settings[1]);
		drawScale = Integer.parseInt(settings[10]);
		size = cellQ * drawScale;

		panelPos = (size - panelWidth) / 2;
		initialize();
	}

	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, size + 6, size + 46);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.red);

		compPanel = new JPanel();
		compPanel.setBackground(c.getDarkBlue());
		compPanel.setBounds(panelPos, size - panelSize, panelWidth, panelSize);
		compPanel.setLayout(null);
		frame.getContentPane().add(compPanel);

		simPanel = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				int R = (int) (Math.random() * 255);
				int G = (int) (Math.random() * 255);
				int B = (int) (Math.random() * 255);
				g.setColor(new Color(R, G, B));
				g.fillRect(0, 0, size, size);
				compPanel.setBounds(compPanel.getX(), compPanel.getY(), compPanel.getWidth(),
						compPanel.getHeight() + 1);
				compPanel.setBounds(compPanel.getX(), compPanel.getY(), compPanel.getWidth(),
						compPanel.getHeight() - 1);

			}
		};
		simPanel.setBounds(0, 0, size, size);
		frame.getContentPane().add(simPanel);

		buildSettingsPanel();
		addActionListeners();

		Timer timer = new Timer(1000, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				simPanel.repaint();
			}
		});
		timer.start();
	}

	private void addActionListeners()
	{
		compPanel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				if (settingsShowing)
				{
					compPanel.setBounds(panelPos, size - 50, panelWidth, panelSize);
					settingsShowing = false;
					compPanel.repaint();
				} else
				{
					compPanel.setBounds(panelPos, size - panelSize, panelWidth, panelSize);
					settingsShowing = true;
					compPanel.repaint();
				}
			}
		});
	}

	private void buildSettingsPanel()
	{
		btnUpdate = new JButton();
		builder.buildButton(btnUpdate);
		compPanel.add(btnUpdate);
		btnUpdate.setBackground(c.getOrange());
		btnUpdate.setText("Update");
		btnUpdate.setBounds(0, compPanel.getHeight()-30, panelWidth, 32);
		
		btnplay = new JButton();
		btnplay.setBorder(BorderFactory.createEmptyBorder());
		btnplay.setContentAreaFilled(false);
		compPanel.add(btnplay);
		btnplay.setIcon(new ImageIcon("res\\play.png"));
		btnplay.setRolloverIcon(new ImageIcon("res\\playHover.png"));
		btnplay.setBounds(20, 5, 40, 40);
		
		btnPause = new JButton();
		btnPause.setBorder(BorderFactory.createEmptyBorder());
		btnPause.setContentAreaFilled(false);
		compPanel.add(btnPause);
		btnPause.setIcon(new ImageIcon("res\\pause.png"));
		btnPause.setRolloverIcon(new ImageIcon("res\\pauseHover.png"));
		btnPause.setBounds(70, 5, 40, 40);
		
		btnStop = new JButton();
		btnStop.setBorder(BorderFactory.createEmptyBorder());
		btnStop.setContentAreaFilled(false);
		compPanel.add(btnStop);
		btnStop.setIcon(new ImageIcon("res\\stop.png"));
		btnStop.setRolloverIcon(new ImageIcon("res\\stopHover.png"));
		btnStop.setBounds(120, 5, 40, 40);
		
		lblGenCount = new JLabel();
		lblGenCount.setText("GEN 0");
		builder.buildLabel(lblGenCount);
		compPanel.add(lblGenCount);
		lblGenCount.setBounds(170, 0, 80, 40);
		
		pdVarsPanel();
		simVarsPanel();
		visualsPanel();
		optimizePanel();
	}

	private void pdVarsPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(compPanel.getBackground());
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.white));
		compPanel.add(panel);
		panel.setBounds(10, 50, 190, 110);

		JLabel lblTitle = new JLabel();
		lblTitle.setText("PD Vars");
		builder.buildLabel(lblTitle);
		panel.add(lblTitle);
		lblTitle.setBounds(50, 0, 100, 40);

		if (cellType != 2)
		{
			JLabel lblT = new JLabel();
			lblT.setText("T");
			builder.buildLabel(lblT);
			panel.add(lblT);

			JLabel lblR = new JLabel();
			lblR.setText("R");
			builder.buildLabel(lblR);
			panel.add(lblR);

			JLabel lblP = new JLabel();
			lblP.setText("P");
			builder.buildLabel(lblP);
			panel.add(lblP);

			JLabel lblS = new JLabel();
			lblS.setText("S");
			builder.buildLabel(lblS);
			panel.add(lblS);

			txtT = new JTextField();
			builder.buildTxtForm(txtT);
			panel.add(txtT);

			txtR = new JTextField();
			builder.buildTxtForm(txtR);
			panel.add(txtR);

			txtP = new JTextField();
			builder.buildTxtForm(txtP);
			panel.add(txtP);

			txtS = new JTextField();
			builder.buildTxtForm(txtS);
			panel.add(txtS);

			// Bounds
			lblT.setBounds(5, 35, 30, 40);
			lblR.setBounds(95, 35, 30, 40);
			lblP.setBounds(5, 70, 30, 40);
			lblS.setBounds(95, 70, 30, 40);

			txtT.setBounds(30, 40, 60, 30);
			txtR.setBounds(120, 40, 60, 30);
			txtP.setBounds(30, 75, 60, 30);
			txtS.setBounds(120, 75, 60, 30);
		} else
		{
			JLabel lblDynTop = new JLabel();
			lblDynTop.setText("Dyn. Top.");
			builder.buildLabel(lblDynTop);
			panel.add(lblDynTop);

			JLabel lblMaxNodes = new JLabel();
			lblMaxNodes.setText("Node Cap");
			builder.buildLabel(lblMaxNodes);
			panel.add(lblMaxNodes);

			boxDynamicTopology = new JCheckBox();
			builder.buildCheckBox(boxDynamicTopology, true);
			panel.add(boxDynamicTopology);

			txtMaxNodes = new JTextField();
			builder.buildTxtForm(txtMaxNodes);
			panel.add(txtMaxNodes);

			lblDynTop.setBounds(5, 35, 110, 40);
			lblMaxNodes.setBounds(5, 70, 110, 40);

			boxDynamicTopology.setBounds(120, 40, 55, 40);
			txtMaxNodes.setBounds(120, 75, 55, 30);
		}
	}

	private void simVarsPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(compPanel.getBackground());
		panel.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 0, Color.white));
		compPanel.add(panel);
		panel.setBounds(205, 50, 190, 110);

		JLabel lblTitle = new JLabel();
		lblTitle.setText("Sim Vars");
		builder.buildLabel(lblTitle);
		panel.add(lblTitle);
		lblTitle.setBounds(50, 0, 100, 40);

		JLabel lblItPerGen = new JLabel();
		lblItPerGen.setText("It / Gen");
		builder.buildLabel(lblItPerGen);
		panel.add(lblItPerGen);

		JLabel lblMutation = new JLabel();
		lblMutation.setText("Mutation");
		builder.buildLabel(lblMutation);
		panel.add(lblMutation);

		txtItPerGen = new JTextField();
		builder.buildTxtForm(txtItPerGen);
		panel.add(txtItPerGen);

		txtMutation = new JTextField();
		builder.buildTxtForm(txtMutation);
		panel.add(txtMutation);

		lblItPerGen.setBounds(5, 35, 100, 40);
		lblMutation.setBounds(5, 70, 100, 40);

		txtItPerGen.setBounds(110, 40, 50, 30);
		txtMutation.setBounds(110, 75, 50, 30);
	}

	private void visualsPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(compPanel.getBackground());
		panel.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.white));
		compPanel.add(panel);
		panel.setBounds(10, 165, 385, 115);

		JLabel lblTitle = new JLabel();
		lblTitle.setText("Visuals");
		builder.buildLabel(lblTitle);
		panel.add(lblTitle);
		lblTitle.setBounds(150, 0, 100, 40);

		JLabel lblColorMode = new JLabel();
		lblColorMode.setText("Color Mode");
		builder.buildLabel(lblColorMode);
		panel.add(lblColorMode);

		JLabel lblDrawDelay = new JLabel();
		lblDrawDelay.setText("Draw Delay");
		builder.buildLabel(lblDrawDelay);
		panel.add(lblDrawDelay);

		comboColorMode = new JComboBox();
		builder.buildComboField(comboColorMode);
		panel.add(comboColorMode);

		txtDrawDelay = new JTextField();
		builder.buildTxtForm(txtDrawDelay);
		panel.add(txtDrawDelay);

		lblColorMode.setBounds(20, 35, 130, 40);
		lblDrawDelay.setBounds(20, 70, 130, 40);

		comboColorMode.setBounds(175, 40, 170, 30);
		txtDrawDelay.setBounds(175, 75, 170, 30);

		if (cellType != 2)
		{
			comboColorMode.addItem("Standard");
			comboColorMode.addItem("Fitness");
		} else
		{

			comboColorMode.addItem("Fitness");
			comboColorMode.addItem("Cooperation");
		}
	}

	private void optimizePanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(compPanel.getBackground());
		panel.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.white));
		compPanel.add(panel);
		panel.setBounds(10, 285, 385, 115);

		JLabel lblTitle = new JLabel();
		lblTitle.setText("Optimizations");
		builder.buildLabel(lblTitle);
		panel.add(lblTitle);
		lblTitle.setBounds(115, 0, 160, 40);

		JLabel lblDelay = new JLabel();
		lblDelay.setText("Delay");
		builder.buildLabel(lblDelay);
		panel.add(lblDelay);

		JLabel lblSerialDelay = new JLabel();
		lblSerialDelay.setText("Ser. Delay");
		builder.buildLabel(lblSerialDelay);
		panel.add(lblSerialDelay);

		txtDelay = new JTextField();
		builder.buildTxtForm(txtDelay);
		panel.add(txtDelay);

		txtSerDelay = new JTextField();
		builder.buildTxtForm(txtSerDelay);
		panel.add(txtSerDelay);

		lblDelay.setBounds(20, 35, 130, 40);
		lblSerialDelay.setBounds(20, 70, 130, 40);

		txtDelay.setBounds(175, 40, 170, 30);
		txtSerDelay.setBounds(175, 75, 170, 30);
	}
}
