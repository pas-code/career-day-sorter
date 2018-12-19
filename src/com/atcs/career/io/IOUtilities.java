//Michael Reineke
//Information Team
//Nov 21, 2018

package com.atcs.career.io;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.atcs.career.data.Event;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.io.importexport.CSVReader;
import com.atcs.career.io.importexport.ScriptInterpreter;

public class IOUtilities
{
   public static void main(String[] args)
   {
//      IOUtilities.loadMasterStudentArray(CSVReader.getFileLocation(".csv"));
//      ArrayList<Room> arr = loadRoomArray(importCSV());
//      ArrayList<Session> arr2 = loadSessionArray(importCSV());
//      System.out.println(arr);
      loadStudentArray(CSVReader.getFileLocation(".csv"));
   }
   
   /**
    * Loads ArrayList with Room objects from local .csv file
    * @return ArrayList of Room objects
    */
   public static ArrayList<Room> loadRoomArray(String fileName){
      ArrayList<Room> rooms = new ArrayList<Room>();
      ArrayList<String[]> lines = CSVReader.readCSV(fileName);
//      ArrayList<String[]> lines = CSVReader.readCSV("src/com/atcs/career/data/DeskCount.csv");
      System.out.println(lines.size());
//      int size = 0;
//      for(String[] l : lines)
//         if(l[0] != null)
//            size++;
      for(int i = 0; i < lines.size(); i++){
         String roomNum = lines.get(i)[0].trim();
         int roomCap = Integer.parseInt(lines.get(i)[1].trim());
         System.out.println(roomNum + ", " + roomCap);
         rooms.add(new Room(roomNum, roomCap));
         if(lines.get(i)[0].equals("255"))
            break;
      }
      return rooms;
   }
   
   /**
    * Loads ArrayList with Session objects from local .csv file
    * @return ArrayList of Session objects
    */
   public static ArrayList<Session> loadSessionArray(String fileName){
      ArrayList<Session> sessions = new ArrayList<Session>();
      ArrayList<String[]> lines = CSVReader.readCSV(fileName);
      for(int i = 0; i < lines.size(); i++){
         String speaker = lines.get(i)[0].substring(0, lines.get(i)[0].indexOf(" - "));
         String title = lines.get(i)[0].substring(lines.get(i)[0].indexOf(" - ") + 3);
         sessions.add(new Session(title, speaker));
         System.out.println(sessions.get(i));
      }
      return sessions;
   }

   /**
    * Loads ArrayList with Student objects from .csv file
    * @param fileName the file name of the .csv file, including suffix and path
    * @return ArrayList of Student objects who submitted a form
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
         int yearSubmitted = Integer.parseInt(lines.get(i)[0].substring(0, 4)) * 1000;
         //Populates an ArrayList of Session objects with each Student's requests
         ArrayList<Session> sessionRequests = new ArrayList<Session>();
         for(int k = 4; k < lines.get(i).length; k++) 
            sessionRequests.add(new Session(lines.get(i)[k].substring(lines.get(i)[k].indexOf("-")+2), lines.get(i)[k].substring(0, lines.get(i)[k].indexOf("-")-1)));         
         
         //Adds Student object to the ArrayList to be returned
         students.add(new Student(lastName, firstName, email, sessionRequests, yearSubmitted + daySubmitted.get(Calendar.DAY_OF_YEAR), true));
         System.out.println(students.get(i-1));
      }
      return students;
   }
   /**
    * 
    * @param fileName
    * @return ArrayList of every student
    */
   public static ArrayList<Student> loadMasterStudentArray(String fileName){
      ArrayList<Student> masterStudents = new ArrayList<Student>();
      ArrayList<String[]> lines = CSVReader.readCSV(fileName);
      for(int i = 1; i < lines.size(); i++){
         //Stores each element of the line as an appropriately name variable
         String lastName = lines.get(i)[0].trim().substring(lines.get(i)[0].trim().indexOf(" ")+1).replace("\"", "");
         String firstName = lines.get(i)[0].trim().substring(0,lines.get(i)[0].trim().indexOf(" ")).replace("\"", "");
         String email = lines.get(i)[2].trim().replace("\"", "");
         
         //Adds Student object to the ArrayList to be returned
         masterStudents.add(new Student(lastName, firstName, email));
         System.out.println(masterStudents.get(i-1));
      }
      return masterStudents;
   }
}
