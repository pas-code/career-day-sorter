//Thomas Varano
//Dec 10, 2018
package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.atcs.career.data.Event;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public abstract class MoreInfo {

	public static abstract class SideInfoPanel extends JPanel {
	   public static final int PREF_W = 400;
	   public static final int PREF_H = 600;
		private static final long serialVersionUID = 1L;
		protected Event event;
		protected InfoPanel infoPanel;

		public SideInfoPanel(InfoPanel infoPanel)
		{
		    this.infoPanel = infoPanel;
		}
		
		public void generalSetup() {
			setFocusable(true);
			this.setBackground(Color.WHITE);
		}

      protected void refreshInfoPanel()
      {
          infoPanel.refresh();
      }
		
	}

	public static class RoomPanel extends SideInfoPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// Room instance variables
		private JButton editRoomNumber;
		private JTextField roomNumber;
		private JScrollPane scrollPane;
		private Room room;
		private JList listSessions;
		DefaultListModel<String> model;
		private InfoPanel infoPanel;
		
		 public RoomPanel(Event event, Room room, InfoPanel infoPanel) 
		   {     
		       super(infoPanel);
			   generalSetup();
			      
			   this.event = event;
			   this.room = room;

			   roomNumber = new JTextField(room.getRoomNumber());
			   roomNumber.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void focusLost(FocusEvent e) {
					room.setRoomNumber(roomNumber.getText());
					
				}
				   
			   });
			      

			      
			      this.setLayout(new BorderLayout());
			      
			      JPanel center = new JPanel(new GridLayout(0,3));
			      JPanel north = new JPanel(new GridLayout(0,1));
			      this.add(north, BorderLayout.NORTH);
			      this.add(center, BorderLayout.CENTER);
			      
			      model = new DefaultListModel<String>();
					
				  listSessions = new JList<String>(model);
				  listSessions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				  JScrollPane scrollPane = new JScrollPane(listSessions);
				  populateList();
				  String title = room.getTitle();
				  setBorder(BorderFactory.createTitledBorder(null, title,
				  		TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
						new Font("Arial", Font.PLAIN, 20), Color.BLACK));
//			      String studentNames[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
//			      JList<String> listNames = new JList<String>(studentNames);
//			      listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//			      scrollPane = new JScrollPane(listNames);
			      
//			      String sessionInfo = "<html>" + room.getResidentSessions()[periodNum] + 
//			      		"  <br><center> <font size=\"7\"> "+ room.getRoomNumber() + "</font></center></html>";

			      /*
			      int residentSessionPeriod = infoPanel.getPeriod()-1;
			      JLabel sessionTitle = new JLabel(room.getResidentSessions()[residentSessionPeriod].getTitle());
			      JLabel sessionSpeaker = new JLabel(room.getResidentSessions()[residentSessionPeriod].getSpeaker());

			      center.add(sessionTitle);
			      center.add(sessionSpeaker);
			      */
			      
//			      String sessionInfo = "Title";
//			      scrollPane.setBorder(BorderFactory.createTitledBorder(null, sessionInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
			      center.add(scrollPane);
			      
			      this.add(north, BorderLayout.NORTH);
			      north.add(roomNumber);
			      
			   }
		 
		 public void populateList(){
			 
			 for (int i = 0; i < room.getResidentSessions().length; i++)
				 model.addElement(room.getResidentSessions()[i].getTitle());
	           listSessions.revalidate();
		 }

//		@Override
//		public void changePeriod(int newPeriod) {
//			// TODO Auto-generated method stub
//			
//		}  

	}

	public static class StudentPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		// Student instance variables (scrollPane already created with Room)
		//DELETE
		private JButton editStudentName, editStudentEmail;
		
		private JTextField studentfName, studentlName, studentEmail;
		
