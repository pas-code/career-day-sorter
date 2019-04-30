//Michael Reineke
//Information Team
//Nov 21, 2018

package com.atcs.career.io;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import com.atcs.career.data.Event;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.io.importexport.CSVReader;

public class IOUtilities
{   
   /**
    * Loads ArrayList with Room objects from local .csv file
    * @return ArrayList of Room objects
    */
   public static ArrayList<Room> loadRoomArray(String fileName, int numPeriods){
      ArrayList<Room> rooms = new ArrayList<Room>();
      String[][] lines = CSVReader.readCSV(fileName);
      for(int i = 0; i < lines.length; i++){
         String roomNum = lines[i][0].trim();
         int roomCap = Integer.parseInt(lines[i][1].trim());
         System.out.println(roomNum + ", " + roomCap);
         rooms.add(new Room(roomNum, roomCap, numPeriods));
         if(lines[i][0].equals("255"))
            break;
      }
      return rooms;
   }
   
   /**
    * Loads ArrayList with Session objects from local .csv file
    * @return ArrayList of Session objects
    */
   public static ArrayList<Session> loadSessionArray(String fileName, Event masterEvent){
      ArrayList<Session> sessions = new ArrayList<Session>();
      String[][] lines = CSVReader.readCSV(fileName);
      for(int i = 0; i < lines.length; i++){
         String speaker = lines[i][0].substring(0, lines[i][0].indexOf(" - "));
         String title = lines[i][0].substring(lines[i][0].indexOf(" - ") + 3);
         sessions.add(new Session(fixExtraneousCharacters(title), fixExtraneousCharacters(speaker), masterEvent));
      }
      return sessions;
   }

   /** 
    * a few instances have blank characters (namely 65279 blank space char) and quotes. just removing these
    */
   private static String fixExtraneousCharacters(String in) {
   	return in.replace((char)65279, '"').replace("\"", "").trim();
   }
   
   /**
    * Loads ArrayList with Student objects from .csv file
    * @param fileName the file name of the .csv file, including suffix and path
    * @return ArrayList of Student objects who submitted a form
    */
   public static ArrayList<Student> loadRequestsArray(String fileName, Event masterEvent){
      ArrayList<Student> students = new ArrayList<Student>();
      String[][] lines = CSVReader.readCSV(fileName);
      for(int i = 1; i < lines.length; i++){
         //Stores each element of the line as an appropriately name variable
         String lastName = lines[i][3].trim().replace("\"", "");
         lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
         String firstName = lines[i][2].trim().replace("\"", "");
         firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
         String email = lines[i][1].trim().replace("\"", "");
         String date = lines[i][0].replace("\"", "");
         System.out.println("date: " + lines[i][0]);
         // I cannot figure why this comes with a quote at the beginning. quoteOffset avoids it,
         // if fixed, just set quoteOffset to 0
         Calendar daySubmitted = new GregorianCalendar(
         		Integer.parseInt(date.substring(0, 4)),
         		Integer.parseInt(date.substring(5, 7)), 
         		Integer.parseInt(date.substring(8, 10)));
         int yearSubmitted = Integer.parseInt(date.substring(0, 4)) * 1000;
         //Populates an ArrayList of Session objects with each Student's requests
         ArrayList<Session> sessionRequests = new ArrayList<Session>();
         for(int k = 4; k < lines[i].length; k++)  {
            sessionRequests.add(new Session(
            		fixExtraneousCharacters(lines[i][k].substring(lines[i][k].indexOf("-")+2)), 
            		fixExtraneousCharacters(lines[i][k].substring(0, lines[i][k].indexOf("-")-1)), masterEvent));         
         }
         
         //Adds Student object to the ArrayList to be returned
         students.add(new Student(
         		lastName, firstName, email, sessionRequests,
         		yearSubmitted + daySubmitted.get(Calendar.DAY_OF_YEAR),
         		true, masterEvent.getNumberOfPeriods()));
      }
      return students;
   }
   /**
    * 
    * @param fileName
    * @return ArrayList of every student
    */
   public static ArrayList<Student> loadMasterStudentArray(String fileName, int numPeriods){
      ArrayList<Student> masterStudents = new ArrayList<Student>();
      String[][] lines = CSVReader.readCSV(fileName);
      System.out.println("\n\nSTUDENTS");
      for(int i = 1; i < lines.length; i++){
         //Stores each element of the line as an appropriately name variable
         String lastName = lines[i][0].trim().substring(lines[i][0].trim().indexOf(" ")+1).replace("\"", "");
         String firstName = lines[i][0].trim().substring(0,lines[i][0].trim().indexOf(" ")).replace("\"", "");
         String email = lines[i][2].trim().replace("\"", "");
         
         //Adds Student object to the ArrayList to be returned
         masterStudents.add(new Student(lastName, firstName, email, numPeriods));
      }
      return masterStudents;
   }
   
   // combine the requests student array and master array
   public static void combineStudentArrays(ArrayList<Student> requests, ArrayList<Student> master) {
   	// sort the student array using names
   	Collections.sort(requests, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				return o1.compareToEmail(o2);
			}	
   	});
   	int index = -1;
   	for (int i = 0; i < master.size(); i++) 
   		if ((index = indexOf(master.get(i), requests)) != -1) 
   			master.set(i, requests.get(index));
   		
   }
   
   /**
    * binary search of the requests for a student. assumes the array is sorted
    */
   private static int indexOf(Student target, ArrayList<Student> requests) {
   	return binarySearch(requests, target, 0, requests.size() - 1);
   }
   
   public static int binarySearch(ArrayList<Student> nums, Student target, int low, int high) {
      if (high < low) return -1;
      int mid = (low + high) / 2;
      if (nums.get(mid).equals(target)) return mid;
      if (nums.get(mid).compareToEmail(target) < 0)
         return binarySearch(nums, target, mid + 1, high);
      return binarySearch(nums, target, low, mid - 1);
   }
}
