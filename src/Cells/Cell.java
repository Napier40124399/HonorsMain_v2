package Cells;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import DataCenter.Bridge;
import NetworkFinal.Network;

/**
 * <h1>Cell</h1> Super class to all cells. Makes use of private variables. Use
 * getters and setters to access variables.<br>
 * All cell types extend this superclass ({@link Cells.Cell_Hard hardcoded},
 * {@link Cells.Cell_TitTat titFortat} or {@link Cells.Cell_NN neuralNet}). When
 * creating a new cell type this class must be extended by the cell. This class
 * grants the reference cell to all cell types. This ensures it will be usable
 * by already existing classes and methods.
 * <h2>Notable components</h2> {@link Cells.Cell#Initialize Initialize}<br>
 * {@link Cells.Cell#doFitness Fitness Logic}<br>
 * {@link Cells.Cell#doNewGeneration Determine Parent}<br>
 * {@link Cells.Cell#doUpdateCell Copy Parent}<br>
 * {@link Cells.Cell#doMutationLogic Mutate}<br>
 * {@link Cells.Cell#doNeighboors Determine Neighbours}<br>
 * {@link Cells.Cell#variables Variables}<br>
 * {@link Cells.Cell#getters Getters}<br>
 * {@link Cells.Cell#setters Setters}<br>
 * 
 * @see Color
 * @see Point2D
 * @see Runnable
 * 
 * @author James F. Taylor
 */
public class Cell {
	// Positions
	private int pos_X;
	private int pos_Y;
	private Point pos_Coords;
	// Prisoner Dilemma vars
	private Float pd_Fitness;
	private ArrayList<Cell> cell_PotentialParents;
	// Visuals
	private Color c;
	// Instances
	private Bridge bridge;
	/**
	 * yoyo
	 */
	private ArrayList<Cell> cell_Neighboors;

	// Internal logic
	private Boolean hc_R;
	private Boolean hc_NextGenR;

	/**
	 * <h1>Initialize</h1> This method replaces the role of a constructor so the
	 * cell can be initialized when most convenient.
	 * 
	 * @param hc_R
	 *            Boolean sets the cell as cooperative or defective (true is
	 *            defect).
	 * @param pos_X
	 *            This cell's X position in the visual representation. This is
	 *            also used for certain logic elements.
	 * @param pos_Y
	 *            This cell's Y position in the visual representation. This is
	 *            also used for certain logic elements.
	 * @param bridge
	 *            Extremely important variable. This allows the cells to
	 *            communicate to each other, as well as to the outside. Without
	 *            correct use of this, the simulation will not work.
	 * @see {@link Cells.Cell Cell superclass index}
	 */
	public void Initialize(boolean hc_R, int pos_X, int pos_Y, Bridge bridge) {
		this.hc_R = hc_R;
		this.pos_X = pos_X;
		this.pos_Y = pos_Y;
		this.bridge = bridge;

		c = Color.black;
		hc_NextGenR = hc_R;
		pos_Coords = new Point(pos_X, pos_Y);
		variables(); // Used it here since this is only called once at the
						// beginning of the simulation and removes the
						// @supressUnused warning
	}

