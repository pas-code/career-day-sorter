//Thomas Varano
//Mar 7, 2018

package com.atcs.career.ui;

import java.awt.Color;

/**
 * Has all the functionality of the {@linkplain Color} class but is mutable, 
 * so values can be changed and updated as they are changed. This is especially useful for updating GUIs, because references do 
 * not change when the value is changed, so new colors do not need to be set (foreground, background, etc.) 
 * @author Thomas Varano
 */
public class MutableColor extends java.awt.Color implements java.io.Serializable {
   private static final long serialVersionUID = 8016610396226834080L;
   private int argb;
   
   public MutableColor() {
      this(Color.WHITE);
   }
   
   public MutableColor(int r, int g, int b, int a) {
      super(r, g, b, a);
      argb = super.getRGB();
   }
   
   public MutableColor(int r, int g, int b) {
      this(r, g, b, 255);
   }
   
   public MutableColor(Color c) {
      this(c.getRGB());
   }
   
   public MutableColor(int value) {
      super(value);
      argb = value;
   }
   
   @Override
   public int getRGB() {
      return argb;
   }
   
   public void setValue(Color c) {
      setValue(c.getRGB());
   }
   
   public void setValue(int r, int g, int b) {
      setValue(r, g, b, getAlpha());
   }
   
   public void setValue(int r, int g, int b, int a) {
      argb = ((a & 0xFF) << 24) |
            ((r & 0xFF) << 16) |
            ((g & 0xFF) << 8)  |
            ((b & 0xFF) << 0);
   }
   public void setValue(int argb) {
      this.argb = argb;
   }
   
   public void setAlpha(int a) {
      setValue(getRed(), getGreen(), getBlue(), a);
   }
   
   public void setRed(int r) {
      setValue(r, getGreen(), getBlue(), getAlpha());
   }
   
   public void setBlue(int b) {
      setValue(getRed(), getGreen(), b, getAlpha());
   }
   
   public void setGreen(int g) {
      setValue(getRed(), g, getBlue(), getAlpha());
   }
}
