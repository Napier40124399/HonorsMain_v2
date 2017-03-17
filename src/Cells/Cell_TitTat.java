package Cells;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * <h1>Cell - Tit for Tat</h1> This is the cell subclass for TFT cells.
 * Start by cooperating then do whatever your opponent did on the previous iteration.
 * @see {@link Cells.Cellular super class}
 * @see {@link Cells.Cell interface}
 */
public class Cell_TitTat extends Cell
{
	private ArrayList<Boolean> memory = new ArrayList<Boolean>();
	private ArrayList<ArrayList<Boolean>> dimensions = new ArrayList<ArrayList<Boolean>>();
	
	private int cur = -1;
	private int flag = 0;
	private int type = 0;

	@Override
	public void mutationLogic()
	{
		if(Math.random() < 0.001)
		{
			this.type = 1;
		}
	}

	@Override
	public void setFitness()
	{
		boolean ceR;
		boolean myR;
		int iteration = 0;
		for(Cell ce : neighboors)
		{
			//Handling opponent
			if(ce.getType() == 1)
			{
				ceR = ce.getR(this.coords);
			}else
			{
				ceR = ce.getR();
			}
			//My Logic
			if(this.type == 1)
			{
				myR = choose(dimensions.get(iteration));
				save(iteration, ceR);
			}else
			{
				myR = R;
			}
			iteration++;
			//Calculating fitness
			if (!ceR)
			{
				if (myR)
				{
					fitness += bridge.getT();
				} else
				{
					fitness += bridge.getR();
				}
			} else
			{
				if (myR)
				{
					fitness += bridge.getP();
				} else
				{
					fitness += bridge.getS();
				}
			}
		}
	}

	@Override
	public void setNewGeneration()
	{
		daddys = new ArrayList<Cell>();
		double old = fitness;
		for (Cell ce : neighboors)
		{
			if (ce.getPower() > old)
			{
				daddys.clear();
				daddys.add(ce);
				old = ce.getPower();
			} else if (ce.getPower() == old)
			{
				daddys.add(ce);
			}
		}
		
		
		if (daddys.size() > 0)
		{
			if(type == 1)
			{
				if(daddys.get(((int) (Math.random() * (daddys.size())))).getType() == 1)
				{
				}else
				{
					type = 0;
					nextGenR = daddys.get(((int) (Math.random() * (daddys.size())))).getR();
				}
			}else
			{
				if(daddys.get(((int) (Math.random() * (daddys.size())))).getType() == 1)
				{
					type = 1;
				}else
				{
					nextGenR = daddys.get(((int) (Math.random() * (daddys.size())))).getR();
				}
			}
		}
	}

	@Override
	public void updateCell()
	{
		if(type == 1)
		{
			c1 = Color.green;
		}else if(R == true)
		{
			c1 = Color.red;
		}else
		{
			c1 = Color.blue;
		}
		this.R = nextGenR;
		fitness = 0;
	}
	
	@Override
	public int getType()
	{
		return this.type;
	}
	
	@Override
	public boolean getR(Point2D coords)
	{
		int cellCount = 0;
		for(Cell ce : this.neighboors)
		{
			if(coords.equals(ce.getCoords()))
			{
				return choose(dimensions.get(cellCount));
			}
			cellCount++;
		}
		//This is a failsafe
		return false;
	}
	
	@Override
	public void setNeighboors()
	{
		super.setNeighboors();
		for(int i = 0; i < neighboors.size(); i++)
		{
			memory = new ArrayList<Boolean>();
			for(int j = 0; j < bridge.getMemory(); j++)
			{
				memory.add(false);
			}
			dimensions.add(memory);
		}
	}
	
	private Boolean choose(ArrayList<Boolean> temp)
	{
		int val = 0;
		for(Boolean b : temp)
		{
			if(b == true){val--;}else{val++;}
		}
		if(val >= 0){return false;}else{return true;}
	}
	
	private void save(int iteration, Boolean ceR)
	{
		memory = (ArrayList<Boolean>) dimensions.get(iteration).clone();
		
		memory.add(ceR);
		memory.remove(0);
		
		dimensions.set(iteration, memory);
	}
}