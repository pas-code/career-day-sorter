//Thomas Varano
//Nov 7, 2018

package com.atcs.career.data;

import java.util.Calendar;

public class Student {
	private String lName, fName, fullName, email;
	private Session[] requests, assignments;
	private int grade, priority, timeEntered;
	
	public static void main(String[] args)
   {
      Student s = new Student("Reineke", "Michael", "mreineke20@pascack.org", null, 100);
      System.out.println(s.getGrade());
   }
	
	public Student(String lastName, String firstName, String email, Session[] requests, int timeEntered) {
		lName = lastName;
		fName = firstName;
		fullName = fName + " " + lName;
		this.email = email; 
		this.requests = requests;
		this.timeEntered = timeEntered;
		grade = getGradeFromEmail();
	}
	  /**
    * @return
    * Grade Level 9-12
    * \n0 if an invalid email is used
    */
	public int getGradeFromEmail(){
	   if(email.charAt(email.indexOf('@')-2)!='2')
	      return 12;
	   int thisYear = Calendar.getInstance().get(Calendar.YEAR);
	   if(Calendar.getInstance().get(Calendar.MONTH)>=0 && Calendar.getInstance().get(Calendar.MONTH)<=6) thisYear--;
	   int gradYear = 0;
	   gradYear = Integer.parseInt("20" + email.charAt(email.indexOf('@')-2) + email.charAt(email.indexOf('@')-1));
	   switch (gradYear-thisYear){
	      case 1: return 12;
	      case 2: return 11;
	      case 3: return 10;
	      case 4: return 9;
	   }   
	   return 0;
	}

   public String getlName()
   {
      return lName;
   }

   public void setlName(String lName)
   {
      this.lName = lName;
   }

   public String getfName()
   {
      return fName;
   }

   public void setfName(String fName)
   {
      this.fName = fName;
   }

   public String getFullName()
   {
      return fullName;
   }

   public void setFullName(String fullName)
   {
      this.fullName = fullName;
   }

   public String getEmail()
   {
      return email;
   }

   public void setEmail(String email)
   {
      this.email = email;
   }

   public Session[] getRequests()
   {
      return requests;
   }

   public void setRequests(Session[] requests)
   {
      this.requests = requests;
   }

   public Session[] getAssignments()
   {
      return assignments;
   }

   public void setAssignments(Session[] assignments)
   {
      this.assignments = assignments;
   }



   public int getPriority()
   {
      return priority;
   }

   public void setPriority(int priority)
   {
      this.priority = priority;
   }

   public int getTimeEntered()
   {
      return timeEntered;
   }

   public void setTimeEntered(int timeEntered)
   {
      this.timeEntered = timeEntered;
   }

   public int getGrade()
   {
      return grade;
   }

   public void setGrade(int grade)
   {
      this.grade = grade;
   }
}
