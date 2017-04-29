package Cells;

import java.awt.Color;

import CellStrategies.Fitness_Hard;
import CellStrategies.Mutation_Hard;
import CellStrategies.Parent_Hard;
import CellStrategies.StratFitness;
import CellStrategies.StratMutation;
import CellStrategies.StratParent;
import CellStrategies.StratUpdate;
import CellStrategies.Update_Hard;

/**
 * <h1>Cell - Hardcoded</h1>This is the hardcoded subclass. This class
 * extends {@link Cells.Cellular Cell}.
 *
 * @see {@link Cells.Cellular super class}
 */
public class Cell_Hard extends Cell
{
	private Color c1;
	private Color c2;
	private StratFitness sf = new Fitness_Hard();
	private StratParent sp = new Parent_Hard();
	private StratMutation sm = new Mutation_Hard();
	private StratUpdate su = new Update_Hard();
	
	@Override
	public void doFitness()
	{
		setPd_Fitness(sf.fitnessStrat(this, getCell_Neighboors(), getHc_R(), getBridge()));
	}

	@Override
	public void doNewGeneration()
	{
		sp.parentStrat(this, getCell_Neighboors(), getBridge());
	}

	@Override
	public void doMutationLogic()
	{
		sm.mutationStrat(this, getBridge());
	}
	
	@Override
	public void doUpdateCell()
	{
		if(getBridge().getSim_Save())
		{
			if(getBridge().getCell_ColorMode() == 0)
			{
				color1();
				setC(c1);
			}else
			{
				color2();
				setC(c2);
			}
		}else
		{
			color1();
			color2();
			if(getBridge().getCell_ColorMode() == 0)
			{
				setC(c1);
			}else
			{
				setC(c2);
			}
		}
		su.copyStrat(this, getBridge(), 0);
	}
	/**
	 * <h1>Color1</h1>Creates a color based off cooperation history. Meant to represent the current and previous cell behaviour.
	 */
	private void color1()
	{
		if (getHc_R() == true)
		{
			if (getHc_R() != getHc_NextGenR())
			{
				c1 = Color.green;
			} else
			{
				c1 = Color.red;
			}
		} else
		{
			if (getHc_R() != getHc_NextGenR())
			{
				c1 = Color.yellow;
			} else
			{
				c1 = Color.blue;
			}
		}
	}

	/**
	 * <h1>Color2</h1>Creates a color based off current fitness as a percentage of maximum fitness.
	 */
	private void color2()
	{
		Float t = (float) (new Float(getCell_Neighboors().size()) * getBridge().getPd_T());
		Float tt = (float) (getPd_Fitness()) / t;
		Float ttt = tt * 250;
		int tttt = (int) (ttt * 1);
		c2 = new Color(1,1,1);//tttt, tttt, tttt);
	}
}