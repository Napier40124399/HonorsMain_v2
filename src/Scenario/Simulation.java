package Scenario;

import java.awt.Point;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Cells.Cell;
import Cells.Cell_Hard;
import Cells.Cell_NN;
import Cells.Cell_TitTat;
import DataCenter.Bridge;
import MultiThreading.SplitTask;

public class Simulation implements Runnable
{
	// Threads
	private Thread t;
	// Instances
	private Bridge bridge;
	private SplitTask sp = new SplitTask();
	private ArrayList<Cell> cells;
	
	//Temporary stuff
	private ArrayList<Float> coopHist = new ArrayList<Float>();
	private ArrayList<Float> fitnHist = new ArrayList<Float>();

	public Simulation(Bridge bridge, int type)
	{
		this.bridge = bridge;
		cells = makeCells(bridge.getCell_Quantity(), bridge, type);
		bridge.setCell_ArrayList(cells);
		sp.splitTasks(cells, bridge.getSim_Threads());
	}

	@Override
	public void run()
	{
		bridge.setSim_CurGen(0);
		for (Cell ce : cells)
		{
			ce.doNeighboors();
		}

		// Specific amount of generations
		for (int i = 0; i < bridge.getCell_MaxGen(); i++)
		{
			simulate();
		}

		// Check if unlimitted
		if (bridge.getCell_MaxGen() == 0)
		{
			// Unlimitted
			while (bridge.getSim_Running())
			{
				simulate();
			}
		}
		if(bridge.getSim_Save())
		{
			writeOut(coopHist, fitnHist);
		}
	}

	private void simulate()
	{
		if (bridge.getSim_Threads() <= 1)
		{
			singleThread();
		} else
		{
			if (bridge.getFireThreadChange())
			{
				sp.splitTasks(cells, bridge.getSim_Threads());
				bridge.setFireThreadChange(false);
			}
			multiThread();
		}
		dispProp();
		bridge.setSim_CurGen(bridge.getSim_CurGen() + 1);
		checkSave();
		try
		{
			Thread.sleep(bridge.getSim_Delay());
			while (bridge.getSim_Paused())
			{
				Thread.sleep(50);
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}

	private void dispProp()
	{
		Float coop = 0f;
		for (Cell ce : cells)
		{
			coop += ce.getCoopHist();
		}
		coop = ((coop / new Float(cells.size()))
				/ new Float(bridge.getCell_ItPerGen()) / new Float(cells.get(0).getCell_Neighboors().size()));
		coopHist.add(coop);
		coop = 0f;
		for(Cell ce : cells)
		{
			coop += ce.getPd_Fitness();
		}
		fitnHist.add(coop);
		System.out.println(coop);
	}

	private void singleThread()
	{
		for (Cell ce : cells)
		{
			ce.doFitness();
		}
		for (Cell ce : cells)
		{
			ce.doNewGeneration();
		}
		for (Cell ce : cells)
		{
			ce.doUpdateCell();
		}
		for (Cell ce : cells)
		{
			ce.doMutationLogic();
		}
	}

	private void multiThread()
	{
		sp.threadTask(0);
		sp.threadTask(1);
		sp.threadTask(2);
		sp.threadTask(3);
	}

	private void checkSave()
	{
		if (bridge.getSim_Save() && bridge.getSim_CurGen() == bridge.getSim_SaveDelay())
		{
			bridge.setSim_CurGen(0);
			// Handle save logic here
		}
	}

	private Cell getCell(int type)
	{
		if (type == 0)
		{
			return new Cell_Hard();
		} else if (type == 1)
		{
			return new Cell_TitTat();
		} else if (type == 2)
		{
			return new Cell_NN();
		} else if (type == 3)
		{
			return new Cell_Hard();
		} else
			return null;
	}

	private ArrayList<Cell> makeCells(int quantity, Bridge bridge, int type)
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for (int i = 0; i < quantity; i++)
		{
			for (int j = 0; j < quantity; j++)
			{
				cells.add(getCell(type));

				if(Math.random() > 0.5)
				{
					cells.get(cells.size() - 1).Initialize(false, i, j, bridge);
				}
				else
				{
					cells.get(cells.size() - 1).Initialize(true, i, j, bridge);
				}
			}
		}
		Point p = new Point(bridge.getCell_Quantity() / 2, bridge.getCell_Quantity() / 2);
		if (type == 3)
		{
			for (Cell ce : cells)
			{
				ce.setHc_R(false);
				ce.setHc_NextGenR(false);
				if (ce.getPos_Coords().equals(p))
				{
					ce.setHc_R(true);
					ce.setHc_NextGenR(true);
				}
			}
		}
		return cells;
	}

	public void start()
	{
		t = new Thread(this);
		t.start();
	}
	
	private void writeOut(ArrayList<Float> coopHist, ArrayList<Float> fitnHist)
	{
		String prefix = bridge.getSim_SavePath();
		try {
			PrintWriter writer = new PrintWriter("C:\\Users\\James\\Documents\\SimulationResults\\"+prefix+"_coopHist.txt", "UTF-8");
			for (Float f : coopHist) {
				writer.println(f);
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("fail");
		}
		
		try {
			PrintWriter writer2 = new PrintWriter("C:\\Users\\James\\Documents\\SimulationResults\\"+prefix+"_fitnHist.txt", "UTF-8");
			for (Float f : fitnHist) {
				writer2.println(f);
			}
			writer2.close();
		} catch (IOException e) {
			System.out.println("fail");
		}
	}
}
