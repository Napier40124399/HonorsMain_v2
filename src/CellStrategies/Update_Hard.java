package CellStrategies;

import Cells.Cell;
import DataCenter.Bridge;

public class Update_Hard implements StratUpdate
{
	public void copyStrat(Cell cell, Bridge bridge, int type)
	{
		cell.setHc_R(cell.getHc_NextGenR());
	}
}
