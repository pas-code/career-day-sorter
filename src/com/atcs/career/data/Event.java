//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.Serializable;
import java.util.ArrayList;

import com.atcs.career.io.IOUtilities;

public class Event implements Serializable{

   private static final long serialVersionUID = -7463051683970561540L;
   private int amountOfSessions;
	private ArrayList<Session> sessions = new ArrayList<Session>();
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private String eventName;
	
	/**
	 * Creates a new Event from scratch
	 */
	public Event(String name){
	   eventName = name;
	   students = IOUtilities.loadStudentArray(IOUtilities.importCSV());
	   amountOfSessions = sessions.size();
	   //rooms =
	   //sessions =
	}
	
	public void save(){
	   
	}


   public int getAmountOfSessions() {
      return amountOfSessions;
   }

   public ArrayList<Session> getSessions() {
      return sessions;
   }

   public ArrayList<Student> getStudents() {
      return students;
   }

   public ArrayList<Room> getRooms() {
      return rooms;
   }
	
}
