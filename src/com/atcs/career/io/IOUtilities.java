//Michael Reineke
//Information Team
//Nov 21, 2018

package com.atcs.career.io;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.io.importexport.CSVReader;
import com.atcs.career.io.importexport.ScriptInterpreter;

public class IOUtilities
{
   public static void main(String[] args)
   {
//      loadStudentArray(importCSV());
//      ArrayList<Room> arr = loadRoomArray();
      ArrayList<Session> arr = loadSessionArray();
      System.out.println(arr);
   }
   
   /**
    * Loads ArrayList with Room objects from local .csv file
    * @return ArrayList of Room objects
    */
   public static ArrayList<Room> loadRoomArray(){
      ArrayList<Room> rooms = new ArrayList<Room>();
      ArrayList<String[]> lines = CSVReader.readCSV("src/com/atcs/career/data/DeskCount.csv");
//      ArrayList<String[]> lines = CSVReader.readCSV(CSVReader.getFileLocation(".csv"));
      
      for(int i = 0; i < lines.size(); i++){
//         if(lines.get(i)[0].equals(""))
//            break;
         String roomNum = lines.get(i)[0].trim();
         int roomCap = Integer.parseInt(lines.get(i)[1].trim());
         rooms.add(new Room(roomNum, roomCap));
      }
      return rooms;
   }
   
   /**
    * Loads ArrayList with Session objects from local .csv file
    * @return ArrayList of Session objects
    */
   public static ArrayList<Session> loadSessionArray(){
      ArrayList<Session> sessions = new ArrayList<Session>();
      ArrayList<String[]> lines = CSVReader.readCSV("src/com/atcs/career/data/sessions.csv");
//      ArrayList<String[]> lines = CSVReader.readCSV(CSVReader.getFileLocation(".csv"));
      
      for(int i = 0; i < lines.size(); i++){
//    	  System.out.println(lines.get(i)[0]);
//    	  System.out.println(lines.get(i)[0].indexOf("-"));
//    	  System.out.println(lines.get(i)[0].substring(lines.get(i)[0].indexOf(" - ") + 3));
         String speaker = lines.get(i)[0].substring(0, lines.get(i)[0].indexOf(" - "));
         String title = lines.get(i)[0].substring(lines.get(i)[0].indexOf(" - ") + 3);
         sessions.add(new Session(title, speaker));
      }
      return sessions;
   }

   /**
    * Loads ArrayList with Student objects from .csv file
    * @param fileName the file name of the .csv file, including suffix and path
    * @return ArrayList of Student objects
    */
	public static ArrayList<Student> loadStudentArray(String fileName) {
		ArrayList<Student> students = new ArrayList<Student>();
		ArrayList<String[]> lines = CSVReader.readCSV(fileName);

		for (int i = 1; i < lines.size(); i++) {
			// Stores each element of the line as an appropriately name variable
			String lastName = lines.get(i)[3].trim().replace("\"", "");
			lastName = lastName.substring(0, 1).toUpperCase()+ lastName.substring(1);
			String firstName = lines.get(i)[2].trim().replace("\"", "");
			firstName = firstName.substring(0, 1).toUpperCase()+ firstName.substring(1);

			String email = lines.get(i)[1].trim().replace("\"", "");
			String date = lines.get(i)[0].replace("\"", "");
			Calendar daySubmitted = new GregorianCalendar(
					Integer.parseInt(date.substring(0, 4)),
					Integer.parseInt(date.substring(5, 7)),
					Integer.parseInt(date.substring(8, 10)));
			// Populates an ArrayList of Session objects with each Student's requests
			ArrayList<Session> sessionRequests = new ArrayList<Session>();
			for (int k = 4; k < lines.get(i).length; k++)
				sessionRequests.add(new Session(
						lines.get(i)[k].substring(lines.get(i)[k].indexOf("-") + 2),
						lines.get(i)[k].substring(0, lines.get(i)[k].indexOf("-") - 1),
						// TODO change amt sessions from 3
						3));

			// Adds Student object to the ArrayList to be returned
			students.add(new Student(lastName, firstName, email, sessionRequests,
					daySubmitted.get(Calendar.DAY_OF_YEAR)));
			System.out.println(students.get(i - 1));
		}
		return students;
	}
}
