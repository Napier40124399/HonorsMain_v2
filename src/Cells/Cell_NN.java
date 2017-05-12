package Cells;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import DataCenter.Bridge;
import NetworkFinal.Network;
import NetworkFinal.Part;
import NetworkFinal.Remember;

/**
 * <h1>Cell - Neural Network</h1>This is the neural network subclass. This class
 * extends {@link Cells.Cellular Cell}.
 *
 * @see {@link Cells.Cellular super class}
 * @author James F. Taylor
 */
public class Cell_NN extends Cell
{
	private Remember memory;
	private Float accepted = 0f;
	private Float total = 0f;
	private int coopHist = 0;

	private ArrayList<Cell_NN> cells_Local = new ArrayList<Cell_NN>();

	// NN Vars
	private Network nextGenNet;
	private Network network;

	// Temp Vars
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
		// network.defect();
	}

	@Override
	public void doNeighboors()
	{
		super.doNeighboors();
		for (Cell ce : getCell_Neighboors())
		{
			cells_Local.add((Cell_NN) ce);
		}
	}

	@Override
	public void doFitness()
	{
		setPd_Fitness(0f);
		coopHist = 0;
		temporaryFitness = 0f;
		for (Cell_NN ce : cells_Local)
		{
			memory.reset();
			ce.resetMemory();
			for (int i = 0; i < getBridge().getCell_ItPerGen(); i++)
			{
				decisionOP = ce.makeDecision();
				decisionME = network.think(memory.getMem());
				saveAll(decisionME, decisionOP);
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
		for (Cell_NN ce : cells_Local)
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
			// getCell_PotentialParents().get(choose).getNetwork().deepClone();
			Cell_NN tempCell = (Cell_NN) getCell_PotentialParents().get(choose);
			nextGenNet = tempCell.getNetwork().makeCopy();
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
				color2();
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
				network = nextGenNet;
			}
			occured = false;
		}
	}

	@Override
	public void doMutationLogic()
	{
		// More to come!!
		network.mutate(getBridge().getCell_MutationChance(), getBridge().getCell_MutationAmount(),
				getBridge().getNn_ConWeightAllowance(), getBridge().getNn_NodeRAChance(),
				getBridge().getNn_LayerRAChance(), getBridge().getNn_DynTop(), getBridge().getNn_MaxNodes());
	}

	/**
	 * <h1>Reset Memory</h1>Resets a cell's memory. Do not implement if cell has
	 * no memory.
	 */
	public void resetMemory()
	{
		memory.reset();
	}

	/**
	 * <h1>handleMemory</h1>Handles memory for the cell, should save both input
	 * Floats as memories of the previous interaction. Logic may be necessary
	 * for complex memory systems.
	 * 
	 * @param decisionOP (Float)
	 *            represents the opponent's decision.
	 * @param decisionME (Float)
	 *            represents the cell's decision.
	 */
	public void handleMemory(Float decisionME, Float decisionOP)
	{
		memory.save(decisionME);
		memory.saveOP(decisionOP);
		memory.normalize();
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

	@Override
	public int getCoopHist()
	{
		return coopHist;
	}

	// Private Methods
	private void color1()
	{
		accepted = getBridge().getCell_ItPerGen() * 5f * getCell_Neighboors().size();
		total = new Float(getPd_Fitness() / accepted);
		Float temp = total * 255;
		int col = (int) (temp * 1);
		try
		{
			c1 = new Color(col, col, col);
		} catch (Exception e)
		{
			System.out.println("Incorrect value: " + col + " >> " + getPd_Fitness() + " >> " + accepted);
			c1 = Color.red;
		}
	}

	private void color2()
	{
		Float nei = new Float(getCell_Neighboors().size() * getBridge().getCell_ItPerGen());
		nei = 255 * (new Float(coopHist) / nei);
		int iii = (int) (nei * 1);
		c2 = new Color(iii, iii, iii);
	}

	// Public communication
	/**
	 * <h1>saveAll</h1>Saves cell's own decision and opponents decision in
	 * memory.
	 * 
	 * @param decisionME
	 *            (Float)
	 * @param decisionOP
	 *            (Float)
	 */
	public void saveAll(Float decisionME, Float decisionOP)
	{
		memory.save(decisionME);
		memory.saveOP(decisionOP);
		memory.normalize();
	}

	/**
	 * <h1>Make Decision</h1>Tells a cell to decide what its next move will be.
	 * 
	 * @return Float representing the cell's move.
	 */
	public Float makeDecision()
	{
		return network.think(memory.getMem());
	}

	/**
	 * <h1>getNetwork</h1>A getter for network however this is more complex than
	 * the average getter. Extensive logic may be necessary to return the
	 * correct network.
	 * 
	 * @return Network, a copy of the cell's network.
	 */
	public Network getNetwork()
	{
		return network;
	}

	/**
	 * <h1>drawNet</h1>A troubleshooting method, used for bug fixing and
	 * ensuring neural networks work correctly after a change.
	 */
	public void drawNet()
	{
		System.out.println(network.getTop());
	}

	/**
	 * <h1>validate</h1>
	 */
	private void validate()
	{
		ArrayList<Float> checker = new ArrayList<Float>();
		for (int i = 0; i < memory.getMem().size(); i++)
		{
			checker.add(new Float(Math.random()));
		}
		Float check = network.think(checker);
		System.out.println("Checked " + check);
		if (check == 0f)
		{
			getNetwork().showCons();
		}
	}
}