package Scenario;

import java.util.ArrayList;

import Cells.Cell;
import Cells.Cell_Hard;
import DataCenter.Bridge;
import MultiThreading.SplitTask;

public class Simulation implements Runnable
{
	//Threads
	private Thread t;
	//Instances
	private Bridge bridge;
	private SplitTask sp = new SplitTask();
	private ArrayList<Cell> cells;
	
	public Simulation(Bridge bridge)
	{
		this.bridge = bridge;
		cells = makeCells(bridge.getCell_Quantity());
		bridge.setCell_ArrayList(cells);
		sp.splitTasks(cells, bridge.getSim_Threads());
	}
	
	@Override
	public void run()
	{
		bridge.setSim_CurGen(0);
		for(Cell ce : cells)
		{
			ce.doNeighboors();
		}
		
		//Specific amount of generations
		for(int i = 0; i < bridge.getCell_MaxGen(); i++)
		{
			simulate();
		}
		
		//Check if unlimitted
		if(bridge.getCell_MaxGen() == 0)
		{
			//Unlimitted
			while(bridge.getSim_Running())
			{
				simulate();
			}
		}
	}
	
	private void simulate()
	{
		if(bridge.getSim_Threads() == 1)
		{
			singleThread();
		}else
		{
			multiThread();
		}
		bridge.setSim_CurGen(bridge.getSim_CurGen() + 1);
		checkSave();
		try
		{
			Thread.sleep(bridge.getSim_Delay());
			while(bridge.getSim_Paused())
			{
				Thread.sleep(50);
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void singleThread()
	{
		for(Cell ce : cells)
		{
			ce.doFitness();
		}
		for(Cell ce : cells)
		{
			ce.doNewGeneration();
		}
		for(Cell ce : cells)
		{
			ce.doMutationLogic();
		}
		for(Cell ce : cells)
		{
			ce.doUpdateCell();
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
		if(bridge.getSim_Save() && bridge.getSim_CurGen() == bridge.getSim_SaveDelay())
		{
			bridge.setSim_CurGen(0);
			//Handle save logic here
		}
	}
	
	private ArrayList<Cell> makeCells(int quantity)
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(int i = 0; i < quantity; i++)
		{
			for(int j = 0; j < quantity; j++)
			{
				cells.add(new Cell_Hard());
				cells.get(cells.size() - 1).Initialize(true, i, j, bridge);
			}
		}
		
		return cells;
	}
	
	public void start()
	{
		t = new Thread(this);
		t.start();
	}
}
