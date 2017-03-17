package MultiThreading;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

public class SplitTask
{
	private ArrayList<Threading> threads;

	public void splitTasks(ArrayList<Cell> cells, int threadCount)
	{
		Float f = new Float(cells.size() / threadCount);
		threads = new ArrayList<Threading>();
		int temp = 0;
		for (int i = 0; i < threadCount; i++)
		{
			int start = (int) (i * f);
			int end = (int) ((i + 1) * f);
			if(i == threadCount-1)
			{
				end = cells.size();
			}
			threads.add(new Threading(start, end, cells));
		}
	}
	
	public void threadFitness()
	{
		for(Threading t : threads)
		{
			t.runStep(0);
		}
	}
	
	public void threadNewGen()
	{
		for(Threading t : threads)
		{
			t.runStep(1);
		}
	}
	
	public void threadMutation()
	{
		for(Threading t : threads)
		{
			t.runStep(2);
		}
	}
	
	public void threadUpdate()
	{
		for(Threading t : threads)
		{
			t.runStep(3);
		}
	}
}
