package com.atcs.career.ui.welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
//Jarrett Bierman
//11/18/18
//Properties
public class PropertiesPane extends JPanel
{
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 700;
   public static final int PREF_H = 300;
   private JPanel gridPanel;
   private JLabel sessionLabel, studentLabel, classroomLabel;
   private JButton sessionButton, studentButton, classroomButton;
   private File sessionFile, studentFile, classroomFile;
   private JButton submit;
   private JTextField title;
   
   private JSpinner periodCount2;
   
   private final String BUTTON_DEFAULT_TEXT = "Choose File";


   public PropertiesPane() 
   {     
      setFocusable(true);   
      
      final String textPrompt = "Enter Project Name Here";

      
      this.setLayout(new BorderLayout());
      this.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
      
      gridPanel = new JPanel(new GridLayout(4,2));
      gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      
      sessionLabel = new JLabel("Session File (.csv): ");
      studentLabel = new JLabel("Student File (.csv): ");
      classroomLabel = new JLabel("Classroom File (.csv): ");
      
      sessionButton = new JButton(BUTTON_DEFAULT_TEXT);
      sessionButton.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            sessionFile = selectFile(sessionButton);
         }
      });
      
      
      studentButton = new JButton(BUTTON_DEFAULT_TEXT);
      studentButton.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e)
         {
            studentFile = selectFile(studentButton);
         }
      });
      
      
      classroomButton = new JButton(BUTTON_DEFAULT_TEXT);
      classroomButton.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e)
         {
            classroomFile = selectFile(classroomButton);
         }
      });
   
      gridPanel.add(sessionLabel);
      gridPanel.add(sessionButton);
      
      gridPanel.add(studentLabel);
      gridPanel.add(studentButton);
      
      gridPanel.add(classroomLabel);
      gridPanel.add(classroomButton);
      
      gridPanel.add(new JLabel("Number of Periods: "));
      periodCount2 = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
      periodCount2.setPreferredSize(new Dimension(30, 0));
      periodCount2.setFont(new Font("Ariel", Font.PLAIN, 50));
      periodCount2.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
      ((JSpinner.DefaultEditor)periodCount2.getEditor()).getTextField().setEditable(false);
      
      
      gridPanel.add(periodCount2);
      
      
      
      
      submit = new JButton("Submit");
      submit.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            try 
            {  
               if(!(title.getText().isEmpty() || periodCount2.getValue() == null || title.getText().equals(textPrompt)))
               {
                  //Do all of the important info work here
                  System.out.println("Title: " + title.getText());
                  System.out.println(sessionFile.getName());
                  System.out.println(studentFile.getName());
                  System.out.println(classroomFile.getName());
                  System.out.println("Periods: " + (int)periodCount2.getValue());
               }
               else
               {
                  JOptionPane.showMessageDialog(null, "You did not select all of the needed files. Please select all options.");                 
               }
            } 
            catch(NullPointerException f) 
            {
               JOptionPane.showMessageDialog(null, "You did not select all of the needed files. Please select all options.");
            }

         }
      });
      this.add(submit, BorderLayout.SOUTH);  
      
      title = new JTextField(textPrompt);
      title.setForeground(Color.GRAY);
      title.setFont(new Font("Ariel", Font.PLAIN, 40));
      title.setHorizontalAlignment(SwingConstants.CENTER);
      title.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e)
         {
            if (title.getText().equals(textPrompt)) {
               title.setText("");
               title.setForeground(Color.BLACK);
            }
         }
         @Override
         public void focusLost(FocusEvent e)
         {
            if (title.getText().equals("")) {
               title.setText(textPrompt);
               title.setForeground(Color.GRAY);
            }
         }
         
      });
      this.add(title, BorderLayout.NORTH);
      
      
      this.add(gridPanel);
    
   }
   
   private File selectFile(JButton b)
   {
      
      //THIS IS THE TEMP VERSION TOM MADE A BETTER VERSION THAT ILL IMPLEMENT LATER
      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv files", "csv");
      chooser.setFileFilter(filter);
      int returnVal = chooser.showOpenDialog(this);
      if(returnVal == JFileChooser.APPROVE_OPTION)
      {
         b.setText(chooser.getSelectedFile().getName());
         return chooser.getSelectedFile();
      }
      return null;
      
   }
   

   /**
    * The paintComponent is a method that is inherited from the JPanel class.
    * Writing code in here overrides the method
    * This is where you draw shapes, pictures, and objects
    */
   @Override
   public void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));     
   }

   
   /**
    * The createAndShowGUI class puts your JPanel into a JFrame and creates the GUI interface.
    * This pretty much sets up your screen so you can see what you have coded.
    */
   private static void createAndShowGUI() {
      PropertiesPane gamePanel = new PropertiesPane();
      JFrame frame = new JFrame("Selection Panel");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(gamePanel);
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
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGUI();
         }
      });
   }
}