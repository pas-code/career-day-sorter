//Information Team
//Nov 7, 2018

package com.atcs.career.data;

public class Room implements Comparable<Room>{
	private int maxCapacity;
	private String roomNumber;
	private Session[] residentSessions;
	

   public Room(String roomNumber, int maxCapacity){
      super();
      this.roomNumber = roomNumber;
      this.maxCapacity = maxCapacity;
   }
	
   @Override
   public int compareTo(Room o){
      return this.maxCapacity - o.getMaxCapacity();
   }

   public Session[] getResidentSessions(){
      return residentSessions;
   }

   public void setResidentSessions(Session[] residentSessions){
      this.residentSessions = residentSessions;
   }

   public String getRoomNumber(){
      return roomNumber;
   }
   
   public void setRoomNumber(String roomNumber){
	   this.roomNumber = roomNumber;
   }

   public int getMaxCapacity(){
      return maxCapacity;
   }
}
