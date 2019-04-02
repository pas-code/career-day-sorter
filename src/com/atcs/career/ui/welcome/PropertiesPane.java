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

import com.atcs.career.data.Event;
import com.atcs.career.io.IOUtilities;
import com.atcs.career.io.importexport.CSVReader;

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
	private JButton sessionButton, studentButton, classroomButton,
			allStudentButton;
	private String sessionFile, studentFile, classroomFile, allStudentFile;
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
		if (e == null)
			this.event = new Event();
		else
			this.event = e;

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
		sessionLabel = new JLabel("Session File <Required> (.csv)");
		allStudentLabel = new JLabel("Master File of all students <Required> (.csv)");
		studentLabel = new JLabel("Student Requests File (.csv)");
		classroomLabel = new JLabel("Classroom File (.csv)");
		periodLabel = new JLabel("Number of Periods");
	}

	private void createButtons() {
		sessionButton = new JButton(event.getSessionFile() == null
				? BUTTON_DEFAULT_TEXT
				: event.getSessionFile());
		sessionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sessionFile = selectFile(sessionButton);
				event.setRequestFile(sessionFile);
			}
		});

		allStudentButton = new JButton(event.getRequestFile() == null
				? BUTTON_DEFAULT_TEXT
				: event.getRequestFile());
		allStudentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				allStudentFile = selectFile(allStudentButton);
				event.setRequestFile(allStudentFile);

			}
		});

		studentButton = new JButton(event.getRequestFile() == null
				? BUTTON_DEFAULT_TEXT
				: event.getRequestFile());
		studentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				studentFile = selectFile(studentButton);
				event.setRequestFile(studentFile);
			}
		});

		classroomButton = new JButton(event.getRoomFile() == null
				? BUTTON_DEFAULT_TEXT
				: event.getRoomFile());

		classroomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				classroomFile = selectFile(classroomButton);
				event.setRequestFile(classroomFile);
			}
		});

		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (readyToSubmit()) {
					// Do all of the important info work here
					System.out.println("Title: " + title.getText());
					System.out.println(sessionFile);
					System.out.println(studentFile);
					System.out.println(classroomFile);
					System.out.println("Periods: " + (int) periodCount.getValue());
					if (welc != null)
						welc.sendEventAndClose(createEvent());
				} else
					JOptionPane.showMessageDialog(null,
							"You did not select all of the needed files. Please select all options.");
			}
		});
		cancel = new JButton("Cancel");
		cancel.addActionListener(e -> {
			cancel();
		});
	}
	
	private Event createEvent() {
		Event ret = new Event();
		ret.setNumberOfPeriods((byte)(int)periodCount.getValue());
		
		ret.changeName(title.getText());
		if (checkNullPassed(sessionFile))
			ret.setSessions(IOUtilities.loadSessionArray(sessionFile));
		if (checkNullPassed(allStudentFile))
			ret.setMasterStudents(IOUtilities.loadMasterStudentArray(allStudentFile, ret.getNumberOfPeriods()));
		if (checkNullPassed(studentFile)) {
			IOUtilities.combineStudentArrays(IOUtilities.loadStudentArray(
					studentFile, ret.getNumberOfPeriods()), ret.getMasterStudents());
		}
		if (checkNullPassed(classroomFile))
			ret.setRooms(IOUtilities.loadRoomArray(classroomFile, ret.getNumberOfPeriods()));
		
		
		ret.setLastModified(LocalDate.now());
		return ret;
	}

	private boolean checkNullPassed(String path) {
		return path != null && path != "" && new File(path).exists();
	}
	
	private void createFieldAndSpinner() {
		title = new JTextField(textPrompt);
		title.setForeground(Color.GRAY);
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

		periodCount = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
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
		gridPanel.add(studentButton);
		gridPanel.add(classroomLabel);
		gridPanel.add(classroomButton);
		gridPanel.add(periodLabel);
		gridPanel.add(periodCount);
	}

	public String selectFile(JButton b) {
		String location = CSVReader.getFileLocation(".csv");
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

	private void cancel() {
		if (welc != null) 
			welc.cancelProps();
	}
	
	public boolean readyToSubmit() { //edit this
	   return !(title.getText().isEmpty() || periodCount.getValue() == null 
	            || title.getText().equals(textPrompt) || sessionFile == null || allStudentFile == null);
	}

}