package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Components.Colors;
import Components.CompBuilder;
import Components.FileChooser;
import FileIO.LoadDefaults;

public class MainWindow
{
	// Components
	private JFrame frame;
	private JButton btnStart;
	// Global Settings
	private JComboBox comboType;
	private JTextField txtCellQ;
	private JTextField txtMutation;
	private JTextField txtDistance;
	private JTextField txtGenerations;
	private JTextField txtIterations;
	private JTextField txtThreads;
	private JTextField txtDelay;
	private JCheckBox boxTaurus;
	// Menu
	private JMenuItem mntmSettings;
	private JMenuItem mntmSaveSettings;
	private JMenuItem mntmLoadSettings;
	// Draw Settings
	private JPanel separator1;
	private JTextField txtDrawDelay;
	private JTextField txtDrawScale;
	// Serialization Settings
	private JPanel separator2;
	private JCheckBox boxSerialize;
	private JTextField txtSerializeDelay;
	private JTextField txtSerializePath;
	// Scenario Specific Settings
	private JPanel separator3;
	private JCheckBox boxDynTop;
	private JTextField txtTop;
	// Side Panel
	private JScrollPane scroll;
	private JTable tblSims;
	private JTextArea area;
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnStop;
	private JCheckBox boxVisi;
	// Labels
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	// Table variables
	private int ID;
	private String[] cols =
	{ "ID", "Gen", "State", "Threads" };
	private String[][] rows = null;
	private TableModel model;
	private SimulationWindow selectedSim = null;
	// Instances
	private CompBuilder build;
	private Colors c = new Colors();
	private ArrayList<SimulationWindow> simulations = new ArrayList<SimulationWindow>();
	private LoadDefaults load = new LoadDefaults();
	private SettingsWindow settingsWindow;
	//Timers
	private Timer timer;

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
		frame.setBounds(100, 100, 850, 1130);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(c.getDarkBlue());
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setTitle("Launcher");

		// MENU
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

		// Components
		comboType = new JComboBox();
		comboType.addItem("Hardcoded");
		comboType.addItem("Tit for tat");
		comboType.addItem("Neural network");
		comboType.addItem("Carpet");
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

		separator3 = new JPanel();
		build.buildSeparator(separator3, frame);

		boxDynTop = new JCheckBox();
		build.buildCheckBox(boxDynTop, false, frame);

		txtTop = new JTextField();
		build.buildTxtForm(txtTop, frame);

		// Labels
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
		labels.add(new JLabel("Dyn Topology"));
		labels.add(new JLabel("Topology"));
		// Special labels
		labels.add(new JLabel("Neural Net Settings"));
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
		labels.get(10).setToolTipText(
				"Amplify lattice by this value when drawing.\nEffectively makes simulation window equal to scale * cell quantity.");
		labels.get(11).setToolTipText("Activate or deactivatethe save functionality. This will output serliazed data.");
		labels.get(12).setToolTipText("Save every X generations.");
		labels.get(13).setToolTipText("Save file location.");
		labels.get(14).setToolTipText("Defines whether topology is dynamic.");
		labels.get(15).setToolTipText(
				"Set starting topology. Enter number of nodes per layer, use '-' delimiter to signal new layer: 6-2-4-2");

		for (JLabel lbl : labels)
		{
			build.buildLabel(lbl, frame);
		}

		btnStart = new JButton();
		btnStart.setText("Start Simulation");
		build.buildButton(btnStart, frame);

		sidePanel();

