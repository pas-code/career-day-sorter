package com.atcs.career.ui.welcome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
//Jarrett Bierman
//9/4/18
//Default JPanel Class (Copy and Paste)
public class WelcomeGUIMaster extends JFrame
{
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 400;
   public static final int PREF_H = 600;

   public WelcomeGUIMaster() 
   {  
      createAndShowGUI();
   }

   private void createAndShowGUI() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.getContentPane().add(new WelcomeScreen("E"));
      this.pack();
      this.setLocationRelativeTo(null);
      this.setVisible(true);
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
      new WelcomeGUIMaster();
   }



}
