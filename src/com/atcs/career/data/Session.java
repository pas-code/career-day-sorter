//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Session implements Comparable<Session>, Serializable {
	private String title, speaker;
	private ArrayList<Student> students;
	/**
	 * list of all grades available to see the session (9, 10, 11, 12)
	 * If it's empty, its all grades.
	 */
	private int[] gradesAvailable;
	private int popularity;

   public Session(String title, String speaker, ArrayList<Student> students, int[] gradesAvailable, int popularity){
      super();
      this.title = title;
      this.speaker = speaker;
      this.students = students;
      this.gradesAvailable = gradesAvailable;
      this.popularity = popularity;
   }

   public Session(String title, String speaker){
      this.title = title;
      this.speaker = speaker;
      this.students = new ArrayList<Student>();
      this.gradesAvailable = new int[]{9,10,11,12};
      this.popularity = 0;
   }
	public String getTitle() {
      return title;
   }
   public void setTitle(String title){
      this.title = title;
   }
   public String getSpeaker() {
      return speaker;
   }
   public void setSpeaker(String speaker) {
      this.speaker = speaker;
   }
   public ArrayList<Student> getStudents() {
      return students;
   }
   public void setStudents(ArrayList<Student> students) {
      this.students = students;
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
   public void setPopularity(int popularity) {
      this.popularity = popularity;
   }

   @Override
   public int compareTo(Session o) {
      return this.popularity - o.getPopularity();
   }

@Override
public String toString() {
	return "Session: [Title=" + title + ", Speaker=" + speaker + ", Popularity=" + popularity + "]";
}
	
}
