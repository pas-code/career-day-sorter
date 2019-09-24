//Information Team
//Program description:
//Nov 29, 2018

package com.atcs.career.io.file;

import java.net.MalformedURLException;
import java.net.URL;

import com.atcs.career.program.CareerDay;

public class Addresses {
   public static final String getExecutiveDir() {
      final String classPath = System.getProperty("java.class.path");
      if (CareerDay.isApp) 
         return classPath.substring(0, classPath.indexOf(".app")) + ".app";
      return classPath;
   }
   
   public static final String getHome() {
      if (CareerDay.isApp)
         return getExecutiveDir() + "/Contents/Data/";
      return System.getProperty("user.home") + "/Applications/ofCourse/Data/";
   }
   
   public static final URL githubHome() {
   	try {
			return new URL("https://github.com/pas-code");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
   }
}
