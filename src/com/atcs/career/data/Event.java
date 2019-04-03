//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import com.atcs.career.io.IOUtilities;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.io.importexport.CSVReader;

public class Event implements Serializable {

	private static final long serialVersionUID = -7463051683970561540L;
	private static final int minSessionSize = 10; //COME BACK AND CHANGE TO PROPER VALUE TODO
	public static int startDay = 0;
	public static int startYear = 0;
	private int amountOfSessions;
	private ArrayList<Session> sessions = new ArrayList<Session>();
	private ArrayList<Student> masterStudents = new ArrayList<Student>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private String eventName, oldName;
	private LocalDate dateLastModified;
	private byte numberOfPeriods;
	private boolean sorted;
	
	private String studentFile, sessionFile, requestFile, roomFile;
	
	public Event() {
		rooms = new ArrayList<Room>();
		sessions = new ArrayList<Session>();
		masterStudents = new ArrayList<Student>();
	}
	
	
	public static Event readTestEvent() { 
		Event ret = new Event();
		ret.changeName("nameChanged");
		
		return ret;
	}
	
	public Session getSessionFromName(String title) {
		for (Session s : sessions)
			if (s.getTitle().equals(title))
				return s;
		return null;
	}

	public void selectStudentFile() {
		IOUtilities.combineStudentArrays(
				IOUtilities.loadStudentArray(CSVReader.getFileLocation(".csv"), getNumberOfPeriods()),
				getMasterStudents());
		amountOfSessions = sessions.size();
	}

	public void selectRoomFile() {
		rooms = IOUtilities.loadRoomArray(CSVReader.getFileLocation(".csv"), getNumberOfPeriods());
	}
	

	public void selectSessionFile() {
		sessions = IOUtilities.loadSessionArray(CSVReader.getFileLocation(".csv"));
		amountOfSessions = sessions.size();
	}
	
	public void save() {
		dateLastModified = LocalDate.now();
		FileHandler.save(this);
	}

	public int getAmountOfSessions() {
		return amountOfSessions;
	}

	public ArrayList<Session> getSessions() {
		return sessions;
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public String getStudentFile() {
		return studentFile;
	}

	public void setStudentFile(String studentFile) {
		this.studentFile = studentFile;
	}

	public String getSessionFile() {
		return sessionFile;
	}

	public void setSessionFile(String sessionFile) {
		this.sessionFile = sessionFile;
	}

	public String getRequestFile() {
		return requestFile;
	}

	public void setRequestFile(String requestFile) {
		this.requestFile = requestFile;
	}

	public String getRoomFile() {
		return roomFile;
	}

	public void setRoomFile(String roomFile) {
		this.roomFile = roomFile;
	}

	public String getEventName() {
		return eventName;
	}

	public void setInitialName(String name) {
		this.eventName = name;
	}
	
	public void changeName(String eventName) {
		oldName = this.eventName;
		this.eventName = eventName;
	}

   public ArrayList<Student> getMasterStudents() {
      return masterStudents;
   }

   public void setMasterStudents(ArrayList<Student> masterStudents) {
      this.masterStudents = masterStudents;
   }

   public static long getSerialversionuid() {
      return serialVersionUID;
   }

   public static int getMinsessionsize() {
      return minSessionSize;
   }

   public void setAmountOfSessions(int amountOfSessions) {
      this.amountOfSessions = amountOfSessions;
   }

   public void setSessions(ArrayList<Session> sessions) {
      this.sessions = sessions;
   }

//   public void setStudents(ArrayList<Student> students) {
//      this.students = students;
//   }
   
   public ArrayList<Student> studentsWithRequests() {
   	ArrayList<Student> retval = new ArrayList<Student>();
   	for (Student s : masterStudents) 
   		if (!s.getRequests().isEmpty())
   			retval.add(s);
   	return retval;
   }
   
   public ArrayList<Student> studentsWithoutRequests() {
   	ArrayList<Student> retval = new ArrayList<Student>();
   	for (Student s : masterStudents)
   		if (s.getRequests().isEmpty())
   			retval.add(s);
   	return retval;
   }

   public void setRooms(ArrayList<Room> rooms) {
      this.rooms = rooms;
   }
   
	public String getOldName() {
		return oldName;
	}
	
	public boolean nameChanged() {
		return oldName != null && !oldName.equals(eventName);
	}
	
	public static String saveFileName(String eventName) {
		return eventName + FileHandler.SUFFIX;
	}
	
	public byte getNumberOfPeriods() {
		return numberOfPeriods;
	}

	public void setNumberOfPeriods(byte numberOfPeriods) {
		this.numberOfPeriods = numberOfPeriods;
	}

	public boolean isSorted() {
		return sorted;
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	public static String extractEventName(String saveFileName) {
		return saveFileName.substring(0, saveFileName.indexOf(FileHandler.SUFFIX));
	}
	
	public LocalDate getLastModified() {
		return dateLastModified;
	}
	
	public void setLastModified(LocalDate date) {
		this.dateLastModified = date;
	}
	
	public String toString() {
		return getEventName();
	}
	
	public String infoString() {
		return getEventName()  + "\n" + masterStudents + "\n" + rooms + "\n" + sessions; 
	}
	
}