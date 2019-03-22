//Michael Ruberto, Joshua Kent, Bennett Bierman
//Program Description:
//Nov 21, 2018

package com.atcs.career.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.atcs.career.data.Event;
import com.atcs.career.data.Priority;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.program.MainClass;

public class Algorithms{
    /*Each sub ArrayList corresponds to a period
    * Students who didn't submit a request will be placed into every sub array
    * Students who couldn't get a top 5 choice placed into specific sub array for that period
    * */
   static ArrayList<ArrayList<Student>> toBeRandomlyAssigned = new ArrayList<ArrayList<Student>>();
   
   public static void sort(Event e) {
   	// check if we need more information
   	// request, room. session, master
   	// 1, 2, 3, 4
   	String thingsMissing = "";
   	if (e.getStudents().isEmpty())
   		thingsMissing += "student request data.\n";
   	if (e.getRooms().isEmpty())
   		thingsMissing += "room data.\n";
   	if (e.getSessions().isEmpty())
   		thingsMissing += "session data.\n";
   	if (e.getMasterStudents().isEmpty())
   		thingsMissing  += "master student array\n";
   		
   	if (!thingsMissing.equals("")) {
   		JOptionPane.showMessageDialog(null, "You are missing important data:\n" + thingsMissing
   				 + "Please input this information and try again.", MainClass.APP_NAME + ": Cannot Sort Students",
   				 JOptionPane.WARNING_MESSAGE, null);
   		e.setSorted(false);
   		return;
   	}
   	if (e.isSorted()) {
   		clearAssignments(e.getStudents(), e.getRooms(), e.getSessions(), e.getNumberOfPeriods());
   		e.setSorted(false);
   	}
   	myBigFatGreekWethod(e.getStudents(), e.getMasterStudents(), e.getRooms(), e.getSessions());
   	e.setSorted(true);
   }
   
   //BIG METHOD THAT DOES EVERYTHING
   private static void myBigFatGreekWethod(ArrayList<Student> students, ArrayList<Student> master, ArrayList<Room> rooms, ArrayList<Session> sessions){
      System.out.println("Method 1 Starting");
      assignRoomsToSessions(students, rooms, sessions);
      System.out.println("Method 1 Done");
      
      System.out.println("Method 2 Starting");
      rankStudents(students, master);
      System.out.println("Method 2 Done");
      
      System.out.println("Method 3 Starting");
      assignStudentsToSessions(students, sessions);
      System.out.println("Method 3 Done");
      
      System.out.println("Accuracy:");
      System.out.println(getSortingAccuracyAverage(students));
   }
   
   private static double getSortingAccuracyAverage(ArrayList<Student> students){   //tells you how good the sorting was based on final contentness
      double totalCont = 0;
      for(int i = 0; i < students.size(); i++){
         System.out.println("Contentness: " + students.get(i).getStudentPriority().getContentness()/50);
         totalCont += ((students.get(i).getStudentPriority().getContentness())/50); //50 is the contentness weight we gave
      }
      return totalCont/students.size();
   }
   
   
   //ALGORITHM 1
   private static void assignRoomsToSessions(ArrayList<Student> students, ArrayList<Room> rooms, ArrayList<Session> sessions){
      
      Collections.sort(rooms);
      
      HashMap<String, Session> sessionHash = new HashMap<String, Session>();
    
      for(int i=0; i<sessions.size(); i++){
         if(sessions.get(i).getSpeaker().charAt(0) == '"') //TEMP FIX FIX IT INFO
            sessionHash.put(sessions.get(i).getSpeaker().substring(1), sessions.get(i));
         else //^^^
            sessionHash.put(sessions.get(i).getSpeaker(), sessions.get(i));
      }
      
//      for(int i=0; i< sessionHash.size(); i++){      //for testing
//         System.out.println("testing: "+i);
//         System.out.println(sessionHash.get(sessions.get(i).getSpeaker()).getSpeaker());
//      }
        
      for(Student stud: students){
         ArrayList<Session> requests = stud.getRequests();
//         System.out.println(stud.toString());
         int requestsSize = requests.size();
         for(int i = 0; i < requestsSize; i++) {
            sessionHash.get(requests.get(i).getSpeaker()).addPopularity(requestsSize-i);   //come back to fix "5-i" if needed
         }
//         System.out.println("Next Student \n");
      }
      
      sessions.clear();
      sessions.addAll(sessionHash.values());
      Collections.sort(sessions);
      
      for(int i = sessions.size() - 1; i >= 0; i--){
         if(rooms.size() > i) { //COME BACK WITH ERROR MANAGER STUFF
            sessions.get(i).setRoom(rooms.get((rooms.size()-1) - ((sessions.size()-1) - i)));
            rooms.get((rooms.size()-1) - ((sessions.size()-1) - i)).setResidentSessions(new Session[] {sessions.get(i)});
         }
      }
      
      //FOR TESTING
//      for(int i = 0; i < rooms.size(); i++){
//         System.out.println(rooms.get(i).toString());
//         System.out.println(sessions.get(i).getSpeaker());
//      }
          
   }
   
