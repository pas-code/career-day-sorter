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
import com.atcs.career.program.MainClass;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.resources.ResourceAccess;


public class FileHandler {
	private static final BasicLogger log = BasicLogger.getLogger(FileHandler.class.getName());
	
   public static String HOME_DIR, SAVE_DIR, LOG_DIR, EMAIL_DIR;
   public static String JAVA_EXEC;
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
      
    	JAVA_EXEC = Addresses.getExecutiveDir() + "/PlugIns/Java.runtime/Contents/Home/bin/java";
   }
   
   public static void createFiles() throws IOException {
      new File(HOME_DIR).mkdirs();
      new File(SAVE_DIR).mkdir();
      new File(LOG_DIR).mkdir();
      new File(EMAIL_DIR).mkdir();
      
//      new File(Addresses.getExecutiveDir() + "/PlugIns/Java.runtime/Contents/Home/bin/").mkdir();
      if (MainClass.isApp) 
	      new File(Addresses.getExecutiveDir() + "/PlugIns/Java.runtime/Contents/Home/bin").mkdir();
      
   }
   
   public static void save(Event e){
   	try {
         String location = SAVE_DIR + e.getEventName() + SUFFIX;
         System.out.println("Create "+location);
         new File(location).createNewFile();
         ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(location));
         os.writeObject(e);
         os.close();
      
      } catch(IOException i){
         i.printStackTrace();
      }
   }
   
   public static Event load(String filepath) throws IOException, ClassNotFoundException{
	   ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
	   Event e = (Event) ois.readObject();
	   System.out.println("Event read.");
	   ois.close();
	   return e;
   }
}
