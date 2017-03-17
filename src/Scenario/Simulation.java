package Scenario;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;
import MultiThreading.SplitTask;

public class Simulation implements Runnable
{
	//Instances
	private Bridge bridge;
	private SplitTask sp = new SplitTask();
	private ArrayList<Cell> cells;
	
	public Simulation(Bridge bridge)
	{
		this.bridge = bridge;
		cells = bridge.getCell_ArrayList();
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
		multiThread();
		bridge.setSim_CurGen(bridge.getSim_CurGen() + 1);
		checkSave();
	}
	
	private void multiThread()
	{
		sp.threadFitness();
		sp.threadNewGen();
		sp.threadMutation();
		sp.threadUpdate();
	}
	
	private void checkSave()
	{
		if(bridge.getSim_Save() && bridge.getSim_CurGen() == bridge.getSim_SaveDelay())
		{
			bridge.setSim_CurGen(0);
			//Handle save logic here
		}
	}
	
}