   //ALGORITHM 2
   private static void rankStudents(ArrayList<Student> students, ArrayList<Student> master){
    //Creates Array Lists for Random Assignment
      for(int i = 0; i < 3; i++) { //Change 3 later to not be a magic number
         toBeRandomlyAssigned.add(new ArrayList<Student>());
      }
      for(int i = 0; i < master.size(); i++) {
         if(!students.contains(master.get(i))) {
            for(int j = 0; j < toBeRandomlyAssigned.size(); j++) {
               toBeRandomlyAssigned.get(j).add(master.get(i));
            }
         }
      }
      
      for(int i = 0; i < students.size(); i++) {
         Student currentStud = students.get(i);
         int yearEntered = (currentStud.getTimeEntered()/1000) - Event.startYear;
         int dayEntered = ((yearEntered * 365) + (currentStud.getTimeEntered()%1000)) - Event.startDay;
         if (currentStud.getGrade() >= Priority.classCutOff) {
            currentStud.setStudentPriority(new Priority(dayEntered, Priority.upperClassMagnitudeValue));
         }
         else if (currentStud.getGrade() < Priority.classCutOff) {
            currentStud.setStudentPriority(new Priority(dayEntered, Priority.lowerClassMagnitudeValue));
         }
      }
      Collections.sort(students);
      
      //FOR TESTING
//      for(int i = 0; i < students.size(); i++){
//         System.out.println(students.get(i));
//      }
      
   }
   
   //ALGORITHM 3
   private static void assignStudentsToSessions(ArrayList<Student> students, ArrayList<Session> sessions){
      int numOfPeriods = 3;
      for(int j = 0; j < numOfPeriods; j++) { //For each period
         for(int i = 0; i < students.size(); i++) { //Go through every student
            Student currentStud = students.get(i); //Makes it easier to refer to current students
            assignBasedOnChoice(currentStud, sessions, j);
         }
         Collections.sort(students);  //reranks students
      } 
      
      
      assignRandomsAtEnd(sessions);
      
      //COMMENT BELOW HERE TO STOP BACKFILL
//      while(!allSessionAreFilledToMin(sessions)){ //hmmmmmm
//         int period = getLeastPopulatedSessionIndex(sessions, 3); //CHANGE
//         Session minSession = getLeastPopulatedSessionPerPeriod(sessions, period);
//         
//         boolean successfullyChangedSomeone = false;
//         for(int i = students.size() - 1; i >= 0; i--){
//            if(students.get(i).getPeriodOfLeastDesired() == getLeastPopulatedSessionIndex(sessions, 3) && students.get(i).isSwitchable()){
//               Session oldSession = students.get(i).getRequests().remove(period); //Take Away Their Old Session
//               sessions.get(sessions.indexOf(oldSession)).getStudents().get(period).remove(students.get(i)); //Take them out of their old session
//               minSession.getStudents().get(period).add(students.get(i)); //Add to new session
//               students.get(i).getAssignments().set(period, minSession); //Tell them they're in the new session (CHANGED from request to assignments)
//               students.get(i).setSwitchable(false);
//               successfullyChangedSomeone = true;
//            }
//            break;
//         }
//         
//         if(!successfullyChangedSomeone){
//            for(int i = students.size() - 1; i >= 0; i--){
//               if(students.get(i).isSwitchable()){
//                  Session oldSession = students.get(i).getRequests().remove(period); //Take Away Their Old Session
//                  if(sessions.indexOf(oldSession) != -1) {
//                     sessions.get(sessions.indexOf(oldSession)).getStudents().get(period).remove(students.get(i)); //Take them out
//                     minSession.getStudents().get(period).add(students.get(i)); //Add to new session
//                     students.get(i).getAssignments().set(period, minSession); //Tell them they're in the new session (CHANGED from request to assignments)
//                     students.get(i).setSwitchable(false);
//                  }
//               }
//            }
//         }
//      }
//      
//      for(int i = 0; i < students.size(); i++){
//         if(!students.get(i).isSwitchable() && students.get(i).getRequests().size() > 0){
//            changeStudentContentness(students.get(i));
//         }
//      }
      //COMMENT ABOVE HERE TO STOP BACKFILL
   }
      
