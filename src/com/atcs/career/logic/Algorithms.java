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
     
         if (currentStud.getGrade() >= classCutOffForGroupLevel) {
            int timeMagForPriority = currentStud.getTimeEntered();     //**Fix how we are getting value for "timeMagForPriority" so its not just a time
            currentStud.setStudentPriority(new Priority(timeMagForPriority, upperClassmanLevelMag));
         }
         else if (currentStud.getGrade() < classCutOffForGroupLevel) {
            int timeMagForPriority = currentStud.getTimeEntered();     //**
            currentStud.setStudentPriority(new Priority(timeMagForPriority, lowerClassmanLevelMag));
         }
      }
      Collections.sort(students);
   }
   
   public static void assignStudentsToSessions(ArrayList<Student> students, ArrayList<Session> sessions){
      int roundOfAssignment = 3;
      for(int j = 0; j < roundOfAssignment; j++) {
         for(int i = 0; i < students.size(); i++) {
            Student currentStud = students.get(i);
            
   //         currentStud.getAssignments().get(i) = currentStud.getRequests().get(i);
           
         }
      }   
   }
}
