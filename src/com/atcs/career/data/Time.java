package com.atcs.career.data;

import java.io.Serializable;
import java.time.LocalTime;

//Thomas Varano
//Aug 31, 2017

/**
 * A simple time class, holding a few integers. Runs in 24 hour time.
 * @author Thomas Varano
 * 
 */
public class Time implements Comparable<Time>, Serializable
{
   private static final long serialVersionUID = -4296172955026537771L;
   public static final Time MIDNIGHT = new Time(0,0), 
         NOON = new Time(12,0), NO_TIME = new Time(-1,-1), BEFORE_MIDNIGHT = MIDNIGHT.minus(1); 
   public static final int HOUR_IN_DAY = 24, MIN_IN_DAY = 1440, SEC_IN_DAY = (MIN_IN_DAY*60), MIN_IN_HOUR = 60;
   private int hour24, minute;
   private boolean am;
   
   /**
    * Creates the Time with a 24 hour setting.
    * 
    * @param hour24 
    *    a military time hour to construct 
    * @param minute
    *    the minute time that the object will hold
    */
   public Time(int hour24, int minute) {
      hour24 += (minute / MIN_IN_HOUR);
      hour24 %= HOUR_IN_DAY;
      minute %= MIN_IN_HOUR;
      this.setHour24(hour24); this.setMinute(minute);
      am = hour24 < 12;
   }
   
   public Time(LocalTime lt) {
      setHour24(lt.getHour()); setMinute(lt.getMinute());
      am = hour24 < 12;
   }
   
   public static Time now() {
   		return new Time(LocalTime.now());
   }
   
   /**
    * Creates the Time with a 12 hour setting. The hours are converted based to a 24 hour scale.
    * 
    * @param hour12 
    *    an hour on a 12 hour scale
    * @param minute 
    *    the minute time that the object will hold
    * @param am
    *    tells if the time is in morning or night
    */
   public Time (int hour12, int minute, boolean am) {
      setHour24((afterNoon(hour12)) ? hour12 + HOUR_IN_DAY / 2: hour12);
      this.am = am; setMinute(minute);
   }
   
   public Time (int totalMins) {
      this(totalMins / MIN_IN_HOUR, totalMins % MIN_IN_HOUR);
   }
   
   public Time() {
      this(-1,-1);
   }
   
   /**
    * calculates the duration of time from start to end.
    * 
    * @param startTime
    * @param endTime
    * @return the difference of the two times in a time object
    */
   public static Time calculateDuration(Time startTime, Time endTime) {
      return startTime.getTimeUntil(endTime);
   }
   
   public Time getTimeUntil(Time t) {
      if (t.compareTo(this) > 0)
         return t.minus(this);
      int minsToNextDay = new Time(24, 00).minus(this).getTotalMins();
      int midnightToTime = t.getTotalMins();
      return new Time(minsToNextDay + midnightToTime);
   }
   
   public String toString() {
      String amString = (am) ? "AM" : "PM";
      return timeString() + " " + amString;
   }

   public static Time fromString(String s) {
      try {
         return new Time(Integer.parseInt(s.substring(0, s.indexOf(','))),
               Integer.parseInt(s.substring(s.indexOf(',') + 1)));
      } catch (Exception e) {
         return null;
      }
   }

   public String durationString() {
      if (hour24 > 0)
         return getHour24() + " hr, " + getMinute() + " min";
      return getMinute() + " min";
   }
   
   public String timeString() {
      String hourString = (afterNoon(hour24)) ? (hour24 - 12)+"" : hour24 + "";
      String minuteString = (minute < 10) ? "0"+minute : minute+"";
      return hourString + ":" + minuteString;
   }
   public boolean equals(Time otherTime) {
      return (getTotalMins() == otherTime.getTotalMins());
   }
   /**
    * subtracts the specified time from the implicit time. Assumes the implicit parameter is the later time. So, 
    * if the given time is later, it will be subtracted as if it was from the day before. 
    * 
    * @param t the time to subtract
    * @return
    *    the difference between the times.
    */
   public Time minus(Time t)  {
      return minus(t.getTotalMins());
   }
   
   public Time minus(int min) {
      int minDif = getTotalMins() - min;
      if (minDif < 0)
         minDif += (MIN_IN_DAY);
      return new Time(minDif);
   }
   
   public static boolean checkMorning(int totalMins) {
      return (totalMins > MIN_IN_DAY /2);
   }
   
   private static boolean afterNoon(int hour) {
      return hour > 12;
   }
   
   /**
    * adds the given minutes to the time the method is being performed on. 
    * 
    * @param min amount of minutes to add
    * @return the time plus amount of minutes
    */
   public Time plus(int min) {
      return new Time((min + getTotalMins()) % MIN_IN_DAY);
   }
   
   public Time plus(Time t) {
      return plus(t.getTotalMins());
   }
   
   public int getHour12() {
      return (afterNoon(hour24)) ? hour24-12 : hour24;
   }
   public int getTotalMins() {
      return (hour24 * MIN_IN_HOUR + minute);
   }
   public int getHour24() {
      return hour24;
   }
   public void setHour24(int hour24) {
      this.hour24 = hour24;
      this.am = (hour24 < 12);
   }
   public void setHour12(int hour12, boolean am) {
      if (am)
         this.setHour24(hour12);
      else 
         this.setHour24(hour12+12);
   }
   
   public int getMinute() {
      return minute;
   }
   public void setMinute(int minute) {
      this.minute = minute;
   }
   public boolean isAM() {
      return am;
   }

   @Override
   public int compareTo(Time o) {
      return getTotalMins() - o.getTotalMins();
   }
}
