package com.atcs.career.ui.welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.atcs.career.data.Event;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.program.MainClass;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.resources.FontManager;
import com.atcs.career.ui.MasterUI;

public class WelcomeScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 600, PREF_H = 400;
	private static final BasicLogger log = BasicLogger.getLogger(WelcomeScreen.class.getName());
	
	private JPanel topPanel, leftPanel, rightPanel;

	private Font openSans;
	private JButton newButton, openButton;
	private Event selected;

	private JLabel title;
	protected JFrame parentFrame;

	private MasterUI master;
	
	public WelcomeScreen(JFrame parentFrame, MasterUI master) {
		log.config("construct welcome screen with master "+master);
		this.master = master;
		
		// configure frame
		this.parentFrame = parentFrame;
	
		configGui();
		configFrame();
		log.config("completed init welcome");
	}
	
	
	// ----------------------------------- INITIAL CONFIGURATION OF GUI ---------------------------------------
	public java.awt.Dimension getPreferredSize() {
		return new java.awt.Dimension(PREF_W, PREF_H);
	}

	private void configureButton(JButton b) {
		b.setFont(openSans);
		b.setBorderPainted(false);
//		b.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		b.setBackground(Color.GRAY);
		b.setFocusable(false);
		b.addMouseListener(buttonListener());
	}
	
	private MouseListener buttonListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// indent
				((AbstractButton) e.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseReleased(MouseEvent e) {				
				((AbstractButton) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				((AbstractButton) e.getSource()).setBorderPainted(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				((AbstractButton) e.getSource()).setBorderPainted(false);
				
			}
		};
	}

	/**
	 * configure the frame surrounding the panel
	 */
	private void configFrame() {
		if (parentFrame == null) {
			parentFrame = createFrame();
		}
		parentFrame.getContentPane().add(this);
		parentFrame.pack();
		parentFrame.setLocationRelativeTo(null);
		parentFrame.setResizable(false);
		parentFrame.setVisible(true);
	}
	
	/**
	 * create a new frame to put the welcome screen in, if needed.
	 */
	private JFrame createFrame() {
		return new JFrame("TEST");
	}
	
	private void configGui() {
		// initialize gui
		title = new JLabel(MainClass.APP_NAME);
		openSans = FontManager.finalFont(25f);
		topPanel = topPanelConfig();

		newButton = new JButton("New");
		openButton = new JButton("Open");
		configureButton(newButton);
		configureButton(openButton);

		newButton.addActionListener(e -> {
			log.info("new event button pressed");
			constructProps();
		});

		openButton.addActionListener(e -> {
			log.info("open event button pressed");
			try {
				sendEventAndClose(OpenScreen.open());
			} catch (Exception e1) {
				log.info("open event prompt cancelled");
			}
		});

		// Class Panel config
		leftPanel = new JPanel(new GridLayout(2, 0));
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.setBackground(Color.gray);

		// Left Panel config
//		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		leftPanel.setPreferredSize(new Dimension(150, 0));
//		((GridLayout) leftPanel.getLayout()).setVgap(20);
		
		
		leftPanel.setBackground(Color.gray);
		leftPanel.add(newButton);
		leftPanel.add(openButton);

		this.add(leftPanel, BorderLayout.WEST);

		rightPanelConfig();

		this.add(rightPanel);
	}
	
	/**
	 * gets the last 4 events opened.
	 */
	private ArrayList<String> getRecentEvents() {
		final int amtRecents = 4;
		
		ArrayList<String> ret = new ArrayList<String>();
		File saveDir = new File(FileHandler.SAVE_DIR);
		final File[] children = saveDir.listFiles();
		for (int i = 0; i < Math.min(children.length, amtRecents); i++) 
			ret.add(getEventName(children[i].getPath()));
		
		return ret;
	}
	
	private String getEventName(String filePath) {
		return Event.extractEventName(filePath.substring(filePath.lastIndexOf(File.separatorChar) + 1));
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
	
	private void rightPanelConfig() {
		// right panel config (recents)
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBorder(BorderFactory.createTitledBorder(
				null, "Recent Events", TitledBorder.LEADING, TitledBorder.TOP, openSans, Color.BLACK));
		DefaultListModel<String> model = new DefaultListModel<String>();
		ArrayList<String> recentEventNames = getRecentEvents();
		for (String s : recentEventNames) 
			model.addElement(s);

		JList<String> recents = new JList<String>(model);
		recents.setFont(FontManager.finalFont(24f).deriveFont(Font.BOLD));

		rightPanel.add(recents);
		JPanel lowerRight = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton openConfirmation = new JButton("Open");
		openConfirmation.setEnabled(false);
		openConfirmation.addActionListener(e -> {
			log.info("recent event "+recents.getSelectedValue() + " opened");
			try {
				sendEventAndClose(FileHandler.load(FileHandler.SAVE_DIR
						+ Event.saveFileName(recents.getSelectedValue())));
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
		});

		recents.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				openConfirmation.setEnabled(true);
			}
		});
		lowerRight.add(openConfirmation);
		rightPanel.add(lowerRight, BorderLayout.SOUTH);
	}
	
	// -------------------------------------------------------------------------------------------------------
	
	
	protected void sendEventAndClose(Event e) {
		sendEvent(e);
		close();
	}
	
	protected void close() {
		// the welcome screen will always be in its own frame, so when closing, the parent frame can dispose
		parentFrame.dispose();
	}
	
	/**
	 * close the props screen. should only be called from the props pane itself.
	 */
	protected void cancelProps() {
		log.info("props cancelled");
		parentFrame.getContentPane().remove(0);
		configFrame();
	}
	
	/** 
	 * sends an event (e) to its master, as called from itself or its props pane.
	 */
	protected void sendEvent(Event e) {
		if (master != null)
			master.openEventFromWelcome(e);
	}
	
	private void constructProps() {
		PropertiesPane props = new PropertiesPane(this, null);
		props.revalidate();
		revalidate();
	}

}
