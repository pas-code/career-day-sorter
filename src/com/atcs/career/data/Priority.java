//Joshua Kent
//Program description:
//Nov 20, 2018

package com.atcs.career.data;

import java.io.Serializable;

public class Priority implements Serializable
{
   
	private static final long serialVersionUID = 8183973489015932252L;
	/*Priority is made up of three factors:
    * -Time
    * -Group
    * -Contentness
    * 
    * The "Magnitude" value represents the actual value (What time, what group, etc.)
    * The "Weight" value is user inputted and controls how much the algorithm factors in that value
    * Magnitude and Weight are used to compute the overall Value for each factor, which in turn calculates their priority
    */
   public static double upperClassMagnitudeValue = 2;
   public static double lowerClassMagnitudeValue = 1;
   
   public static double contentnessWeightValue = 50;
   public static double timeWeightValue = 2;
   public static double groupWeightValue = 100;
   
   public static int classCutOff = 11;
   
   private double contentnessMagnitude, timeMagnitude, groupMagnitude;
   
   
   public Priority(double tMag, double gMag)
   {
      contentnessMagnitude = 0;
      this.timeMagnitude = tMag;
      this.groupMagnitude = gMag;
   }
   
   public double getContentness(){
      return contentnessMagnitude*contentnessWeightValue;
   }
   
   public void setContentness(double cMag){
      this.contentnessMagnitude = cMag;
   }
   
   public double getTime(){
      return timeMagnitude*timeWeightValue;
   }
   
   public double getGroup(){
      return groupMagnitude*groupWeightValue;
   }
   
   public double getPriority(){
      return getGroup() - getContentness() - getTime();
   }

   public static void setContentnessWeight(double cWeight){
      Priority.contentnessWeightValue = cWeight;
   }

   public static void setTimeWeight(double tWeight){
      Priority.timeWeightValue = tWeight;
   }

   public static void setGroupWeight(double gWeight){
      Priority.groupWeightValue = gWeight;
   }

   
}
