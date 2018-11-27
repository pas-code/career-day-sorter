//Thomas Varano
//Nov 27, 2018

package com.atcs.career.program.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class SampleLoggingClass {
	private static final BasicLogger log = BasicLogger.getLogger(SampleLoggingClass.class.getName());
	
	static {
		// logging levels are used for how detailed a log is. 
		// if youre debugging (like a sysout in a loop or something) put it to a finer level.
		// then, when youre running, you can set the log's FILTER so it only sysouts what you want.
		log.setFilter(Level.FINE);
		
		//you can change the output too, using a printstream
		// it is normally system.out, but you can change it
		log.setOut(System.out);
		
		//if you want more details, change verbose. it is initially set to false
		log.setVerbose(true);	
	}
	
	public static void main(String[] args) {
		log.log(Level.WARNING, "this is a verbose warning");
		
		// this log wont show up because the filter is higher than the level of the log.
		log.log(new LogRecord(Level.FINER, "a fine log"));
		
		log.setVerbose(false);
		log.log(Level.CONFIG, "this is a nonverbose config msg");
	}
	
	

}
