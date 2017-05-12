package Scenario;

import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import Cells.Cell;
import Cells.CellFactory;
import Cells.Cell_Hard;
import Cells.Cell_NN;
import Cells.Cell_TitTat;
import DataCenter.Bridge;
import MultiThreading.SplitTask;

/**
 * <h1>StealthSim</h1>Simulation environment for interface which do not use a
 * GUI.
 * 
 * @author James
 *
 */
public class StealthSim implements Runnable {
	private Bridge bridge;
	private SplitTask splitTask = new SplitTask();
	private ArrayList<Cell> cells;
	private ArrayList<Float> coopHist = new ArrayList<Float>();
	private ArrayList<Float> fitnHist = new ArrayList<Float>();

	/**
	 * <h1>StealthSim</h1>Class which manages the simulation. Handles
	 * multi-threading, creation of cells etc.. <br>
	 * Input as String[] must be as follows:
	 * <ul>
	 * <li>0) synch (bool) - true/false</li>
	 * <li>1) taurus (bool) - true/false</li>
	 * <li>2) dyn top (bool) - true/false</li>
	 * <li>3) cellQ (int) - numbers, no letters, no decimals</li>
	 * <li>4) threads (int) - numbers, no letters, no decimals</li>
	 * <li>5) distance (int) - numbers, no letters, no decimals</li>
	 * <li>6) gens (int) - numbers, no letters, no decimals</li>
	 * <li>7) iterations (int) - numbers, no letters, no decimals</li>
	 * <li>8) save every (int) - numbers, no letters, no decimals</li>
	 * <li>9) max nodes (int) - numbers, no letters, no decimals</li>
	 * <li>10) cell type (int) - numbers, no letters, no decimals</li>
	 * <li>11) T (Float) - numbers, no letters</li>
	 * <li>12) R (Float) - numbers, no letters</li>
	 * <li>13) P (Float) - numbers, no letters</li>
	 * <li>14) S (Float) - numbers, no letters</li>
	 * <li>15) mutation (Float) - numbers, no letters</li>
	 * <li>16) save location (String) - save path use / or \ it doesn't matter
	 * in this context</li>
	 * <li>17) start top (String) - topologies must be formatted as follows;
	 * number of nodes on the layer seperated by '-' to signal a new layer, the
	 * output layer is not to be specified. For example: 6-2</li>
	 * </ul>
	 * 
	 * @param args
	 *            (String[])
	 * @see {@link CLI.MainCommand Launcher}
	 */
	public StealthSim(String[] settings) {
		bridge = new Bridge();
		bridge.setNn_Topology(extrapolateTopology(settings[17]));
		bridge.setNn_DynTop(Boolean.parseBoolean(settings[2]));
		bridge.setUp(getInt(settings[3]), 0, getInt(settings[7]), getInt(settings[6]), getInt(settings[5]),
				Float.valueOf(settings[15]), Float.valueOf(settings[11]), Float.valueOf(settings[12]),
				Float.valueOf(settings[13]), Float.valueOf(settings[14]), Boolean.parseBoolean(settings[1]),
				getInt(settings[9]), true, settings[16], getInt(settings[8]), getInt(settings[4]), 0, 0, 0);
		bridge.setCell_ArrayList(makeCells(bridge.getCell_Quantity(), bridge, getInt(settings[10])));
		splitTask.splitTasks(bridge.getCell_ArrayList(), bridge.getSim_Threads());
		cells = bridge.getCell_ArrayList();
		// Thread t = new Thread(this);
		// t.start();
		doIt();
	}

	/**
	 * <h1>extrapolateTopology</h1>Takes in a string formatted according to
	 * required input and returns an Integer[] which represents the starting
	 * topology.
	 * 
	 * @param s (String)
	 * @return Integer[]
	 */
	private Integer[] extrapolateTopology(String s) {
		String[] ss = s.split("-");
		Integer[] top = new Integer[ss.length];
		for (int i = 0; i < ss.length; i++) {
			top[i] = Integer.parseInt(ss[i]);
		}

		return top;
	}

