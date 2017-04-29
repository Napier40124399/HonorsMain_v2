package CellStrategies;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

public class Fitness_TitTat implements StratFitness
{
	public Float fitnessStrat(Cell cell, ArrayList<Cell> neighbours, Boolean R, Bridge bridge)
	{
		Float fitness = 0f;
		Boolean move;
		Boolean memory = false;
		if(cell.getType() == 0)
		{
			memory = cell.getHc_R();
		}
		for (Cell ce : neighbours)
		{
			for(int i = 0; i < bridge.getCell_ItPerGen(); i++)
			{
				move = ce.getHc_R(memory);
				if (!move)
				{
					if (memory)
					{
						fitness += bridge.getPd_T();
					} else
					{
						fitness += bridge.getPd_R();
					}
				} else
				{
					if (memory)
					{
						fitness += bridge.getPd_P();
					} else
					{
						fitness += bridge.getPd_S();
					}
				}
				if(cell.getType() == 1)
				{
					memory = move;
				}
			}
			ce.resetMemory();
			if(cell.getType() == 1)
			{
				memory = true;
			}
		}
		return fitness;
	}
}