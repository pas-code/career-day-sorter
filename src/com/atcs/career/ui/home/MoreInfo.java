//Thomas Varano
//Dec 10, 2018
package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import com.atcs.career.data.Event;
import com.atcs.career.data.GuiListable;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;

public abstract class MoreInfo {

	public static SideInfoPanel getInfoPanel(GuiListable g, CareerDayGUI master) {
		if (g instanceof Student) return new StudentPanel((Student) g, master);
		if (g instanceof Room) return new RoomPanel((Room) g, master);
		if (g instanceof Session) return new SessionPanel((Session) g, master);
		return null;
	}
	
	static ArrayList<String> getNames(ArrayList<Student> students) {
		ArrayList<String> ret = new ArrayList<String>();
		for (Student s : students)
			ret.add(s.getFullName());
		return ret;
	}

	public static abstract class SideInfoPanel extends JPanel {
		public static final int PREF_W = 230;
		public static final int PREF_H = 600;
		private static final long serialVersionUID = 1L;
		private CareerDayGUI master;
		// protected Event event;
		// protected InfoPanel infoPanel;

		public SideInfoPanel(CareerDayGUI master) {
			this.master = master;
			generalSetup();
		}

		public void generalSetup() {
			setFocusable(true);
			this.setBackground(Color.WHITE);
		}
		
		protected byte getPeriod() {
			return 0;
//			return master.getSelectedPeriod();
		}
		
		protected CareerDayGUI getMaster() {
			return master;
		}
		
		protected abstract void refresh();
		
		public void setPeriod(byte period) {
			refresh();
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(PREF_W, PREF_H);
		}

	}

	/*
	 * TODO be able to move students directly from this panel. have a popup idc
	 * when selecting a student, have a button like "move student" or "remove student" and then do those popups
	 * make sure to log all changes just in case they want to undo
	 * 	make it a human-readable list 
	 */
	public static class RoomPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		// Room instance variables
		private JList<Student> studentList;
		private JLabel sessionName;
		private Room room;

		public RoomPanel(Room room, CareerDayGUI master) {
			super(master);
			this.room = room;

			initializeUI();
			refresh();
		}
		
		private void initializeUI() {
			JTextField roomNumberField = new JTextField(room.getRoomNumber());
			roomNumberField.setBorder(BorderFactory.createTitledBorder("Room Name"));
			
			roomNumberField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					room.setRoomNumber(roomNumberField.getText());
				}
			});
			
			JTextField roomCapacityField = new JTextField(room.getMaxCapacity() + "");
			roomCapacityField.setBorder(BorderFactory.createTitledBorder("Room Capacity"));
			
			sessionName = new JLabel();
			sessionName.setBorder(BorderFactory.createTitledBorder("Resident Session"));
			
			roomCapacityField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						room.setMaxCapacity(Integer.parseInt(roomCapacityField.getText()));
					} catch (NumberFormatException er) {
						roomCapacityField.setText(room.getMaxCapacity() + "");
					}
				}
			});

			this.setLayout(new BorderLayout());

			JPanel center = new JPanel(new GridLayout(0, 1));
			JPanel north = new JPanel(new GridLayout(0, 1));

			this.add(center, BorderLayout.CENTER);

			studentList =  new JList<Student>();
			studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(studentList);
			scrollPane.setBorder(BorderFactory.createTitledBorder("Resident Students"));

			center.add(scrollPane);
			north.add(roomNumberField);
			north.add(roomCapacityField);
			north.add(sessionName);
			

			this.add(north, BorderLayout.NORTH);
		}

		protected void refresh() {
			System.out.println("refresh");
			sessionName.setText(room.getResidentSessions() == null || room.getResidentSessions().length == 0 ? 
					"No Resident Session" : room.getResidentSessions()[getPeriod()].getIdentifier());
			studentList.setListData(room.getResidentSessions() == null || room.getResidentSessions().length == 0 ?
					new Student[0] : 
					
					//get the current session
					room.getResidentSessions()[getPeriod()]
							// get that session's students for this period
							.getStudents().get(getPeriod())
							// turn to standard array
									.toArray(new Student[room.getResidentSessions()[getPeriod()].getStudents()
													.get(getPeriod()).size()]));
			
		}
		
	}

	/* TODO
	 * add resident sessions to use and add / remove
	 */
	
	public static class StudentPanel extends SideInfoPanel {
		private Student student;
		private JList<String> memberSessions;
		public StudentPanel(Student s, CareerDayGUI master) {
			super(master);
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			this.student = s;
			initializeUI();
		}
		
		private void initializeUI() {
			JPanel north = new JPanel(new GridLayout(0, 1));
			north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
			north.add(createInfoField("First Name", student.getfName(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					student.setfName(((JTextComponent) e.getSource()).getText());
					north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
				}
			}));
			
			
			north.add(createInfoField("Last Name", student.getlName(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					student.setlName(((JTextComponent) e.getSource()).getText());
					north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
				}
			}));
			
			north.add(createInfoField("Grade", student.getGrade(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
					 student.setGrade(Integer.parseInt(((JTextComponent) e.getSource()).getText()));
					} catch (NumberFormatException er) {
						((JTextComponent) e.getSource()).setText(student.getGrade() + "");
					}
				}
			}));
			north.add(createInfoField("Email", student.getEmail(), new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					student.setEmail(((JTextComponent) e.getSource()).getText());
				}
			}));
			add(north, BorderLayout.NORTH);
			
			JPanel south = new JPanel(new GridLayout(0, 1));
			south.setBorder(BorderFactory.createTitledBorder("Assigned Sessions"));
