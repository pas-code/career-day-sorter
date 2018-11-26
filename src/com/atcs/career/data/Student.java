//Thomas Varano
//Nov 7, 2018

package com.atcs.career.data;

public class Student implements Comparable<Student>{
	private String name, email;
   private Session[] requests, assignments;
	private int grade;
	private Priority priority;
	
	  public Student(String name, String email, Session[] requests)
	  {
	      this.name = name;
	      this.email = email;
	      this.requests = requests;
	      priority = getStudentPriority();
	  }
	  
	  public Priority getStudentPriority(){
	     return priority;
	  }

   @Override
   public int compareTo(Student o){
      return (int)(this.getStudentPriority().getPriority() - o.getStudentPriority().getPriority());
   }
	
}
