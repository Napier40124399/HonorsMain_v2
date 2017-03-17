package Scenario;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

public class Simulation implements Runnable
{
	private int counter;
	private Bridge bridge;
	private ArrayList<Cell> cells;
	private SplitTask sp = new SplitTask();
	private ArrayList<ArrayList<String>> serializeAtEnd = new ArrayList<ArrayList<String>>();

	public Simulation(Bridge bridge)
	{
		this.bridge = bridge;
		cells = bridge.getCells();
	}

	@Override
	public void run()
	{
		counter = 0;
		for (Cell ce : cells)
		{
			ce.setNeighboors();
		}
		for (int i = 0; i < bridge.getTotalGen(); i++)
		{
			live();
			counter++;
			checkSave();
			System.out.println("Gen: "+bridge.getGen());
		}
		if (bridge.getTotalGen() == 0)
		{
			while (bridge.running())
			{
				live();
				counter++;
				checkSave();
				System.out.print("\rGen: "+bridge.getGen());
			}
		}
		serialize();
		System.out.println("******** Simulation finished ********");
	}

	private void live()
	{
		multiThread();
		bridge.addGen();
	}
	
	private void multiThread()
	{
		sp.splitCores(cells, bridge.getCores(), bridge, 1);
		sp.splitCores(cells, bridge.getCores(), bridge, 2);
		sp.splitCores(cells, bridge.getCores(), bridge, 4);
		sp.splitCores(cells, bridge.getCores(), bridge, 3);
	}
	
	private void basic()
	{
		for (Cell ce : cells)
		{
			ce.setFitness();
		}
		for (Cell ce : cells)
		{
			ce.setNewGeneration();
		}
		for (Cell ce : cells)
		{
			ce.updateCell();
		}
		for (Cell ce : cells)
		{
			ce.mutationLogic();
		}
	}
	
	private void checkSave()
	{
		if(counter == bridge.getSaveEvery())
		{
			serialize();
			counter = 0;
		}
	}
	
	private void serialize()
	{
		try (ObjectOutputStream oos =
				new ObjectOutputStream(new FileOutputStream(bridge.getPath()+"\\"+bridge.getGen())))
		{
			ArrayList<String> loc = new ArrayList<String>();
		    for(Cell c : cells)
		    {
		    	loc.add(c.serialize());
		    }
			oos.writeObject(loc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setCells()
	{
		cells = bridge.getCells();
	}

	public void setBridge(Bridge bridge)
	{
		this.bridge = bridge;
	}
}
