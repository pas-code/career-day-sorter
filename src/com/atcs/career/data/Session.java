//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Comparable<Session>, Serializable, GuiListable {

	private static final long serialVersionUID = -3388864228230228543L;
	private String title, speaker;
	private ArrayList<ArrayList<Student>> students;
	private Room room;
	/**
	 * list of all grades available to see the session (9, 10, 11, 12) If it's
	 * empty, its all grades.
	 */
	private int[] gradesAvailable;
	private boolean[] periodAvailability;
	private int popularity;
	private Event masterEvent;

	public Session(String title, String speaker, Event masterEvent) {
		this.title = title;
		this.speaker = speaker;
		this.students = new ArrayList<ArrayList<Student>>();
		for (int i = 0; i < masterEvent.getNumberOfPeriods(); i++) {
			students.add(new ArrayList<Student>());
		}
		this.gradesAvailable = new int[]{9, 10, 11, 12};
		this.periodAvailability = new boolean[masterEvent.getNumberOfPeriods()];
		for(int i = 0; i < periodAvailability.length; i++) {
		   periodAvailability[i] = true;
		}
		this.popularity = 0;
		if(this.speaker.charAt(0) == '"') {
         this.speaker = this.speaker.substring(1);
      }
		this.masterEvent = masterEvent;
	}
//	public Session(String title, String speaker) {
//		this(title, speaker, 3, null);
//	}

	public Session() {
		this("New Session", "New Speaker", null);
		System.out.println(" WARNING WARNING UH OH ROOM CREATON");
	}

	@Override
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSpeaker() {
		return speaker;
	}
	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	public ArrayList<ArrayList<Student>> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<ArrayList<Student>> students) {
		this.students = students;
	}
	public void setStudents(ArrayList<Student> students, int period) {
		this.students.set(period, students);
	}

	public int[] getGradesAvailable() {
		return gradesAvailable;
	}
	public void setGradesAvailable(int[] gradesAvailable) {
		this.gradesAvailable = gradesAvailable;
	}
	public int getPopularity() {
		return popularity;
	}

	public void addPopularity(int addBy) {
		popularity += addBy;
	}
	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public int compareTo(Session o) {
		return this.popularity - o.getPopularity();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Session))
			return false;
		Session otherSession = (Session) obj;
		return this.speaker.equals(otherSession.getSpeaker())
				&& this.title.equals(otherSession.getTitle());

	}

//	@Override
//	public String toString() {
//		return "Session [title=" + title + ", speaker=" + speaker
//				+ ", popularity=" + popularity + "]";
//	}
	
	@Override
	public String toString() {
		return title;
	}

	@Override
	public String getInfo(int i) {
		switch (i) {
			case 0 : return speaker;
			case 1 :
				return students.isEmpty() || students.get(masterEvent.getCurrentPeriod()) == null
								? ""
								: students.get(masterEvent.getCurrentPeriod()).size() + "";
			default : return getTitle();
		}
	}
	
	@Override
	public String getInfoTitle(int i) {
		switch (i) {
			case 0 : return "Speaker";
			case 1 : return "Resident Students";
			default : return "";
			
		}
	}

	@Override
	public String getType() {
		return "Session";
	}

	@Override
	public String getIdentifier() {
		return title + speaker;
	}

   public boolean[] getPeriodAvailability(){
      return periodAvailability;
   }

   public void setPeriodAvailability(boolean[] availableThisPeriod){
      this.periodAvailability = availableThisPeriod;
   }

}
