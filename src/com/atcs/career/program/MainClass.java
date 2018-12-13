//Thomas Varano
//Nov 27, 2018

package com.atcs.career.program;

import com.atcs.career.ui.welcome.WelcomeScreen;

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
				initialize();
			}
		}).start();
	}
	
	private static void initialize() {
		new WelcomeScreen();
	}
}
