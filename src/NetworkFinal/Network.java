package NetworkFinal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class Network implements Cloneable, Serializable
{
	private ArrayList<Part> parts;
	private ArrayList<ArrayList<Part>> fabric = new ArrayList<ArrayList<Part>>();
	private ArrayList<Float> biases = new ArrayList<Float>();
	private Float id;
	private Float biasWeight = 0.001f;
	private Double newNodeChance = 0.05;

	// NEW
	private Part outputNode;

	public Network(Integer[] size)
	{
		setUp(size);
	}

	public void setUp(Integer[] size)
	{
		outputNode = new Part();

		Random r = new Random();
		parts = new ArrayList<Part>();
		for (int i = 0; i < size[0]; i++)
		{
			biases.add(new Float(r.nextGaussian() * biasWeight));
			parts.add(new Part());
		}
		fabric.add(parts);

		for (int i = 1; i < size.length; i++)
		{
			addLayer();
			for (int j = 0; j < size[i] - 1; j++)
			{
				addNode(i);
			}
		}
	}

	public Float think(ArrayList<Float> memory)
	{
		ArrayList<Float> temp = new ArrayList<Float>();
		for (int i = 0; i < memory.size(); i++)
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
		for (int i = 1; i < fabric.size(); i++)
		{
			for (Part p : fabric.get(i))
			{
				p.handle();
				p.pass();
			}
		}
		Float res = outputNode.getVal();
		outputNode.pass();
		return res;
	}

	//A complicated method to use
	public void mutate(Float mutationChance, Float muationAmount, Float conWeightAllowance, Float nodeChangeChance,
			Float layerChangeChance, Boolean dynTop, int maxNodes)
	{
		// All initialized the same way, this is the difference
		for (ArrayList<Part> layer : fabric)
		{
			for (Part p : layer)
			{
				p.mutate(muationAmount, mutationChance, conWeightAllowance);
			}
		}
		outputNode.mutate(muationAmount, mutationChance, conWeightAllowance);

		// Dynamic topology logic
		if (dynTop)
		{
			for (int i = 1; i < fabric.size(); i++)
			{
				for (Part p : fabric.get(i))
				{
					if (Math.random() < nodeChangeChance)
					{
						if (fabric.get(i).size() > 1)
						{
							removeNode(i, p);
						}else
						{
							removeLayer(i);
						}
					}
					
				}
				if (fabric.get(i).size() < maxNodes)
				{
					if (Math.random() < nodeChangeChance)
					{
						addNode(i);
					}
				}
			}
			if(Math.random() < layerChangeChance)
			{
				addLayer();
			}
		}

		// Biases
		Random r = new Random();
		for (int i = 0; i < 6; i++)
		{
			biases.set(i, new Float(biases.get(i) + (r.nextGaussian() * biasWeight)));
		}

		for (int i = 0; i < 6; i++)
		{
			if (biases.get(i) >= biasWeight * 10)
			{
				biases.set(i, new Float(biasWeight * 10));
			} else if (biases.get(i) <= -biasWeight * 10)
			{
				biases.set(i, new Float(-biasWeight * 10));
			}
		}
	}

	private void addLayer()
	{
		for (Part p : fabric.get(fabric.size() - 1))
		{
			p.removeNode(outputNode);
		}
		fabric.add(new ArrayList<Part>());
		addNode(fabric.size() - 1);
	}
	
	private void removeLayer(int i)
	{
		for(Part p : fabric.get(i))
		{
			fabric.get(i).remove(p);
		}
		
		fabric.remove(i);
		
		if(i == fabric.size()-1)
		{
			for(Part p : fabric.get(i-1))
			{
				p.addNode(outputNode);
			}
		}else
		{
			for(Part p1 : fabric.get(i-1))
			{
				for(Part p2 : fabric.get(i))
				{
					p1.addNode(p2);
				}
			}
		}
	}

	private void addNode(int i)
	{
		fabric.get(i).add(new Part());
		for (Part p : fabric.get(i - 1))
		{
			p.addNode(fabric.get(i - 1).get(fabric.get(i - 1).size() - 1));
		}
		fabric.get(i - 1).get(fabric.get(i - 1).size() - 1).addNode(outputNode);
	}

	private void removeNode(int i, Part p)
	{
		for (Part previousP : fabric.get(i - 1))
		{
			previousP.removeNode(p);
		}
	}

	public Network deepClone()
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			Network net = (Network) ois.readObject();
			return net;
		} catch (IOException e)
		{
			return null;
		} catch (ClassNotFoundException e)
		{
			return null;
		}
	}

	public String getTop()
	{
		String temp = "";

		for (ArrayList<Part> layer : fabric)
		{
			temp = temp + layer.size() + "-";
		}

		return temp;
	}
}
