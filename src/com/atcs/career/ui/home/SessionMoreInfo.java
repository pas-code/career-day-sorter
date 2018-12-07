package com.atcs.career.ui.home;
//Screen for when you want more information on the session
import java.awt.BorderLayout;
import java.awt.Button;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

public class SessionMoreInfo extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
   private static final long serialVersionUID = 1L;
   public static final int PREF_W = 600;
   public static final int PREF_H = 400;
   
   String sessionName = "pasta";
   int classroomNumber = 0;
   String speakerName = "Pasta man";
   private JButton editStudent, addStudent, removeStudent, editClassroom, editSpeakerName, periodOne, periodTwo, periodThree;
   //BlankButton is for formatting
   
   

   public SessionMoreInfo() 
   { 
	   periodOne = new JButton("1");
	   periodTwo = new JButton("2");
	   periodThree = new JButton("3");
	   editStudent = new JButton("Edit Student");
	   editStudent.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}		   
	   });
	   
	   addStudent = new JButton("Add Student");
	   addStudent.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}		   
	   });
	   
	   removeStudent = new JButton("Remove Student");
	   removeStudent.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}  
	   });
	   
	   editClassroom = new JButton("Edit Classroom");
	   editClassroom.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	   });
	   
	   editSpeakerName = new JButton("Edit Spreaker Name");
	   editSpeakerName.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}  
	   });
	   
	   
	   
	   
	  
	   
	  
      addKeyListener(this);
      addMouseListener(this);
      addMouseMotionListener(this);
      setFocusable(true);
      this.setBackground(Color.WHITE);
      
      
      SessionMoreInfo allContent = this;
      allContent.setLayout(new BorderLayout());
      JFrame frame = new JFrame("More Info on Session");
      JPanel west = new JPanel();
      JPanel center = new JPanel(new BorderLayout());
      JPanel south = new JPanel(new BorderLayout());
      JPanel southNorth = new JPanel();
      JPanel southSouth = new JPanel();
      
      
      allContent.add(center, BorderLayout.CENTER);
      String studentNames[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
      JList<String> listNames = new JList<String>(studentNames);
      listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      JScrollPane scrollPane = new JScrollPane(listNames);
      String sessionInfo = "<html>" + sessionName + "  <br><center> <font size=\"7\"> next line </font></center></html>";
      scrollPane.setBorder(BorderFactory.createTitledBorder(null, sessionInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
      
      center.add(scrollPane);
      
      allContent.add(west, BorderLayout.WEST);
      west.setLayout(new GridLayout(0,1));
      west.setBorder(BorderFactory.createTitledBorder(null, "Periods", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 12), Color.BLACK));
      west.add(periodOne);
      west.add(periodTwo);
      west.add(periodThree);
      
      
      allContent.add(south, BorderLayout.SOUTH);
      south.setLayout(new BorderLayout());
      south.add(southNorth, BorderLayout.NORTH);
      southNorth.setLayout(new GridLayout(0,3));
      southNorth.add(editStudent);
      southNorth.add(addStudent);
      southNorth.add(removeStudent);
      
      south.add(southSouth, BorderLayout.SOUTH);
      southSouth.setLayout(new GridLayout(0,2));
      southSouth.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
      southSouth.add(editClassroom);
      southSouth.add(editSpeakerName);
      
      
      
      
      //east.add(new Button("Remove Student"));
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(allContent);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
 
   public void update()
   {      
      
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
    * keyPressed does something when you press a key on a keyboard
    * Every key has a unique int value (and can access with e.getKeyCode()
    * you can choose which specific key with an if statement (shown below)
    */
   @Override
   public void keyPressed(KeyEvent e)
   {
      
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

   private static void createLayout() {
	 
      
      
      
      
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
      new SessionMoreInfo();
   }
}