	/**
	 * <h1>Fitness Calculation</h1>This algorithm handles the bulk of the
	 * interaction between cells. This is where the prisoner’s dilemma algorithm
	 * is implemented. The cell interacts with its
	 * {@link Cells.Cell#cell_Neighboors neighbours} and gets a payoff.<br>
	 * Fitness value should be whiped at the start of the method and written to
	 * the superclass variable using getters and setters.
	 * 
	 * @see {@link Cells.Cell#pd_Fitness Fitness Variable}
	 * @see {@link Cells.Cell#getPd_Fitness Fitness Getter}
	 * @see {@link Cells.Cell#setPd_Fitness Fitness Setter}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	public void doFitness() {
	}

	/**
	 * <h1>Parent Calculation</h1>This algorithm determines which neighbouring
	 * cell will become this cell's offspring's parent. This is done using the
	 * previously calculated fitness value and comparing it to each neighbour.
	 * The neighbour with the highest fitness is kept as an independant copy of
	 * itself for later use. The copy is subclass specific and should be stored
	 * on that level<br>
	 * Parent value should be whiped at the start of this method.
	 * 
	 * @see {@link Cells.Cell#cell_Neighboors Neighbours Variable}
	 * @see {@link Cells.Cell#getCell_Neighboors Neighbours Getter}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	public void doNewGeneration() {
	}

	/**
	 * <h1>Parent Copying</h1>This method takes the stored copy of the
	 * {@link Cells.Cell#doNewGeneration parent} and copis it exactly. This
	 * method is usualy small but is necessary for the correct functioning of
	 * multi-threading and synchronous compute.
	 * 
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	public void doUpdateCell() {
	}

	/**
	 * <h1>Mutation Logic</h1>This is where all mutation logic should be
	 * handled. Use variables stored in bridge to achieved this.
	 * 
	 * @see {@link DataCenter.Bridge Bridge}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	public void doMutationLogic() {
	}

	/**
	 * <h1>Neighbours Calculation</h1>This method handles all neighbour
	 * calculations for you. Don't worry about it. <br>
	 * Using the one dimensional cell array stored on {@link DataCenter.Bridge
	 * bridge} in coordination with the X and Y values of each cell, this method
	 * will determine which cells are neighbours and store them.
	 * {@link DataCenter.Bridge#getPd_Taurus() Taurus} logic is also,
	 * automatically handled here.
	 * 
	 * @see {@link Cells.Cell#cell_Neighboors Neighbours Variable}
	 * @see {@link Cells.Cell#getCell_Neighboors Neighbours Getter}
	 * @see {@link Cells.Cell#setCell_Neighboors Neighbours Setter}
	 * @see {@link Cells.Cell#notNormal notNormal Method}
	 * @see {@link Cells.Cell#isNormal isNormal Method}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	public void doNeighboors() {
		cell_Neighboors = new ArrayList<Cell>();

		ArrayList<Cell> temp = bridge.getCell_ArrayList();
		int distance = (int) bridge.getCell_NeiDistance();
		int size = (int) Math.sqrt(temp.size());

		int startX = pos_Y - distance;
		int endX = pos_Y + distance;

		int startY = (pos_X - distance) * size;
		int endY = (pos_X + distance) * size;

		if (bridge.getPd_Taurus()) {
			if (startX < 0 || endX >= size) {
				notNormal(startX, startY, endX, endY, size, temp);
			}

			else if ((pos_X - distance) < 0 || (pos_X + distance) >= size) {
				notNormal(startX, startY, endX, endY, size, temp);
			}

			else {
				isNormal(startX, startY, endX, endY, size, temp);
			}
		} else {
			if (startX < 0 || endX >= size) {
			} else if ((pos_X - distance) < 0 || (pos_X + distance) >= size) {
			} else {
				isNormal(startX, startY, endX, endY, size, temp);
			}
		}
	}

	/**
	 * <h1>notNormal</h1>You shouldn't touch this method, it is an extension of
	 * the {@link Cells.Cell#doNeighboors() doNeighbours} method. It handles
	 * situations where the cell is on an edge or corner.
	 * 
	 * @param startX
	 *            is an integer which specifies the starting point on the X
	 *            axis.
	 * @param startY
	 *            is an integer which specifies the starting point on the Y
	 *            axis.
	 * @param endX
	 *            is an integer which specifies the ending point on the X axis.
	 * @param endY
	 *            is an integer which specifies the ending point on the Y axis.
	 * @param size
	 *            is the grid size (one side of the grid therefore it is also
	 *            the square root of the number of cells).
	 * @param temp
	 *            is the temporary array of cell meant to become the neighbours
	 *            array.
	 * @see {@link Cells.Cell#doNeighboors() doNeighbours}
	 * @see {@link Cells.Cell#isNormal isNormal}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	private void notNormal(int startX, int startY, int endX, int endY, int size, ArrayList<Cell> temp) {
		for (int i = startX; i <= endX; i++) {
			for (int j = startY; j <= endY; j += size) {
				int switcheroo = 0;

				// WORKS, DON'T QUESTION IT
				if (i < 0) {
					switcheroo += 1;
				}
				if (i >= size) {
					switcheroo += 2;
				}
				if (j < 0) {
					switcheroo += 4;
				}
				if (j >= size * size) {
					switcheroo += 8;
				}

				// Supposed to be more efficient than if statements in cases
				// where long lists are made
				switch (switcheroo) {
				case 0:
					cell_Neighboors.add(temp.get(i + j));
					break;
				case 1:
					cell_Neighboors.add(temp.get(regulateI(i, size, false) + j));
					break;
				case 2:
					cell_Neighboors.add(temp.get(regulateI(i, size, true) + j));
					break;
				case 4:
					cell_Neighboors.add(temp.get((regulateJ(j, size, false)) + i));
					break;
				case 8:
					cell_Neighboors.add(temp.get((regulateJ(j, size, true)) + i));
					break;

				// Corners
				case 5:
					cell_Neighboors.add(temp.get(regulateI(i, size, false) + regulateJ(j, size, false)));
					break;
				case 6:
					cell_Neighboors.add(temp.get(regulateI(i, size, true) + regulateJ(j, size, false)));
					break;
				case 9:
					cell_Neighboors.add(temp.get(regulateI(i, size, false) + regulateJ(j, size, true)));
					break;
				case 10:
					cell_Neighboors.add(temp.get(regulateI(i, size, true) + regulateJ(j, size, true)));
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * <h1>isNormal</h1>You shouldn't touch this method, it is an extension of
	 * the {@link Cells.Cell#doNeighboors() doNeighbours} method. It handles
	 * situations where the cell is not on an edge or corner.
	 * 
	 * @param startX
	 *            is an integer which specifies the starting point on the X
	 *            axis.
	 * @param startY
	 *            is an integer which specifies the starting point on the Y
	 *            axis.
	 * @param endX
	 *            is an integer which specifies the ending point on the X axis.
	 * @param endY
	 *            is an integer which specifies the ending point on the Y axis.
	 * @param size
	 *            is the grid size (one side of the grid therefore it is also
	 *            the square root of the number of cells).
	 * @param temp
	 *            is the temporary array of cell meant to become the neighbours
	 *            array.
	 * @see {@link Cells.Cell#doNeighboors() doNeighbours}
	 * @see {@link Cells.Cell#isNormal isNormal}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	private void isNormal(int startX, int startY, int endX, int endY, int size, ArrayList<Cell> temp) {
		for (int i = startX; i <= endX; i++) {
			for (int j = startY; j <= endY; j += size) {
				cell_Neighboors.add(temp.get(i + j));
			}
		}
	}

	/**
	 * <h1>regulateI</h1> Is an extension of {@link Cells.Cell#notNormal
	 * notNormal} method.
	 * 
	 * @param pos
	 *            integer
	 * @param size
	 *            integer
	 * @param upper
	 *            boolean
	 * @return int
	 * @see {@link Cells.Cell#regulateJ regulateJ}
	 * @see {@link Cells.Cell#doNeighboors doNeighboors}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	private int regulateI(int pos, int size, boolean upper) {
		int over = 0;
		if (upper) {
			over = pos - size; // Should be positive
		} else {
			over = size + pos; // Should be negative
		}
		return over;
	}

	/**
	 * <h1>regulateJ</h1> Is an extension of {@link Cells.Cell#notNormal
	 * notNormal} method.
	 * 
	 * @param pos
	 *            integer
	 * @param size
	 *            integer
	 * @param upper
	 *            boolean
	 * @return int
	 * @see {@link Cells.Cell#regulateI regulateI}
	 * @see {@link Cells.Cell#doNeighboors doNeighboors}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	private int regulateJ(int pos, int size, boolean upper) {
		int over = 0;

		if (upper) {
			over = pos - size * size; // Should be positive
		} else {
			over = pos + size * size; // Should be negative
		}
		return over;
	}

	// Public methods
	/**
	 * <h1>serialize</h1>Gather important cell information and concatenate it
	 * into a String.
	 * 
	 * @return String of important cell info.
	 */
	public String serialize() {
		return "";
	}
	
