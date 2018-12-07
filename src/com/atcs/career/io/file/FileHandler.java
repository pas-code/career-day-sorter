//Information Team
//Program description:
//Nov 29, 2018

package com.atcs.career.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.atcs.career.data.Event;

public class FileHandler {
   public static String HOME_DIR, SAVE_DIR, LOG_DIR, EMAIL_DIR;
   public static final String SUFFIX = ".event";
   
   //initialFileWork()
   public static void createFileNames() {
      HOME_DIR = Addresses.getHome();
      SAVE_DIR = HOME_DIR + "Events/";
      LOG_DIR = HOME_DIR + "Logs/";
      EMAIL_DIR = HOME_DIR + "EmailTemp/";
   }
   
   public static void createFiles() {
      new File(HOME_DIR).mkdirs();
      new File(SAVE_DIR).mkdir();
      new File(LOG_DIR).mkdir();
      new File(EMAIL_DIR).mkdir();
      
   }
   
   public static void save(Event e){
      createFileNames();
      createFiles();
      try {
         String location = SAVE_DIR + e.getEventName() + SUFFIX;
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
