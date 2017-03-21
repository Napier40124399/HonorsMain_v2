package DataCenter;

import java.util.ArrayList;

import Cells.Cell;

/**
 * @author hoijf
 *         <h1>Bridge</h1>The bridge controls all the data running between
 *         threads. This allows for each part of the system to communicate with
 *         oneanother. In this manner everything is accessbile from anywhere.
 *         Only drawback is all instances of any class wanting to communicate
 *         need access to the same instance of this class as all the others.
 *         <br>
 *         Using singleton pattern may alleviate this somewhat but everything
 *         currently works just fine and singleton is messy.
 * 
 * @param cell_ArrayList
 *            is one of two data structures used to store all cells.
 * @param cell_Array
 *            is one of two data structure used to store all cells.
 * @param cell_Quantity
 *            defines the square root of quantity of cells.
 * @param cell_ColorMode
 *            defines the color mode used to draw the cells.
 * @param cell_ItPerGen
 *            defines the amount of iterations between each generation.
 *            (Quantity of interactions between each cell and its neighbors over
 *            a single generation).
 * @param cell_MaxGen
 *            defines the amount of generations to run the simulation for.
 * @param cell_NeiDistance
 *            defines the distance over which a cell may consider other cells
 *            its neighbors.
 * @param cell_Mutation
 *            defines the strength of mutation. Usualy keep between 0.001 and
 *            0.1.
 */
public class Bridge
{
	// Cell Variables
	private ArrayList<Cell> cell_ArrayList;
	private Cell cell_Array[];
	private int cell_Quantity;
	private int cell_ColorMode;
	private int cell_ItPerGen;
	private int cell_MaxGen;
	private int cell_NeiDistance;
	private Float cell_MutationAmount = 0.01f;
	private Float cell_MutationChance;
	// PD Variables
	private Float pd_T;
	private Float pd_R;
	private Float pd_P;
	private Float pd_S;
	private Boolean pd_Taurus;
	// Neural Network Variables
	private int nn_MaxNodes;
	private Float nn_ConWeightAllowance = 1f;
	private Integer[] nn_Topology;
	private Boolean nn_DynTop;
	private Float nn_NodeRAChance= 0.05f;  //Chance a node is removed or added
	private Float nn_LayerRAChance= 0.05f;  //Chance a node is removed or added
	// Simulation Variables
	private Boolean sim_Running;
	private Boolean sim_Paused;
	private Boolean sim_Save;
	private String sim_SavePath;
	private int sim_SaveDelay;
	private int sim_Threads;
	private int sim_Delay;
	private int sim_CurGen;
	// Draw Variables
	private int draw_Delay;
	private int draw_Scale;
	
	//Internal Logic Variables
	private Boolean fireThreadChange = false;

	public void setUp(int cell_Quantity, int cell_ColorMode, int cell_ItPerGen,
			int cell_MaxGen, int cell_NeiDistance, Float cell_MutationChance, Float pd_T, Float pd_R, Float pd_P, Float pd_S,
			Boolean pd_Taurus, int nn_MaxNodes, Boolean sim_Save, String sim_SavePath, int sim_SaveDelay,
			int sim_Threads, int sim_Delay, int draw_Delay, int draw_Scale)
	{
		this.cell_Quantity		= cell_Quantity;
		this.cell_ColorMode		= cell_ColorMode;
		this.cell_ItPerGen		= cell_ItPerGen;
		this.cell_MaxGen		= cell_MaxGen;
		this.cell_NeiDistance	= cell_NeiDistance;
		this.cell_MutationChance= cell_MutationChance;
		this.pd_Taurus		= pd_Taurus;
		this.nn_MaxNodes	= nn_MaxNodes;
		this.sim_Save		= sim_Save;
		this.sim_SavePath	= sim_SavePath;
		this.sim_SaveDelay	= sim_SaveDelay;
		this.sim_Threads	= sim_Threads;
		this.sim_Delay		= sim_Delay;
		this.draw_Delay		= draw_Delay;
		this.draw_Scale		= draw_Scale;
		this.pd_T	= pd_T;
		this.pd_R	= pd_R;
		this.pd_P	= pd_P;
		this.pd_S	= pd_S;
		sim_Running	= false;
		sim_Paused	= false;
	}

	public ArrayList<Cell> getCell_ArrayList()
	{
		return cell_ArrayList;
	}

	public void setCell_ArrayList(ArrayList<Cell> cell_ArrayList)
	{
		this.cell_ArrayList = cell_ArrayList;
	}

	public Cell[] getCell_Array()
	{
		return cell_Array;
	}

	public void setCell_Array(Cell[] cell_Array)
	{
		this.cell_Array = cell_Array;
	}

	public int getCell_Quantity()
	{
		return cell_Quantity;
	}

	public void setCell_Quantity(int cell_Quantity)
	{
		this.cell_Quantity = cell_Quantity;
	}

	public int getCell_ColorMode()
	{
		return cell_ColorMode;
	}

	public void setCell_ColorMode(int cell_ColorMode)
	{
		this.cell_ColorMode = cell_ColorMode;
	}

	public int getCell_ItPerGen()
	{
		return cell_ItPerGen;
	}

	public void setCell_ItPerGen(int cell_ItPerGen)
	{
		this.cell_ItPerGen = cell_ItPerGen;
	}

