package NetworkFinal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class Network implements Cloneable, Serializable
{
	private ArrayList<Part> parts;
	private ArrayList<ArrayList<Part>> fabric = new ArrayList<ArrayList<Part>>();
	private ArrayList<Float> biases = new ArrayList<Float>();
	private Float id;
	private Float biasWeight = 0.0001f;

	public Network(int size)
	{
		if (size != 0)
		{
			setUp(size);
		}
	}

	public Float think(ArrayList<Float> memory)
	{
		ArrayList<Float> temp = new ArrayList<Float>();
		for (int i = 0; i < 6; i++)
		{
			temp.add(new Float(biases.get(i) + memory.get(i)));
		}
		int j = 0;
		for (Part p : fabric.get(0))
		{
			p.receive(temp.get(j));
			p.pass();
			j++;
		}
		int ssize = fabric.size()-1;
		for (int i = 1; i < ssize; i++)
		{
			for (Part p : fabric.get(i))
			{
				p.handle();
				p.pass();
			}
		}
		Float res = fabric.get(ssize).get(0).getVal();
		return res;
	}

	public void setUp(int size)
	{
		Random r = new Random();
		for (int i = 0; i < 6; i++)
		{
			biases.add(new Float(r.nextGaussian() * biasWeight));
		}
		
		parts = new ArrayList<Part>();
		parts.add(new Part());
		parts.add(new Part());
		parts.add(new Part());
		parts.add(new Part());
		parts.add(new Part());
		parts.add(new Part());
		fabric.add(parts);

		addLayer();
		/*
		for (int i = 0; i < size-1; i++)
		{
			addNode(1);
		}
		*/
		addLayer();
	}
	
	public void makeDefect()
	{
		
	}

	public void mutate(Float mutate, Boolean dynTop, int maxNodes)
	{
		// All initialized the same way, this is the difference
		for (ArrayList<Part> layer : fabric)
		{
			for (Part p : layer)
			{
				p.mutate(mutate);
				//p.makeCoop();
			}
		}

		Random r = new Random();
		for (int i = 0; i < 6; i++)
		{
			biases.set(i, new Float(biases.get(i) + (r.nextGaussian() * biasWeight)));
		}
		for(int i = 0; i < 6; i++)
		{
			if(biases.get(i) >= biasWeight)
			{
				biases.set(i, new Float(biasWeight));
			}else if(biases.get(i) <= -biasWeight)
			{
				biases.set(i, new Float(-biasWeight));
			}
		}
		
		if(dynTop)
		{
			if(Math.random() > 0.995)
			{
				if(fabric.get(fabric.size()-2).size() <= maxNodes)
				{
					addNodeHot(fabric.size()-2);
				}else 
				{
					addLayer();
				}
			}
		}
	}

	private void addLayer()
	{
		fabric.add(new ArrayList<Part>());
		addNode(fabric.size() - 1);
	}

	private void addNode(int i)
	{
		Part part = new Part();
		fabric.get(i).add(part);
		if (i > 0)
		{
			for (Part p : fabric.get(i - 1))
			{
				p.addNode(part);
			}
		}
	}
	
	private void addNodeHot(int i)
	{
		Part part = new Part();
		fabric.get(i).add(part);
		if (i > 0)
		{
			for (Part p : fabric.get(i - 1))
			{
				p.addNode(part);
			}
			for(Part p : fabric.get(i + 1))
			{
				part.addNode(p);
			}
		}
	}

	private void addNodeR()
	{
		int i = (int) (Math.random() * (fabric.size() - 1));
		if (i != 0)
		{
			if (i != fabric.size() - 1)
			{
				Part pp = new Part();
				fabric.get(i).add(pp);
				if (i > 0)
				{
					for (Part p : fabric.get(i - 1))
					{
						p.addNode(pp);
					}
				}
			}
		}
	}
	
	public Network deepClone()
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Network) ois.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public String getTop()
	{
		String temp = "";
		
		for(ArrayList<Part> layer : fabric)
		{
			temp = temp + layer.size() + "-";
		}
		
		return temp;
	}
	
	public void output()
	{
		System.out.println("Fabric size: "+fabric.size());
		for(ArrayList<Part> layer : fabric)
		{
			System.out.println("Layer size: "+layer.size());
		}
		System.out.println("===== Biases =====");
		for(Float f : biases)
		{
			System.out.println(f);
		}
		for(ArrayList<Part> level : fabric)
		{
			System.out.println("=== Weights ===");
			for(Part p : level)
			{
				p.showCon();
			}
		}
	}
}