	// GETTERS AND SETTERS
	/**
	 * <h1>getPos_X</h1>Simple getter for the pos_X variable.
	 * 
	 * @return {@link Cells.Cell#pos_X pos_X} (int)
	 */
	public int getPos_X() {
		return pos_X;
	}

	/**
	 * <h1>setPos_X</h1>Simple setter for the pos_X variable.
	 * 
	 * @param pos_X
	 *            (int)
	 * @see {@link Cells.Cell#pos_X pos_X} (int)
	 */
	public void setPos_X(int pos_X) {
		this.pos_X = pos_X;
	}

	/**
	 * <h1>getPos_Y</h1>Simple getter for the pos_Y variable.
	 * 
	 * @return {@link Cells.Cell#pos_Y pos_Y} (int)
	 */
	public int getPos_Y() {
		return pos_Y;
	}

	/**
	 * <h1>setPos_Y</h1>Simple setter for the pos_Y variable.
	 * 
	 * @param pos_Y
	 *            (int)
	 * @see {@link Cells.Cell#pos_Y pos_Y} (int)
	 */
	public void setPos_Y(int pos_Y) {
		this.pos_Y = pos_Y;
	}

	/**
	 * <h1>getPos_Coords</h1>Simple getter for the pos_Coords variable.
	 * 
	 * @return {@link Cells.Cell#pos_Coords pos_Coords} (Point)
	 * @see {@link Point What is a Point type?}
	 */
	public Point getPos_Coords() {
		return pos_Coords;
	}

