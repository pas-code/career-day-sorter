//Information Team
//Nov 7, 2018

package com.atcs.career.data;

import java.io.Serializable;

public class Room implements Comparable<Room>, Serializable, GuiListable {
	
	private static final long serialVersionUID = -406223752974907136L;
	private String roomNumber;
	private int maxCapacity;
	private Session[] residentSessions;
	private byte TYPE_NUM  = 2;

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
   
   public String toString(){
      return "Room: " + "[Room Number: " + roomNumber +"] [Capacity: " + maxCapacity +"]" ; 
   }

@Override
public String getTitle()
{
    return roomNumber+"";
}

@Override
public String getInfo(int i)
{
    switch(i)
    {
    case 0: return maxCapacity+"";
    default: return roomNumber+"";
    }
}

@Override
public String getType()
{
    return "Room";
}

@Override
public byte getTypeNum()
{
    return TYPE_NUM;
}

@Override
public String getIdentifier() {
	return roomNumber;
}
}
