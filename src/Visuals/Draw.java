package Visuals;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Cells.Cell;
import Components.Colors;
import DataCenter.Bridge;

public class Draw
{
	private Bridge bridge;
	private Colors c = new Colors();
	
	public Draw(Bridge bridge)
	{
		this.bridge = bridge;
	}
	
	public void draw(Graphics g)
	{
		if(bridge.getSim_Running())
		{
			drawCells(g);
		}
		if(bridge.getSim_Paused())
		{
			drawPaused(g);
		}
	}
	
	private void drawCells(Graphics g)
	{
		for (Cell c : bridge.getCell_ArrayList())
		{
			g.setColor(c.getC());
			g.fillRect(c.getPos_X() * bridge.getDraw_Scale(), c.getPos_Y() * bridge.getDraw_Scale(),
					bridge.getDraw_Scale(), bridge.getDraw_Scale());
		}
	}
	
	private void drawNotRunning(Graphics g)
	{
		
	}
	
	private void drawPaused(Graphics g)
	{
		int size = bridge.getCell_Quantity() * bridge.getDraw_Scale();
		int paneWidth = 360;
		int paneHeight = 30;
		
		int topCornerX = (size-paneWidth)/2;
		int topCornerY = 20;
		
		g.setColor(Color.white);
		g.fillRect(topCornerX, topCornerY, paneWidth, paneHeight);
		g.setColor(c.getDarkBlue());
		g.fillRect(topCornerX + 2, topCornerY + 2, paneWidth - 4, paneHeight - 4);
		g.setColor(Color.white);
		g.setFont(new Font("Verdana", Font.BOLD, 18));
		g.drawString("PAUSED, PRESS PLAY TO UNPAUSE", topCornerX+2, topCornerY+20);
	}
}
