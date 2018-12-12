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

import com.atcs.career.io.importexport.CSVReader;
//Jarrett Bierman
//11/18/18
//Properties
public class PropertiesPane extends JPanel
{
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 700;
   public static final int PREF_H = 300;
   public static final int BORDER_SIZE = 50;
   
   
   private JPanel gridPanel;
   private JLabel sessionLabel, studentLabel, classroomLabel, periodLabel;
   private JButton sessionButton, studentButton, classroomButton;
   private File sessionFile, studentFile, classroomFile;
   private JButton submit;
   private JTextField title;
   private final String textPrompt = "Enter Project Name Here";
   
   private JSpinner periodCount;
   
   private final String BUTTON_DEFAULT_TEXT = "Choose File";


   public PropertiesPane() 
   {     
      setFocusable(true);   
      this.setLayout(new BorderLayout());
      this.setBorder(BorderFactory.createEmptyBorder(0, BORDER_SIZE, 0, BORDER_SIZE));  
      gridPanel = new JPanel(new GridLayout(4,2));
      
      createLabels();
      createButtons();
      createFieldAndSpinner();
      addGridStuff();
      
      
      this.add(submit, BorderLayout.SOUTH);  
      this.add(title, BorderLayout.NORTH);         
      this.add(gridPanel);
   }

   
   
   public void createLabels()
   {
      sessionLabel = new JLabel("Session File (.csv): ");   
      studentLabel = new JLabel("Student File (.csv): ");    
      classroomLabel = new JLabel("Classroom File (.csv): ");    
      periodLabel = new JLabel("Number of Periods: ");
   }
   
   public void createButtons()
   {
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
      
      submit = new JButton("Submit");
      submit.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e)
         {
            try 
            {  
               if(!(title.getText().isEmpty() || periodCount.getValue() == null || title.getText().equals(textPrompt)))
               {
                  //Do all of the important info work here
                  System.out.println("Title: " + title.getText());
                  System.out.println(sessionFile.getName());
                  System.out.println(studentFile.getName());
                  System.out.println(classroomFile.getName());
                  System.out.println("Periods: " + (int)periodCount.getValue());
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
   }
   
   public void createFieldAndSpinner()
   {
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
      
      periodCount = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
      periodCount.setPreferredSize(new Dimension(30, 0));
      periodCount.setFont(new Font("Ariel", Font.PLAIN, 50));
      periodCount.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
      ((JSpinner.DefaultEditor)periodCount.getEditor()).getTextField().setEditable(false);  
      
   }
   
   public void addGridStuff()
   {
      gridPanel.add(sessionLabel);
      gridPanel.add(sessionButton);     
      gridPanel.add(studentLabel);
      gridPanel.add(studentButton);     
      gridPanel.add(classroomLabel);
      gridPanel.add(classroomButton);    
      gridPanel.add(periodLabel);
      gridPanel.add(periodCount);
   }
   
   
   
   /**
    * The Select the File Method
    * @param b
    * @return
    */
   public File selectFile(JButton b)
   {
      String location = CSVReader.getFileLocation(".csv");
      int index = 0;
      for(int i = 0; i < location.length(); i++)
      {
         if(location.charAt(i) == File.separatorChar)
            index = i;
      }        
      String name = location.substring(index + 1);
      b.setText(name);
      
      return new File(location);
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