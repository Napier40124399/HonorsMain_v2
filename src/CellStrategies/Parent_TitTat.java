package CellStrategies;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

public class Parent_TitTat implements StratParent
{
	public int parentStrat(Cell cell, ArrayList<Cell> neighbours, Bridge bridge)
	{
		Cell parent = null;
		ArrayList<Cell> potentialParents = new ArrayList<Cell>();
		Float currentFitness = cell.getPd_Fitness();
		for(Cell ce : neighbours)
		{
			if(ce.getPd_Fitness() > currentFitness)
			{
				potentialParents.clear();
				potentialParents.add(ce);
				currentFitness = ce.getPd_Fitness();
			}else if (ce.getPd_Fitness() == currentFitness)
			{
				potentialParents.add(ce);
			}
		}
		if(potentialParents.size() > 1)
		{
			double position = Math.random() * potentialParents.size();
			if(position < 0){position = 0;}
			else if(position >= potentialParents.size()){position = potentialParents.size()-1;}
			parent = potentialParents.get((int)position);
		}else
		{
			parent = potentialParents.get(0);
		}
		cell.setHc_NextGenR(parent.getHc_R());
		return parent.getType();
	}
	
}
