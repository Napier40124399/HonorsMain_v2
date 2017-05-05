package DataCenter;

import java.util.ArrayList;

import Cells.Cell;

/**
 * @author hoijf
 *         <h1>Bridge</h1>The bridge controls all the data running between
 *         threads. This allows for each part of the system to communicate with
 *         one another. In this manner everything is accessible from anywhere.
 *         Only drawback is all instances of any class wanting to communicate
 *         need access to the same instance of this class as all the others.
 *         <br>
 *         Using singleton pattern may alleviate this somewhat but everything
 *         currently works just fine and singleton is messy.
 */
public class Bridge {
	// Cell Variables
	/**
	 * <h1>cell_ArrayList</h1>ArrayList of Cells.
	 * 
	 * @see {@link Cells.Cell}
	 */
	private ArrayList<Cell> cell_ArrayList;
	/**
	 * <h1>cell_Quantity</h1>int
	 */
	private int cell_Quantity;
	/**
	 * <h1>cell_ColorMode</h1>int
	 */
	private int cell_ColorMode;
	/**
	 * <h1>cell_ItPerGen</h1>int which reflects
	 */
	private int cell_ItPerGen;
	/**
	 * <h1>cell_MaxGen</h1>int which reflects the number of generations to run
	 * the simulation for.
	 */
	private int cell_MaxGen;
	/**
	 * <h1>cell_NeiDistance</h1>int which reflects distance over which cells
	 * interact.
	 */
	private int cell_NeiDistance;
	/**
	 * <h1>cell_MutationAmount</h1>Float which reflects the allowed spread for
	 * connection weights.
	 */
	private Float cell_MutationAmount = 1f;
	/**
	 * <h1>cell_MutationChance</h1>Float which reflects the chance of mutation
	 * on a connection weight.
	 */
	private Float cell_MutationChance;
	// PD Variables
	/**
	 * <h1>pd_T</h1>Float which represents the T value in PD.
	 */
	private Float pd_T;
	/**
	 * <h1>pd_R</h1>Float which represents the R value in PD.
	 */
	private Float pd_R;
	/**
	 * <h1>pd_P</h1>Float which represents the P value in PD.
	 */
	private Float pd_P;
	/**
	 * <h1>pd_S</h1>Float which represents the S value in PD.
	 */
	private Float pd_S;
	/**
	 * <h1>pd_Taurus</h1>Boolean which dictates whether taurus logic is active
	 * or not.
	 */
	private Boolean pd_Taurus;
	// Neural Network Variables
	/**
	 * <h1>nn_MaxNodes</h1>int which dictates the maximum number of nodes on any
	 * single layer.
	 */
	private int nn_MaxNodes;
	/**
	 * <h1>nn_ConWeightAllowance</h1>Float which reflects connection weight
	 * spread allowance.
	 */
	private Float nn_ConWeightAllowance = 1f;
	/**
	 * <h1>nn_Topology</h1>Integer[] which specifies the starting network
	 * topology.
	 */
	private Integer[] nn_Topology;
	/**
	 * <h1>nn_DynTop</h1>Boolean which specifies whether dynamic topology is
	 * active or not.
	 */
	private Boolean nn_DynTop;
	/**
	 * <h1>nn_NodeRAChance</h1>Float which specifies the per node removal or
	 * addition chance.
	 */
	private Float nn_NodeRAChance = 0.01f; // Chance a node is removed or added
	/**
	 * <h1>nn_LayerRAChance</h1>Float which specifies the per layer removal or
	 * addition chance.
	 */
	private Float nn_LayerRAChance = 0.01f; // Chance a node is removed or added
	// Simulation Variables
	/**
	 * <h1>sim_Running</h1>Boolean specifies whether the simulation is running.
	 */
	private Boolean sim_Running;
	/**
	 * <h1>sim_Paused</h1>Boolean specifies whether the simulation is paused.
	 */
	private Boolean sim_Paused;
	/**
	 * <h1>sim_Save</h1>Boolean dictates save on or off.
	 */
	private Boolean sim_Save;
	/**
	 * <h1>sim_SavePath</h1>String specifies save path if save is active.
	 */
	private String sim_SavePath;
	/**
	 * <h1>sim_SaveDelay</h1>int specifies how many generations each save should
	 * be seperated by.
	 */
	private int sim_SaveDelay;
	/**
	 * <h1>sim_Threads</h1>int specifies how many threads the sim logic should
	 * run on (does not count interface and core simulation management threads).
	 */
	private int sim_Threads;
	/**
	 * <h1>sim_Delay</h1>int specifies how many milliseconds the simulation is
	 * paused for between generations.
	 */
	private int sim_Delay;
	/**
	 * <h1>sim_CurGen</h1>int specifies the current generation of the
	 * simulation.
	 */
	private int sim_CurGen;
	// Draw Variables
	/**
	 * <h1>draw_Delay</h1>int specifies how many generations should seperate
	 * each draw.
	 */
	private int draw_Delay;
	/**
	 * <h1>draw_Scale</h1>int used to scale the simulation window to an
	 * appropriate size.
	 */
	private int draw_Scale;