	public int getCell_MaxGen()
	{
		return cell_MaxGen;
	}

	public void setCell_MaxGen(int cell_MaxGen)
	{
		this.cell_MaxGen = cell_MaxGen;
	}

	public int getCell_NeiDistance()
	{
		return cell_NeiDistance;
	}

	public void setCell_NeiDistance(int cell_NeiDistance)
	{
		this.cell_NeiDistance = cell_NeiDistance;
	}

	public Float getCell_MutationAmount()
	{
		return cell_MutationAmount;
	}

	public void setCell_MutationAmount(Float cell_Mutation)
	{
		this.cell_MutationAmount = cell_Mutation;
	}

	public Float getPd_T()
	{
		return pd_T;
	}

	public void setPd_T(Float pd_T)
	{
		this.pd_T = pd_T;
	}

	public Float getPd_R()
	{
		return pd_R;
	}

	public void setPd_R(Float pd_R)
	{
		this.pd_R = pd_R;
	}

	public Float getPd_P()
	{
		return pd_P;
	}

	public void setPd_P(Float pd_P)
	{
		this.pd_P = pd_P;
	}

	public Float getPd_S()
	{
		return pd_S;
	}

	public void setPd_S(Float pd_S)
	{
		this.pd_S = pd_S;
	}

	public Boolean getPd_Taurus()
	{
		return pd_Taurus;
	}

	public void setPd_Taurus(Boolean pd_Taurus)
	{
		this.pd_Taurus = pd_Taurus;
	}

	public int getNn_MaxNodes()
	{
		return nn_MaxNodes;
	}

	public void setNn_MaxNodes(int nn_MaxNodes)
	{
		this.nn_MaxNodes = nn_MaxNodes;
	}

	public Boolean getSim_Running()
	{
		return sim_Running;
	}

	public void setSim_Running(Boolean sim_Running)
	{
		this.sim_Running = sim_Running;
	}

	public Boolean getSim_Paused()
	{
		return sim_Paused;
	}

	public void setSim_Paused(Boolean sim_Paused)
	{
		this.sim_Paused = sim_Paused;
	}

	public Boolean getSim_Save()
	{
		return sim_Save;
	}

	public void setSim_Save(Boolean sim_Save)
	{
		this.sim_Save = sim_Save;
	}

	public String getSim_SavePath()
	{
		return sim_SavePath;
	}

	public void setSim_SavePath(String sim_SavePath)
	{
		this.sim_SavePath = sim_SavePath;
	}

	public int getSim_SaveDelay()
	{
		return sim_SaveDelay;
	}

	public void setSim_SaveDelay(int sim_SaveDelay)
	{
		this.sim_SaveDelay = sim_SaveDelay;
	}

	public int getSim_Threads()
	{
		return sim_Threads;
	}

	public void setSim_Threads(int sim_Threads)
	{
		if(this.sim_Threads != sim_Threads)
		{
			fireThreadChange = true;
		}
		this.sim_Threads = sim_Threads;
	}

	public int getSim_Delay()
	{
		return sim_Delay;
	}

	public void setSim_Delay(int sim_Delay)
	{
		this.sim_Delay = sim_Delay;
	}

	public int getSim_CurGen()
	{
		return sim_CurGen;
	}

	public void setSim_CurGen(int sim_CurGen)
	{
		this.sim_CurGen = sim_CurGen;
	}

	public int getDraw_Delay()
	{
		return draw_Delay;
	}

	public void setDraw_Delay(int draw_Delay)
	{
		this.draw_Delay = draw_Delay;
	}

	public int getDraw_Scale()
	{
		return draw_Scale;
	}

	public void setDraw_Scale(int draw_Scale)
	{
		this.draw_Scale = draw_Scale;
	}

	public Boolean getFireThreadChange()
	{
		return fireThreadChange;
	}

	public void setFireThreadChange(Boolean fireThreadChange)
	{
		this.fireThreadChange = fireThreadChange;
	}

	public Boolean getNn_DynTop()
	{
		return nn_DynTop;
	}

	public void setNn_DynTop(Boolean nn_DynTop)
	{
		this.nn_DynTop = nn_DynTop;
	}

	public Integer[] getNn_Topology()
	{
		return nn_Topology;
	}

	public void setNn_Topology(Integer[] nn_Topology)
	{
		this.nn_Topology = nn_Topology;
	}

	public Float getCell_MutationChance()
	{
		return cell_MutationChance;
	}

	public void setCell_MutationChance(Float cell_MutationChance)
	{
		this.cell_MutationChance = cell_MutationChance;
	}

	public Float getNn_ConWeightAllowance()
	{
		return nn_ConWeightAllowance;
	}

	public void setNn_ConWeightAllowance(Float nn_ConWeightAllowance)
	{
		this.nn_ConWeightAllowance = nn_ConWeightAllowance;
	}

	public Float getNn_NodeRAChance()
	{
		return nn_NodeRAChance;
	}

	public void setNn_NodeRAChance(Float nn_NodeRAChance)
	{
		this.nn_NodeRAChance = nn_NodeRAChance;
	}

	public Float getNn_LayerRAChance()
	{
		return nn_LayerRAChance;
	}

	public void setNn_LayerRAChance(Float nn_LayerRAChance)
	{
		this.nn_LayerRAChance = nn_LayerRAChance;
	}
}
