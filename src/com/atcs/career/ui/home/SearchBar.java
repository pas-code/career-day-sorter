//Thomas Varano
//Mar 12, 2019

package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;

public class SearchBar<T extends Searchable> extends JPanel implements ActionListener, KeyListener, FocusListener {
	private static final long serialVersionUID = 1L;
	/**
	 * target list
	 */
	private JList<T> list;
	/**
	 * all data from the list, no matter what query is searched
	 */
	private ArrayList<Searchable> allData;
	private boolean hasGhostText;
	/**
	 * field for queries
	 */
	private JTextField field;
	
	/**
	 * clear button
	 */
	private JButton clear;
	
	public SearchBar() {
		super(new BorderLayout());
		field = new JTextField();
		add(field);
		
		clear = new JButton("clear");
		add(clear, BorderLayout.EAST);
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				field.setText("");
				search("");
			}
		});
		
		allData = new ArrayList<Searchable>();
		field.addActionListener(this);
		field.addKeyListener(this);
		field.addFocusListener(this);
		enterGhostText();
	}
	
	public SearchBar(JList<T> list) {
		this();
		this.list = list;
		setAllData();
	}
	
	/**
	 * before any searching is done, makes sure all data is held in the allData ArrayList
	 */
	private void setAllData() {
		allData.removeAll(allData);
		ListModel<T> model = list.getModel();
		for (int i = 0; i < model.getSize(); i++)
			allData.add(model.getElementAt(i));
	}
	
	private void search(String target) {
		System.out.println("searching for " + target);
		ArrayList<Searchable> acceptableValues = new ArrayList<Searchable>();
		for (int i = 0; i < allData.size(); i++) {
			if (allData.get(i).getIdentifier().toLowerCase().contains(target.toLowerCase()))
				acceptableValues.add(allData.get(i));
		}
		setListData(acceptableValues);
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * given an array of values for the list to show, changes the list data to display only these items 
	 * @param values the values to be shown on the list
	 */
	private void setListData(ArrayList<Searchable> values) {
		System.out.println("setting list data to: " + list.hashCode());
		if (values.isEmpty())
			list.setListData((T[]) new Searchable[0]);
		list.setListData((T[]) (values.toArray(new Searchable[values.size()])));
	}
	
	private void close() {
		// set the data back to what it was
		setListData(allData);
		// delete query
		field.setText("");
	}
	
	public void setList(JList<T> list) {
		setList(list, false);
	}
	
	/**
	 * 
	 * @param list the master JList of values to search.
	 * @param firstSet if this is the first set (only to be used if the default constructor was used)
	 */
	public void setList(JList<T> list, boolean firstSet) {
		if (!firstSet)
			close();
		this.list = list;
//		if (!firstSet)
		setAllData();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		search(field.getText());
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		search(field.getText());
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if (hasGhostText)
			removeGhostText();
	}

	@Override
	public void focusLost(FocusEvent e) {
		enterGhostText();
	}
	
	private void enterGhostText() {
		if (field.getText().isEmpty()) {
			field.setForeground(Color.GRAY);
			field.setText("Search...");
			hasGhostText = true;
		}
	}
	
	private void removeGhostText() {
		hasGhostText = false;
		field.setText("");
		field.setForeground(Color.BLACK);
	}
	
	public String getText() {
		return field.getText();
	}
	
	public void setText(String text) {
		field.setText(text);
		search(field.getText());
	}
}
