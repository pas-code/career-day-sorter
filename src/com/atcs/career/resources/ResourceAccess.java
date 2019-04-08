//Thomas Varano
//[Program Descripion]
//Nov 28, 2017

package com.atcs.career.resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;

import javax.swing.ImageIcon;

import com.atcs.career.program.ErrorManager;
import com.atcs.career.program.logging.BasicLogger;

public final class ResourceAccess {
	private static final BasicLogger log = BasicLogger.getLogger(ResourceAccess.class.getName());
	
   public static InputStream getResourceStream(String localPath) {
      return ResourceAccess.class.getResourceAsStream(localPath);
   }
   
   public static URL getResource(String localPath) {
   		return ResourceAccess.class.getResource(localPath);
   }
   
   public static ImageIcon getIcon(String localPath) {
      try {
         return new ImageIcon(ResourceAccess.class.getResource(localPath));
      } catch (NullPointerException e) {
      	com.atcs.career.program.ErrorManager.processException(
      			e, "cannot find image at " + localPath, false, false);
         return null;
      }
   }
   
   public static String readHtml(URL site) throws IOException {
   	log.log(Level.INFO, "read site " + site.getPath());
      java.io.BufferedReader in = null;
      in = new java.io.BufferedReader(new java.io.InputStreamReader(site.openStream()));
      java.lang.StringBuilder b = new java.lang.StringBuilder();
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
         b.append(inputLine);
         b.append("\n");
      }
      in.close();
      return b.toString();
   }
   
   public static void transfer(String localPath, File f, int skipLines) {
      try {
      	log.log(Level.INFO, "transfer "+localPath + " to "+f.getName());
         if (!f.createNewFile()) return;
         Scanner in = new Scanner(ResourceAccess.getResourceStream(localPath));
         for (int i = 0; i < skipLines; i++)
            in.nextLine();
         BufferedWriter bw = new BufferedWriter(new FileWriter(f));
         while (in.hasNextLine()) {
            bw.write(in.nextLine()+"\r\n");
         }
         f.setWritable(false);
         in.close();
         bw.close();
      } catch (IOException | NullPointerException e) {
         ErrorManager.processException(e, "error in transferring "+localPath, false, false);
      }
   }
}
