//Joshua Kent
//Program description:
//Nov 20, 2018

package com.atcs.career.data;

public class Priority
{
   public final static double CWEIGHT = 1;
   public final static double TWEIGHT = 1;
   public final static double GWEIGHT = 1;
   private double cMag, tMag, gMag;
   private double cWeight, tWeight, gWeight;
   
   public Priority(double tMag, double gMag, double cWeight, double tWeight, double gWeight)
   {
      cMag = 0;
      this.tMag = tMag;
      this.gMag = gMag;
      this.cWeight = cWeight;
      this.tWeight = tWeight;
      this.gWeight = gWeight;
   }
   
   public double getContentness(){
      return cMag*cWeight;
   }
   
   public double getTime(){
      return tMag*tWeight;
   }
   
   public double getGroup(){
      return gMag*gWeight;
   }
   
   public double getPriority(){
      return getContentness() + getTime() + getGroup();
   }

   
}