	// Internal Logic Variables
	private Boolean fireThreadChange = false;

	/**
	 * <h1>setUp</h1>Delayed constructor for Bridge class.
	 * @param cell_Quantity (int) ,  {@link DataCenter.Bridge#cell_Quantity info}
	 * @param cell_ColorMode (int) ,  {@link DataCenter.Bridge#cell_ColorMode info}
	 * @param cell_ItPerGen (int) ,  {@link DataCenter.Bridge#cell_ItPerGen info}
	 * @param cell_MaxGen (int) ,  {@link DataCenter.Bridge#cell_MaxGen info}
	 * @param cell_NeiDistance (int) ,  {@link DataCenter.Bridge#cell_NeiDistance info}
	 * @param cell_MutationChance (Float) ,  {@link DataCenter.Bridge#cell_MutationChance info}
	 * @param pd_T (Float) ,  {@link DataCenter.Bridge#pd_T info}
	 * @param pd_R (Float) ,  {@link DataCenter.Bridge#pd_R info}
	 * @param pd_P (Float) ,  {@link DataCenter.Bridge#pd_p info}
	 * @param pd_S (Float) ,  {@link DataCenter.Bridge#pd_S info}
	 * @param pd_Taurus (Boolean) ,  {@link DataCenter.Bridge#pd_Taurus info}
	 * @param nn_MaxNodes (int) ,  {@link DataCenter.Bridge#nn_MaxNodes info}
	 * @param sim_Save (Boolean) ,  {@link DataCenter.Bridge#sim_Save info}
	 * @param sim_SavePath (String) ,  {@link DataCenter.Bridge#sim_SavePath info}
	 * @param sim_SaveDelay (int) ,  {@link DataCenter.Bridge#sim_SaveDelay info}
	 * @param sim_Threads (int) ,  {@link DataCenter.Bridge#sim_Threads info}
	 * @param sim_Delay (int) ,  {@link DataCenter.Bridge#sim_Delay info}
	 * @param draw_Delay (int) ,  {@link DataCenter.Bridge#draw_Delay info}
	 * @param draw_Scale (int) ,  {@link DataCenter.Bridge#draw_Scale info}
	 */
	public void setUp(int cell_Quantity, int cell_ColorMode, int cell_ItPerGen, int cell_MaxGen, int cell_NeiDistance,
			Float cell_MutationChance, Float pd_T, Float pd_R, Float pd_P, Float pd_S, Boolean pd_Taurus,
			int nn_MaxNodes, Boolean sim_Save, String sim_SavePath, int sim_SaveDelay, int sim_Threads, int sim_Delay,
			int draw_Delay, int draw_Scale) {
		this.cell_Quantity = cell_Quantity;
		this.cell_ColorMode = cell_ColorMode;
		this.cell_ItPerGen = cell_ItPerGen;
		this.cell_MaxGen = cell_MaxGen;
		this.cell_NeiDistance = cell_NeiDistance;
		this.cell_MutationChance = cell_MutationChance;
		this.pd_Taurus = pd_Taurus;
		this.nn_MaxNodes = nn_MaxNodes;
		this.sim_Save = sim_Save;
		this.sim_SavePath = sim_SavePath;
		this.sim_SaveDelay = sim_SaveDelay;
		this.sim_Threads = sim_Threads;
		this.sim_Delay = sim_Delay;
		this.draw_Delay = draw_Delay;
		this.draw_Scale = draw_Scale;
		this.pd_T = pd_T;
		this.pd_R = pd_R;
		this.pd_P = pd_P;
		this.pd_S = pd_S;
		sim_Running = false;
		sim_Paused = false;
	}

	public ArrayList<Cell> getCell_ArrayList() {
		return cell_ArrayList;
	}

	public void setCell_ArrayList(ArrayList<Cell> cell_ArrayList) {
		this.cell_ArrayList = cell_ArrayList;
	}

	public int getCell_Quantity() {
		return cell_Quantity;
	}

	public void setCell_Quantity(int cell_Quantity) {
		this.cell_Quantity = cell_Quantity;
	}

	public int getCell_ColorMode() {
		return cell_ColorMode;
	}

	public void setCell_ColorMode(int cell_ColorMode) {
		this.cell_ColorMode = cell_ColorMode;
	}

	public int getCell_ItPerGen() {
		return cell_ItPerGen;
	}

	public void setCell_ItPerGen(int cell_ItPerGen) {
		this.cell_ItPerGen = cell_ItPerGen;
	}

	public int getCell_MaxGen() {
		return cell_MaxGen;
	}

	public void setCell_MaxGen(int cell_MaxGen) {
		this.cell_MaxGen = cell_MaxGen;
	}

