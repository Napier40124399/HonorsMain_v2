package CellStrategies;

import java.util.ArrayList;

import Cells.Cell;
import Cells.Cell_NN;
import DataCenter.Bridge;

public class Fitness_NN implements StratFitness
{
	public Float fitnessStrat(Cell cell, ArrayList<Cell> neighbours, Boolean R, Bridge bridge)
	{
		cell.setPd_Fitness(0f);
		int coopHist = 0;
		Float temporaryFitness = 0f;
		for (Cell_NN ce : cells_Local)
		{
			memory.reset();
			ce.resetMemory();
			for (int i = 0; i < getBridge().getCell_ItPerGen(); i++)
			{
				decisionOP = ce.makeDecision();
				decisionME = network.think(memory.getMem());
				saveAll(decisionME, decisionOP);
				ce.handleMemory(decisionOP, decisionME);
				temporaryFitness += (-0.75f * decisionME) + (1.75f * decisionOP) + 2.5f;
				if (decisionME > 0)
				{
					coopHist += 1;
				}
			}
		}
		setPd_Fitness(temporaryFitness);
	}
}
