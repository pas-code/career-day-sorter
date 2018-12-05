//Michael Ruberto
//Program Description:
//Nov 21, 2018

package com.atcs.career.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.atcs.career.data.Priority;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public class Algorithms{

   
   public static void assignRoomsToSessions(ArrayList<Student> students, ArrayList<Room> rooms, ArrayList<Session> sessions){
      
      Collections.sort(rooms);
      
      HashMap<String, Session> sessionHash = new HashMap<String, Session>();
      for(Session s: sessions){
         sessionHash.put(s.getSpeaker(), s);
      }
 
      for(Student stud: students){
         ArrayList<Session> requests = stud.getRequests();
         int requestsSize = requests.size();
         for(int i = 0; i < requestsSize; i++) {
            sessionHash.get(requests.get(i)).addPopularity(requestsSize-i);   //come back to fix "5-i" if needed
         }
      }
      
      sessions = (ArrayList<Session>) sessionHash.values();
      Collections.sort(sessions);
      
      for(int i = 0; i < sessions.size(); i++){
         if(rooms.size() < i) { //COME BACK WITH ERROR MANAGER STUFF
            sessions.get(i).setRoom(rooms.get(i));
         }
      }
   }
   
   public static void rankStudents(ArrayList<Student> students, int classCutOffForGroupLevel, int upperClassmanLevelMag, int lowerClassmanLevelMag){
      for(int i = 0; i < students.size(); i++) {
         Student currentStud = students.get(i);
         int timeMagForPriority = currentStud.getTimeEntered();     //**Fix how we are getting value for "timeMagForPriority" so its not just a time
         if (currentStud.getGrade() >= classCutOffForGroupLevel) {
            currentStud.setStudentPriority(new Priority(timeMagForPriority, upperClassmanLevelMag)); //Dont set priority w/ constructor
         }
         else if (currentStud.getGrade() < classCutOffForGroupLevel) {
            currentStud.setStudentPriority(new Priority(timeMagForPriority, lowerClassmanLevelMag)); //Same
         }
      }
      Collections.sort(students);
   }
   
   public static void assignStudentsToSessions(ArrayList<Student> students, ArrayList<Session> sessions){
      int numOfPeriods = 3;
      for(int j = 0; j < numOfPeriods; j++) { //For each period
         for(int i = 0; i < students.size(); i++) { //Go through every student
            Student currentStud = students.get(i); //Makes it easier to refer to current students
            assignBasedOnChoice(currentStud, sessions, j);
         }
      } 
      
      //Assign randoms
   }
   
   
   public static void assignBasedOnChoice(Student currentStud, ArrayList<Session> sessions, int period) {
      for(int k = 0; k < currentStud.getRequests().size(); k++){
         Session desiredSession = sessions.get(findIndexOfSession(currentStud.getRequests().get(k), sessions));
         if(desiredSession.getStudents().get(period).size() < desiredSession.getRoom().getMaxCapacity()){
            desiredSession.getStudents().get(period).add(currentStud);
            currentStud.getAssignments().set(period - 1, desiredSession);
            currentStud.getRequests().remove(k); //Remove the session so it doesnt try and double-add them
            return;
         }
      }
      currentStud.getAssignments().set(period - 1, null); //PLACEHOLDER LINE UNTIL RANDOM STUFF WORKS
      //Couldn't assign this student into a session of their choice. Use ArrayList of ArrayList to hold these students till end
   }
   
   public static int findIndexOfSession(Session requestedSession, ArrayList<Session> sessions){
      for(int i = 0; i < sessions.size(); i++){
         if (sessions.get(i).getSpeaker().equals(requestedSession.getSpeaker()));
            return i;
      }
      return -1;     
   }
}