//		@Override
//		public void changePeriod(int newPeriod) {
//			
//		}
		
		public StudentPanel(Event event, Student student, InfoPanel infoPanel) 
        {     
            super(infoPanel);
			studentfName = new JTextField(student.getfName());
			studentfName.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void focusLost(FocusEvent e) {
					student.setfName(studentfName.getText());
					
				}
				
			});
			studentlName = new JTextField(student.getlName());
			studentlName.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void focusLost(FocusEvent e) {
					student.setlName(studentlName.getText());
					
				}
				
			});
			studentEmail = new JTextField(student.getEmail());
			studentEmail.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void focusLost(FocusEvent e) {
					student.setEmail(studentEmail.getText());
					
				}
				
			});
			
			
			generalSetup();
			
			setLayout(new BorderLayout());
			
			JPanel north = new JPanel(new GridLayout(2, 1));
			JPanel center = new JPanel(new BorderLayout());
			
			north.add(studentfName);
			north.add(studentlName);
			north.add(studentEmail);
			
			
			add(center, BorderLayout.CENTER);
			String studentNames[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
					"J"};
			JList<String> listNames = new JList<String>(studentNames);
			listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(listNames);

			String title = student.getAssignment(infoPanel.getPeriod()).getTitle(); // TODO what?
			setBorder(BorderFactory.createTitledBorder(null, title,
					TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
					new Font("Arial", Font.PLAIN, 20), Color.BLACK));

			center.add(scrollPane);
				
		}
	}

	public static class SessionPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		// Session instance variables
		private JButton addStudent, removeStudent;
		private JTextField speakerName, classroomNumber;
		private Event event;
		private Session session;
		private JList listStudents;
		DefaultListModel<String> model;

		public SessionPanel(Event event, Session session, InfoPanel infoPanel) 
        {     
            super(infoPanel);
            this.event = event;
			this.session = session;
			addStudent = new JButton("Add Student");
			removeStudent = new JButton("Remove Student");
			
			speakerName = new JTextField(session.getSpeaker());
			speakerName.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void focusLost(FocusEvent e) {
					session.setSpeaker(speakerName.getText());
					System.out.println("speaker name saved");
				}
				
			});
			
			classroomNumber = new JTextField("255");//session.getRoom().getRoomNumber());
			classroomNumber.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void focusLost(FocusEvent e) {
					
					session.getRoom().setRoomNumber(classroomNumber.getText());
					
					
				}
				
			});
			
		
			
			addStudent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Student s;
					JTextField last = new JTextField("Last Name"), first = new JTextField("First Name"), email = new JTextField("email@email");
					JTextField[] fields = {last, first, email};
					JOptionPane.showConfirmDialog(null, fields, "Enter Last Name, First Name, Email", JOptionPane.OK_CANCEL_OPTION);
					s = new Student(last.getText(), first.getText(), email.getText(), new ArrayList<Session>(), 9999999);
//					System.out.println(s);
					session.getStudents().get(0).add(s);
					addStudentToList(s);
					
					//add this new student to the list of students of this event LATER once we figure it out
				}
			});

			
			




			generalSetup();

			setLayout(new BorderLayout());

			JPanel north = new JPanel(new GridLayout(2, 0));
			JPanel center = new JPanel(new BorderLayout());
			JPanel south = new JPanel(new BorderLayout());

			add(center, BorderLayout.CENTER);
		
			model = new DefaultListModel<String>();
			
			listStudents = new JList<String>(model);
			listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(listStudents);
			populateList(0);
			String title = session.getTitle();
			setBorder(BorderFactory.createTitledBorder(null, title,
					TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
					new Font("Arial", Font.PLAIN, 20), Color.BLACK));

			center.add(scrollPane);

			add(north, BorderLayout.NORTH);
			north.setLayout(new GridLayout(3,0));
			north.add(speakerName);
			north.add(classroomNumber);
	

			add(south, BorderLayout.SOUTH);
			south.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
			south.setLayout(new GridLayout(5, 1));
			south.add(addStudent);
			south.add(removeStudent);
			
			removeStudent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if (listStudents.getSelectedIndex()!=-1)
					{
					   model.removeElement(listStudents.getSelectedValue());
					   listStudents.revalidate();
//						scrollPane.revalidate();
//						scrollPane.repaint();
						//standard = studentNames.toArray(new String[studentNames.size()]);
					}
				}
			});

			
		}
		
		public void populateList(int period)
		{
           for(Student s : session.getStudents().get(period))
              model.addElement(s.getFullName());
           listStudents.revalidate();
		}
		
		public void addStudentToList(Student s)
		{
		   model.addElement(s.getFullName());
		}
		

