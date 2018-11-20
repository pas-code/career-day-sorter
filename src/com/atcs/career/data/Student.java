//Thomas Varano
//Nov 7, 2018

package com.atcs.career.data;

public class Student {
	private String name, email;
   private Session[] requests, assignments;
	private int grade;
	private Priority priority;
	
	  public Student(String name, String email, Session[] requests, Session[] assignments)
	  {
	      this.name = name;
	      this.email = email;
	      this.requests = requests;
	      this.assignments = assignments;
	      priority = getStudentPriority();
	  }
	  
	  public Priority getStudentPriority(){
	     return null;
	  }
	
}
