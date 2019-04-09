//Thomas Varano
//Nov 9, 2018

package com.atcs.career.io.importexport;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.atcs.career.data.Event;
import com.atcs.career.data.Student;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.program.MainClass;

public class CSVWriter {
	
	/**
	 * returns a string location of a file to save to, as selected by the user.
	 * @param acceptableSuffix the preferred file suffix (.txt, .csv) as a string
	 */
	public static String getFileLocation(String acceptableSuffix) {
		JFrame parent = new JFrame();
		FileDialog fd = new FileDialog(parent, "Export as...", FileDialog.SAVE);
		fd.setDirectory(System.getProperty("user.home"));
		fd.setFile("untitled"+acceptableSuffix);
		fd.setFilenameFilter((dir, name) -> name.endsWith(acceptableSuffix));
		fd.setVisible(true);
		String ret = (fd.getFile() == null) ? null : fd.getDirectory() + fd.getFile();
		parent.dispose();
		System.out.println(ret);
		return fd.getDirectory() + fd.getFile();
	}
	
	/**
	 * exports to 2 csv, one for students and one for speakers
	 * @param e
	 */
	public static void exportEvent(Event e) {
		File destination = new File(getFileLocation(".csv"));
		try {
			writeCSV(assignmentCSVData(e), destination);
			JOptionPane.showMessageDialog(
					null, "Event Exported.", MainClass.APP_NAME, JOptionPane.INFORMATION_MESSAGE, null);
		} catch (FileNotFoundException e1) {
			com.atcs.career.program.ErrorManager.processException(
					e1, "Cannot find file for event export.", false, true);
		}
	}
	
	private static ArrayList<ArrayList<String>> assignmentCSVData(Event e) {
		/*
		 * csv for students
		 * 	student name (first last), student email, period 1 speaker, period 1 room, period 2 speaker, period 2 room, etc.
		 */
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		
		// header, row 1
		ArrayList<String> row = new ArrayList<String>();
		row.add("Student Email, ");
		row.add("Student First Name, ");
		row.add("Student Last Name, ");
		for (byte i = 0; i < e.getNumberOfPeriods(); i++) {
			row.add("Period " + (i + 1) + " Session, ");
			row.add("Period " + (i + 1) + " Room, ");
		}
		values.add(row);
		
		//student rows
		for (int i = 0; i < e.getMasterStudents().size(); i++) {
			row = new ArrayList<String>();
			Student current = e.getMasterStudents().get(i);
			row.add(current.getEmail() + ", ");
			row.add(current.getfName() + ", ");
			row.add(current.getlName() + ", ");
			for (byte p = 0; p < e.getNumberOfPeriods(); p++) {
				row.add(current.getAssignment(p).getTitle() + ", ");
				row.add(current.getAssignment(p).getRoom().getRoomNumber() + ", ");
			}
			values.add(row);
		}
		return values;
	}
	
	private static void writeCSV(ArrayList<ArrayList<String>> data, File destination) throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream(destination));
		System.out.println("WRITEE");
		for (ArrayList<String> printRow: data) {
			for (String s : printRow) {
				out.print(s);
				System.out.print(s);
			}
			System.out.println();
			out.println();
		}
		out.close();
	}
	
	
	public static void exportEventToEmailAssignments(Event e) {
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		
		// header, row 1
		ArrayList<String> row = new ArrayList<String>();
		row.add("email");
		row.add("firstname, lastname");
		for (byte i = 0; i < e.getNumberOfPeriods(); i++) {
			row.add("session" + (i + 1) + ", ");
			row.add("room" + (i + 1) + ", ");
		}
		values.add(row);
		
		//student rows
		for (int i = 0; i < e.getMasterStudents().size(); i++) {
			row = new ArrayList<String>();
			Student current = e.getMasterStudents().get(i);
			row.add(current.getEmail() + ", ");
			row.add(current.getfName() + ", ");
			row.add(current.getlName() + ", ");
			for (byte p = 0; p < e.getNumberOfPeriods(); p++) {
				row.add(current.getAssignment(p).getTitle() + ", ");
				row.add(current.getAssignment(p).getRoom().getRoomNumber() + ", ");
			}
			values.add(row);
		}
		
		File destination = new File(FileHandler.EMAIL_CSV);
		try {
			writeCSV(values, destination);
		} catch (FileNotFoundException e1) {
			com.atcs.career.program.ErrorManager.processException(
					e1, "Cannot find file for event export.", false, true);
		}
	}
	
	public static void exportEventToEmailReminder(Event e) {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		ArrayList<String> row = new ArrayList<String>();
		row.add("email, firstname, lastname");
		data.add(row);
		for (Student s : e.studentsWithoutRequests()) {
			row = new ArrayList<String>();
			row.add(s.getEmail() + ", ");
			row.add(s.getfName() + ", ");
			row.add(s.getlName());
			data.add(row);
		}
		try {
			writeCSV(data, new File(FileHandler.EMAIL_CSV));
		} catch (FileNotFoundException e1) {
			com.atcs.career.program.ErrorManager.processException(
					e1, "Cannot find file for event export.", false, true);
		}
	}
}
