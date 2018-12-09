//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.Serializable;
import java.util.ArrayList;

import com.atcs.career.io.IOUtilities;
import com.atcs.career.io.file.FileHandler;

public class Event implements Serializable {

	private static final long serialVersionUID = -7463051683970561540L;
	private int amountOfSessions;
	private ArrayList<Session> sessions = new ArrayList<Session>();
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private String eventName;

	// TESTING
	public static void main(String[] args) {
		Event e = new Event("career");
		FileHandler.save(e);
	}

	/**
	 * Creates a new Event from scratch
	 */
	public Event(String name) {
		eventName = name;
		// students = IOUtilities.loadStudentArray(IOUtilities.importCSV());
		// rooms = IOUtilities.loadRoomArray(IOUtilities.importCSV());
		// sessions = IOUtilities.loadSessionArray(IOUtilities.importCSV());
		// amountOfSessions = sessions.size();
		students = new ArrayList<Student>();
		rooms = new ArrayList<Room>();
		sessions = new ArrayList<Session>();
		amountOfSessions = 0;
	}

	public void selectStudentFile() {
		students = IOUtilities.loadStudentArray(IOUtilities.importCSV());
		amountOfSessions = sessions.size();
	}

	public void selectRoomFile() {
		rooms = IOUtilities.loadRoomArray(IOUtilities.importCSV());
	}

	public void selectSessionFile() {
		sessions = IOUtilities.loadSessionArray(IOUtilities.importCSV());
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