   private static void assignBasedOnChoice(Student currentStud, ArrayList<Session> sessions, int period) {
      System.out.println(currentStud);
      
      for(int k = 0; k < currentStud.getRequests().size(); k++){ //Check every request the student makes
         System.out.println("REQUEST: " + currentStud.getRequests().get(k));
         int sessionIndex = findIndexOfSession(currentStud.getRequests().get(k), sessions);
         
         Session desiredSession = sessions.get(0);
         if(sessionIndex >= 0) desiredSession = sessions.get(sessionIndex);
         
         System.out.println("FOUND SESSION: " + desiredSession);
         if(desiredSession.getStudents().get(period).size() < desiredSession.getRoom().getMaxCapacity() &&
           !currentStud.getAssignments().contains(desiredSession)){
            System.out.println("SUCCESS!");
            desiredSession.getStudents().get(period).add(currentStud);
            currentStud.getAssignments().add(period, desiredSession); //Changed from set --> add //took out period - 1
            changeStudentContentness(currentStud); //Deals with contentness
            return;
         }
      }
      System.out.println("No, Next!");
      
      
      
      //They couldn't get in any session they chose this period
      
      currentStud.getAssignments().add(period, new Session()); //Changed from set --> add //took out period - 1
      toBeRandomlyAssigned.get(period).add(currentStud); //took out period - 1
      
      
      changeStudentContentness(currentStud); //Deals with contentness
   }
   
   private static boolean allSessionAreFilledToMin(ArrayList<Session> sessions){ 
      int minCapacity = 10;
      for(int i = 0; i < sessions.size(); i++) {
         for(int j=0; j < sessions.get(i).getStudents().size(); j++){
            if(sessions.get(i).getStudents().get(j).size() < minCapacity) //changed second i to j
               return false;
         }        
      }
      return true;
   }
   
   
   private static void assignRandomsAtEnd(ArrayList<Session> sessions){
      for(int i = 0; i < toBeRandomlyAssigned.size(); i++) {   //toBeRandomlyAssigned.size() is representing the amount of periods
         for(int j = 0; j < toBeRandomlyAssigned.get(i).size(); j++){
            Session sessionToAssign = getLeastPopulatedSessionPerPeriod(sessions, i);
            Student stud = toBeRandomlyAssigned.get(i).remove(j);
            System.out.println("assigning "+stud.getAssignments());
            sessionToAssign.getStudents().get(i).add(stud);
            if (stud.getAssignments().size() <= i)
            	stud.getAssignments().add(sessionToAssign);
            else
            	stud.getAssignments().set(i, sessionToAssign);
         }
      }
   }
   
   private static Session getLeastPopulatedSessionPerPeriod(ArrayList<Session> sessions, int period) {
      Session min = sessions.get(0);
      for(int i = 0; i < sessions.size(); i++){
         if(sessions.get(i).getStudents().get(period).size() < min.getStudents().get(period).size()){
            min = sessions.get(i);
         }
      }
      return min;
   }
   
   private static int getLeastPopulatedSessionIndex(ArrayList<Session> sessions, int numberOfPeriods){
      ArrayList<Session> leastPopulatedSessions = new ArrayList<Session>();
      for(int i = 0; i < numberOfPeriods; i++){
         leastPopulatedSessions.add(getLeastPopulatedSessionPerPeriod(sessions, i));  
      }
      
      Session min = leastPopulatedSessions.get(0);
      for(int i = 0; i < leastPopulatedSessions.size(); i++){
         if(leastPopulatedSessions.get(i).getStudents().get(i).size() < min.getStudents().get(leastPopulatedSessions.indexOf(min)).size()){
            min = leastPopulatedSessions.get(i);
         }
      }
      return leastPopulatedSessions.indexOf(min);
   }
   
   private static void changeStudentContentness(Student currentStud){
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
   
   private static int findIndexOfSession(Session requestedSession, ArrayList<Session> sessions){
      for(int i = 0; i < sessions.size(); i++){
         if (sessions.get(i).getSpeaker().equals(requestedSession.getSpeaker()))
            return i;
      }
      return -1;     
   }
   
   
   private static void clearAssignments(ArrayList<Student> studentRequestList, ArrayList<Room> rooms, ArrayList<Session> sessions, int numPeriods) {
   	for (Student s : studentRequestList)
   		s.setAssignments(new ArrayList<Session>());
   	for (Room r : rooms) 
   		r.setResidentSessions(new Session[0]);
   	for (Session s : sessions) {
   		s.setStudents(new ArrayList<ArrayList<Student>>());
   		s.setRoom(null);
   		for (int i = 0; i < numPeriods; i++) 
   			s.getStudents().add(new ArrayList<Student>());
   	}
   	toBeRandomlyAssigned = new ArrayList<ArrayList<Student>>();
   	
   }
}