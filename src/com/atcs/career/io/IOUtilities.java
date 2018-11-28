//Michael Reineke
//Information Team
//Nov 21, 2018

package com.atcs.career.io;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.io.importexport.CSVReader;
import com.atcs.career.io.importexport.ScriptInterpreter;

public class IOUtilities
{
   public static void main(String[] args)
   {
      loadStudentArray(importCSV()); 
   }
   
   /**
    * Prompts user to open a file (.csv)
    * @return 
    * String representing the .csv file path
    */
   public static String importCSV(){
      String filePath = "";
      URL importer = CSVReader.class.getResource("ImportLocation.scpt");
       try {
          filePath = ScriptInterpreter.getProcessValues(new ProcessBuilder("osascript", importer.getPath()))[0];
          filePath = filePath.replace(":", "/");
          filePath = filePath.substring(filePath.indexOf("/"));
          System.out.println("File: " + filePath);
//        loadStudentArray(filePath); 
          return filePath;
       } catch (IOException | InterruptedException e) {
          e.printStackTrace();
          return "File not found";
       }
   }

   /**
    * Loads ArrayList with Student objects from .csv file
    * @param fileName the file name of the .csv file, including suffix and path
    * @return ArrayList of Student objects
    */
   public static ArrayList<Student> loadStudentArray(String fileName){
      ArrayList<Student> students = new ArrayList<Student>();
      ArrayList<String[]> lines = CSVReader.readCSV(fileName);
      for(int i = 1; i < lines.size(); i++){
         //Stores each element of the line as an appropriately name variable
         String lastName = lines.get(i)[3].trim().replace("\"", "");
         lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
         String firstName = lines.get(i)[2].trim().replace("\"", "");
         firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
         String email = lines.get(i)[1].trim().replace("\"", "");
         String date = lines.get(i)[0].replace("\"", "");
         Calendar daySubmitted = new GregorianCalendar(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5,7)), Integer.parseInt(date.substring(8,10)));
         //Populates an ArrayList of Session objects with each Student's requests
         ArrayList<Session> sessionRequests = new ArrayList<Session>();
         for(int k = 4; k < lines.get(i).length; k++) 
            sessionRequests.add(new Session(lines.get(i)[k].substring(lines.get(i)[k].indexOf("-")+2), lines.get(i)[k].substring(0, lines.get(i)[k].indexOf("-")-1)));         
         
         //Adds Student object to the ArrayList to be returned
         students.add(new Student(lastName, firstName, email, sessionRequests, daySubmitted.get(Calendar.DAY_OF_YEAR)));
         System.out.println(students.get(i-1));
      }
      return students;
   }
}
