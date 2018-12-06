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
	
	
	//TESTING
	public static void main(String[] args) {
		Event e = new Event("career");
//		FileHandler.save(e);
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
	

<<<<<<< HEAD
=======
    public Event()
    {
        super();
        sessions = new ArrayList<Session>();
        sessions.add(new Session("Business", "Donald Trump"));
        sessions.add(new Session("Investment", "Warren Buffet"));
        sessions.add(new Session("Military", "James Mattis"));
        sessions.add(new Session("Electrical Engineering", "Elon Musk"));
        sessions.add(new Session("Astronomy", "Albert Einstein"));
        sessions.add( new Session("Criminal Defense", "Robert Shapiro"));
        sessions.add(new Session("Intelligence", "James Comey"));
        sessions.add(new Session("Software Development", "Johnny Ive"));
        
        students = new ArrayList<Student>();
//        students.add(new Student("Peter", "Peter", "ppeter20@pascack.org", new Session[](sessions[0], sessions[1], sessions[2]), 0))
        
        this.students = students;
        this.rooms = rooms;
        this.eventName = eventName;
    }
>>>>>>> origin/ui-edward


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