//Thomas Varano
//Dec 10, 2018
package com.atcs.career.ui.home.info;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import com.atcs.career.data.GuiListable;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.program.CareerDay;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.resources.FontManager;
import com.atcs.career.ui.home.CareerDayGUI;
import com.atcs.career.ui.home.Searchable;

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
		protected CareerDayGUI master;
		protected static final BasicLogger log = BasicLogger.getLogger(SideInfoPanel.class.getSimpleName());

		public SideInfoPanel(CareerDayGUI master) {
			this.master = master;
			generalSetup();
		}

		public void generalSetup() {
			setFocusable(true);
			setFont(FontManager.finalFont(18));
			this.setBackground(Color.WHITE);
		}
		
		protected byte getPeriod() {
			return master.getCurrentPeriod();
		}
		
		protected CareerDayGUI getMaster() {
			return master;
		}
		
		public abstract void refresh();
		
		public void setPeriod(byte period) {
			refresh();
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(PREF_W, PREF_H);
		}

		protected byte amtPeriods() {
			return master.getEvent().getNumberOfPeriods();
		}
		
	}

	/*
	 * TODO be able to move students directly from this panel. have a popup idc
	 * when selecting a student, have a button like "move student" or "remove student" and then do those popups 
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
				@Override
				public void actionPerformed(ActionEvent e) {
					room.setRoomNumber(roomNumberField.getText());
				}
			});
			
			JTextField roomCapacityField = new JTextField(room.getMaxCapacity() + "");
			roomCapacityField.setBorder(BorderFactory.createTitledBorder("Room Capacity"));
			
			sessionName = new JLabel();
			sessionName.setBorder(BorderFactory.createTitledBorder("Resident Session"));
			
			roomCapacityField.addActionListener(new ActionListener() {
				@Override
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

		
		@Override
		public void refresh() {
			log.info("refresh");
			sessionName.setText(room.getResidentSession() == null
					? 
					"No Resident Session" :
						// only one resident session per room, so no need for an array.
								room.getResidentSession().getIdentifier());
			studentList.setListData(room.getResidentSession() == null ?
					new Student[0] : 
					
					//get the current session
					room.getResidentSession()
							// get that session's students for this period
							.getStudents().get(getPeriod())
							// turn to standard array
									.toArray(new Student[room.getResidentSession().getStudents()
													.get(getPeriod()).size()]));		
		}
	}
	
	public static class StudentPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		private Student student;
		private JList<Session> memberSessions;
		private JPanel requestPanel;
		public StudentPanel(Student s, CareerDayGUI master) {
			super(master);
			log.setFilter(Level.FINEST);
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			this.student = s;
			initializeUI();
		}
		
		private void initializeUI() {
			JPanel north = new JPanel(new GridLayout(0, 1));
			north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
			north.add(createInfoField("First Name", student.getfName(), new EditingAction() {
				@Override
				public void edit(AWTEvent e) {
					student.setfName(((JTextComponent) e.getSource()).getText());
					north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
				}
			}));
			
			
			north.add(createInfoField("Last Name", student.getlName(), new EditingAction() {
				@Override
				public void edit(AWTEvent e) {
					student.setlName(((JTextComponent) e.getSource()).getText());
					north.setBorder(BorderFactory.createTitledBorder(student.getFullName() + " Student Info"));
				}
			}));
			
			north.add(createInfoField("Grade", student.getGrade(), new EditingAction() {
				@Override
				public void edit(AWTEvent e) {
					try {
					 student.setGrade(Integer.parseInt(((JTextComponent) e.getSource()).getText()));
					} catch (NumberFormatException er) {
						((JTextComponent) e.getSource()).setText(student.getGrade() + "");
					}
				}
			}));
			north.add(createInfoField("Email", student.getEmail(), new EditingAction() {
				@Override
				public void edit(AWTEvent e) {
					student.setEmail(((JTextComponent) e.getSource()).getText());
				}
			}));
			add(north, BorderLayout.NORTH);
						
			requestPanel = new JPanel(new GridLayout(0, 1));
			requestPanel.setBorder(BorderFactory.createTitledBorder("Requests"));
			for (int i = 0; i < student.getRequests().size(); i++)
				requestPanel.add(createRequestSlot(i + 1, student.getRequests().get(i)));
			if (student.getRequests().size() < Student.MAX_REQUESTS) {
				JButton addRequest = new JButton("Add Request");
				addRequest.addActionListener(e -> {
					addRequest(addRequest);
				});
				requestPanel.add(addRequest);	
			}
			add(requestPanel);
			
			
			JPanel south = new JPanel(new GridLayout(0, 1));
			south.setBorder(BorderFactory.createTitledBorder("Assigned Sessions"));
			
			memberSessions = new JList<Session>();
			memberSessions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			refresh();
			JButton addAssignment = new JButton("Add Session");
			JButton removeAssignment = new JButton("Remove");
			addAssignment.setEnabled(periodsEmpty().length > 0);
			removeAssignment.setEnabled(false);
			memberSessions.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					removeAssignment.setEnabled(true);
				}
			});
			addAssignment.addActionListener(e -> {
				// add new assignment
				int[] periodsAvailable = periodsEmpty();
				log.finer("NEW ASSIGNMENT");
				byte periodToInsertTo;
				Session sessionToInsert;
				JTextField sessionBox = sessionSearchField(e1 -> {
				});
				System.out.println("periods available: "+Arrays.toString(periodsAvailable));

				@SuppressWarnings("serial")
				/**
				 * A spinner model to show only the periods available for inserting. For example, if the student has 
				 * an assignment in period 2, but not the other ones, when the "add session" prompt is shown, 
				 * the user will only be able to add a session into periods 1 & 3 (periods are indexed from 1 for
				 * the user, but 0 for the computer. that is why there are a lot of -1 all over the place.)
				 */
				SpinnerNumberModel model = new SpinnerNumberModel(periodsAvailable[0] + 1, periodsAvailable[0] + 1,
						periodsAvailable[periodsAvailable.length - 1] + 1, 1) {					
					@Override
					public Object getNextValue() {
						return incrValue(+1);
					}
					@Override
					public Object getPreviousValue() {
						return incrValue(-1);
					}
					
					private Number incrValue(int dir) {
						Number value = (Number) getValue();
						int index = indexOf(value.intValue() -1 , periodsAvailable);
						
						if (dir > 0 && ++index < periodsAvailable.length)
								return periodsAvailable[index] + 1;
						else if (dir < 0 && --index >= 0)
								return periodsAvailable[index] + 1;
						return null;
					}
				};
				
				JSpinner periodInsertSelect = new JSpinner(model);

				JPanel msg = new JPanel(new GridLayout(0, 1));
				JPanel row = new JPanel(new BorderLayout());
				row.add(new JLabel("Insert Session: "), BorderLayout.WEST);
				row.add(sessionBox, BorderLayout.CENTER);
				sessionBox.setEnabled(true);
				msg.add(row);
				
				row = new JPanel(new BorderLayout());
				row.add(new JLabel("Into Period: "), BorderLayout.WEST);
				row.add(periodInsertSelect);
				msg.add(row);
				
				int confirmation = JOptionPane.showOptionDialog(
						null, msg, "Add Session Assignment", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, null, JOptionPane.OK_OPTION);
				log.finer("confirm?: " + confirmation);
				if (confirmation != JOptionPane.OK_OPTION) return;
				periodToInsertTo = (byte) (((Integer)periodInsertSelect.getValue()).intValue() - 1);
				
				log.finer("HELP PLS into period " + periodToInsertTo);
				if ((sessionToInsert = master.getEvent().getSessionFromName(sessionBox.getText())) == null)
					return;
				else {
					log.finer("INSERT SESSION: " + sessionToInsert);
					student.getAssignments()[periodToInsertTo] = sessionToInsert;
					sessionToInsert.getStudents().get(periodToInsertTo).add(student);
					log.finer("new sessions: " + Arrays.toString(student.getAssignments()));
				}
				addAssignment.setEnabled(
						periodsEmpty().length > 0);
				removeAssignment.setEnabled(false);
				refresh();
			});
			
			removeAssignment.addActionListener(e -> {
				Session val = memberSessions.getSelectedValue();
				student.getAssignments()[memberSessions.getSelectedIndex()] = null;
				val.getStudents().get(memberSessions.getSelectedIndex()).remove(student);

				refresh();
				addAssignment.setEnabled(periodsEmpty().length > 0);
				removeAssignment.setEnabled(false);
			});
			
			JButton removeStudent = new JButton("Delete Student");
			removeStudent.addActionListener(e -> {
				for (int i = 0; i < student.getAssignments().length; i++) 
					if (student.getAssignment(i) != null)
						student.getAssignment(i).getStudents().get(i).remove(student);
				
				master.getEvent().getMasterStudents().remove(student);
				master.refresh("Students");
				refresh();
			});
			
			south.add(memberSessions);
			south.add(addAssignment);
			south.add(removeAssignment);
			south.add(removeStudent);
			add(south, BorderLayout.SOUTH);
			refresh();
		}
		
		private static int indexOf(int target, int[] arr) {
			for (int i = 0; i < arr.length; i++)
				if (arr[i] == target)
					return i;
			return -1;
		}
		
		private int[] periodsEmpty() {
			int sizeOfRet = 0;
			for (int i = 0; i < student.getAssignments().length; i++)
				if (student.getAssignment(i) == null)
					sizeOfRet++;
			int[] ret = new int[sizeOfRet];
			int index = 0;
			for (int i = 0; i < student.getAssignments().length; i++)
				if (student.getAssignment(i) == null) 
					ret[index++] = i;
			return ret;
		}
		
		private JPanel createRequestSlot(int choice, Session request) {
			JPanel ret = new JPanel(new BorderLayout());
			ret.add(new JLabel(choice + ":"), BorderLayout.WEST);
			JTextField title = sessionSearchField(e1 -> {
				// check if the session is valid. if not, tell them
				for (Session s : master.getEvent().getSessions())
					if (s.getTitle().equals(((JTextComponent) e1.getSource()).getText())) {
						submitNewRequest(s, choice - 1);
						return;
					}
				//notify not found and reset text? TODO
			});
			
			title.setText(request == null ? "" : request.getTitle());
			ret.add(title);
			JButton remove = new JButton("Remove");
			remove.addActionListener(e -> {
				removeRequest(ret);
				if (request != null)
					student.getRequests().remove(request);
			});
			ret.add(remove, BorderLayout.EAST);
			return ret;
		}
		
		private void removeRequest(JPanel requestSlot) {
			// counts the amount of spots in the request panel
			int amountSlots = 0;
			for (Component c : requestPanel.getComponents())
				if (c instanceof JPanel)
					amountSlots++;
				else 
					// if not a jpanel, requests are over.
					break;
			//already under = before anything, does the student not have enough requests?
			boolean alreadyUnder = amountSlots < Student.MAX_REQUESTS;
			requestPanel.remove(requestSlot);
			//move every request up a slot from what you just removed
			for (int i = 0; i < amountSlots - 1; i++)
				((JLabel) ((JPanel)requestPanel.getComponent(i)).getComponent(0)).setText((i + 1) + ": ");
			// essentially, if the addButton was already present, don't bother adding it.
			if (!alreadyUnder) {
				JButton addButton = new JButton("Add Request");
				addButton.addActionListener(e -> {
					addRequest(addButton);
				});
				requestPanel.add(addButton);
			}
			requestPanel.revalidate();
		}
		
		private void addRequest(JButton target) {
			requestPanel.add(createRequestSlot(requestPanel.getComponentCount(), null),
					requestPanel.getComponentCount() - 1);
			if (requestPanel.getComponents().length - 1 >= Student.MAX_REQUESTS) 
				requestPanel.remove(target);
			requestPanel.revalidate();
		}
		
		private JTextField sessionSearchField(ActionListener action) {
			return anonSearchBar(master.getEvent().getSessions(), action);
		}
		
		private void submitNewRequest(Session s, int slot) {
			student.getRequests().add(slot, s);
		}
		
		
		@Override
		public void refresh() {
			memberSessions.setListData(student.getAssignments());
			revalidate();
		}
	}

	public static class SessionPanel extends SideInfoPanel {
		private static final long serialVersionUID = 1L;
		// Session instance variables
		private JPanel speakerName, classroomNumber;
		private Session session;
		private JList<Student> listStudents;

		public SessionPanel(Session session, CareerDayGUI master) {
			super(master);
			this.session = session;

			speakerName = createInfoField("Speaker", session.getSpeaker(), new EditingAction() {
				@Override
				public void edit(AWTEvent e) {
					session.setSpeaker(((JTextField)e.getSource()).getText());
				}
			});

			classroomNumber = new JPanel(new BorderLayout());
			classroomNumber.add(new JLabel("Room: "), BorderLayout.WEST);
			JTextField editField = roomSearchField(e -> {
				Room set = searchRooms(((JTextComponent) e.getSource()).getText());
				if (set == null) {
					((JTextComponent) e.getSource()).setText(session.getRoom() == null
									? ""
									: session.getRoom().getRoomNumber());
					return;
				}
				
				// if there is already a session in the room, ask if you want to reassign
				if (set.getResidentSession() != null 
						&& !set.getResidentSession().equals(session)) 
					if (JOptionPane.showConfirmDialog(null,
							"This deletes the room assignment for:\n"
									+ set.getResidentSession() + ".\n"
									+ "Do you want to continue?",
							CareerDay.APP_NAME, JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, null)
							!= JOptionPane.YES_OPTION)
						return;
				if (session.getRoom() != null) 
					session.getRoom().setResidentSession(null);
				if (set.getResidentSession() != null)
					set.getResidentSession().setRoom(null);
				session.setRoom(set);
				set.setResidentSession(session);
				refresh();
			});
			editField.setText(session.getRoom() == null ? "" : session.getRoom().getRoomNumber());
			classroomNumber.add(editField, BorderLayout.CENTER);
			

			setLayout(new BorderLayout());

			JPanel north = new JPanel(new GridLayout(2, 0));
			JPanel center = new JPanel(new BorderLayout());
			JPanel south = new JPanel(new BorderLayout());

			add(center, BorderLayout.CENTER);


			listStudents = new JList<Student>();
			listStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(listStudents);
			scrollPane.setBorder(BorderFactory.createTitledBorder("Resident Students"));
			populateList(getPeriod());
			String title = session.getTitle();
			setBorder(BorderFactory.createTitledBorder(null, title,
					TitledBorder.LEADING, TitledBorder.ABOVE_TOP,
					new Font("Arial", Font.PLAIN, 20), Color.BLACK));

			center.add(scrollPane);

			add(north, BorderLayout.NORTH);
			north.setLayout(new GridLayout(2, 0));
			north.add(speakerName);
			north.add(classroomNumber);

			add(south, BorderLayout.SOUTH);
			south.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
			south.setLayout(new GridLayout(0, 1));
			
			JButton button = new JButton("Add Student");
			button.addActionListener(e -> {
				JPanel studentSearchPanel = new JPanel(new BorderLayout());
				studentSearchPanel.add(studentSearchField(e1 -> {
					for (Student s : master.getEvent().getMasterStudents())
						if (s.getFullName().equals(((JTextComponent) e1.getSource()).getText())) {
							// student exists
							session.getStudents().get(getPeriod()).add(s);
							if (s.getAssignments().length > getPeriod())
								s.getAssignments()[getPeriod()] = session;
							
						}
				}));
			});
			
			south.add(button);
			
			JButton studentInfo = new JButton("Show Student Info");
			studentInfo.addActionListener(e -> {
				master.getTabs().setSelectedIndex(1);
				master.getLists().get(1).setSelectedValue(listStudents.getSelectedValue(), true);
			});
			south.add(studentInfo);
			studentInfo.setEnabled(false);
			
			JButton remove = new JButton("Remove from Session");
			remove.addActionListener(e -> {
				Student selected = listStudents.getSelectedValue();
				if (selected == null) return;
				session.getStudents().get(getPeriod()).remove(selected);
				selected.getAssignments()[getPeriod()] = null;
				refresh();
			});
			remove.setEnabled(false);
			
			listStudents.addListSelectionListener(e -> {
				remove.setEnabled(true);
				studentInfo.setEnabled(true);
			});
			south.add(remove);
			
			south.add(new JLabel("Available in: "));
			
			for (int i = 0; i < master.getEvent().getNumberOfPeriods(); i++)
				south.add(availableInPeriod(i));
//			south.add(availability);
			
			
			JButton delete = new JButton("Delete Session");
			south.add(delete);
			delete.addActionListener(e -> {
				master.getEvent().getSessions().remove(this.session);
				master.refresh("Sessions");
			});
		}
		
		private JCheckBox availableInPeriod(int period) {
			JCheckBox ret = new JCheckBox("Period " + (period + 1));
			ret.addActionListener(e -> {
				session.getPeriodAvailability()[period] = ret.isSelected();
			});
			ret.setSelected(session.getPeriodAvailability()[period]);
			return ret;
		}
		
		private JTextField roomSearchField(ActionListener action) {
			return anonSearchBar(master.getEvent().getRooms(), action);
		}
		
		private Room searchRooms(String roomTitle) {
			for (Room r : master.getEvent().getRooms())
				if (r.getTitle().equals(roomTitle))
					return r;
			return null;
		}

		public void populateList(int period) {
			// model = new DefaultListModel<String>();
			listStudents.setListData(
					session.getStudents().get(period).toArray(new Student[session.getStudents().get(period).size()]));
			listStudents.revalidate();
		}

		@Override
		public void refresh() {
			populateList(getPeriod());
			//TODO uh oh
//			classroomNumber.setText(session.getRoom() == null ? "" : session.getRoom().getRoomNumber());
		}
		
		private JTextField studentSearchField(ActionListener action) {
			return anonSearchBar(master.getEvent().getMasterStudents(), action);
		}
		
		
	}
	
	private static JTextField anonSearchBar(ArrayList<? extends Searchable> values, ActionListener action) {
		JTextField searchBar = new JTextField();
		searchBar.addActionListener(action);
		//auto-complete
		searchBar.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {
				final int maxAmtResults = 5;
				ArrayList<Searchable> resultData = new ArrayList<Searchable>();
				for (Searchable s : values) 
					if (s.getIdentifier().toLowerCase().contains(searchBar.getText().toLowerCase()))
						resultData.add(s);
				if (resultData.isEmpty()) return;
				JPopupMenu results = new JPopupMenu();
				for (int i = 0; i < Math.min(maxAmtResults, resultData.size()); i++) {
					JMenuItem addition = new JMenuItem(((GuiListable) resultData.get(i)).getTitle());
					final int index = i;
					addition.addActionListener(e1 -> {
						searchBar.setText(((GuiListable) resultData.get(index)).getTitle());
						action.actionPerformed(new ActionEvent(searchBar, 0, "autocomplete student select"));
					});
					results.add(addition);
				}
				results.show(searchBar, 0, searchBar.getHeight() - 3);
				searchBar.requestFocus();
//				searchBar.setCaretPosition(searchBar.getText().length());
				searchBar.setSelectionStart(searchBar.getText().length());
			}
		});
		return searchBar;
	}
	
	public static JPanel createInfoField(String title, Object data, EditingAction action) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel(title + ": "), BorderLayout.WEST);
		JTextField editField = new JTextField(data.toString());
		editField.addActionListener(action);
		editField.addFocusListener(action);
		panel.add(editField, BorderLayout.CENTER);
		return panel;
	}
}