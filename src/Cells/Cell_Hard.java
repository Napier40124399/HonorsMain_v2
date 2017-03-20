package Cells;

import java.awt.Color;
import java.util.ArrayList;

/**
 * <h1>Cell - Hard Coded</h1> This is the cell subclass for hardcoded cells.
 *
 * @see {@link Cells.Cellular super class}
 * @see {@link Cells.Cell interface}
 */
public class Cell_Hard extends Cell
{
	private Color c1;
	private Color c2;
	
	@Override
	public void doFitness()
	{
		setPd_Fitness(0f);
		for (Cell ce : getCell_Neighboors())
		{
			if (!ce.getHc_R())
			{
				if (getHc_R())
				{
					setPd_Fitness(new Float(getPd_Fitness() + getBridge().getPd_T()));
				} else
				{
					setPd_Fitness(new Float(getPd_Fitness() + getBridge().getPd_R()));
				}
			} else
			{
				if (getHc_R())
				{
					setPd_Fitness(new Float(getPd_Fitness() + getBridge().getPd_P()));
				} else
				{
					setPd_Fitness(new Float(getPd_Fitness() + getBridge().getPd_S()));
				}
			}
		}
	}

	@Override
	public void doNewGeneration()
	{
		setCell_PotentialParents(new ArrayList<Cell>());
		Float old = getPd_Fitness();
		for (Cell ce : getCell_Neighboors())
		{
			if (ce.getPd_Fitness() > old)
			{
				getCell_PotentialParents().clear();
				getCell_PotentialParents().add(ce);
				old = ce.getPd_Fitness();
			} else if (ce.getPd_Fitness() == old)
			{
				getCell_PotentialParents().add(ce);
			}
		}
		if (getCell_PotentialParents().size() > 0)
		{
			setHc_NextGenR(getCell_PotentialParents().get(((int) (Math.random() * (getCell_PotentialParents().size()))))
					.getHc_R());
		}else{setHc_NextGenR(getHc_R());}
	}

	@Override
	public void doMutationLogic()
	{
		if(Math.random() < getBridge().getCell_Mutation())
		{
			setHc_NextGenR(!getHc_NextGenR());
		}
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
		setHc_R(getHc_NextGenR());
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
}