package com.atcs.career.ui.home;

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

import com.atcs.career.data.Event;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public class MoreInfoGeneral extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 400;
   public static final int PREF_H = 600;
   
   private Event event;
   private Student student;
   private Room room;
   private Session session;
   
   //Room instance variables
   private JButton editRoomNumber;
   private JScrollPane scrollPane;
   private String roomNumber;
   
   //Session instance variables
   private JButton editStudent, addStudent, removeStudent, editClassroom, editSpeakerName, periodOne, periodTwo, periodThree;
   
   //Student instance variables (scrollPane already created with Room)
   private JButton editStudentName, editStudentEmail;

   public MoreInfoGeneral(Event event, Session session) 
   {     
      addKeyListener(this);
      addMouseListener(this);
      addMouseMotionListener(this);
      setFocusable(true);
      this.setBackground(Color.WHITE);
      
   } 
   
   public MoreInfoGeneral(Event event, Student student) 
   {     
	   addKeyListener(this);
	      addMouseListener(this);
	      addMouseMotionListener(this);
	      setFocusable(true);
	      this.setBackground(Color.WHITE);
	      
	     
	      
	      editStudentName = new JButton("Edit Name");
	      editStudentName.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}   	  
	      });
	      editStudentEmail = new JButton("Edit Email");
	      editStudentEmail.addActionListener(new ActionListener(){

	  		@Override
	  		public void actionPerformed(ActionEvent e) {
	  			// TODO Auto-generated method stub
	  			
	  		}   	  
	        });
	      
	      
//	      createLayout();
	      MoreInfoGeneral allContent = this;
	      allContent.setLayout(new BorderLayout());
	      JFrame frame = new JFrame("More Info on Student");
	      JPanel center = new JPanel(new BorderLayout());
	      JPanel south = new JPanel(new GridLayout(0,1));
	      
	      
	      allContent.add(center, BorderLayout.CENTER);
	      String studentNames[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };//student.getassignments
	      JList<String> listNames = new JList<String>(studentNames);
	      listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      scrollPane = new JScrollPane(listNames);
	      String sessionInfo = "<html>" + student.getfName()+ " " + student.getlName() + "  <br><center> <font size=\"7\"> next line </font></center></html>";
	      scrollPane.setBorder(BorderFactory.createTitledBorder(null, sessionInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
	      center.add(scrollPane);
	      
	      allContent.add(south, BorderLayout.SOUTH);
	      south.setPreferredSize(new Dimension(PREF_W, 100));
	      south.add(editStudentName);
	      south.add(editStudentEmail);
	      
	      
	 	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.getContentPane().add(allContent);
	      frame.pack();
	      frame.setLocationRelativeTo(null);
	      frame.setVisible(true);
	      
	      
	      
      
   } 
   
   public MoreInfoGeneral(Event event, Room room) 
   {     
	   addKeyListener(this);
	   addMouseListener(this);
	   addMouseMotionListener(this);
	   setFocusable(true);
	   this.setBackground(Color.WHITE);
	      
	   this.event = event;
	     
	   roomNumber = session.getRoom().getRoomNumber();
	    
	      
	      editRoomNumber = new JButton("Edit Room Number");
	      editRoomNumber.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String roomNumber = JOptionPane.showInputDialog("Please input the new room number: ");
				session.getRoom().setRoomNumber(roomNumber);
				roomNumber = session.getRoom().getRoomNumber();
				String sessionInfo = "<html>" + event.getSessions().get(0).getTitle() + "  <br><center> <font size=\"7\"> "+ roomNumber + "</font></center></html>";
				scrollPane.setBorder(BorderFactory.createTitledBorder(null, sessionInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
				
			}    	  
	      });

	      
	      
//	      createLayout();
	      MoreInfoGeneral allContent = this;
	      allContent.setLayout(new BorderLayout());
	      JFrame frame = new JFrame("More Info on Classroom");
	      JPanel center = new JPanel(new BorderLayout());
	      JPanel south = new JPanel(new GridLayout(0,1));
	      
	      
	      allContent.add(center, BorderLayout.CENTER);
	      String studentSessions[]= new String[student.getAssignments().size()];
	      for (int i = 0; i < student.getAssignments().size(); i++)
	      {
	    	  studentSessions[i] = student.getAssignments().get(i);
	      }
	      String studentSessions[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	      JList<String> listNames = new JList<String>(studentNames);
	      listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      scrollPane = new JScrollPane(listNames);
	      String sessionInfo = "<html>" + sessionName + "  <br><center> <font size=\"7\"> "+ classroomNumber + "</font></center></html>";
	      scrollPane.setBorder(BorderFactory.createTitledBorder(null, sessionInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
	      center.add(scrollPane);
	      
	      allContent.add(south, BorderLayout.SOUTH);
	      south.setPreferredSize(new Dimension(PREF_W, 100));
	      south.add(editRoomNumber);
	      
	      
	      
	 	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.getContentPane().add(allContent);
	      frame.pack();
	      frame.setLocationRelativeTo(null);
	      frame.setVisible(true);
	      
	   } 
   
  
   
   
   
   /**
    * keyPressed does something when you press a key on a keyboard
    * Every key has a unique int value (and can access with e.getKeyCode()
    * you can choose which specific key with an if statement (shown below)
    */
   @Override
   public void keyPressed(KeyEvent e)
   {
      int key = e.getKeyCode();
      if(key == KeyEvent.VK_E)
         System.out.println("I am pressing the 'E' key");
      
   }
   
   /**
    * keyReleased is the same as keyPressed, but it does something when you release a key
    */
   @Override
   public void keyReleased(KeyEvent e)
   {
     
         
   }
   @Override
   public void keyTyped(KeyEvent e){}
   /**
    * mousePressed does a think when you press your mouse down
    * you can also access the location of the mouse through the methods e.getX() and e.getY()
    */
   @Override
   public void mousePressed(MouseEvent e)
   {
     
   }
   
   @Override
   public void mouseReleased(MouseEvent e)
   {
   }
   
   public void mouseClicked(MouseEvent e){}
   
   @Override
   public void mouseEntered(MouseEvent e)
   {
   }
   
   @Override
   public void mouseExited(MouseEvent e)
   {
   }
   
   @Override
   public void mouseDragged(MouseEvent e)
   {
     
   }
   @Override
   public void mouseMoved(MouseEvent e)
   {
   }
   
   /**
    * The createAndShowGUI class puts your JPanel into a JFrame and creates the GUI interface.
    * This pretty much sets up your screen so you can see what you have coded.
    */
   private static void createAndShowGUI() {
//      MoreInfoGeneral gamePanel = new MoreInfoGeneral();
//      JFrame frame = new JFrame("Default JPanel");
//      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      frame.getContentPane().add(gamePanel);
//      frame.pack();
//      frame.setLocationRelativeTo(null);
//      frame.setVisible(true);
	   new MoreInfoGeneral()
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

