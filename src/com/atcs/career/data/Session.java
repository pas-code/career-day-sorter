//Thomas Varano
//Nov 7, 2018

package com.atcs.career.data;

import java.util.ArrayList;

public class Session implements Comparable<Session>{
	private String title, speaker;
	private ArrayList<Student> students;
	/**
	 * list of all grades available to see the session (9, 10, 11, 12)
	 * If it's empty, its all grades.
	 */
	private int[] gradesAvailable;
	private int popularity;
   
   public Session(String title, String speaker){
      super();
      this.title = title;
      this.speaker = speaker;
   }
   
   @Override
   public int compareTo(Session o){
      return this.popularity - o.getPopularity();
   }

   public String getTitle(){
      return title;
   }

   public String getSpeaker(){
      return speaker;
   }

   public ArrayList<Student> getStudents(){
      return students;
   }

   public int getPopularity(){
      return popularity;
   }
	
}