//		@Override
//		public void changePeriod(int newPeriod) {
//			// TODO Auto-generated method stub
//			
//		}
	}

	/* TODO come back to this
	public static class EventPanel extends SideInfoPanel {

		public EventPanel(InfoPanel infoPanel)
        {
            super(infoPanel);
            // TODO Auto-generated constructor stub
        }

        private static final long serialVersionUID = 1L;

//		@Override
//		public void changePeriod(int newPeriod) {
//			
//		}
	}
	*/
	
	private static void show(SideInfoPanel p) {
		JFrame f = new JFrame("test info panel");
		f.getContentPane().add(p);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		Event e = Event.testEvent();
//		MoreInfo.SessionPanel s = new MoreInfo.SessionPanel(e, e.getSessions().get(0), null);
		MoreInfo.RoomPanel s = new MoreInfo.RoomPanel(e, e.getRooms().get(0), null);
		show(s);
	}
}
	/*
	 * THIS IS WHAT EDWARD HAD... KEEPING FOR REFERENCE BUT SHOULD NOT
	 * 
	 * I REPEAT SHOULD NOT BE USED
	 *
=======
public abstract class MoreInfo
{
    public static abstract class SideInfoPanel extends JPanel
    {
        public static final int PREF_W = 400;
        public static final int PREF_H = 600;
        private static final long serialVersionUID = 1L;
        protected Event event;
        protected int periodNum;
        protected InfoPanel infoPanel;

        public SideInfoPanel(InfoPanel infoPanel)
        {
            this.infoPanel = infoPanel;
        }

        public void generalSetup()
        {
            setFocusable(true);
            this.setBackground(Color.WHITE);
        }

        protected void refreshInfoPanel()
        {
            infoPanel.refresh();
        }
    }

    public static class RoomPanel extends SideInfoPanel
    {
        
        private static final long serialVersionUID = 1L;
        // Room instance variables
        private JButton editRoomNumber;
        private JTextField roomNumber;
        private JScrollPane scrollPane;
        private Room room;

        public RoomPanel(Event event, Room room, InfoPanel infoPanel)
        {
            super(infoPanel);
            generalSetup();
            this.event = event;
            this.room = room;
            periodNum = 1;
            roomNumber = new JTextField(room.getRoomNumber());
            roomNumber.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                    
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    room.setRoomNumber(roomNumber.getText());
                    refreshInfoPanel();
                }
            });
            // editRoomNumber = new JButton("Edit Room Number");
            // editRoomNumber.addActionListener(new ActionListener(){
            // @Override
            // public void actionPerformed(ActionEvent e) {
            //
            // String roomNumber = JOptionPane.showInputDialog("Please input the
            // new room number: ");
            // room.setRoomNumber(roomNumber);
            // String sessionInfo = "<html>" +
            // event.getSessions().get(0).getTitle() + " <br><center> <font
            // size=\"7\"> "+ roomNumber + "</font></center></html>";
            // scrollPane.setBorder(BorderFactory.createTitledBorder(null,
            // sessionInfo, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new
            // Font("Arial", Font.PLAIN, 30), Color.BLACK));
            //
            // }
            // });
            this.setLayout(new BorderLayout());
            JPanel center = new JPanel(new BorderLayout());
            JPanel north = new JPanel(new GridLayout(0, 1));
            this.add(center, BorderLayout.CENTER);
            String studentNames[] =
            { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
            JList<String> listNames = new JList<String>(studentNames);
            listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane = new JScrollPane(listNames);
            // String sessionInfo = "<html>" +
            // room.getResidentSessions()[periodNum] +
            // " <br><center> <font size=\"7\"> "+ room.getRoomNumber() +
            // "</font></center></html>";
            String sessionInfo = "Title";
            scrollPane.setBorder(BorderFactory.createTitledBorder(null, sessionInfo, TitledBorder.CENTER,
                    TitledBorder.ABOVE_TOP, new Font("Arial", Font.PLAIN, 30), Color.BLACK));
            center.add(scrollPane);
            this.add(north, BorderLayout.NORTH);
            north.add(roomNumber);
        }
        // @Override
        // public void changePeriod(int newPeriod) {
        // // 
        //
        // }
    }

    public static class StudentPanel extends SideInfoPanel
    {
        private static final long serialVersionUID = 1L;
        // Student instance variables (scrollPane already created with Room)
        // DELETE
        private JButton editStudentName, editStudentEmail;
        private JTextField studentfName, studentlName, studentEmail;
        //
        // @Override
        // public void changePeriod(int newPeriod) {
        //
        // }

        public StudentPanel(Event event, Student student, InfoPanel infoPanel)
        {
            super(infoPanel);
            studentfName = new JTextField(student.getfName());
            studentfName.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                    // 
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    student.setfName(studentfName.getText());
                    refreshInfoPanel();
                }
            });
            studentlName = new JTextField(student.getlName());
            studentlName.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                    // 
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    student.setlName(studentlName.getText());
                    refreshInfoPanel();
                }
            });
            studentEmail = new JTextField(student.getEmail());
            studentEmail.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                    // 
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    student.setEmail(studentEmail.getText());
                    refreshInfoPanel();
                }
            });
            generalSetup();
            setLayout(new BorderLayout());
            JPanel north = new JPanel(new GridLayout(2, 1));
            JPanel center = new JPanel(new BorderLayout());
            north.add(studentfName);
            north.add(studentlName);
            north.add(studentEmail);
            add(center, BorderLayout.CENTER);
            String studentNames[] =
            { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
            JList<String> listNames = new JList<String>(studentNames);
            listNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(listNames);
            String title = student.getAssignment(periodNum).getTitle(); // TODO
                                                                        // what?
            setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
                    new Font("Arial", Font.PLAIN, 20), Color.BLACK));
            center.add(scrollPane);
        }
    }

    public static class SessionPanel extends SideInfoPanel
    {
        private static final long serialVersionUID = 1L;
        // Session instance variables
        private JButton editStudent, addStudent, removeStudent;
        private JTextField speakerName, classroomNumber;

        public SessionPanel(Event event, Session session, InfoPanel infoPanel)
        {
            super(infoPanel);
            addStudent = new JButton("Add Student");
            editStudent = new JButton("Edit Student");
            removeStudent = new JButton("Remove Student");
            speakerName = new JTextField(session.getSpeaker());
            speakerName.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                    // 
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    session.setSpeaker(speakerName.getText());
                    System.out.println("speaker name saved");
                    refreshInfoPanel();
                }
            });
            classroomNumber = new JTextField("255");// session.getRoom().getRoomNumber());
            classroomNumber.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent e)
                {
                    // 
                }

                @Override
                public void focusLost(FocusEvent e)
                {
                    session.getRoom().setRoomNumber(classroomNumber.getText());
                    refreshInfoPanel();
                }
            });
            addStudent.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // joption
                }
            });
            periodNum = 1;
            generalSetup();
            setLayout(new BorderLayout());
            JPanel north = new JPanel(new GridLayout(2, 0));
            JPanel center = new JPanel(new BorderLayout());
            JPanel south = new JPanel(new BorderLayout());
            add(center, BorderLayout.CENTER);
            String studentNames[] =
            { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
            JList<String> listStudents = new JList<String>(studentNames);
            listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(listStudents);
            String title = session.getTitle();
            setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
                    new Font("Arial", Font.PLAIN, 20), Color.BLACK));
            center.add(scrollPane);
            add(north, BorderLayout.NORTH);
            north.setLayout(new GridLayout(3, 0));
            north.add(speakerName);
            north.add(classroomNumber);
            editStudent.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                }
            });
            add(south, BorderLayout.SOUTH);
            south.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
            south.setLayout(new GridLayout(5, 1));
            south.add(editStudent);
            south.add(addStudent);
            south.add(removeStudent);
            removeStudent.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    listStudents.remove(listStudents.getSelectedIndex());
                }
            });
        }
        // @Override
        // public void changePeriod(int newPeriod) {
        // // 
        //
        // }
        // }

        public static class EventPanel extends SideInfoPanel
        {
            public EventPanel(InfoPanel infoPanel)
            {
                super(infoPanel);
                // 
            }

            private static final long serialVersionUID = 1L;

            // @Override
            // public void changePeriod(int newPeriod) {
            //
            // }
            // }
            private static void show(SideInfoPanel p)
            {
                JFrame f = new JFrame("test info panel");
                f.getContentPane().add(p);
                f.pack();
                f.setLocationRelativeTo(null);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }

            public static void main(String[] args)
            {
                Event e = new Event();
                MoreInfo.SessionPanel s = new MoreInfo.SessionPanel(e, e.getSessions().get(0), null);
                show(s);
            }
        }
    }
>>>>>>> origin/ui-edward
}
	*/