//Thomas Varano
//Mar 18, 2019

package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.atcs.career.data.Event;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public class ElementCreator {
	public static Session createSession(Event masterEvent) {
		JPanel message = new JPanel(new GridLayout(0,1));
		ArrayList<JTextField> fields = new ArrayList<JTextField>();
		message.add(createInfoField("Title", fields));
		message.add(createInfoField("Speaker", fields));
		
		if (JOptionPane.showConfirmDialog(null, message, "Create Session",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
			return new Session(fields.get(0).getText(), fields.get(1).getText(), masterEvent);
		return null;
	}
	
	public static Student createStudent(int numPeriods) {
		JPanel message = new JPanel(new GridLayout(0, 1));
		ArrayList<JTextField> fields = new ArrayList<JTextField>();
		message.add(createInfoField("First Name", fields));
		message.add(createInfoField("Last Name", fields));
		message.add(createInfoField("Email", fields));
		message.add(new JLabel("Change requests in the main editor"));
		
		if (JOptionPane.showConfirmDialog(null, message, "Create Student",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) 
			return new Student(fields.get(1).getText(), fields.get(0).getText(), fields.get(2).getText(), numPeriods);
		return null;
	}
	
	public static Room createRoom(int numPeriods) {
		JPanel message = new JPanel(new GridLayout(0, 1));
		ArrayList<JTextField> fields = new ArrayList<JTextField>();
		message.add(createInfoField("Room Name", fields));
		message.add(createInfoField("Capacity", fields));
//		JOptionPane.showMessageDialog(null, message, "Create Room", JOptionPane.PLAIN_MESSAGE);
		if (JOptionPane.showConfirmDialog(null, message, "Create Room",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			try {
				return new Room(fields.get(0).getText(),Integer.parseInt(fields.get(1).getText()), numPeriods);
			} catch (NumberFormatException e) {
				showException(e);
				// return null;
			}
		}
		return null;
		
	}
	
	private static JPanel createInfoField(String title, ArrayList<JTextField> fields) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel(title + ": "), BorderLayout.WEST);
		JTextField editField = new JTextField();
		panel.add(editField, BorderLayout.CENTER);
		fields.add(editField);
		return panel;
	}
	
	private static void showException(Exception e) {
		System.err.println("Showing exception "+e + " while creating element");
		JOptionPane.showMessageDialog(null, "An exception occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE, null);
	}
}
