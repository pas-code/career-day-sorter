//Thomas Varano
//Nov 7, 2018

package com.atcs.career.data;

public class Room implements Comparable<Room>{
	private int roomNumber, maxCapacity;
	private Session[] residentSessions;
	

   public Room(int roomNumber, int maxCapacity){
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

   public int getRoomNumber(){
      return roomNumber;
   }

   public int getMaxCapacity(){
      return maxCapacity;
   }
}
