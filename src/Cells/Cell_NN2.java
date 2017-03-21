package Cells;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import DataCenter.Bridge;
import NetworkFinal.Network;
import NetworkFinal.Part;
import NetworkFinal.Remember;

/**
 * <h1>Cell_NN</h1> Neural network designed to mimic the hardcoded cell logic.
 * 
 * @author James Taylor
 *
 */
public class Cell_NN2 extends Cell
{
	private Remember memory;
	private Float accepted = 0f;
	private Float total = 0f;
	private int coopHist = 0;

	// NN Vars
	private Network nextGenNet;
	private Network network;

	// Temp Vars
	private ArrayList<ArrayList<Part>> nextGenFab;
	private ArrayList<Float> nextGenBias;
	private Boolean occured = false;

	// PD Vars
	private Float decisionOP;
	private Float decisionME;
	private Float temporaryFitness;

	// Visual Vars
	private Color c1;
	private Color c2;

	// Initialize network and memory
	@Override
	public void Initialize(boolean hc_R, int pos_X, int pos_Y, Bridge bridge)
	{
		super.Initialize(hc_R, pos_X, pos_Y, bridge);
		network = new Network(getBridge().getNn_Topology());
		memory = new Remember(getBridge().getNn_Topology()[0] / 2);
	}

	@Override
	public void doFitness()
	{
		coopHist = 0;
		temporaryFitness = 0f;
		for (Cell ce : getCell_Neighboors())
		{
			memory.reset();
			ce.resetMemory();
			for (int i = 0; i < getBridge().getCell_ItPerGen(); i++)
			{
				decisionOP = ce.makeDecisionCorrected();
				decisionME = network.think(memory.getMem());
				memory.save(decisionME);
				memory.saveOP(decisionOP);
				memory.normalize();
				ce.handleMemory(decisionOP, decisionME);
				temporaryFitness += (-0.75f * decisionME) + (1.75f * decisionOP) + 2.5f;
				if (decisionME > 0)
				{
					coopHist += 1;
				}
			}
		}
		setPd_Fitness(temporaryFitness);
	}

	@Override
	public void doNewGeneration()
	{
		setCell_PotentialParents(new ArrayList<Cell>());
		temporaryFitness = getPd_Fitness();
		for (Cell ce : getCell_Neighboors())
		{
			if (ce.getPd_Fitness() > temporaryFitness)
			{
				getCell_PotentialParents().clear();
				getCell_PotentialParents().add(ce);
				temporaryFitness = ce.getPd_Fitness();
			} else if (ce.getPd_Fitness() == temporaryFitness)
			{
				getCell_PotentialParents().add(ce);
			}
		}
		if (getCell_PotentialParents().size() > 0)
		{
			int choose = (int) (Math.random() * (getCell_PotentialParents().size()));
			nextGenNet = getCell_PotentialParents().get(choose).getNetwork().deepClone();
			occured = true;
		} else
		{
			nextGenNet = null;
		}
		getCell_PotentialParents().clear();
	}

	@Override
	public void doUpdateCell()
	{
		if (getBridge().getSim_Save())
		{
			if (getBridge().getCell_ColorMode() == 0)
			{
				color1();
				setC(c1);
			} else
			{
				color2();
				setC(c2);
			}
		} else
		{
			color1();
			color2();
			if (getBridge().getCell_ColorMode() == 0)
			{
				setC(c1);
			} else
			{
				setC(c2);
			}
		}
		if (occured)
		{
			if (!nextGenNet.equals(network))
			{
				network = nextGenNet.deepClone();
			}
			occured = false;
		}

		setPd_Fitness(0f);
	}

	@Override
	public void doMutationLogic()
	{
		// More to come!!
		network.mutate(getBridge().getCell_MutationChance(), getBridge().getCell_MutationAmount(),
				getBridge().getNn_ConWeightAllowance(), getBridge().getNn_NodeRAChance(),
				getBridge().getNn_LayerRAChance(), getBridge().getNn_DynTop(), getBridge().getNn_MaxNodes());
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
	public Float makeDecisionCorrected()
	{
		return network.think(memory.getMem());
	}

	@Override
	public Network getNetwork()
	{
		return network;
	}

	@Override
	public void drawNet()
	{
		System.out.println(network.getTop());
	}

	@Override
	public String serialize()
	{
		String output = "";
		output = output + c1.getRed() + "/";
		output = output + c2.getRed() + "/";
		output = output + getPos_X() + "/";
		output = output + getPos_Y() + "/";
		output = output + network.getTop();

		return output;
	}

	// Private Methods
	private void color1()
	{
		accepted = getPd_Fitness();
		total = new Float(getCell_Neighboors().size());
		Float tt = accepted / total;
		Float temp = (tt / 5f) / getBridge().getCell_ItPerGen();
		Float fff = 250 * temp;
		int iii = (int) (fff * 1);

		c1 = new Color(iii, iii, iii);
	}

	private void color2()
	{
		Float nei = new Float(getCell_Neighboors().size() * getBridge().getCell_ItPerGen());
		nei = 255 * (new Float(coopHist) / nei);
		int iii = (int) (nei * 1);

		c2 = new Color(iii, iii, iii);
	}
}