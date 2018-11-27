//Thomas Varano
//[Program Descripion]
//Nov 28, 2017

package com.atcs.career.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;

public final class ResourceAccess
{
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
         e.printStackTrace();
         return null;
      }
   }
   
   public static String readHtml(URL site) throws IOException {
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
}
