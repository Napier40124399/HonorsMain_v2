package Cells;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

import DataCenter.Bridge;
import NetworkFinal.Network;

/**
 * <h1>Cellular</h1> Super class to all cells. Makes use of protected variables
 * so edit and use with great care!! <br>
 * Each time a cell is created, {@link Cells.Cell_Hard hardcoded},
 * {@link Cells.Cell_TitTat titFortat} or otherwise, this will be accessible and
 * replace much of the hassle of repeating code. Each cell has its own instance
 * of the super class.
 * 
 * @param bridge
 *            Extremely important variable. This allows the cells to communicate
 *            to each other, as well as to the outside. Without correct use of
 *            this, the simulation will not work.
 * @param neighboors
 *            An {@link ArrayList} of the neighbooring cells. This is defined by
 *            running {@link Cells.Cellular#setNeighboors() setNeighboors}, a
 *            method inside this class.
 * @param coords
 *            This cell's position in the visual representation. This is also
 *            used for certain logic elements.
 * @see Color
 * @see Point2D
 * @see Runnable
 */
public class Cell
{
	// Positions
	private int pos_X;
	private int pos_Y;
	private Point2D pos_Coords;
	// Prisoner Dilemma vars
	private Float pd_Fitness;
	private ArrayList<Cell> cell_PotentialParents;
	// Visuals
	private Color c;
	// Instances
	private Bridge bridge;
	private ArrayList<Cell> cell_Neighboors; 
	
	//HardCoded
	private Boolean hc_R;
	private Boolean hc_NextGenR;

	public void Initialize(boolean hc_R, int pos_X, int pos_Y, Bridge bridge)
	{
		this.hc_R = hc_R;
		this.pos_X = pos_X;
		this.pos_Y = pos_Y;
		this.bridge = bridge;
		
		c = Color.black;
		hc_NextGenR = hc_R;
		pos_Coords = new Point2D.Double(pos_X, pos_Y);
	}
	
	public void doFitness(){}
	public void doNewGeneration(){}
	public void doUpdateCell(){}
	public void doMutationLogic(){}
	
	public void doNeighboors()
	{
		cell_Neighboors = new ArrayList<Cell>();

		ArrayList<Cell> temp = bridge.getCell_ArrayList();
		int distance = (int) bridge.getCell_NeiDistance();
		int size = (int) Math.sqrt(temp.size());

		int startX = pos_Y - distance;
		int endX = pos_Y + distance;

		int startY = (pos_X - distance) * size;
		int endY = (pos_X + distance) * size;

		if (bridge.getPd_Taurus())
		{
			if (startX < 0 || endX >= size)
			{
				notNormal(startX, startY, endX, endY, size, temp, distance);
			}

			else if ((pos_X - distance) < 0 || (pos_X + distance) >= size)
			{
				notNormal(startX, startY, endX, endY, size, temp, distance);
			}

			else
			{
				isNormal(startX, startY, endX, endY, size, temp);
			}
		} else
		{
			if (startX < 0 || endX >= size)
			{
			} else if ((pos_X - distance) < 0 || (pos_X + distance) >= size)
			{
			} else
			{
				isNormal(startX, startY, endX, endY, size, temp);
			}
		}
	}
	
	private void notNormal(int startX, int startY, int endX, int endY, int size, ArrayList<Cell> temp, int dist)
	{
		for (int i = startX; i <= endX; i++)
		{
			for (int j = startY; j <= endY; j += size)
			{
				int switcheroo = 0;

				// WORKS, DON'T QUESTION IT
				if (i < 0)
				{
					switcheroo += 1;
				}
				if (i >= size)
				{
					switcheroo += 2;
				}
				if (j < 0)
				{
					switcheroo += 4;
				}
				if (j >= size * size)
				{
					switcheroo += 8;
				}

				// Supposed to be more efficient than if statements
				switch (switcheroo)
				{
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

	private void isNormal(int startX, int startY, int endX, int endY, int size, ArrayList<Cell> temp)
	{
		for (int i = startX; i <= endX; i++)
		{
			for (int j = startY; j <= endY; j += size)
			{
				cell_Neighboors.add(temp.get(i + j));
			}
		}
	}
	
	private int regulateI(int pos, int size, boolean upper)
	{
		int over = 0;
		if (upper)
		{
			over = pos - size; // Should be positive
		} else
		{
			over = size + pos; // Should be negative
		}
		return over;
	}

	private int regulateJ(int pos, int size, boolean upper)
	{
		int over = 0;

		if (upper)
		{
			over = pos - size * size; // Should be positive
		} else
		{
			over = pos + size * size; // Should be negative
		}
		return over;
	}
	
	//GETTERS AND SETTERS
	public int getPos_X()
	{
		return pos_X;
	}

	public void setPos_X(int pos_X)
	{
		this.pos_X = pos_X;
	}

	public int getPos_Y()
	{
		return pos_Y;
	}

	public void setPos_Y(int pos_Y)
	{
		this.pos_Y = pos_Y;
	}

	public Point2D getPos_Coords()
	{
		return pos_Coords;
	}

	public void setPos_Coords(Point2D pos_Coords)
	{
		this.pos_Coords = pos_Coords;
	}

	public Float getPd_Fitness()
	{
		return pd_Fitness;
	}

	public void setPd_Fitness(Float pd_Fitness)
	{
		this.pd_Fitness = pd_Fitness;
	}

	public ArrayList<Cell> getCell_PotentialParents()
	{
		return cell_PotentialParents;
	}

	public void setCell_PotentialParents(ArrayList<Cell> cell_PotentialParents)
	{
		this.cell_PotentialParents = cell_PotentialParents;
	}

	public Color getC()
	{
		return c;
	}

	public void setC(Color c)
	{
		this.c = c;
	}

	public Bridge getBridge()
	{
		return bridge;
	}

	public void setBridge(Bridge bridge)
	{
		this.bridge = bridge;
	}

	public ArrayList<Cell> getCell_Neighboors()
	{
		return cell_Neighboors;
	}

	public void setCell_Neighboors(ArrayList<Cell> cell_Neighboors)
	{
		this.cell_Neighboors = cell_Neighboors;
	}

	public Boolean getHc_R()
	{
		return hc_R;
	}

	public void setHc_R(Boolean hc_R)
	{
		this.hc_R = hc_R;
	}

	public Boolean getHc_NextGenR()
	{
		return hc_NextGenR;
	}

	public void setHc_NextGenR(Boolean hc_NextGenR)
	{
		this.hc_NextGenR = hc_NextGenR;
	}
}
