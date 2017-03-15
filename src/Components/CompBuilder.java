package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CompBuilder
{
	private Colors c = new Colors();
	
	public void buildTxtForm(JTextField txt)
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
	}
	
	public void buildComboField(JComboBox combo)
	{
		combo.setFont(new Font("Verdana", Font.BOLD, 20));
		combo.setForeground(Color.white);
		combo.setBackground(c.getDarkBlue());
		combo.setFocusable(false);
		
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		combo.setBorder(border);
	}
	
	public void buildLabel(JLabel lbl, JFrame frame)
	{
		lbl.setFocusable(false);
		lbl.setFont(new Font("Verdana", Font.BOLD, 20));
		lbl.setForeground(Color.white);
		lbl.setBackground(c.getDarkBlue());
		frame.getContentPane().add(lbl);
	}
	
	public void buildComboField(JCheckBox box, boolean isSelected)
	{
		box.setSelected(isSelected);
		box.setIcon(new ImageIcon("res\\unSelected.png"));
		box.setSelectedIcon(new ImageIcon("res\\selected.png"));
		box.setOpaque(false);
		box.setText("");
		box.setFocusable(false);
	}
}
