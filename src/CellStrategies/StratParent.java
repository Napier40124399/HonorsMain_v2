package CellStrategies;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

public interface StratParent
{
	public int parentStrat(Cell cell, ArrayList<Cell> neighbours, Bridge bridge);
}
