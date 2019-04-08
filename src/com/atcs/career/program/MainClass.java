//Thomas Varano
//Nov 27, 2018

package com.atcs.career.program;

import java.io.IOException;

import com.atcs.career.data.Event;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.ui.MasterUI;

/**
 * NAME WILL BE CHANGED. 
 * @author Thomas Varano
 *
 */
public class MainClass {
	public static final String APP_NAME = "Career Day";
   public static final String BUILD = "0.1";
   public static final String LAST_UPDATED = "Apr 2019";
   public static final boolean fullRelease = false;
   public static final boolean isApp = System.getProperty("user.dir").indexOf(".app") > 0; 
   
	public static final BasicLogger changeLog = BasicLogger.getLogger("Change Log");


	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				initialize(concatArgs(args));
			}
		}).start();
	}
	
	private static String concatArgs(String[] args) {
		if (args.length == 0) return null;
		String ret = args[0];
		for (int i = 1; i < args.length; i++) 
			ret += " " +args[i];
		return ret;
	}
	
	private static void initialize(String eventPathToOpen) {
		if (eventPathToOpen == null || eventPathToOpen == "") {
			//open the welcome screen
			new MasterUI().openWelcome();
		}
		else {
			try {
				Event e = FileHandler.load(eventPathToOpen);
				new MasterUI().openEventFromWelcome(e);
			} catch (ClassNotFoundException | IOException e) {
				//open the main screen with said event
				e.printStackTrace();
			}
		}
	}
}
