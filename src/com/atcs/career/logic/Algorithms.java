//Michael Ruberto
//Program Description:
//Nov 21, 2018

package com.atcs.career.logic;

import java.util.ArrayList;
import java.util.Collections;

import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public class Algorithms{

   
   public void assignRoomsToSessions(ArrayList<Student> students, ArrayList<Room> rooms, ArrayList<Session> sessions){
      
      Collections.sort(rooms);
      
      for(Student s: students){
         ArrayList<Session> requests = s.getRequests();
         for(int i = 0; i < requests.size(); i++) {
            requests.get(i);
         }
      }
      
      Collections.sort(sessions);
      for(int i = 0; i < sessions.size(); i++){
         if(rooms.size() < i) { //COME BACK WITH ERROR MANAGER STUFF
            sessions.get(i).setRoom(rooms.get(i));
         }
      }
   }
   
   
   public static void rankStudents(){
      
   }
   
   
   
   public static void assignStudentsToSessions(){
      
   }
}
