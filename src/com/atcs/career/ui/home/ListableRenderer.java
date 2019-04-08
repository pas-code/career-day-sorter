//Thomas Varano
//Mar 12, 2019

package com.atcs.career.ui.home;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import com.atcs.career.data.GuiListable;

public class ListableRenderer extends JPanel implements ListCellRenderer<GuiListable>, FocusListener {
	private static final long serialVersionUID = 1L;
	private CareerDayGUI master;
	
	public ListableRenderer(CareerDayGUI master) {
		this.master = master;
	}
	
	private void refresh(GuiListable g, boolean isSelected) {
		Border line = BorderFactory.createLineBorder(isSelected ? Color.BLACK : Color.GRAY, isSelected ? 5 : 1);
		this.setBorder(BorderFactory.createTitledBorder(line, g.getTitle()));
		this.removeAll();
		this.add(new JLabel(g.getInfo(0)));
		this.add(new JLabel("Students: " + g.getInfo(1)));
		if (isSelected)
			master.setMoreInfo(g);
	}
	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends GuiListable> list, GuiListable value, int index,
			boolean isSelected, boolean cellHasFocus) {
		refresh(value, isSelected);
		return this;
	}

	@Override
	public void focusGained(FocusEvent e) {}



	@Override
	public void focusLost(FocusEvent e) {}

}