		place();
		placeHolders();
		actionListeners();
	}

	private void sidePanel()
	{
		JPanel separator4 = new JPanel();
		build.buildSeparator(separator4, frame);

		JLabel lblOngoing = new JLabel("Ongoing Simulations");
		build.buildLabel(lblOngoing, frame);

		model = new DefaultTableModel(rows, cols);
		tblSims = new JTable(model);
		build.buildTable(tblSims);

		scroll = new JScrollPane(tblSims);
		build.buildScrollPane(scroll);
		frame.getContentPane().add(scroll);

		JLabel lblDetails = new JLabel("Simulation Details");
		build.buildLabel(lblDetails, frame);

		area = new JTextArea();
		area.setEditable(false);
		area.setFocusable(false);
		build.buildArea(area, frame);
		
		btnPlay = new JButton();
		btnPlay.setBorder(BorderFactory.createEmptyBorder());
		btnPlay.setContentAreaFilled(false);
		frame.getContentPane().add(btnPlay);
		btnPlay.setIcon(new ImageIcon("res\\play.png"));
		btnPlay.setRolloverIcon(new ImageIcon("res\\playHover.png"));

		btnPause = new JButton();
		btnPause.setBorder(BorderFactory.createEmptyBorder());
		btnPause.setContentAreaFilled(false);
		frame.getContentPane().add(btnPause);
		btnPause.setIcon(new ImageIcon("res\\pause.png"));
		btnPause.setRolloverIcon(new ImageIcon("res\\pauseHover.png"));

		btnStop = new JButton();
		btnStop.setBorder(BorderFactory.createEmptyBorder());
		btnStop.setContentAreaFilled(false);
		frame.getContentPane().add(btnStop);
		btnStop.setIcon(new ImageIcon("res\\stop.png"));
		btnStop.setRolloverIcon(new ImageIcon("res\\stopHover.png"));

		boxVisi = new JCheckBox();
		build.buildCheckBox(boxVisi, false, frame);
		boxVisi.setEnabled(false);
		
		timer = new Timer(60000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(simulations.size() > 0)
				{
					ArrayList<String[]> dets = new ArrayList<String[]>();
					for(int i = 0; i < simulations.size(); i++)
					{
						dets.add(simulations.get(i).getVals());
					}
					String[][] newRows = new String[simulations.size()][4];
					for(int i = 0; i < simulations.size(); i++)
					{
						newRows[i] = dets.get(i);
					}
					refreshTable();
				}
			}
		});
		timer.start();

		// Bounds
		// separator4.setBounds(460, 30, 2, 1000);
		lblOngoing.setBounds(540, 5, 250, 40);
		scroll.setBounds(480, 50, 350, 400);
		lblDetails.setBounds(555, 510, 250, 40);
		area.setBounds(480, 550, 350, 450);
		btnPlay.setBounds(560, 1010, 40, 40);
		btnPause.setBounds(600, 1010, 40, 40);
		btnStop.setBounds(640, 1010, 40, 40);
		boxVisi.setBounds(680, 1010, 80, 40);
	}
	
	private void check(String[] vals)
	{
		
	}

	private void newRow(String[] settings)
	{
		String[] row =
		{ "" + (simulations.size() - 1), "" + simulations.get(simulations.size() - 1).getGen(),
				simulations.get(simulations.size() - 1).getState(),
				"" + simulations.get(simulations.size() - 1).getThreads() };
		if (tblSims.getRowCount() > 0)
		{
			String[][] newRows = new String[rows.length + 1][4];
			newRows[0] = row;
			for (int i = 0; i < rows.length; i++)
			{
				newRows[i + 1] = rows[i];
			}
			rows = newRows;
		} else
		{
			rows = new String[1][4];
			rows[0] = row;
		}
		refreshTable();
	}

	private void refreshTable()
	{
		model = new DefaultTableModel(rows, cols)
		{

		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		tblSims.setModel(model);
		tblSims.repaint();
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
				if (validate())
				{
					String[] settings = new String[16];
					settings[0] = comboType.getSelectedIndex() + "";
					settings[1] = txtCellQ.getText();
					settings[2] = txtMutation.getText();
					settings[3] = txtDistance.getText();
					settings[4] = txtGenerations.getText();
					settings[5] = txtIterations.getText();
					settings[6] = txtThreads.getText();
					settings[7] = txtDelay.getText();
					settings[8] = boxTaurus.isSelected() + "";
					settings[9] = txtDrawDelay.getText();
					settings[10] = txtDrawScale.getText();
					settings[11] = boxSerialize.isSelected() + "";
					settings[12] = txtSerializeDelay.getText();
					settings[13] = txtSerializePath.getText();
					settings[14] = boxDynTop.isSelected() + "";
					settings[15] = txtTop.getText();

					simulations.add(
							new SimulationWindow(settings, new Point(frame.getX() + frame.getWidth(), frame.getY())));
					newRow(settings);
				}
			}
		});

		// Menu
		mntmSettings.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				settingsWindow.setVisible(true, new Point(frame.getX() + frame.getWidth(), frame.getY()));
			}
		});

		tblSims.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				ID = Integer.parseInt((String)tblSims.getModel().getValueAt(tblSims.getSelectedRow(), 0));
				selectedSim = simulations.get(ID);
				area.setText("");
				for (String s : selectedSim.getDetails())
				{
					area.append(s);
					area.append("\n");
				}
				selectedSim.bringToFront();
				boxVisi.setEnabled(true);
				boxVisi.setSelected(selectedSim.isVisible());
			}
		});
		btnPlay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!selectedSim.equals(null))
				{
					selectedSim.play();
				}
			}
		});
		btnPause.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!selectedSim.equals(null))
				{
					selectedSim.pause();
				}
			}
		});
		btnStop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!selectedSim.equals(null))
				{
					selectedSim.stop();
				}
			}
		});
		boxVisi.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!selectedSim.equals(null))
				{
					selectedSim.toggleVisible(boxVisi.isSelected());
				}
			}
		});
	}

	private Boolean validate()
	{
		String message = "";
		if (numbersOnly(txtCellQ.getText()))
		{
			message += "Cell Quantity can only be numbers.\n";
		}
		if (Integer.parseInt(txtCellQ.getText()) > 2500 || Integer.parseInt(txtCellQ.getText()) < 5)
		{
			message += "Cell Quantity out of bounds. (5 < Q < 2500)\n";
		}
		if (Integer.parseInt(txtTop.getText().split("-")[0]) % 2 != 0)
		{
			//return false;
		}
		return true;
	}

	private Boolean numbersOnly(String s)
	{
		if (s.matches("\\d+"))
		{
			return true;
		} else
		{
			return false;
		}
	}

	private void placeHolders()
	{
		comboType.setSelectedIndex(2);
		txtCellQ.setText("100");
		txtMutation.setText("0.01");
		txtDistance.setText("1");
		txtGenerations.setText("0");
		txtIterations.setText("51");
		txtThreads.setText((Runtime.getRuntime().availableProcessors() / 2) + "");
		txtDelay.setText("10");
		txtDrawDelay.setText("2");
		txtDrawScale.setText("10");
		txtSerializeDelay.setText("10");
		txtSerializePath.setText("NULL");
		txtTop.setText("6-2");
	}

	private void place()
	{
		int left = 200;
		int pos = 60;
		int skip = 50;
		int width = 220;
		int height = 40;
		int verify = 0;
		for (JLabel lbl : labels)
		{
			lbl.setBounds(20, pos, 160, 40);
			pos += skip;
			verify++;
			if (verify == 9 || verify == 11 || verify == 14)
			{
				pos += skip;
			}
		}

		pos = 10;
		labels.get(labels.size() - 1).setBounds(130, pos - 5, 200, height);
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
		boxTaurus.setBounds(left, pos, 200, height);
		pos += skip;
		separator1.setBounds(30, pos, 370, 3);
		labels.get(labels.size() - 2).setBounds(130, pos, 200, height);
		pos += skip;
		txtDrawDelay.setBounds(left, pos, width, height);
		pos += skip;
		txtDrawScale.setBounds(left, pos, width, height);
		pos += skip;
		separator2.setBounds(30, pos, 370, 3);
		labels.get(labels.size() - 3).setBounds(90, pos, 240, height);
		pos += skip;
		boxSerialize.setBounds(left, pos, width, height);
		pos += skip;
		txtSerializeDelay.setBounds(left, pos, width, height);
		pos += skip;
		txtSerializePath.setBounds(left, pos, width, height);
		pos += skip;
		separator3.setBounds(30, pos, 370, 3);
		labels.get(labels.size() - 4).setBounds(110, pos, 230, height);
		pos += skip;
		boxDynTop.setBounds(left, pos, width, height);
		pos += skip;
		txtTop.setBounds(left, pos, width, height);
		btnStart.setBounds(-2, pos + 55, 450, height);
	}
}