	/**
	 * <h1>doIt</h1>Runs cells through their required states. Manages threading
	 * of cell activity and synchronous state management.
	 * 
	 * @see {@link Scenario.StealthSim#run() run} - secondary version of this
	 *      method.
	 */
	private void doIt() {
		bridge.setSim_CurGen(0);
		for (Cell ce : bridge.getCell_ArrayList()) {
			ce.doNeighboors();
		}

		// Specific amount of generations
		for (int i = 0; i < bridge.getCell_MaxGen(); i++) {
			simulate();
		}

		// Check if unlimitted
		if (bridge.getCell_MaxGen() == 0) {
			// Unlimitted
			while (bridge.getSim_Running()) {
				simulate();
			}
		}
		if (bridge.getSim_Save()) {
			writeOut(coopHist, fitnHist);
		}
	}

	/**
	 * <h1>run</h1>Is the same method as {@link Scenario.StealthSim#doIt()
	 * doIt()} but runs on its own thread.
	 */
	@Override
	public void run() {
		bridge.setSim_CurGen(0);
		for (Cell ce : bridge.getCell_ArrayList()) {
			ce.doNeighboors();
		}

		// Specific amount of generations
		for (int i = 0; i < bridge.getCell_MaxGen(); i++) {
			simulate();
		}

		// Check if unlimitted
		if (bridge.getCell_MaxGen() == 0) {
			// Unlimitted
			while (bridge.getSim_Running()) {
				simulate();
			}
		}
		if (bridge.getSim_Save()) {
			writeOut(coopHist, fitnHist);
		}
	}

	/**
	 * <h1>simulate</h1>
	 */
	private void simulate() {
		if (bridge.getSim_Threads() < 2) {
			singleThread();
		} else {
			if (bridge.getFireThreadChange()) {
				splitTask.splitTasks(cells, bridge.getSim_Threads());
				bridge.setFireThreadChange(false);
			}
			multiThread();
		}
		bridge.setSim_CurGen(bridge.getSim_CurGen() + 1);
		checkSave();
	}

	private void checkSave() {
		if (bridge.getSim_Save() && bridge.getSim_CurGen() % bridge.getSim_SaveDelay() == 0) {
			// Handle save logic here
			try (ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(bridge.getSim_SavePath() + "\\" + bridge.getSim_CurGen()))) {
				ArrayList<String> loc = new ArrayList<String>();
				for (Cell c : cells) {
					loc.add(c.serialize());
				}
				oos.writeObject(loc);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void singleThread() {
		for (Cell ce : cells) {
			ce.doFitness();
		}
		for (Cell ce : cells) {
			ce.doNewGeneration();
		}
		for (Cell ce : cells) {
			ce.doUpdateCell();
		}
		for (Cell ce : cells) {
			ce.doMutationLogic();
		}
	}

	private void multiThread() {
		splitTask.threadTask(0);
		splitTask.threadTask(1);
		splitTask.threadTask(2);
		splitTask.threadTask(3);
	}

	private ArrayList<Cell> makeCells(int quantity, Bridge bridge, int type) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		CellFactory factory = new CellFactory();
		for (int i = 0; i < quantity; i++) {
			for (int j = 0; j < quantity; j++) {
				cells.add(factory.getCell(type));

				if (Math.random() > 0.5) {
					cells.get(cells.size() - 1).Initialize(false, i, j, bridge);
				} else {
					cells.get(cells.size() - 1).Initialize(true, i, j, bridge);
				}
			}
		}
		Point p = new Point(bridge.getCell_Quantity() / 2, bridge.getCell_Quantity() / 2);
		if (type == 3) {
			for (Cell ce : cells) {
				ce.setHc_R(false);
				ce.setHc_NextGenR(false);
				if (ce.getPos_Coords().equals(p)) {
					ce.setHc_R(true);
					ce.setHc_NextGenR(true);
				}
			}
		}
		return cells;
	}

	private int getInt(String s) {
		return Integer.parseInt(s);
	}

	private void writeOut(ArrayList<Float> coopHist, ArrayList<Float> fitnHist) {
		String prefix = bridge.getSim_SavePath();
		try {
			PrintWriter writer = new PrintWriter(prefix + "\\coopHist.txt", "UTF-8");
			for (Float f : coopHist) {
				writer.println(f);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("fail");
		}

		try {
			PrintWriter writer2 = new PrintWriter(prefix + "\\fitnHist.txt", "UTF-8");
			for (Float f : fitnHist) {
				writer2.println(f);
			}
			writer2.close();
		} catch (IOException e) {
			System.out.println("fail");
		}
	}
}
