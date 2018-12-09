//Thomas Varano
//Nov 9, 2018

package com.atcs.career.io.importexport;

import java.awt.FileDialog;

import javax.swing.JFrame;

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
}