	public int getCell_NeiDistance() {
		return cell_NeiDistance;
	}

	public void setCell_NeiDistance(int cell_NeiDistance) {
		this.cell_NeiDistance = cell_NeiDistance;
	}

	public Float getCell_MutationAmount() {
		return cell_MutationAmount;
	}

	public void setCell_MutationAmount(Float cell_Mutation) {
		this.cell_MutationAmount = cell_Mutation;
	}

	public Float getPd_T() {
		return pd_T;
	}

	public void setPd_T(Float pd_T) {
		this.pd_T = pd_T;
	}

	public Float getPd_R() {
		return pd_R;
	}

	public void setPd_R(Float pd_R) {
		this.pd_R = pd_R;
	}

	public Float getPd_P() {
		return pd_P;
	}

	public void setPd_P(Float pd_P) {
		this.pd_P = pd_P;
	}

	public Float getPd_S() {
		return pd_S;
	}

	public void setPd_S(Float pd_S) {
		this.pd_S = pd_S;
	}

	public Boolean getPd_Taurus() {
		return pd_Taurus;
	}

	public void setPd_Taurus(Boolean pd_Taurus) {
		this.pd_Taurus = pd_Taurus;
	}

	public int getNn_MaxNodes() {
		return nn_MaxNodes;
	}

	public void setNn_MaxNodes(int nn_MaxNodes) {
		this.nn_MaxNodes = nn_MaxNodes;
	}

	public Boolean getSim_Running() {
		return sim_Running;
	}

	public void setSim_Running(Boolean sim_Running) {
		this.sim_Running = sim_Running;
	}

	public Boolean getSim_Paused() {
		return sim_Paused;
	}

	public void setSim_Paused(Boolean sim_Paused) {
		this.sim_Paused = sim_Paused;
	}

	public Boolean getSim_Save() {
		return sim_Save;
	}

	public void setSim_Save(Boolean sim_Save) {
		this.sim_Save = sim_Save;
	}

	public String getSim_SavePath() {
		return sim_SavePath;
	}

	public void setSim_SavePath(String sim_SavePath) {
		this.sim_SavePath = sim_SavePath;
	}

	public int getSim_SaveDelay() {
		return sim_SaveDelay;
	}

	public void setSim_SaveDelay(int sim_SaveDelay) {
		this.sim_SaveDelay = sim_SaveDelay;
	}

	public int getSim_Threads() {
		return sim_Threads;
	}

	public void setSim_Threads(int sim_Threads) {
		if (this.sim_Threads != sim_Threads) {
			fireThreadChange = true;
		}
		this.sim_Threads = sim_Threads;
	}

	public int getSim_Delay() {
		return sim_Delay;
	}

	public void setSim_Delay(int sim_Delay) {
		this.sim_Delay = sim_Delay;
	}

	public int getSim_CurGen() {
		return sim_CurGen;
	}

	public void setSim_CurGen(int sim_CurGen) {
		this.sim_CurGen = sim_CurGen;
	}

	public int getDraw_Delay() {
		return draw_Delay;
	}

	public void setDraw_Delay(int draw_Delay) {
		this.draw_Delay = draw_Delay;
	}

	public int getDraw_Scale() {
		return draw_Scale;
	}

	public void setDraw_Scale(int draw_Scale) {
		this.draw_Scale = draw_Scale;
	}

	public Boolean getFireThreadChange() {
		return fireThreadChange;
	}

	public void setFireThreadChange(Boolean fireThreadChange) {
		this.fireThreadChange = fireThreadChange;
	}

	public Boolean getNn_DynTop() {
		return nn_DynTop;
	}

	public void setNn_DynTop(Boolean nn_DynTop) {
		this.nn_DynTop = nn_DynTop;
	}

	public Integer[] getNn_Topology() {
		return nn_Topology;
	}

	public void setNn_Topology(Integer[] nn_Topology) {
		this.nn_Topology = nn_Topology;
	}

	public Float getCell_MutationChance() {
		return cell_MutationChance;
	}

	public void setCell_MutationChance(Float cell_MutationChance) {
		this.cell_MutationChance = cell_MutationChance;
	}

	public Float getNn_ConWeightAllowance() {
		return nn_ConWeightAllowance;
	}

	public void setNn_ConWeightAllowance(Float nn_ConWeightAllowance) {
		this.nn_ConWeightAllowance = nn_ConWeightAllowance;
	}

	public Float getNn_NodeRAChance() {
		return nn_NodeRAChance;
	}

	public void setNn_NodeRAChance(Float nn_NodeRAChance) {
		this.nn_NodeRAChance = nn_NodeRAChance;
	}

	public Float getNn_LayerRAChance() {
		return nn_LayerRAChance;
	}

	public void setNn_LayerRAChance(Float nn_LayerRAChance) {
		this.nn_LayerRAChance = nn_LayerRAChance;
	}
}
