package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CompBuilder
{
	private Colors c = new Colors();
	
	public void buildTxtForm(JTextField txt, JFrame frame)
	{
		txt.setFont(new Font("Verdana", Font.BOLD, 20));
		txt.setMargin(new Insets(0, 20, 0, 0));
		txt.setForeground(Color.white);
		txt.setBackground(c.getDarkBlue());
		
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		txt.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 0, Color.white));
		txt.setBorder(border);
		frame.getContentPane().add(txt);
	}
	
	public void buildComboField(JComboBox combo, JFrame frame)
	{
		combo.setFont(new Font("Verdana", Font.BOLD, 20));
		combo.setForeground(Color.white);
		combo.setBackground(c.getDarkBlue());
		combo.setFocusable(false);
		
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		combo.setBorder(border);
		frame.getContentPane().add(combo);
	}
	
	public void buildLabel(JLabel lbl, JFrame frame)
	{
		lbl.setFocusable(false);
		lbl.setFont(new Font("Verdana", Font.BOLD, 20));
		lbl.setForeground(Color.white);
		lbl.setBackground(c.getDarkBlue());
		frame.getContentPane().add(lbl);
	}
	
	public void buildCheckBox(JCheckBox box, boolean isSelected, JFrame frame)
	{
		box.setSelected(isSelected);
		box.setIcon(new ImageIcon("res\\unSelected.png"));
		box.setSelectedIcon(new ImageIcon("res\\selected.png"));
		box.setOpaque(false);
		box.setText("");
		box.setFocusable(false);
		frame.getContentPane().add(box);
	}
	
	public void buildSeparator(JPanel panel, JFrame frame)
	{
		panel.setBackground(Color.white);
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		panel.setBorder(border);
		frame.getContentPane().add(panel);
	}
	
	public void buildButton(JButton btn, JFrame frame)
	{
		btn.setFont(new Font("Verdana", Font.BOLD, 20));
		btn.setForeground(Color.white);
		btn.setFocusable(false);
		btn.setBackground(c.getDarkGreen());
		btn.addMouseListener(new MouseAdapter()
		{
			public void mouseExited(MouseEvent e)
			{
				Color temp = e.getComponent().getBackground();
				e.getComponent().setBackground(e.getComponent().getForeground());
				e.getComponent().setForeground(temp);
			}
			
			public void mouseEntered(MouseEvent e)
			{
				Color temp = e.getComponent().getBackground();
				e.getComponent().setBackground(e.getComponent().getForeground());
				e.getComponent().setForeground(temp);
			}
		});
		
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		btn.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 0, Color.white));
		btn.setBorder(border);
		frame.getContentPane().add(btn);
	}
	
	//Without frames
	
	public void buildTxtForm(JTextField txt)
	{
		txt.setFont(new Font("Verdana", Font.BOLD, 20));
		txt.setMargin(new Insets(0, 20, 0, 0));
		txt.setForeground(Color.white);
		txt.setBackground(null);
		txt.setOpaque(true);
		
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		txt.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 0, Color.white));
		txt.setBorder(border);
	}
	
	public void buildComboField(JComboBox combo)
	{
		combo.setFont(new Font("Verdana", Font.BOLD, 20));
		combo.setForeground(Color.white);
		combo.setBackground(c.getDarkGreen());
		combo.setFocusable(false);
		
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		combo.setBorder(border);
	}
	
	public void buildLabel(JLabel lbl)
	{
		lbl.setFocusable(false);
		lbl.setFont(new Font("Verdana", Font.BOLD, 20));
		lbl.setForeground(Color.white);
		lbl.setBackground(c.getDarkBlue());
	}
	
	public void buildCheckBox(JCheckBox box, boolean isSelected)
	{
		box.setSelected(isSelected);
		box.setIcon(new ImageIcon("res\\unSelected.png"));
		box.setSelectedIcon(new ImageIcon("res\\selected.png"));
		box.setOpaque(false);
		box.setText("");
		box.setFocusable(false);
		box.setBackground(null);
	}
	
	public void buildSeparator(JPanel panel)
	{
		panel.setBackground(Color.white);
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		panel.setBorder(border);
	}
	
	public void buildButton(JButton btn)
	{
		btn.setFont(new Font("Verdana", Font.BOLD, 20));
		btn.setForeground(Color.white);
		btn.setFocusable(false);
		btn.setBackground(c.getDarkGreen());
		btn.addMouseListener(new MouseAdapter()
		{
			public void mouseExited(MouseEvent e)
			{
				Color temp = e.getComponent().getBackground();
				e.getComponent().setBackground(e.getComponent().getForeground());
				e.getComponent().setForeground(temp);
			}
			
			public void mouseEntered(MouseEvent e)
			{
				Color temp = e.getComponent().getBackground();
				e.getComponent().setBackground(e.getComponent().getForeground());
				e.getComponent().setForeground(temp);
			}
		});
		
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		//btn.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 0, Color.white));
		btn.setBorder(border);
	}
}
