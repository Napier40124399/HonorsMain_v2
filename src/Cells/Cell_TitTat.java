package Cells;

import java.awt.Color;
import java.util.ArrayList;

import CellStrategies.Fitness_TitTat;
import CellStrategies.Mutation_TitTat;
import CellStrategies.Parent_TitTat;
import CellStrategies.StratFitness;
import CellStrategies.StratMutation;
import CellStrategies.StratParent;
import CellStrategies.StratUpdate;
import CellStrategies.Update_TitTat;

/**
 * <h1>Cell - Tit For Tat</h1>This is the tit for tat subclass. This class
 * extends {@link Cells.Cellular Cell}.
 *
 * @see {@link Cells.Cellular super class}
 * 
 * @author James F. Taylor
 */
public class Cell_TitTat extends Cell
{
	private Color c1;
	private Color c2;
	private int type = 0;
	private int nextGenType = 0;
	private Boolean memory = true;
	private StratFitness sf = new Fitness_TitTat();
	private StratParent sp = new Parent_TitTat();
	private StratUpdate su = new Update_TitTat();
	private StratMutation sm = new Mutation_TitTat();

	private ArrayList<Cell_TitTat> cells_Local = new ArrayList<Cell_TitTat>();

	@Override
	public void doFitness()
	{
		setPd_Fitness(sf.fitnessStrat(this, getCell_Neighboors(), getHc_R(), getBridge()));
	}

	@Override
	public void doNewGeneration()
	{
		nextGenType = sp.parentStrat(this, getCell_Neighboors(), getBridge());
	}

	@Override
	public void doUpdateCell()
	{
		memory = true;
		color1();
		setC(c1);
		if (type == 1)
		{
			setC(new Color(0, 255, 100));
		}
		su.copyStrat(this, getBridge(), nextGenType);
	}

	@Override
	public void doMutationLogic()
	{
		sm.mutationStrat(this, getBridge());
	}

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

	private void color2()
	{
		Float t = (float) (new Float(getCell_Neighboors().size()) * getBridge().getPd_T());
		Float tt = (float) (getPd_Fitness()) / t;
		Float ttt = tt * 250;
		int tttt = (int) (ttt * 1);
		c2 = new Color(tttt, tttt, tttt);
	}

	@Override
	public Boolean getHc_R(Boolean hc_R)
	{
		if (type == 1)
		{
			Boolean giving = memory;
			memory = hc_R;
			return giving;
		} else
		{
			return getHc_R();
		}
	}

	@Override
	public int getType()
	{
		return this.type;
	}

	@Override
	public void setType(int type)
	{
		this.type = type;
	}

	public void resetMemory()
	{
		memory = true;
	}
}