	/**
	 * <h1>setPos_Coords</h1>Simple setter for the pos_Coords variable.
	 * 
	 * @param pos_Coords
	 * @see {@link Cells.Cell#pos_Coords pos_Coords}
	 */
	public void setPos_Coords(Point pos_Coords) {
		this.pos_Coords = pos_Coords;
	}

	/**
	 * <h1>getPd_Fitness</h1>Simple getter for the cell's fitness value as set
	 * by the prisoner's dilemma interaction.
	 * 
	 * @return
	 */
	public Float getPd_Fitness() {
		return pd_Fitness;
	}

	/**
	 * <h1>setPd_Fitness</h1>Simples setter for the cell's fitness.
	 * 
	 * @param pd_Fitness
	 *            Float
	 * @see {@link Cells.Cell#pd_Fitness pd_Fitness}
	 */
	public void setPd_Fitness(Float pd_Fitness) {
		this.pd_Fitness = pd_Fitness;
	}

	/**
	 * <h1>getCell_PotentialParent</h1>Returns an arraylist of the cell's
	 * potential parents.
	 * 
	 * @return
	 */
	public ArrayList<Cell> getCell_PotentialParents() {
		return cell_PotentialParents;
	}

	/**
	 * <h1>setCell_PotentialParents</h1>Setter for cell_PotentialParents
	 * variable.
	 * 
	 * @param cell_PotentialParents
	 *            (ArrayList<Cell>)
	 * @see {@link ArrayList What is an arraylist?}
	 */
	public void setCell_PotentialParents(ArrayList<Cell> cell_PotentialParents) {
		this.cell_PotentialParents = cell_PotentialParents;
	}

	/**
	 * <h1>getC</h1>Simple getter for c (Color).
	 * 
	 * @return {@link Color Color}
	 */
	public Color getC() {
		return c;
	}

	/**
	 * <h1>setC</h1>Setter for Color c.
	 * 
	 * @param c
	 *            ({@link Color Color})
	 */
	public void setC(Color c) {
		this.c = c;
	}

	/**
	 * <h1>getBridge</h1>Getter for bridge.
	 * 
	 * @return {@link DataCenter.Bridge Bridge}
	 */
	public Bridge getBridge() {
		return bridge;
	}

