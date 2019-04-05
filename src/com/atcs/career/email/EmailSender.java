//Thomas Varano
//Dec 19, 2018

package com.atcs.career.email;

import java.io.IOException;

import com.atcs.career.data.Event;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.io.importexport.CSVWriter;
import com.atcs.career.io.importexport.ScriptInterpreter;

/*
 * sam args
 * args[0] is the csv file
 * args[1] is the template file
 */
public class EmailSender {
	private static void runEmailJar(String[] args) {
		try {
			ScriptInterpreter.printResponse(
					ScriptInterpreter.runJar(FileHandler.JAVA_EXEC, FileHandler.EMAIL_JAR, args));
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendReminderEmail(Event e) {
		CSVWriter.exportEventToEmailReminder(e);
		runEmailJar(new String[] {FileHandler.EMAIL_CSV, FileHandler.REMINDER_TEMPLATE});
	}

	public static void sendAssignmentEmail(Event e) {
		CSVWriter.exportEventToEmailReminder(e);
		runEmailJar(new String[] {FileHandler.EMAIL_CSV, FileHandler.ASSIGNMENT_TEMPLATE});
	}
}
