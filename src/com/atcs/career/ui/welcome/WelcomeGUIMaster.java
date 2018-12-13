package com.atcs.career.ui.welcome;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
//Jarrett Bierman
//9/4/18
//Default JPanel Class (Copy and Paste)
public class WelcomeGUIMaster extends JPanel
{
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 600;
   public static final int PREF_H = 400;
   private WelcomeScreen welcomeScreen;
   private PropertiesPane propertiesPane;
   private JFrame frame;

   public WelcomeGUIMaster() 
   {  
      super(new CardLayout());            
      welcomeScreen = new WelcomeScreen("A Thing");
      this.add(welcomeScreen);
   }

   private void constructFrame() {
      frame = new JFrame("Welcome!");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(this);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   /**
    * This overrides the JPanel's getPreferredSize() method
    * It tells the JPanel to be a certain width and height
    */
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }

   /**
    * The main method runs your entire program
    * It has the method createAndShowGUI() and runs it.
    * This makes your whole program work.
    */
   public static void main(String[] args) 
   {
      new Thread(new Runnable() {
         public void run() {
            new WelcomeGUIMaster().constructFrame();
         }
      }).start();
   }



}
