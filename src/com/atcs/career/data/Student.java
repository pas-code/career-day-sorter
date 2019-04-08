//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Student implements Comparable<Student>, Serializable, GuiListable {

	private static final long serialVersionUID = 2820547053629559120L;
	public static int MAX_REQUESTS = 5;
	private String lName, fName, email;
	private ArrayList<Session> requests;
	private Session[] assignments;
	private int grade, timeEntered;
	private Priority priority;
	private boolean isSwitchable;
	private boolean submitted;

	/**
	 * 
	 * @param lName
	 * @param fName
	 * @param email
	 * @param requests
	 * @param timeEntered
	 * @param submitted
	 */
	public Student(String lName, String fName, String email,
			ArrayList<Session> requests, int timeEntered, boolean submitted, int numPeriods) {
		this.lName = lName;
		this.fName = fName;
		this.email = email;
		this.requests = requests;
		grade = getGradeFromEmail();
		this.timeEntered = timeEntered;
		createAndSetStudentPriority();
		this.assignments = new Session[numPeriods];
		isSwitchable = true;
		this.submitted = submitted;
	}

	/**
	 * Student Constructor used for ArrayList of Students who submitted
	 * 
	 * @param lName
	 * @param fName
	 * @param email
	 * @param requests
	 * @param timeEntered
	 */
	public Student(String lName, String fName, String email, 
			ArrayList<Session> requests, int timeEntered, int numPeriods) {
		this(lName, fName, email, requests, timeEntered, true, numPeriods);
	}

	/**
	 * Student Constructor used for master ArrayList of Students
	 * 
	 * @param lName
	 * @param fName
	 * @param email
	 */
	public Student(String lName, String fName, String email, int numPeriods) {
		this(lName, fName, email, new ArrayList<Session>(), 0, false, numPeriods);
	}

	public boolean equals(Student s){
   return email.equals(s.getEmail());
}
	/**
	 * Returns 0 if an invalid email is used
	 * 
	 * @return Grade Level 9-12
	 */

	public int getGradeFromEmail() {
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		if (Calendar.getInstance().get(Calendar.MONTH) >= 0
				&& Calendar.getInstance().get(Calendar.MONTH) <= 6)
			thisYear--;
		int gradYear = 0;
		for (int c = 0; c < email.substring(0, email.indexOf('@'))
				.length(); c++) {
			if (email.toCharArray()[c] >= '0' && email.toCharArray()[c] <= '9') {
				if (email.substring(c + 1, c + 2).equals("@")) return 12;
				gradYear = Integer.parseInt("20" + email.substring(c, c + 2));
				break;
			}
		}
		if ((gradYear + "").length() < 4) return 12;

		switch (gradYear - thisYear) {
			case 1 :
				return 12;
			case 2 :
				return 11;
			case 3 :
				return 10;
			case 4 :
				return 9;
		}
		return 0;
	}

	public int getPeriodOfLeastDesired() {
		int leastDesiredPeriodIndex = 0;
		for (int i = 1; i < assignments.length; i++) {
			if (requests.indexOf(assignments[i]) == -1) return i;
			if (requests.indexOf(assignments[i]) > requests
					.indexOf(assignments[leastDesiredPeriodIndex]))
				leastDesiredPeriodIndex = i;
		}
		return leastDesiredPeriodIndex;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getFullName() {
		return fName + " " + lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<Session> getRequests() {
		return requests;
	}

	public void setRequests(ArrayList<Session> requests) {
		this.requests = requests;
	}

	public Session[] getAssignments() {
		return assignments;
	}

	public void setAssignments(Session[] assignments) {
		this.assignments = assignments;
	}

	public int getTimeEntered() {
		return timeEntered;
	}

	public void setTimeEntered(int timeEntered) {
		this.timeEntered = timeEntered;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Priority getStudentPriority() {
		return priority;
	}
	
	public void createAndSetStudentPriority() {
		if (getTimeEntered() != 0) {
			int yearEntered = (getTimeEntered()/1000) - Event.startYear;
			int dayEntered = ((yearEntered * 365) + (getTimeEntered()%1000)) - Event.startDay;
			if (getGrade() >= Priority.classCutOff) 
	         setStudentPriority(new Priority(dayEntered, Priority.upperClassMagnitudeValue));
	      
	      else if (getGrade() < Priority.classCutOff) 
	         setStudentPriority(new Priority(dayEntered, Priority.lowerClassMagnitudeValue));
		} else {
			setStudentPriority(new Priority(0,
					getGrade() >= Priority.classCutOff
							? Priority.upperClassMagnitudeValue
							: Priority.lowerClassMagnitudeValue));
		}
		
	}

	public void setStudentPriority(Priority newPriority) {
		this.priority = newPriority;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Student))
			return false;
		Student otherStudent = (Student) obj;
//		return this.getFullName().equals(otherStudent.getFullName())
//				&& this.getGrade() == otherStudent.getGrade();
		return this.getEmail().equals(otherStudent.getEmail());
	}

	@Override
	public String toString() {
		return getFullName();
		/* return "Student: [" + fName + " " + lName + "] [email: " + email
				+ "] [grade: " + grade + "] [timeEntered: " + timeEntered
				+ "] [priority: " + priority + "]";
				*/
	}

	@Override
	public int compareTo(Student o) {
		return (int) (this.getStudentPriority().getPriority()
				- o.getStudentPriority().getPriority());
	}

	public int compareToEmail(Student o) {
		return getEmail().compareTo(o.getEmail());
	}
	
	@Override
	public String getTitle() {
		return fName + " " + lName;
	}

	@Override
	public String getInfo(int i) {
		switch (i) {
			case 0 :
				return getGrade() + "";
			case 1 :
				return "";
			case 2 :
				return getEmail();
			default :
				return lName;
		}

	}

	public Session getAssignment(int period) {
		if (period < assignments.length)
			return assignments[period];
		else
			return new Session();

	}

	@Override
	public String getType() {
		return "Student";
	}

	public boolean isSwitchable() {
		return isSwitchable;
	}

	public void setSwitchable(boolean isSwitchable) {
		this.isSwitchable = isSwitchable;
	}

	@Override
	public String getIdentifier() {
		return getFullName();
	}
	
	public boolean assignmentsContain(Session sess) {
		for (Session s : assignments)
			if (s != null && s.equals(sess))
				return true;
		return false;
	}
	
	public int getLowestAvailableAssignmentIndex() {
		for (int i = 0; i < assignments.length; i++)
			if (assignments[i] == null)
				return i;
		
		return -1;
	}

}
