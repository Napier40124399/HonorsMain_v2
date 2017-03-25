package MultiThreading;

import java.util.ArrayList;

import Cells.Cell;

public class Threading implements Runnable
{
	private int start;
	private int end;
	private ArrayList<Cell> cells;

	private int step = 0;

	public Threading(int start, int end, ArrayList<Cell> cells)
	{
		this.start = start;
		this.end = end;
		this.cells = cells;
	}

	@Override
	public void run()
	{
		if (step == 0)
		{
			for (int i = start; i < end; i++)
			{
				threadFitness(i);
			}
		} else if (step == 1)
		{
			for (int i = start; i < end; i++)
			{
				threadNewGen(i);
			}
		} else if (step == 2)
		{
			for (int i = start; i < end; i++)
			{
				threadUpdate(i);
			}
		} else
		{
			for (int i = start; i < end; i++)
			{
				threadMutation(i);
			}
		}
	}

	private void threadFitness(int i)
	{
		cells.get(i).doFitness();
	}

	private void threadNewGen(int i)
	{
		cells.get(i).doNewGeneration();
	}

	private void threadMutation(int i)
	{
		cells.get(i).doMutationLogic();
	}

	private void threadUpdate(int i)
	{
		cells.get(i).doUpdateCell();
	}

	public Thread runStep(int step)
	{
		this.step = step;
		return new Thread(this);
	}
}
