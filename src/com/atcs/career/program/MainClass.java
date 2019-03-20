//Thomas Varano
//Nov 27, 2018

package com.atcs.career.program;

import java.io.IOException;

import com.atcs.career.data.Event;
import com.atcs.career.io.file.FileHandler;

/**
 * NAME WILL BE CHANGED. 
 * @author Thomas Varano
 *
 */
public class MainClass {
	public static final String APP_NAME = "ofCourse";
   public static final String BUILD = "0.1";
   public static final String LAST_UPDATED = "Dec 2018";
   public static final boolean fullRelease = false;
   public static final boolean isApp = System.getProperty("user.dir").indexOf(".app") > 0; 

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
		if (eventPathToOpen == null || eventPathToOpen == "")
			//open the welcome screen
			System.out.println("we have an event");
		else {
			try {
				Event e = FileHandler.load(eventPathToOpen);
			} catch (ClassNotFoundException | IOException e) {
				//open the main screen with said event
				e.printStackTrace();
			}
		}
	}
}
