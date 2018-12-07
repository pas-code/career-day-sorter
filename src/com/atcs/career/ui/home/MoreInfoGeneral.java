//GUI Team

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

public class MoreInfoGeneral extends JPanel 
{
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 400;
   public static final int PREF_H = 600;
   
   private Event event;
   private Student student;
   private Room room;
   private Session session;
   
   private int periodNum;
   
   //Room instance variables
   private JButton editRoomNumber;
   private JScrollPane scrollPane;
   private String roomNumber;
   
   //Session instance variables
   private JButton editStudent, addStudent, removeStudent, editClassroom, editSpeakerName;
   
   //Student instance variables (scrollPane already created with Room)
   private JButton editStudentName, editStudentEmail;
   
   public void setup(){
	      setFocusable(true);
	      this.setBackground(Color.WHITE);
   }
   

   public MoreInfoGeneral(Event event, Session session) 
   {     
	   
	   editStudent = new JButton("Edit Student");
	   editStudent.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}		   
	   });
	   
	   addStudent = new JButton("Add Student");
	   addStudent.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// joption
			
		}		   
	   });
	   
	   removeStudent = new JButton("Remove Student");
	   removeStudent.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			//list.removeSelected
			
		}  
	   });
	   
	   // DELETE THIS
	   editClassroom = new JButton("Edit Classroom");
	   editClassroom.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}
	   });
	   
	   // DELETE THIS
	   editSpeakerName = new JButton("Edit Spreaker Name");
	   editSpeakerName.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}  
	   });
	   
	   periodNum = 1;	  

      setup();
      
      
      MoreInfoGeneral allContent = this;
      allContent.setLayout(new BorderLayout());
      JFrame frame = new JFrame("More Info on Session");
      
      JPanel north = new JPanel(new GridLayout(2, 0));
      JPanel center = new JPanel(new BorderLayout());
      JPanel south = new JPanel(new BorderLayout());
      
      
      allContent.add(center, BorderLayout.CENTER);
      String studentNames[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
      JList<String> listNames = new JList<String>(studentNames);
      listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane scrollPane = new JScrollPane(listNames);
      String title = session.getTitle();
      allContent.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 20), Color.BLACK));
      
      center.add(scrollPane);
      
      allContent.add(north, BorderLayout.NORTH);
      
      
      allContent.add(south, BorderLayout.SOUTH);
      south.setBorder(BorderFactory.createEmptyBorder(0,25,0,25));
      south.setLayout(new GridLayout(5,1));
      south.add(editStudent);
      south.add(addStudent);
      south.add(removeStudent);
      
      south.add(editClassroom);
      south.add(editSpeakerName);
      
      
      
      
      //east.add(new Button("Remove Student"));
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(allContent);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   } 
   
   private static class RoomEditPane extends JPanel {
	   
   }
   
   public MoreInfoGeneral(Event event, Student student) 
   {     
	   setup();
	      this.student = student;
	      this.event = event;
	     
	      
	      editStudentName = new JButton("Edit Name");
	      editStudentName.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String newName = JOptionPane.showInputDialog("Enter the new name of the Student(first name last name): ");
				int spacePlace = newName.indexOf(" ");
				student.setfName(newName.substring(0, spacePlace));
				student.setlName(newName.substring(spacePlace+1, newName.length()));
				String studentInfo = "<html>" + student.getfName()+ " " + student.getlName() + "  <br><center> <font size=\"7\"> next line </font></center></html>";
				scrollPane.setBorder(BorderFactory.createTitledBorder(null, studentInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
				
			}   	  
	      });
	      editStudentEmail = new JButton("Edit Email");
	      editStudentEmail.addActionListener(new ActionListener(){

	  		@Override
	  		public void actionPerformed(ActionEvent e) {
	  			String newEmail = JOptionPane.showInputDialog("Enter the new email of the Student: ");
	  			student.setEmail(newEmail);
	  		}   	  
	        });
	      
	      
	      MoreInfoGeneral allContent = this;
	      allContent.setLayout(new BorderLayout());
	      JFrame frame = new JFrame("More Info on Student");
	      JPanel center = new JPanel(new BorderLayout());
	      JPanel south = new JPanel(new GridLayout(2,1));
	      
	      
	      
	      allContent.add(center, BorderLayout.CENTER);
	      String studentSessions[]= new String[student.getAssignments().size()];
	      for (int i = 0; i < student.getAssignments().size(); i++)
	      {
	    	  studentSessions[i] = student.getAssignments().get(i).getTitle();
	      }

	      String studentInfo = "<html>" + student.getfName()+ " " + student.getlName() + "  <br><center> <font size=\"7\"> next line </font></center></html>";
	      scrollPane.setBorder(BorderFactory.createTitledBorder(null, studentInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
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
	   setup();
	      
	   this.event = event;
	   this.room = room;
	     
	   roomNumber = room.getRoomNumber();
	   periodNum = 1;
	   
	   
	      
	      editRoomNumber = new JButton("Edit Room Number");
	      editRoomNumber.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String roomNumber = JOptionPane.showInputDialog("Please input the new room number: ");
				session.getRoom().setRoomNumber(roomNumber);
				roomNumber = room.getRoomNumber();
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
	      
	      
	      String studentNames[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	      JList<String> listNames = new JList<String>(studentNames);
	      listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	      scrollPane = new JScrollPane(listNames);
	      String sessionInfo = "<html>" + room.getResidentSessions()[periodNum] + "  <br><center> <font size=\"7\"> "+ room.getRoomNumber() + "</font></center></html>";
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
   
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }
   
   public static void main(String[] args) {
	   Event e = new Event();
	   new MoreInfoGeneral(e, e.getSessions().get(0));
   }
}

