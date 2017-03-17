package Scenario;

import java.util.ArrayList;

import Cells.Cell;
import DataCenter.Bridge;

public class SplitTask
{
	private ArrayList<Thread> t1;
	private ArrayList<Thread> t2;
	private ArrayList<Thread> t3;
	private ArrayList<Thread> t4;

	public void splitTasks(ArrayList<Cell> cells, int cores)
	{

		t1 = new ArrayList<Thread>();

		Float f = new Float(cells.size() / cores);

		for (int i = 0; i < cores; i++)
		{
			final int val = i;
			t1.add(new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					int Start = (int) (val * f);
					int End = (int) ((val + 1) * f);
					for (int i = Start; i < End; i++)
					{
						step1(cells, i);
					}
				}
			}));
		}

		t2 = new ArrayList<Thread>();

		for (int i = 0; i < cores; i++)
		{
			final int val = i;
			t2.add(new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					int Start = (int) (val * f);
					int End = (int) ((val + 1) * f);
					for (int i = Start; i < End; i++)
					{
						step2(cells, i);
					}
				}
			}));
		}

		t3 = new ArrayList<Thread>();

		for (int i = 0; i < cores; i++)
		{
			final int val = i;
			t3.add(new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					int Start = (int) (val * f);
					int End = (int) ((val + 1) * f);
					for (int i = Start; i < End; i++)
					{
						step3(cells, i);
					}
				}
			}));
		}

		t4 = new ArrayList<Thread>();

		for (int i = 0; i < cores; i++)
		{
			final int val = i;
			t4.add(new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					int Start = (int) (val * f);
					int End = (int) ((val + 1) * f);
					for (int i = Start; i < End; i++)
					{
						step4(cells, i);
					}
				}
			}));
		}
	}

	public void doThreading(int caser)
	{
		switch (caser)
		{
		case 1:
			st1();
			break;
		case 2:
			st2();
			break;
		case 3:
			st3();
			break;
		case 4:
			st4();
			break;
		}
	}

	private void st1()
	{
		ArrayList<Thread> threads = (ArrayList<Thread>) t1.clone();
		for (Thread t : threads)
		{
			t.start();
		}
		for (Thread t : threads)
		{
			try
			{
				t.join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void st2()
	{
		ArrayList<Thread> threads = (ArrayList<Thread>) t2.clone();
		for (Thread t : threads)
		{
			t.start();
		}
		for (Thread t : threads)
		{
			try
			{
				t.join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void st3()
	{
		ArrayList<Thread> threads = (ArrayList<Thread>) t3.clone();
		for (Thread t : threads)
		{
			t.start();
		}
		for (Thread t : threads)
		{
			try
			{
				t.join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void st4()
	{
		ArrayList<Thread> threads = (ArrayList<Thread>) t4.clone();
		for (Thread t : threads)
		{
			t.start();
		}
		for (Thread t : threads)
		{
			try
			{
				t.join();
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void splitCores(ArrayList<Cell> cells, int cores, Bridge bridge)
	{
		ArrayList<Thread> threads = new ArrayList<Thread>();

		Float f = new Float(cells.size() / cores);

		for (int i = 0; i < cores; i++)
		{
			final int val = i;
			threads.add(new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					int Start = (int) (val * f);
					int End = (int) ((val + 1) * f);
					for (int i = Start; i < End; i++)
					{
						cells.get(i).setFitness();
					}
				}
			}));
		}

		for (Thread tt : threads)
		{
			tt.start();
		}

		for (Thread tt : threads)
		{
			try
			{
				tt.join();
			} catch (InterruptedException e)
			{
			}
		}
	}

	public void splitCores(ArrayList<Cell> cells, int cores, Bridge bridge, int caser)
	{
		ArrayList<Thread> threads = new ArrayList<Thread>();

		Float f = new Float(cells.size() / cores);

		for (int i = 0; i < cores; i++)
		{
			final int val = i;
			threads.add(new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					int Start = (int) (val * f);
					int End = (int) ((val + 1) * f);
					for (int i = Start; i < End; i++)
					{
						switch (caser)
						{
						case 1:
							step1(cells, i);
							break;
						case 2:
							step2(cells, i);
							break;
						case 3:
							step3(cells, i);
							break;
						case 4:
							step4(cells, i);
							break;
						}
					}
				}
			}));
		}

		for (Thread tt : threads)
		{
			tt.start();
		}

		for (Thread tt : threads)
		{
			try
			{
				tt.join();
			} catch (InterruptedException e)
			{
			}
		}
	}

	private void step1(ArrayList<Cell> cells, int i)
	{
		cells.get(i).setFitness();
	}

	private void step2(ArrayList<Cell> cells, int i)
	{
		cells.get(i).setNewGeneration();
	}

	private void step3(ArrayList<Cell> cells, int i)
	{
		cells.get(i).mutationLogic();
	}

	private void step4(ArrayList<Cell> cells, int i)
	{
		cells.get(i).updateCell();
	}

	public void splitCores(Bridge bridge, ArrayList<Cell> cells)
	{
		for (int i = 0; i < bridge.getItPerGen(); i++)
		{
			for (Cell c : cells)
			{
				c.setFitness(); // FITNESS
			}
		}
	}
}
