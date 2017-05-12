package Cells;

public class CellFactory {
	
	public Cell getCell_Old(int type) {
		switch (type) {
		case 0:
			return new Cell_Hard();
		case 1:
			return new Cell_TitTat();
		case 2:
			return new Cell_NN();
		default:
			return null;
		}
	}
	
	public Cell getCell(int type)
	{
		Cell cell = null;
		
		if(type == 0)
		{
			cell = new Cell_Hard();
		}else if(type == 1)
		{
			cell = new Cell_TitTat();
		}else if(type == 2)
		{
			cell = new Cell_NN();
		}
		
		return cell;
	}
}
