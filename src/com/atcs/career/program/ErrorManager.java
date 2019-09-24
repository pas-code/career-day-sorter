//Thomas Varano
//Nov 27, 2018

package com.atcs.career.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import com.atcs.career.io.file.FileHandler;
import com.atcs.career.program.logging.BasicLogger;

public class ErrorManager {
	private static final BasicLogger logger = BasicLogger.getLogger(ErrorManager.class.getName());
	
	static {
		try {
			logger.setOut(new PrintStream(new FileOutputStream(new File(FileHandler.ERROR_LOG))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * report the error (likely via email) to devs.
	 */
	private static void reportException(Throwable e, String msg) {
		
	}
	
	/**
	 * show the exception to the user as a popup
	 */
	public static void showException(Throwable e, String msg) {
		
	}
	
	public static void showErrorMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, CareerDay.APP_NAME + " | Error", JOptionPane.ERROR_MESSAGE, null);
	}
	
	public static void processException(Throwable e, String usrMsg, String logMsg, boolean fatal, boolean notify) {
		if (fatal) {
			// report the error in a separate thread
			new Thread(new Runnable() {
				@Override
				public void run() {
					reportException(e, logMsg);
				}
			}).start();
			// show the exception to the user.
			showException(e, usrMsg);
			e.printStackTrace();
		}
		else {
			if (notify)
				showErrorMessage(usrMsg);
			StackTraceElement topElem = e.getStackTrace()[0];
			logger.error(e, topElem.getClassName() + "-" + topElem.getLineNumber() + " " + logMsg);
		}
	}
	
	public static void processException(Throwable e, String univMsg, boolean fatal, boolean notify) {
		processException(e, univMsg, univMsg, fatal, notify);
	}
}
