//Information Team
//Program description:
//Nov 29, 2018

package com.atcs.career.io.file;

import java.io.File;
import java.io.IOException;

import com.atcs.career.resources.ResourceAccess;

public class FileHandler {
   public static String HOME_DIR, SAVE_DIR, LOG_DIR, EMAIL_DIR;
   public static String JAVA_EXEC;
   //initialFileWork()
   static {
   	createFileNames();
   	try {
			createFiles();
			ResourceAccess.transfer("java", new File(JAVA_EXEC), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
   }
   
   public static void createFileNames() {
      HOME_DIR = Addresses.getHome();
      SAVE_DIR = HOME_DIR + "Events/";
      LOG_DIR = HOME_DIR + "Logs/";
      EMAIL_DIR = HOME_DIR + "EmailTemp/";
      
      JAVA_EXEC = Addresses.getExecutiveDir() + "/PlugIns/Java.runtime/Contents/Home/bin/java";
   }
   
   public static void createFiles() throws IOException {
      new File(HOME_DIR).mkdir();
      new File(SAVE_DIR).mkdir();
      new File(LOG_DIR).mkdir();
      new File(EMAIL_DIR).mkdir();
      
      new File(Addresses.getExecutiveDir() + "/PlugIns/Java.runtime/Contents/Home/bin").mkdir();
      
      new File(JAVA_EXEC).createNewFile();
   }
}
