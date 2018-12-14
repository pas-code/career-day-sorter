package com.atcs.career.ui.welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CancellationException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.atcs.career.data.Event;
import com.atcs.career.program.MainClass;
import com.atcs.career.resources.FontManager;

public class WelcomeScreen extends JPanel {

	public static final int PREF_W = 600, PREF_H = 400;
	private JPanel topPanel;
	private JPanel leftPanel;
	private Font openSans;
	private JButton newButton, openButton;
	private Event selected;

	private JLabel title;

	public WelcomeScreen() {
		leftPanel = new JPanel();
		title = new JLabel(MainClass.APP_NAME);
		openSans = FontManager.finalFont(25f);
		topPanel = topPanelConfig();

		newButton = new JButton("New");
		openButton = new JButton("Open");
		configureButton(newButton);
		configureButton(openButton);

		newButton.addActionListener(e -> {
			System.out.println("new");
		});

		openButton.addActionListener(e -> {
			System.out.println("open");
		});

		configGui();
	}

	private void configureButton(JButton b) {
		b.setFont(openSans);
		// b.setBorderPainted(false);
		 b.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 25));
		b.setOpaque(true);
		// b.setBackground(Color.red);
	}

	private void configGui() {
		// Class Panel config
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.setBackground(Color.gray);

		// Left Panel config
		leftPanel.setLayout(new GridLayout());
		leftPanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		leftPanel.setPreferredSize(new Dimension(150, 0));
		leftPanel.setLayout(new GridLayout(2, 0));
		leftPanel.setBackground(Color.gray);
		leftPanel.add(newButton);
		leftPanel.add(openButton);

		this.add(leftPanel, BorderLayout.WEST);


	}
	
	public Event getEvent() {
		return selected;
	}

	private JPanel topPanelConfig() {
		JPanel t = new JPanel();
		t.setPreferredSize(new Dimension(0, 50));
		t.setBackground(Color.black);
		t.setLayout(new BorderLayout());

		title.setForeground(Color.white);
		title.setFont(openSans);

		t.add(title, BorderLayout.WEST);
		return t;
	}
	
	public static Event openAndRetrieveEvent() throws CancellationException {
		JFrame frame = new JFrame("Welcome");
		WelcomeScreen welcome = new WelcomeScreen();
		frame.getContentPane().add(welcome);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				throw new CancellationException();
			}
		});
	}

	private static Event createNewEventFromProps() {
		
	}
	
	static 

	public static void main(String args[]) {
		JFrame f = new JFrame("Testwelcome");
		f.getContentPane().add(new WelcomeScreen(null));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
