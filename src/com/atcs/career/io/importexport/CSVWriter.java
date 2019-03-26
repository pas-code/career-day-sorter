//Thomas Varano
//Nov 9, 2018

package com.atcs.career.io.importexport;

import java.awt.FileDialog;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.atcs.career.data.Event;

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
		/*
		 * csv for students
		 * 	student name (first last), student email, period 1 speaker, period 1 room, period 2 speaker, period 2 room, etc.
		 */
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		// header, row 1
		ArrayList<String> row = new ArrayList<String>();
		row.add("Student Name");
		row.add("Student Email");
		row.add("Student Email");
	}
	
	public static void exportEventToEmail(Event e) {
		
	}
}
