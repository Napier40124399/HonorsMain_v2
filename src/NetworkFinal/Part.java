package NetworkFinal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

@SuppressWarnings("serial")
public class Part implements Cloneable, Serializable
{
	private ArrayList<Part> next = new ArrayList<Part>();
	private ArrayList<Float> weights = new ArrayList<Float>();
	private Float input = new Float(0);

	public void pass()
	{
		for (int i = 0; i < weights.size(); i++)
		{
			next.get(i).receive(output(i));
		}
		reset();
	}

	public void handle()
	{
		passThroughFunction();
	}

	public void receive(Float input)
	{
		this.input += input;
	}

	public void addNode(Part part)
	{
		Random r = new Random();
		next.add(part);
		weights.add(new Float(r.nextGaussian() / 2));
	}
	
	public void addPrepNode(Part part)
	{
		next.add(part);
	}
	
	public void addNode(Part part, Float con)
	{
		next.add(part);
		weights.add(con);
	}
	
	public void removeNode(Part part)
	{
		int ii = 0;
		for(int i = 0; i < next.size(); i++)
		{
			if(next.get(i).equals(part))
			{
				ii = i;
				break;
			}
		}
		weights.remove(ii);
		next.remove(ii);
	}

	public void mutate(Float mutationAmount, Float mutationChance, Float conWeightAllowance)
	{
		for (int i = 0; i < weights.size(); i++)
		{
			if(new Float(Math.random()) > mutationChance)
			{
				weights.set(i, weights.get(i) * mutationAmount);
			}
			if(weights.get(i) > conWeightAllowance || weights.get(i) < -conWeightAllowance)
			{
				weights.set(i, (weights.get(i) * 0.9f));
			}
		}
	}
	
	public void becomeDefector()
	{
		for(int i = 0; i < weights.size(); i++)
		{
			weights.set(i, -1f);
		}
	}
	
	public void setNext(ArrayList<Part> next)
	{
		this.next.clear();
		this.next = (ArrayList<Part>) next.clone();
	}
	
	public void setWeights(ArrayList<Float> weights)
	{
		this.weights.clear();
		this.weights = (ArrayList<Float>) weights.clone();
	}

	public Float getVal()
	{
		passThroughFunction();
		return this.input;
	}

	// PRIVATE METHODS

	private void passThroughFunction()
	{

		Float tel = new Float(2 * (Math.pow((1 + Math.exp(-input)), -1) - 0.5));
		
		input = tel;
	}

	private void reset()
	{
		input = 0f;
	}
	
	public void showWeights()
	{
		for(Float f : weights)
		{
			System.out.println(f);
		}
	}

	private Float output(int i)
	{
		return weights.get(i) * input;
	}

	private Float mutate(int i, Float mutate)
	{
		Random r = new Random();
		Float tt = new Float(r.nextGaussian() * mutate);
		return weights.get(i) + tt;
	}

	public ArrayList<Float> getCon()
	{
		return (ArrayList<Float>) this.weights.clone();
	}
	
	public Float[] getCons()
	{
		Float[] cons = new Float[weights.size()];
		for(int i = 0; i < cons.length; i++)
		{
			cons[i] = weights.get(i);
		}
		
		return cons;
	}

	public void sett(ArrayList<Float> con)
	{
		weights = (ArrayList<Float>) con.clone();
	}
	
	public void getWeights()
	{
		for(Float f : weights)
		{
			System.out.println(f);
		}
	}
	
	public Part partialCopy()
	{
		Part part = new Part();
		part.setWeights(weights);
		return part;
	}
}
