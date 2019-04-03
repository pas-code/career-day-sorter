//Information Team
//Program description:
//Nov 29, 2018

package com.atcs.career.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import com.atcs.career.data.Event;
import com.atcs.career.program.ErrorManager;
import com.atcs.career.program.MainClass;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.resources.ResourceAccess;


public class FileHandler {
	private static final BasicLogger log = BasicLogger.getLogger(FileHandler.class.getName());
	
	
	// TODO change to a hashmap?
   public static String HOME_DIR, SAVE_DIR, LOG_DIR, EMAIL_DIR;
   public static String JAVA_EXEC;
   public static String GEN_LOG, ERROR_LOG, CHANGE_LOG;
   public static String EMAIL_CSV, EMAIL_JAR, ASSIGNMENT_TEMPLATE, REMINDER_TEMPLATE;
   
   public static final String SUFFIX = ".event";
      
   //initial file work
   static {
   	log.log(Level.CONFIG, "initial file work commenced");
   	createFileNames();
   	try {
			createFiles();
			if (MainClass.isApp)
				ResourceAccess.transfer("java", new File(JAVA_EXEC), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
   	log.log(Level.CONFIG, "file work completed");
   }
   
   public static void createFileNames() {
      HOME_DIR = Addresses.getHome();
      SAVE_DIR = HOME_DIR + "Events/";
      LOG_DIR = HOME_DIR + "Logs/";
      EMAIL_DIR = HOME_DIR + "EmailTemp/";
      
      EMAIL_CSV = EMAIL_DIR + "emailDataTransfer.csv";
      EMAIL_JAR = EMAIL_DIR + "emailer.jar";
      ASSIGNMENT_TEMPLATE = EMAIL_DIR + "emailAssignmentTemplate.txt";
      REMINDER_TEMPLATE = EMAIL_DIR + "emailReminderTemplate.txt";
      
    	JAVA_EXEC = Addresses.getExecutiveDir() + "/PlugIns/Java.runtime/Contents/Home/bin/java";
   }
   
   public static void createFiles() throws IOException {
      new File(HOME_DIR).mkdirs();
      new File(SAVE_DIR).mkdir();
      new File(LOG_DIR).mkdir();
      new File(EMAIL_DIR).mkdir();
      log.config("created files.");
      
      new File(EMAIL_CSV).createNewFile();
      new File(EMAIL_JAR).createNewFile();
      ResourceAccess.transfer("assignmentEmailTemplate.txt", new File(ASSIGNMENT_TEMPLATE), 0);
      ResourceAccess.transfer("reminderEmailTemplate.txt", new File(REMINDER_TEMPLATE), 0);
      ResourceAccess.transfer("emailer.jar", new File(EMAIL_JAR), 0);
      
//      new File(Addresses.getExecutiveDir() + "/PlugIns/Java.runtime/Contents/Home/bin/").mkdir();
      if (MainClass.isApp) 
	      new File(Addresses.getExecutiveDir() + "/PlugIns/Java.runtime/Contents/Home/bin").mkdir();
      
   }
   
   public static void save(Event e){
   	try {
   		// save new file
         String location = SAVE_DIR + e.getEventName() + SUFFIX;
         System.out.println("Create "+location);
         new File(location).createNewFile();
         ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(location));
         os.writeObject(e);
         os.close();
         
         // delete old file
         if (e.nameChanged() && e.getOldName() != null)
         	new File(SAVE_DIR + e.getOldName() + SUFFIX).delete();

      } catch(IOException e1){
         ErrorManager.processException(
         		e1, "Cannot Save Event.\nPlease Try Again.", "cannot save event. ioException", false, true);
      }
   }
   
   public static Event load(String filePath) throws IOException, ClassNotFoundException{
   	if (filePath == null || filePath == "") {
   		return null;
   	}
	   ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
	   Event e = (Event) ois.readObject();
	   System.out.println("Event read.");
	   ois.close();
	   return e;
   }
}
