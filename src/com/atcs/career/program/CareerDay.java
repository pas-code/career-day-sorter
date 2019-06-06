//Thomas Varano
//Nov 27, 2018

package com.atcs.career.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.atcs.career.data.Event;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.ui.MasterUI;

public class CareerDay {
	public static final String APP_NAME = "Career Day";
   public static final String BUILD = "1.0";
   public static final String LAST_UPDATED = "May 2019";
   public static final boolean fullRelease = false;
   public static final boolean isApp = System.getProperty("user.dir").indexOf(".app") > 0; 
   
	public static final BasicLogger changeLog = BasicLogger.getLogger("Change Log");
	static {
		try {
			changeLog.setOut(new PrintStream(new FileOutputStream(new File(FileHandler.CHANGE_LOG))));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				initialize(concatArgs(args));
//				initialize(null);
				changeLog.severe("Opening program");
			}
		}).start();
	}
	
	/**
	 * used for opening files but not implemented yet (hopefully in the future, give an option for .event 
	 * files and have a "open with careerday" option)
	 */
	private static String concatArgs(String[] args) {
		if (args.length == 0) return null;
		String ret = args[0];
		for (int i = 1; i < args.length; i++) 
			ret += " " +args[i];
		return ret;
	}
	
	/**
	 * open the program
	 * @param eventPathToOpen the event opened if the option for opening with 
	 */
	private static void initialize(String eventPathToOpen) {
		MasterUI master = new MasterUI();
		if (eventPathToOpen == null || eventPathToOpen == "") {
			//open the welcome screen
			master.openWelcome();
		}
		else {
			try {
				Event e = FileHandler.load(eventPathToOpen);
				master.openEventFromWelcome(e);
			} catch (ClassNotFoundException | IOException e) {
				//open the main screen with said event
				e.printStackTrace();
				master.openWelcome();
			}
		}
	}
}
