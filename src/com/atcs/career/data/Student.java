//Thomas Varano
//Nov 7, 2018

package com.atcs.career.data;

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
	 */
	public int getGradeFromEmail(){
	   if(email.charAt(email.indexOf('@')-2)=='2' && email.charAt(email.indexOf('@')-1)=='2')
	      return 9;
	   else if(email.charAt(email.indexOf('@')-2)=='2' && email.charAt(email.indexOf('@')-1)=='1')
	      return 10;
	   else if(email.charAt(email.indexOf('@')-2)=='2' && email.charAt(email.indexOf('@')-1)=='0')
         return 11;      
	   return 12;
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
