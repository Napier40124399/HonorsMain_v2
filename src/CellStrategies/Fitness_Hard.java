package CellStrategies;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

public class Fitness_Hard implements StratFitness
{
	public Float fitnessStrat(Cell cell, ArrayList<Cell> neighbours, Boolean R, Bridge bridge)
	{
		Float fitness = 0f;
		for (Cell ce : neighbours)
		{
			for(int i = 0; i < bridge.getCell_ItPerGen(); i++)
			{
				if (!ce.getHc_R())
				{
					if (R)
					{
						fitness += bridge.getPd_T();
					} else
					{
						fitness += bridge.getPd_R();
					}
				} else
				{
					if (R)
					{
						fitness += bridge.getPd_P();
					} else
					{
						fitness += bridge.getPd_S();
					}
				}
			}
		}
		return fitness;
	}
}
