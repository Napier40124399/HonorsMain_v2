package CellStrategies;

import Cells.Cell;
import DataCenter.Bridge;

public class Update_TitTat implements StratUpdate
{
	public void copyStrat(Cell cell, Bridge bridge, int type)
	{
		cell.setType(type);
		cell.setHc_R(cell.getHc_NextGenR());
	}
}
