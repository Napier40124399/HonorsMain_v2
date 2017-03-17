package Scenario;

import java.util.ArrayList;

import Cells.Cell;
import Cells.Cell_Hard;
import Cells.Cell_NN2;
import Cells.Cell_TitTat;
import DataCenter.Bridge;

public class Scenario
{
	private Thread t;
	private Bridge bridge = new Bridge();
	
	public Scenario(ArrayList<String> settings)
	{
		bridge.setSettings(settings);
		bridge.setCells(makeCells(Integer.parseInt(settings.get(2)), Integer.parseInt(settings.get(0))));
		bridge.setTotalGen(Integer.parseInt(settings.get(3)));
		bridge.setItPerGen(Integer.parseInt(settings.get(4)));
		bridge.setSaveEvery(Integer.parseInt(settings.get(5)));
		bridge.setDistance(Double.parseDouble(settings.get(6)));
		bridge.setTaurus(isTrue(settings.get(7)));
		bridge.setCellCount(Integer.parseInt(settings.get(2)));
		bridge.setCores(Integer.parseInt(settings.get(8)));
		bridge.setRunning(true);
		bridge.setNodes(Integer.parseInt(settings.get(9)));
		bridge.setMutation(Integer.parseInt(settings.get(10)));
		bridge.setPath(settings.get(11));
		
		t = new Thread(new Simulation(bridge));
		t.start();
	}
	
	private ArrayList<Cell> makeCells(int quantity, int type)
	{
		ArrayList<Cell> cells = new ArrayList<Cell>();
		for(int i = 0; i < quantity; i++)
		{
			for(int j = 0; j < quantity; j++)
			{
				cells.add(setCells(type));
				cells.get(cells.size() - 1).Cell(true, i, j, bridge);
			}
		}
		
		return cells;
	}
	
	private Cell setCells(int i)
	{
		if(i == 1){return new Cell_Hard();}
		else if(i == 2){return new Cell_TitTat();}
		else{return new Cell_NN2();}
	}
	
	private boolean isTrue(String s)
	{
		if(s.equals("1")){return true;}
		else {return false;}
	}
	
	public void stop()
	{
		bridge.setRunning(false);
		try{t.join();}
		catch (InterruptedException e){}
	}
}