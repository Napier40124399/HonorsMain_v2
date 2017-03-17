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
public class Part implements Serializable
{
	private ArrayList<Part> next = new ArrayList<Part>();
	private ArrayList<Float> weights = new ArrayList<Float>();
	private Float input = new Float(0);

	public Part()
	{
		
	}

	public void pass()
	{
		for (int i = 0; i < weights.size(); i++)
		{
			//next.get(i).receive(1f);
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

	public void mutate(Float mute)
	{
		for (int i = 0; i < weights.size(); i++)
		{
			weights.set(i, mutate(i, mute));
			if(weights.get(i) > 0.5 || weights.get(i) < -0.5)
			{
				weights.set(i, (weights.get(i) * 0.9f));
			}
		}
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

	private Float output(int i)
	{
		return weights.get(i) * input;
	}

	public void makeCoop()
	{
		for (int i = 0; i < weights.size(); i++)
		{
			weights.set(i, 10f);
		}
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

	public void sett(ArrayList<Float> con)
	{
		weights = (ArrayList<Float>) con.clone();
	}

	public Part deepClone()
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Part) ois.readObject();
		} catch (IOException e)
		{
			return null;
		} catch (ClassNotFoundException e)
		{
			return null;
		}
	}
	
	public void showCon()
	{
		System.out.println("= Cons =");
		for(Float f : weights)
		{
			System.out.println(f);
		}
	}
}
