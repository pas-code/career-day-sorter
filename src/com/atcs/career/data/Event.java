//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	
	
	//TESTING
	public static void main(String[] args) {
		Event e = new Event("career");
		save(e);
	}
	
	
	
	
	
	
	
	/**
	 * Creates a new Event from scratch
	 */
	public Event(String name){
	   eventName = name;
	   students = IOUtilities.loadStudentArray(IOUtilities.importCSV());
	   amountOfSessions = sessions.size();
	   rooms = IOUtilities.loadRoomArray();
	   sessions = IOUtilities.loadSessionArray();
	}
	
	public static void save(Event e){
		try {
			String location = System.getProperty("user.home") + "/Desktop/Event/Event.event";
			new File(location).createNewFile();
			FileOutputStream fos = new FileOutputStream(location);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(e);
			fos.close();
			os.close();
		
		} catch(IOException i){
			i.printStackTrace();
		}
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

   public String getEventName() {
      return eventName;
   }

   public void setEventName(String eventName) {
      this.eventName = eventName;
   }
	
}
