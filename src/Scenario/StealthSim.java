package Scenario;

import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import Cells.Cell;
import Cells.Cell_Hard;
import Cells.Cell_NN;
import Cells.Cell_TitTat;
import DataCenter.Bridge;
import MultiThreading.SplitTask;

public class StealthSim implements Runnable {
	private Bridge bridge;
	private SplitTask splitTask = new SplitTask();
	private ArrayList<Cell> cells;
	private ArrayList<Float> coopHist = new ArrayList<Float>();
	private ArrayList<Float> fitnHist = new ArrayList<Float>();

	/**
	 * <ul>
	 * <li>0) synch (bool)</li>
	 * <li>1) taurus (bool)</li>
	 * <li>2) dyn top (bool)</li>
	 * <li>3) cellQ (int)</li>
	 * <li>4) threads (int)</li>
	 * <li>5) distance (int)</li>
	 * <li>6) gens (int)</li>
	 * <li>7) iterations (int)</li>
	 * <li>8) save every (int)</li>
	 * <li>9) max nodes (int)</li>
	 * <li>10) cell type (int)</li>
	 * <li>11) T (Float)</li>
	 * <li>12) R (Float)</li>
	 * <li>13) P (Float)</li>
	 * <li>14) S (Float)</li>
	 * <li>15) mutation (Float)</li>
	 * <li>16) save location (String)</li>
	 * <li>17) start top (String)</li>
	 * </ul>
	 * 
	 * @param settings
	 */
	public StealthSim(String[] settings) {
		bridge = new Bridge();
		bridge.setUp(getInt(settings[3]), 0, getInt(settings[7]), getInt(settings[6]), getInt(settings[5]),
				Float.valueOf(settings[15]), Float.valueOf(settings[11]), Float.valueOf(settings[12]),
				Float.valueOf(settings[13]), Float.valueOf(settings[14]), Boolean.valueOf(settings[1]),
				getInt(settings[9]), true, settings[16], getInt(settings[8]), getInt(settings[4]), 0, 0, 0);
		bridge.setCell_ArrayList(makeCells(bridge.getCell_Quantity(), bridge, getInt(settings[10])));
		String[] top = settings[17].split("-");
		Integer[] topology = new Integer[top.length];
		for(int i = 0; i < top.length; i++)
		{
			topology[i] = getInt(top[i]);
		}
		bridge.setNn_Topology(topology);
		splitTask.splitTasks(bridge.getCell_ArrayList(), bridge.getSim_Threads());
		cells = bridge.getCell_ArrayList();
		Thread t = new Thread(this);
		t.start();
	}

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

	private Cell getCell(int type) {
		switch (type) {
		case 0:
			return new Cell_Hard();
		case 1:
			return new Cell_TitTat();
		case 2:
			return new Cell_NN();
		default:
			return null;
		}
	}

	private ArrayList<Cell> makeCells(int quantity, Bridge bridge, int type) {
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int i = 0; i < quantity; i++) {
			for (int j = 0; j < quantity; j++) {
				cells.add(getCell(type));

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
