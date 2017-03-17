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
public class Bridge_Temp
{
	// Cell Variables
	private ArrayList<Cell> cell_ArrayList;
	private Cell cell_Array[];
	private int cell_Quantity;
	private int cell_ColorMode;
	private int cell_ItPerGen;
	private int cell_MaxGen;
	private int cell_NeiDistance;
	private Float cell_Mutation;
	// PD Variables
	private Float pd_T;
	private Float pd_R;
	private Float pd_P;
	private Float pd_S;
	private Boolean pd_Taurus;
	// Neural Network Variables
	private int nn_MaxNodes;
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
}
