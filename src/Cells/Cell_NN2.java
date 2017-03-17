package Cells;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import NetworkFinal.Network;
import NetworkFinal.Remember;

/**
 * <h1>Cell_NN</h1> Neural network designed to mimic the hardcoded cell logic.
 * 
 * @author James Taylor
 *
 */
public class Cell_NN2 extends Cell
{
	private Network nextGenNet;
	private Network w;
	private Remember memory;;
	private Float accepted = 0f;
	private Float total = 0f;
	private int cooped = 0;

	public Cell_NN2()
	{
		w = new Network(10);
	}

	@Override
	public void setFitness()
	{
		cooped = 0;
		for (Cell ce : neighboors)
		{
			memory.reset();
			ce.resetMemory();
			for(int j = 0; j < bridge.getItPerGen(); j++)
			{
				Float decisionOP = ce.getOut(coords);
				Float decisionME = w.think(memory.getMem());
				
				memory.save(decisionME);
				memory.saveOP(decisionOP);
				memory.normalize();
				
				ce.handleMemory(decisionOP, decisionME);
				Float te = (-0.75f * decisionME) + (1.75f * decisionOP) + 2.5f;
				
				if(decisionME > 0){cooped+=1;}
				fitness += te;
			}
		}
	}

	@Override
	public void setNewGeneration()
	{
		/**
		 * One of two ways, either manualy copy a network or use cloneable.
		 */

		daddys = new ArrayList<Cell>();
		double old = fitness;
		for (Cell ce : neighboors)
		{
			if (ce.getPower() > old)
			{
				daddys.clear();
				daddys.add(ce);
				old = ce.getPower();
			} else if (ce.getPower() == old)
			{
				daddys.add(ce);
			}
		}
		if (daddys.size() > 0)
		{
			int tt = (int) (Math.random() * (daddys.size()));
			nextGenNet = daddys.get(tt).getNetwork();
		}
		daddys.clear();
	}
	
	@Override
	public void updateCell()
	{
		color1();
		color2();
		
		if(!nextGenNet.equals(null))
		{
			if(!nextGenNet.equals(w))
			{
				w = nextGenNet.deepClone();
			}
		}
		
		fitness = 0;
	}
	
	private void color1()
	{
		accepted = new Float(fitness);
		total = new Float(neighboors.size());
		Float tt = accepted / total;
		Float temp = (tt / 5f) / bridge.getItPerGen();
		Float fff = 250 * temp;
		int iii = (int) (fff * 1);

		c1 = new Color(iii, iii, iii);
	}
	
	private void color2()
	{
		Float nei = new Float(neighboors.size() * bridge.getItPerGen());
		nei = 255 * (new Float(cooped) / nei);
		int iii = (int) (nei * 1);
		
		c2 = new Color(iii,iii,iii);
	}

	@Override
	public void mutationLogic()
	{
		// More to come!!
		w.mutate(bridge.getMutation());
	}
	
	@Override
	public void resetMemory()
	{
		memory.reset();
	}
	
	@Override
	public void handleMemory(Float decisionME, Float decisionOP)
	{
		memory.save(decisionME);
		memory.saveOP(decisionOP);
		memory.normalize();
	}
	
	@Override
	public Float getOut(Point2D cods)
	{
		for (int i = 0; i < neighboors.size(); i++)
		{
			if (neighboors.get(i).getCoords().equals(coords))
			{
				return w.think(memory.getMem());
			}
		}
		// Failsafe
		return new Float(0);
	}
	
	@Override
	public Network getNetwork()
	{
		return w;
	}

	@Override
	public void setNeighboors()
	{
		super.setNeighboors();
		memory = new Remember(3);
	}
	
	private void Output()
	{
		
	}
	
	@Override
	public void manage(int i)
	{
		w.output();
	}
	
	@Override
	public String serialize()
	{
		String output = "";
		output = output + c1.getRed()+"/";
		output = output + c2.getRed()+"/";
		output = output + this.x + "/";
		output = output + this.y + "/";
		output = output + w.getTop();
		
		return output;
	}
}