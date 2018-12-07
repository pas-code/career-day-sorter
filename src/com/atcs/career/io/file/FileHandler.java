//Information Team
//Program description:
//Nov 29, 2018

package com.atcs.career.io.file;

import java.io.File;

public class FileHandler {
   public static String HOME_DIR, SAVE_DIR, LOG_DIR, EMAIL_DIR;
   //initialFileWork()
   public static void createFileNames() {
      HOME_DIR = Addresses.getHome();
      SAVE_DIR = HOME_DIR + "Events/";
      LOG_DIR = HOME_DIR + "Logs/";
      EMAIL_DIR = HOME_DIR + "EmailTemp/";
   }
   
   public static void createFiles() {
      new File(HOME_DIR).mkdir();
      new File(SAVE_DIR).mkdir();
      new File(LOG_DIR).mkdir();
      new File(EMAIL_DIR).mkdir();
      
   }
}
