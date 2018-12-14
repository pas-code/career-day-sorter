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
import java.awt.event.WindowListener;
import java.io.File;

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
import com.atcs.career.io.importexport.CSVReader;
//Jarrett Bierman
//11/18/18
//Properties
public class PropertiesPane extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 700;
	public static final int PREF_H = 300;
	public static final int BORDER_SIZE = 50;

	private JPanel gridPanel;
	private JLabel sessionLabel, studentLabel, classroomLabel, periodLabel, allStudentLabel;
	private JButton sessionButton, studentButton, classroomButton, allStudentButton;
	private File sessionFile, studentFile, classroomFile, allStudentFile;
	private JButton submit;
	private JTextField title;
	private final String textPrompt = "Enter Project Name Here";
	private Event event;
	
	private WelcomeGUIMaster master;

	private JSpinner periodCount;

	private final String BUTTON_DEFAULT_TEXT = "Choose File";

	/**
	 * master can be none. will bring a popup if not.
	 */
	public PropertiesPane(WelcomeGUIMaster master, Event e) {
		setFocusable(true);
		this.master = master;
		if (master == null) createFrame();
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

		this.add(submit, BorderLayout.SOUTH);
		this.add(title, BorderLayout.NORTH);
		this.add(gridPanel);
		// createAndShowGUI();
		revalidate();
	}

	private void createFrame() {
		JFrame container = new JFrame("Set Event Data");
		container.getContentPane().add(this);
		container.pack();
		container.setLocationRelativeTo(null);
		container.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// notify that data has been lost
			}
		});
		container.setVisible(true);
	}

	public void createLabels() {
		sessionLabel = new JLabel("Session File <Required> (.csv)");
		allStudentLabel = new JLabel("Master File of all students <Required> (.csv)");
		studentLabel = new JLabel("Google Form Result Student File (.csv)");
		classroomLabel = new JLabel("Classroom File (.csv)");
		periodLabel = new JLabel("Number of Periods <Required>");
	}

	public void createButtons() {
		sessionButton = new JButton(event.getSessionFile() == null ? BUTTON_DEFAULT_TEXT : event.getSessionFile());
		sessionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sessionFile = selectFile(sessionButton);
				event.setRequestFile(sessionFile.getName());
			}
		});

		allStudentButton = new JButton(event.getRequestFile() == null ? BUTTON_DEFAULT_TEXT : event.getRequestFile());
		allStudentButton.addActionListener(new ActionListener() {		   
		   @Override
		   public void actionPerformed(ActionEvent e) {
		      allStudentFile = selectFile(allStudentButton);
              event.setRequestFile(allStudentFile.getName());

		   }
		});
		
		studentButton = new JButton(event.getRequestFile() == null ? BUTTON_DEFAULT_TEXT : event.getRequestFile());
		studentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				studentFile = selectFile(studentButton);
                event.setRequestFile(studentFile.getName());

			}
		});

		classroomButton = new JButton(event.getRoomFile() == null ? BUTTON_DEFAULT_TEXT : event.getRoomFile());
		classroomButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				classroomFile = selectFile(classroomButton);
                event.setRequestFile(classroomFile.getName());

			}
		});

		submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (readyToSubmit()) 
					{
						// Do all of the important info work here
						System.out.println("Title: " + title.getText());
						System.out.println(sessionFile.getName());
						System.out.println(studentFile.getName());
						System.out.println(classroomFile.getName());
						System.out
								.println("Periods: " + (int) periodCount.getValue());
					} else {
						JOptionPane.showMessageDialog(null,
								"You did not select all of the needed files. Please select all options.");
					}
				} catch (NullPointerException f) {
					JOptionPane.showMessageDialog(null,
							"You did not select all of the needed files. Please select all options.");
				}

			}
		});
	}

	public void createFieldAndSpinner() {
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
		periodCount.setPreferredSize(new Dimension(30, 0));
		periodCount.setFont(new Font("Ariel", Font.PLAIN, 30));
		periodCount.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		((JSpinner.DefaultEditor) periodCount.getEditor()).getTextField()
				.setEditable(false);

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

	/**
	 * The Select the File Method
	 * 
	 * @param b
	 * @return
	 */
	public File selectFile(JButton b) {
		String location = CSVReader.getFileLocation(".csv");
		if (location == null) 
			return null;
		int index = 0;
		for (int i = 0; i < location.length(); i++) {
			if (location.charAt(i) == File.separatorChar) index = i;
		}
		String name = location.substring(index + 1);
		b.setText(name);
		return new File(location);
	}
	
	public boolean readyToSubmit()//edit this
	{
	   return !(title.getText().isEmpty() || periodCount.getValue() == null || title.getText().equals(textPrompt));
	}

	/**
	 * The createAndShowGUI class puts your JPanel into a JFrame and creates the
	 * GUI interface. This pretty much sets up your screen so you can see what
	 * you have coded. //
	 */
//	 private void createAndShowGUI() {
//	 PropertiesPane gamePanel = this;
//	 JFrame frame = new JFrame("Selection Panel");
//	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	 frame.getContentPane().add(gamePanel);
//	 frame.pack();
//	 frame.setLocationRelativeTo(null);
//	 frame.setVisible(true);
//	 }

	/**
	 * This overrides the JPanel's getPreferredSize() method It tells the JPanel
	 * to be a certain width and height
	 */
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	/**
	 * The main method runs your entire program It has the method
	 * createAndShowGUI() and runs it. This makes your whole program work.
	 */
	public static void main(String[] args) {
		new PropertiesPane(null, null);
	}
}