package NetworkFinal;

import java.util.ArrayList;

public class Remember
{
	private ArrayList<Float> memory = new ArrayList<Float>();
	private ArrayList<Float> memCur = new ArrayList<Float>();
	
	private ArrayList<Float> opMem = new ArrayList<Float>();
	private ArrayList<Float> opCur = new ArrayList<Float>();
	
	private int size;
	
	public Remember(int size)
	{
		this.size = size;
		for(int i = 0; i < size; i++)
		{
			memory.add(new Float(0));
			opMem.add(new Float(0));
		}
		memCur = (ArrayList<Float>) memory.clone();
		opCur = (ArrayList<Float>) opMem.clone();
	}
	
	public void reset()
	{
		memory.clear();
		memCur.clear();
		opMem.clear();
		opCur.clear();
		for(int i = 0; i < size; i++)
		{
			memory.add(new Float(0));
			opMem.add(new Float(0));
		}
		memCur = (ArrayList<Float>) memory.clone();
		opCur = (ArrayList<Float>) opMem.clone();
	}
	
	public ArrayList<Float> getMem()
	{
		ArrayList temp = new ArrayList<Float>();
		
		for(int i = 0; i < size; i++)
		{
			temp.add(memory.get(i));
		}
		for(int i = 0; i < size; i++)
		{
			temp.add(opMem.get(i));
		}
		return temp;
	}
	
	public void normalize()
	{
		memory = (ArrayList<Float>) memCur.clone();
		opMem = (ArrayList<Float>) opCur.clone();
	}
	
	public Float getMem(int i)
	{
		return memory.get(i);
	}
	
	public void save(Float mem)
	{
		memCur.add(mem);
		memCur.remove(0);
	}
	
	public void saveOP(Float mem)
	{
		opCur.add(mem);
		opCur.remove(0);
	}
}
