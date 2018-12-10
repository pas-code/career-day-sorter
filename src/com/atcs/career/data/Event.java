//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.atcs.career.io.IOUtilities;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.io.importexport.CSVReader;

public class Event implements Serializable {

	private static final long serialVersionUID = -7463051683970561540L;
	private int amountOfSessions;
	private ArrayList<Session> sessions = new ArrayList<Session>();
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private String eventName;

	// TESTING
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Event e = new Event("career");
//		Event e = new Event();
		FileHandler.save(e);
		e = FileHandler.load(CSVReader.getFileLocation(".event"));
		System.out.println(e.getStudents());
		System.out.println(e.getRooms());
		System.out.println(e.getSessions());
	}

	/**
	 * Creates a new Event from scratch
	 */
	public Event(String name) {
		eventName = name;
		 students = IOUtilities.loadStudentArray(CSVReader.getFileLocation(".csv"));
		 rooms = IOUtilities.loadRoomArray(CSVReader.getFileLocation(".csv"));
		 sessions = IOUtilities.loadSessionArray(CSVReader.getFileLocation(".csv"));
		 amountOfSessions = sessions.size();
	}
	
	public Event()
	{
		sessions = new ArrayList<Session>();
		sessions.add(new Session("Business", "Donald Trump", 3));
		sessions.add(new Session("Investment", "Warren Buffet", 3));
		sessions.add(new Session("Military", "James Mattis", 3));
		sessions.add(new Session("Electrical Engineering", "Elon Musk", 3));
		sessions.add(new Session("Astronomy", "Albert Einstein", 3));
		sessions.add( new Session("Criminal Defense", "Robert Shapiro", 3));
		sessions.add(new Session("Intelligence", "James Comey", 3));
		sessions.add(new Session("Software Development", "Johnny Ive", 3));
		
		students = new ArrayList<Student>();
	}

	public void selectStudentFile() {
		students = IOUtilities.loadStudentArray(CSVReader.getFileLocation(".csv"));
		amountOfSessions = sessions.size();
	}

	public void selectRoomFile() {
		rooms = IOUtilities.loadRoomArray(CSVReader.getFileLocation(".csv"));
	}
	

	public void selectSessionFile() {
		sessions = IOUtilities.loadSessionArray(CSVReader.getFileLocation(".csv"));
		amountOfSessions = sessions.size();
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
