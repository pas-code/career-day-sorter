package com.atcs.career.ui.welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.atcs.career.program.MainClass;
import com.atcs.career.resources.FontManager;

public class WelcomeScreen extends JPanel {

	public static final int PREF_W = 600, PREF_H = 400;
	private JPanel topPanel;
	private JPanel leftPanel;
	private Font openSans;
	private WelcomeGUIMaster master;
	private JButton newButton, openButton;

	private JLabel title;

	public WelcomeScreen(WelcomeGUIMaster master) {
		leftPanel = new JPanel();
		this.master = master;
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

		// cb = new MenuCircleButton("New",
		// ColorManager.get("welcome.button.background"),
		// ColorManager.get("welcome.foreground"));
		// lb = new
		// MenuCircleButton("Open",ColorManager.get("welcome.button.background"),
		// ColorManager.get("welcome.foreground"));
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
		// Frame config

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

		// f.setVisible(true);

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

	private void sendEvent() {

	}

	public static void main(String args[]) {
		JFrame f = new JFrame("Testwelcome");
		f.getContentPane().add(new WelcomeScreen(null));
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