	/**
	 * <h1>setBridge</h1>Setter for bridge.
	 * 
	 * @param bridge
	 *            ({@link DataCenter.Bridge Bridge})
	 */
	public void setBridge(Bridge bridge) {
		this.bridge = bridge;
	}

	/**
	 * <h1>getCell_Neighboors</h1>Getter for cell_Neighboors.
	 * 
	 * @return {@link Cells.Cell#cell_Neighboors cell_Neighboors}
	 */
	public ArrayList<Cell> getCell_Neighboors() {
		return cell_Neighboors;
	}

	/**
	 * <h1>setCell_Neighboors</h1>Setter for {@link Cells.Cell#cell_Neighboors
	 * cell}
	 * 
	 * @param ArrayList<Cell>
	 */
	public void setCell_Neighboors(ArrayList<Cell> cell_Neighboors) {
		this.cell_Neighboors = cell_Neighboors;
	}

	/**
	 * <h1>getHc_R</h1>Getter for {@link Cells.Cell#hc_R hc_R};
	 * 
	 * @return Boolean
	 */
	public Boolean getHc_R() {
		return hc_R;
	}

	/**
	 * <h1>getHc_R(Boolean)</h1>An extension of {@link Cells.Cell#getHc_R
	 * getHc_R} which takes in a Boolean and outputs a Boolean. Intended use it
	 * to perform some local logic using the argument.
	 * 
	 * @param hc_R
	 * @return Boolean
	 */
	public Boolean getHc_R(Boolean hc_R) {
		return this.hc_R;
	}

	/**
	 * <h1>setHc_R</h1>Setter for {@link Cells.Cell#hc_R hc_R}.
	 * 
	 * @param hc_R
	 *            (Boolean)
	 */
	public void setHc_R(Boolean hc_R) {
		this.hc_R = hc_R;
	}

	/**
	 * <h1>getHc_NextGenR</h1>Getter for {@link Cells.Cell#hc_NextGenR
	 * hc_NextGenR}.
	 * 
	 * @return Boolean
	 */
	public Boolean getHc_NextGenR() {
		return hc_NextGenR;
	}

	/**
	 * <h1>setHc_NextGenR</h1>Setter for {@link Cells.Cell#hc_NextGenR
	 * hc_NextGenR}.
	 * 
	 * @param {@link
	 * 			Cells.Cell#hc_NextGenR hc_NextGenR} (Boolean)
	 */
	public void setHc_NextGenR(Boolean hc_NextGenR) {
		this.hc_NextGenR = hc_NextGenR;
	}

	/**
	 * <h1>getCoopHist</h1>Getter for cooperation history.
	 * 
	 * @return int
	 */
	public int getCoopHist() {
		return 0;
	}

	/**
	 * <h1>getType</h1>Getter for type.
	 * 
	 * @return int
	 */
	public int getType() {
		return 0;
	}

	/**
	 * <h1>setType</h1>Setter for type (int).
	 * 
	 * @param int
	 */
	public void setType(int type) {

	}

	//

	/**
	 * <h1>Variables</h1> Variables are stored privately so they must be accesed
	 * using getters and setters.
	 * 
	 * @see {@link Cells.Cell#pos_X pos_X}
	 * @see {@link Cells.Cell#pos_Y pos_Y}
	 * @see {@link Cells.Cell#pos_Coords pos_Coords}
	 * @see {@link Cells.Cell#pd_Fitness pd_Fitness}
	 * @see {@link Cells.Cell#cell_PotentialParents cell_PotentialParents}
	 * @see {@link Cells.Cell#c c}
	 * @see {@link Cells.Cell#bridge bridge}
	 * @see {@link Cells.Cell#cell_Neighboors cell_Neighboors}
	 * @see {@link Cells.Cell#hc_R hc_R}
	 * @see {@link Cells.Cell#hc_NextGenR hx_NextGenR}
	 * @see {@link Cells.Cell Cell Superclass Index}
	 */
	private void variables() {
	}
}
