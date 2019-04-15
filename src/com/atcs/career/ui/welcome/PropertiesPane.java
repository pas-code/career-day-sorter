package com.atcs.career.ui.welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.atcs.career.data.Event;
import com.atcs.career.io.IOUtilities;
import com.atcs.career.io.importexport.CSVReader;
import com.atcs.career.program.MainClass;

//Jarrett Bierman
//11/18/18
//Properties
public class PropertiesPane extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 700;
	public static final int PREF_H = 300;
	public static final int BORDER_SIZE = 50;

	
	//note for future... not nuts about having every single element as an instance variable...
	private JPanel gridPanel;
	private JLabel sessionLabel, studentLabel, classroomLabel, periodLabel,
			allStudentLabel;
	private JButton sessionButton, requestButton, classroomButton,
			allStudentButton;
	private String sessionFile, requestFile, classroomFile, allStudentFile;
	private String lastDirAccessed;
	private JButton submit, cancel;
	private JTextField title;
	private static final String textPrompt = "Enter Project Name Here";
	private Event event;

	private WelcomeScreen welc;
	private JSpinner periodCount;

	private static final String BUTTON_DEFAULT_TEXT = "Choose File";

	/**
	 * welc can be none. will bring a popup if not.
	 * @param welc welcome screen calling the props pane. 
	 * @param e the event being edited
	 */
	public PropertiesPane(WelcomeScreen welc, Event e) {
		setFocusable(true);
		this.welc = welc;
		this.event = (e == null) ? new Event() : e;

		setFilesInAccordanceToEvent();

		this.setLayout(new BorderLayout());
		this.setBorder(
				BorderFactory.createEmptyBorder(0, BORDER_SIZE, 0, BORDER_SIZE));
		gridPanel = new JPanel(new GridLayout(5, 2));

		createLabels();
		createButtons();
		createFieldAndSpinner();
		addGridStuff();

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		buttonPanel.add(cancel);
		buttonPanel.add(submit);

		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(title, BorderLayout.NORTH);
		this.add(gridPanel);
		// createAndShowGUI();
		configFrame();
		revalidate();
	}

	private void setFilesInAccordanceToEvent() {
		sessionFile = event.getSessionFile();
		requestFile = event.getRequestFile();
		classroomFile = event.getRoomFile();
		allStudentFile = event.getStudentFile();
	}
	
	
	
	// -----------------------------------CONFIG GUI--------------------------------------------

	private JFrame createFrame() {
		return new JFrame("Set Event Data");
	}

	private void configFrame() {
		//make a frame. if welc is given, remove welc from its frame and use it.
		JFrame container = welc == null ? createFrame() : welc.parentFrame;
		if (welc != null) {
			container.getContentPane().remove(0);
		}
		container.getContentPane().add(this);
		container.pack();
		container.setLocationRelativeTo(null);
		container.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// TODO notify that data has been lost
			}
		});
		container.setVisible(true);
	}

	public void createLabels() {
		sessionLabel = new JLabel("Session File (.csv)");
		allStudentLabel = new JLabel("Master File of all students (.csv)");
		studentLabel = new JLabel("Requests File (.csv)");
		classroomLabel = new JLabel("Classroom File (.csv)");
		periodLabel = new JLabel("Number of Periods");
	}

	private void createButtons() {
		final int homeDirLength = System.getProperty("user.home").length();
		sessionButton = new JButton(event.getSessionFile() == null
				? BUTTON_DEFAULT_TEXT
				: event.getSessionFile().substring(homeDirLength));
		sessionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = selectFile(sessionButton);
				sessionFile = selected == null ? sessionFile : selected;
				sessionButton.setText(sessionFile == null ?
						BUTTON_DEFAULT_TEXT : sessionFile.substring(homeDirLength));
				if (sessionFile != null) 
					lastDirAccessed = sessionFile.substring(0, sessionFile.lastIndexOf('/'));
			}
		});

		allStudentButton = new JButton(allStudentFile == null
				? BUTTON_DEFAULT_TEXT
				: allStudentFile.substring(homeDirLength));
		allStudentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = selectFile(allStudentButton);
				allStudentFile = selected == null ? allStudentFile : selected;
				allStudentButton.setText(allStudentFile == null ?
						BUTTON_DEFAULT_TEXT : allStudentFile.substring(homeDirLength));
				if (allStudentFile != null) 
					lastDirAccessed = allStudentFile.substring(0, allStudentFile.lastIndexOf('/'));
			}
		});

		requestButton = new JButton(requestFile == null
				? BUTTON_DEFAULT_TEXT
				: requestFile.substring(homeDirLength));
		requestButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = selectFile(requestButton);
				requestFile = selected == null ? requestFile : selected;
				requestButton.setText(requestFile == null ?
						BUTTON_DEFAULT_TEXT : requestFile.substring(homeDirLength));
				if (requestFile != null) 
					lastDirAccessed = requestFile.substring(0, requestFile.lastIndexOf('/'));
			}
		});

		classroomButton = new JButton(classroomFile == null
				? BUTTON_DEFAULT_TEXT
				: classroomFile.substring(homeDirLength));

		classroomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = selectFile(classroomButton);
				classroomFile = selected == null ? classroomFile : selected;
				classroomButton.setText(classroomFile == null ?
						BUTTON_DEFAULT_TEXT : classroomFile.substring(homeDirLength));
				if (classroomFile != null) 
					lastDirAccessed = classroomFile.substring(0, classroomFile.lastIndexOf('/'));
			}
		});

		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (readyToSubmit()) {
					if (welc != null)
						welc.sendEventAndClose(createEvent());
					else {
						updateEventData();
						close();
						
					}
				} else
					JOptionPane.showMessageDialog(null,
							"You did not select all of the needed files. Please select all options.");
			}
		});
		cancel = new JButton("Cancel");
		cancel.addActionListener(e -> {
			close();
		});
	}
	
	private Event createEvent() {
		Event ret = new Event();
		ret.setNumberOfPeriods((byte) (int) periodCount.getValue());

		ret.changeName(title.getText());
		if (checkNullPassed(sessionFile)) {
			ret.setSessions(IOUtilities.loadSessionArray(sessionFile, ret));
			ret.setSessionFile(sessionFile);
		}
		if (checkNullPassed(allStudentFile)) {
			ret.setMasterStudents(IOUtilities.loadMasterStudentArray(
					allStudentFile, ret.getNumberOfPeriods()));
			ret.setStudentFile(allStudentFile);
		}
		if (checkNullPassed(requestFile)) {
			IOUtilities.combineStudentArrays(IOUtilities.loadRequestsArray(
					requestFile, ret), ret.getMasterStudents());
			ret.setRequestFile(requestFile);
		}
		if (checkNullPassed(classroomFile)) {
			ret.setRooms(IOUtilities.loadRoomArray(classroomFile,
					ret.getNumberOfPeriods()));
			ret.setRoomFile(classroomFile);
		}
		
		
		ret.setLastModified(LocalDate.now());
		MainClass.changeLog.info("Event Created:\n" + ret.infoString());
		return ret;
	}
	
	private void updateEventData() {
		if (!sessionFile.equals(event.getSessionFile())) {
			event.setSessionFile(sessionFile);
			
		}
		
		event.changeName(title.getText());
		if (!sessionFile.equals(event.getSessionFile())) {
			event.setSessions(IOUtilities.loadSessionArray(sessionFile, event));
			event.setSessionFile(sessionFile);
			MainClass.changeLog.info("sessions file changed to " + sessionFile);
		}
		if (!allStudentFile.equals(event.getStudentFile())) {
			event.setMasterStudents(IOUtilities.loadMasterStudentArray(
					allStudentFile, event.getNumberOfPeriods()));
			event.setStudentFile(allStudentFile);
			MainClass.changeLog.info("students file changed to " + allStudentFile);
			// add in the requests.
			if (event.getRequestFile() != null) {
				IOUtilities
						.combineStudentArrays(
								IOUtilities.loadRequestsArray(requestFile,
										event), event.getMasterStudents());
			}
		}
		if (!requestFile.equals(event.getRequestFile())) {
			// TODO clear requests
			// TODO should I clear the requests or just overwrite them? some might stay depending on data.
			IOUtilities.combineStudentArrays(IOUtilities.loadRequestsArray(
					requestFile, event), event.getMasterStudents());
			event.setRequestFile(requestFile);
			MainClass.changeLog.info("requests file changed to " + requestFile);
		}
		if (!classroomFile.equals(event.getRoomFile())) {
			event.setRooms(IOUtilities.loadRoomArray(classroomFile,
					event.getNumberOfPeriods()));
			event.setRoomFile(classroomFile);
			MainClass.changeLog.info("room file changed to " + classroomFile);
		}
			
	}

	private boolean checkNullPassed(String path) {
		return path != null && path != "" && new File(path).exists();
	}
	
	private void createFieldAndSpinner() {
		title = new JTextField(event.getEventName()== null ? textPrompt : event.getEventName());
		title.setForeground(title.getText().equals(textPrompt) ? Color.GRAY : Color.BLACK);
		title.setFont(new Font("Ariel", Font.PLAIN, 40));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (title.getText().equals(textPrompt)) {
					title.setText("");
					title.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (title.getText().equals("")) {
					title.setText(textPrompt);
					title.setForeground(Color.GRAY);
				}
			}

		});

		periodCount = new JSpinner(new SpinnerNumberModel(
				event.getNumberOfPeriods() == 0 ? 1 : event.getNumberOfPeriods(), 1, 99, 1));
		periodCount.setFont(new Font("Ariel", Font.PLAIN, 30));
		periodCount.setBorder(BorderFactory.createEmptyBorder(0, 170, 0, 50));
		((JSpinner.DefaultEditor) periodCount.getEditor()).getTextField().setEditable(false);
	}

	public void addGridStuff() {
		gridPanel.add(sessionLabel);
		gridPanel.add(sessionButton);
		gridPanel.add(allStudentLabel);
		gridPanel.add(allStudentButton);
		gridPanel.add(studentLabel);
		gridPanel.add(requestButton);
		gridPanel.add(classroomLabel);
		gridPanel.add(classroomButton);
		gridPanel.add(periodLabel);
		gridPanel.add(periodCount);
	}

	public String selectFile(JButton b) {
		String location = CSVReader.getFileLocation(".csv", lastDirAccessed);
		if (location == null) 
			return null;
		int index = 0;
		for (int i = 0; i < location.length(); i++) {
			if (location.charAt(i) == File.separatorChar) 
			   index = i;
		}
		String name = location.substring(index + 1);
		b.setText(name);
		return location;
	}

	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	// ------------------------------------------END GUI CONFIGURATION----------------------------------------

	private void close() {
		if (welc != null) 
			welc.cancelProps();
		else
			((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
	}
	
	public boolean readyToSubmit() { //edit this
		return !(title.getText().isEmpty() || title.getText().equals(textPrompt));
//	   return !(title.getText().isEmpty() || periodCount.getValue() == null 
//	            || title.getText().equals(textPrompt) || sessionFile == null || allStudentFile == null);
	}

}