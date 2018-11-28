//Thomas Varano
//Nov 27, 2018

package com.atcs.career.program;

import com.atcs.career.program.logging.BasicLogger;

public class ErrorManager {
	private static final BasicLogger logger = BasicLogger.getLogger(ErrorManager.class.getName());
	
	/**
	 * report the error (likely via email) to devs.
	 */
	private static void reportException(Throwable e, String msg, boolean fatal) {
		
	}
	
	/**
	 * show the exception to the user as a popup
	 */
	public static void showException(Throwable e, String msg, boolean fatal) {
		
	}
	
	/**
	 * the same as {@link #processException(Throwable, BasicLogger, String, boolean)}, 
	 * but if you don't have a logger
	 * @param callingClass the class the error occurred in. just use <code>ClassName.class</code>
	 */
	public static void processException(Throwable e, Class<?> callingClass, String msg, boolean fatal) {
		logger.error(e, " in " + callingClass.getName() + " \"" + msg + "\"");
		universalProcess(e, msg, fatal);
	}
	
	/**
	 * the main method to be used for processing exceptions, including logging and reporting errors. 
	 * When you come across an exception in a catch statement, unless the error is very unimportant, call this method
	 * <p><strong>NOTE if the error is trivial, just log it on the level "info"</strong></p>
	 * @param e the exception thrown
	 * @param log the logger in your class. if you don't have one, use {@link #processException(Throwable, Class, String, boolean)}
	 * @param msg more data to say in the report on the error
	 * @param fatal if the error crashes the program
	 */
	public static void processException(Throwable e, BasicLogger log, String msg, boolean fatal) {
		logger.error(e, msg);
		universalProcess(e, msg, fatal);
	}
	
	private static void universalProcess(Throwable e, String msg, boolean fatal) {
		if (fatal) {
			// report the error in a separate thread
			new Thread(new Runnable() {
				public void run() {
					reportException(e, msg, fatal);
				}
			}).start();
			// show the exception to the user.
			showException(e, msg, fatal);
		}
	}
}
