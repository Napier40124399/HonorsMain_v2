package CellStrategies;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

public interface StratFitness
{
	public  Float fitnessStrat(Cell cell, ArrayList<Cell> neighbours, Boolean R, Bridge bridge);
}
