package Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * <h1>CompBuilder</h1>Used to build components quickly.
 * 
 * @author James F. Taylor
 *
 */
public class CompBuilder {
	private Colors c = new Colors();

	public void buildTxtForm(JTextField txt, JFrame frame) {
		txt.setFont(new Font("Verdana", Font.BOLD, 20));
		txt.setMargin(new Insets(0, 20, 0, 0));
		txt.setForeground(Color.white);
		txt.setBackground(c.getDarkBlue());

		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		txt.setBorder(border);
		frame.getContentPane().add(txt);
	}

	public void buildComboField(JComboBox combo, JFrame frame) {
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

	public void buildLabel(JLabel lbl, JFrame frame) {
		lbl.setFocusable(false);
		lbl.setFont(new Font("Verdana", Font.BOLD, 20));
		lbl.setForeground(Color.white);
		lbl.setBackground(c.getDarkBlue());
		frame.getContentPane().add(lbl);
	}

	public void buildCheckBox(JCheckBox box, boolean isSelected, JFrame frame) {
		box.setSelected(isSelected);
		box.setIcon(new ImageIcon("res\\unSelected.png"));
		box.setSelectedIcon(new ImageIcon("res\\selected.png"));
		box.setDisabledIcon(new ImageIcon("res\\disabled.png"));
		box.setOpaque(false);
		box.setText("");
		box.setFocusable(false);
		frame.getContentPane().add(box);
	}

	public void buildSeparator(JPanel panel, JFrame frame) {
		panel.setBackground(Color.white);
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		panel.setBorder(border);
		frame.getContentPane().add(panel);
	}

	public void buildButton(JButton btn, JFrame frame) {
		btn.setFont(new Font("Verdana", Font.BOLD, 20));
		btn.setForeground(Color.white);
		btn.setFocusable(false);
		btn.setBackground(c.getDarkGreen());
		btn.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				Color temp = e.getComponent().getBackground();
				e.getComponent().setBackground(e.getComponent().getForeground());
				e.getComponent().setForeground(temp);
			}

			public void mouseEntered(MouseEvent e) {
				Color temp = e.getComponent().getBackground();
				e.getComponent().setBackground(e.getComponent().getForeground());
				e.getComponent().setForeground(temp);
			}
		});

		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		btn.setBorder(border);
		frame.getContentPane().add(btn);
	}

	public void buildNicePanel(JPanel panel, JFrame frame) {
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		panel.setBorder(border);

		panel.setBackground(c.getDarkBlue());
		frame.getContentPane().add(panel);
	}

	public void buildArea(JTextArea txt, JFrame frame) {
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		txt.setBorder(border);

		txt.setFont(new Font("Verdana", Font.BOLD, 20));
		txt.setForeground(Color.white);
		txt.setBackground(c.getDarkBlue());
		frame.getContentPane().add(txt);
	}

	// Without frames

	public void buildScrollPane(JScrollPane scroll) {
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		scroll.setBorder(border);

		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scroll.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

		scroll.getViewport().setBackground(c.getDarkBlue());
		scroll.setBackground(c.getDarkBlue());
	}

	public void buildTable(JTable tbl) {
		tbl.setRowHeight(30);
		tbl.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"Enter");
		tbl.getActionMap().put("Enter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
			}
		});

		tbl.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"Enter");
		tbl.getActionMap().put("Enter", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
			}
		});

		tbl.setFont(new Font("ComicSans", Font.BOLD, 14));
		JTableHeader header = tbl.getTableHeader();
		header.setBackground(c.getDarkBlue());
		header.setForeground(Color.white);
		header.setBorder(new LineBorder(Color.black));
		header.setFont(new Font("Verdana", Font.BOLD, 20));

		ArrayList<Component> cells = new ArrayList<Component>();

		for (int i = 0; i < tbl.getRowCount(); i++) {
			for (int j = 0; j < tbl.getColumnCount(); j++) {
				TableCellRenderer cellRenderer = tbl.getCellRenderer(i, j);
				cells.add(cellRenderer.getTableCellRendererComponent(tbl, null, false, true, i, j));
			}
		}
		tbl.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int col) {
				table.getColumnModel().getColumn(0).setMaxWidth(40);
				table.getColumnModel().getColumn(0).setPreferredWidth(40);

				table.getColumnModel().getColumn(0).setPreferredWidth(100);

				table.getColumnModel().getColumn(0).setPreferredWidth(150);

				table.getColumnModel().getColumn(0).setMaxWidth(40);
				table.getColumnModel().getColumn(0).setPreferredWidth(40);
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
				setFont(new Font("Verdana", Font.BOLD, 20));
				if (row % 2 == 0) {
					setForeground(Color.white);
					setBackground(c.getDarkBlue().darker());
				} else {
					setForeground(Color.white);
					setBackground(c.getDarkBlue().darker().darker());
				}
				if (isSelected) {
					setForeground(Color.white);
					setBackground(c.getDarkGreen());
				}
				return this;
			}
		});
	}

	public void buildTxtForm(JTextField txt) {
		txt.setFont(new Font("Verdana", Font.BOLD, 20));
		txt.setMargin(new Insets(0, 20, 0, 0));
		txt.setForeground(Color.white);
		txt.setBackground(null);
		txt.setOpaque(true);

		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		txt.setBorder(border);
	}

	public void buildComboField(JComboBox combo) {
		combo.setFont(new Font("Verdana", Font.BOLD, 20));
		combo.setForeground(Color.white);
		combo.setBackground(c.getDarkBlue());
		combo.setFocusable(false);

		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		combo.setBorder(border);
	}

	public void buildLabel(JLabel lbl) {
		lbl.setFocusable(false);
		lbl.setFont(new Font("Verdana", Font.BOLD, 20));
		lbl.setForeground(Color.white);
		lbl.setBackground(c.getDarkBlue());
	}

	public void buildCheckBox(JCheckBox box, boolean isSelected) {
		box.setSelected(isSelected);
		box.setIcon(new ImageIcon("res\\unSelected.png"));
		box.setSelectedIcon(new ImageIcon("res\\selected.png"));
		box.setOpaque(false);
		box.setText("");
		box.setFocusable(false);
		box.setBackground(null);
	}

	public void buildSeparator(JPanel panel) {
		panel.setBackground(Color.white);
		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		panel.setBorder(border);
	}

	public void buildButton(JButton btn) {
		btn.setFont(new Font("Verdana", Font.BOLD, 20));
		btn.setForeground(Color.white);
		btn.setFocusable(false);
		btn.setBackground(c.getDarkGreen());
		btn.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				Color temp = e.getComponent().getBackground();
				e.getComponent().setBackground(e.getComponent().getForeground());
				e.getComponent().setForeground(temp);
			}

			public void mouseEntered(MouseEvent e) {
				Color temp = e.getComponent().getBackground();
				e.getComponent().setBackground(e.getComponent().getForeground());
				e.getComponent().setForeground(temp);
			}
		});

		Border rounded = new LineBorder(Color.white, 2, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		btn.setBorder(border);
	}
}
