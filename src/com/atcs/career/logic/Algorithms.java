//Michael Ruberto
//Program Description:
//Nov 21, 2018
/*TODO
 * - Rerank contentness between rounds
 * - Implement ability to randomly assign sessions to students who didn't answer/couldn't get in a selected session
 */


package com.atcs.career.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.atcs.career.data.Priority;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public class Algorithms{
    /*Each sub ArrayList corresponds to a period
    * Students who didn't submit a request will be placed into every sub array
    * Students who couldn't get a top 5 choice placed into specific sub array for that period
    * */
   static ArrayList<ArrayList<Student>> toBeRandomlyAssigned = new ArrayList<ArrayList<Student>>();
   
   //ALGORITHM 1
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
   
   //ALGORITHM 2
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
   
   //ALGORITHM 2.5
   public static void rerankStudents() {
      
   }
   
   //ALGORITHM 3
   public static void assignStudentsToSessions(ArrayList<Student> students, ArrayList<Session> sessions){
      //Creates Array Lists for Random Assignment
      for(int i = 0; i < 3; i++) { //Change 3 later to not be a magic number
         toBeRandomlyAssigned.add(new ArrayList<Student>());
      }
      
      int numOfPeriods = 3;
      for(int j = 0; j < numOfPeriods; j++) { //For each period
         for(int i = 0; i < students.size(); i++) { //Go through every student
            Student currentStud = students.get(i); //Makes it easier to refer to current students
            assignBasedOnChoice(currentStud, sessions, j);
         }
         //ADD RERANK STUDENTS METHOD
      } 
      
      //Assign randoms
   }
   
   
   public static void assignBasedOnChoice(Student currentStud, ArrayList<Session> sessions, int period) {
      for(int k = 0; k < currentStud.getRequests().size(); k++){ //Check every request the student makes
         Session desiredSession = sessions.get(findIndexOfSession(currentStud.getRequests().get(k), sessions));
         if(desiredSession.getStudents().get(period).size() < desiredSession.getRoom().getMaxCapacity() &&
           !currentStud.getAssignments().contains(desiredSession)){
            desiredSession.getStudents().get(period).add(currentStud);
            currentStud.getAssignments().set(period - 1, desiredSession);
            changeStudentContentness(currentStud); //Deals with contentness
            return;
         }
      }
      
      //They couldn't get in any session they chose this period
      currentStud.getAssignments().set(period - 1, null); //PLACEHOLDER LINE UNTIL RANDOM STUFF WORKS
      toBeRandomlyAssigned.get(period - 1).add(currentStud);
      
      
      changeStudentContentness(currentStud); //Deals with contentness
   }
   
   public static void changeStudentContentness(Student currentStud){
      int selectionsAlreadyMade = currentStud.getAssignments().size();
      
      double numerator = 0;
      double denominator = 0;
      for(int i = 0; i < selectionsAlreadyMade; i++) {
         int choiceIndex = currentStud.getRequests().indexOf(currentStud.getAssignments().get(i));
         if(choiceIndex != -1)
            numerator += currentStud.getRequests().size() - choiceIndex;
         denominator += currentStud.getRequests().size() - i;
      }
      
      currentStud.getStudentPriority().setContentness(numerator/denominator);
   }
   
   public static int findIndexOfSession(Session requestedSession, ArrayList<Session> sessions){
      for(int i = 0; i < sessions.size(); i++){
         if (sessions.get(i).getSpeaker().equals(requestedSession.getSpeaker()));
            return i;
      }
      return -1;     
   }
}