//			JButton add
			JButton add = new JButton("Add Session");
			JButton remove = new JButton("Remove");
			south.add(add);
			south.add(remove);
			remove.setEnabled(false);
			
			memberSessions = new JList<String>();
			memberSessions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			memberSessions.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {

				}
			});
//			student.
			add(memberSessions);
			add(south, BorderLayout.SOUTH);
		}
		
		private JPanel createInfoField(String title, Object data, ActionListener action) {
			JPanel panel = new JPanel(new BorderLayout());
			panel.add(new JLabel(title + ": "), BorderLayout.WEST);
			JTextField editField = new JTextField(data.toString());
			editField.addActionListener(action);
			panel.add(editField, BorderLayout.CENTER);
			return panel;
		}
		
		
		protected void refresh() {
			System.out.println("refresh");
		}
	}
	
	
	public static class StudentPanel1 extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		// Student instance variables (scrollPane already created with Room)
		// DELETE
		private JButton editStudentName, editStudentEmail;

		private JTextField studentfName, studentlName, studentEmail;

		public StudentPanel1(Student student, CareerDayGUI master) {
			super(master);
			studentfName = new JTextField(student.getfName());
			studentfName.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {

				}

				@Override
				public void focusLost(FocusEvent e) {
					student.setfName(studentfName.getText());
				}

			});
			studentlName = new JTextField(student.getlName());
			studentlName.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {

				}

				@Override
				public void focusLost(FocusEvent e) {
					student.setlName(studentlName.getText());

				}

			});
			studentEmail = new JTextField(student.getEmail());
			studentEmail.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					student.setEmail(studentEmail.getText());

				}

			});

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

			setBorder(BorderFactory.createTitledBorder(null, "TITLE",
					TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
					new Font("Arial", Font.PLAIN, 20), Color.BLACK));

			center.add(scrollPane);

		}
		
		protected void refresh() {
			System.out.println("REFresh");
		}
	}

	public static class SessionPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		// Session instance variables
		private JButton editStudent, addStudent, removeStudent;
		private JTextField speakerName, classroomNumber;
		private Session session;
		private JList listStudents;
		DefaultListModel<String> model;

		public SessionPanel(Session session, CareerDayGUI master) {
			super(master);
			this.session = session;
			addStudent = new JButton("Add Student");
			editStudent = new JButton("Edit Student");
			removeStudent = new JButton("Remove Student");

			speakerName = new JTextField(session.getSpeaker());
			speakerName.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {

				}

				@Override
				public void focusLost(FocusEvent e) {
					session.setSpeaker(speakerName.getText());
					System.out.println("speaker name saved");
				}

			});

			classroomNumber = new JTextField("255");// session.getRoom().getRoomNumber());
			classroomNumber.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {

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
					JTextField last = new JTextField("Last Name"),
							first = new JTextField("First Name"),
							email = new JTextField("email@email");
					JTextField[] fields = {last, first, email};
					JOptionPane.showConfirmDialog(null, fields,
							"Enter Last Name, First Name, Email",
							JOptionPane.OK_CANCEL_OPTION);
					s = new Student(last.getText(), first.getText(), email.getText(),
							new ArrayList<Session>(), 9999999);
					// System.out.println(s);
					session.getStudents().get(0).add(s);
					addStudentToList(s);

					// add this new student to the list of students of this event
					// LATER once we figure it out
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
			north.setLayout(new GridLayout(3, 0));
			north.add(speakerName);
			north.add(classroomNumber);

			editStudent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});

			add(south, BorderLayout.SOUTH);
			south.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
			south.setLayout(new GridLayout(5, 1));
			south.add(editStudent);
			south.add(addStudent);
			south.add(removeStudent);

			removeStudent.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					System.out.println("test");
					if (listStudents.getSelectedIndex() != -1) {
						// System.out.println(studentNames);
						// studentNames.remove(listStudents.getSelectedIndex());
						// System.out.println(studentNames);
						scrollPane.revalidate();
						scrollPane.repaint();
						// standard = studentNames.toArray(new
						// String[studentNames.size()]);
					}
				}
			});

		}

		public void populateList(int period) {
			// model = new DefaultListModel<String>();
			for (Student s : session.getStudents().get(period))
				model.addElement(s.getFullName());
			listStudents.revalidate();
		}

		public void addStudentToList(Student s) {
			model.addElement(s.getFullName());
		}

		protected void refresh() {
			System.out.println("refresh");
		}
	}

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
		
		
//		MoreInfo.SessionPanel s = new MoreInfo.SessionPanel(e,
//				e.getSessions().get(0), null);

//		SideInfoPanel s = MoreInfo.getInfoPanel(e.getRooms().get(0), null);
		SideInfoPanel s = MoreInfo.getInfoPanel(e.getStudents().get(0), null);
//		SideInfoPanel s = MoreInfo.getInfoPanel(e.getSessions().get(0), null);
		 show(s);
	}
}