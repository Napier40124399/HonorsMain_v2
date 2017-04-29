package CellStrategies;

import Cells.Cell;
import DataCenter.Bridge;

public class Mutation_TitTat implements StratMutation
{
	public void mutationStrat(Cell ce, Bridge bridge)
	{
		if(ce.getType() == 0)
		{
			if(Math.random() < bridge.getCell_MutationChance()*0.01)
			{
				ce.setType(1);
			}
			if(Math.random() < bridge.getCell_MutationChance())
			{
				//ce.setHc_R(!ce.getHc_R());
			}
		}
	}
}
