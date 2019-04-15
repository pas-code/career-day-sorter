package com.atcs.career.ui.welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.atcs.career.data.Event;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.program.MainClass;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.resources.FontManager;
import com.atcs.career.ui.ColorManager;
import com.atcs.career.ui.Themes;
import com.atcs.career.ui.MasterUI;

public class WelcomeScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 600, PREF_H = 400;
	private static final BasicLogger log = BasicLogger.getLogger(WelcomeScreen.class.getName());
	
	private JPanel topPanel, leftPanel, rightPanel;

	private JButton newButton, openButton;
	private Event selected;

	private JLabel title;
	protected JFrame parentFrame;

	private MasterUI master;
	
	public WelcomeScreen(JFrame parentFrame, MasterUI master) {
		log.config("construct welcome screen with master "+master);
		this.master = master;
		
		Themes.initializeColors();
		Themes.setCurrentTheme(Themes.whiteBlue);
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

	public JButton configureButton(JButton b) {
	    b.setBackground(ColorManager.get(Themes.getCurrentTheme(), "secondary"));
		b.setFont(getFont());
//		b.setBorderPainted(true);
//		b.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		b.setFocusable(false);
		b.addMouseListener(buttonListener());
		b.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ColorManager.get(Themes.getCurrentTheme(), "primary")));
//		b.setOpaque(false);
//		b.setContentAreaFilled(false);
//		b.setBorderPainted(false);
		
		return configButtonLaf(b);
	}
	
	private MouseListener buttonListener() {
		return new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// indent
		//	((AbstractButton) e.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());
			}

			@Override
			public void mouseReleased(MouseEvent e) {				
			
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				((AbstractButton) e.getSource()).setBackground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				((AbstractButton) e.getSource()).setBackground(ColorManager.get(Themes.getCurrentTheme(), "secondary"));
				
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
		//Resize set to true only for testing
		parentFrame.setResizable(true);
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
		setForeground(ColorManager.get("text"));
		title = new JLabel("    " + MainClass.APP_NAME + "    ");
		setFont(FontManager.finalFont(25f));
		topPanel = topPanelConfig();
		
		topPanel.setBackground(ColorManager.get(Themes.getCurrentTheme(), "secondary"));

		newButton = configureButton(new JButton("New"));
		openButton = configureButton(new JButton("Open"));

		newButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, ColorManager.get(Themes.getCurrentTheme(), "primary")));
		openButton.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, ColorManager.get(Themes.getCurrentTheme(), "primary")));
		
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
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.setOpaque(true);
		
		// Left Panel config
		leftPanel = new JPanel(new GridLayout(2, 0));
		leftPanel.setPreferredSize(new Dimension(150, 0));
		leftPanel.setBackground(Color.white);
		
		leftPanel.add(newButton);
		leftPanel.add(openButton);

		this.add(leftPanel, BorderLayout.WEST);

		// config right panel (recents / submit)
		rightPanelConfig();
		rightPanel.setBackground(Color.white);
		this.add(rightPanel);
	}
	
	private static JButton configButtonLaf(JButton b) {
		b.setForeground(ColorManager.get("text"));
		b.setOpaque(true);
		return b;
	}
	
	/**
	 * gets the last 9 events opened.
	 */
	private ArrayList<RecentEventListItem> getRecentEvents() {
		final int amtRecents = 9;
		
		ArrayList<RecentEventListItem> ret = new ArrayList<RecentEventListItem>();
		File saveDir = new File(FileHandler.SAVE_DIR);
		// get all saved event files
		File[] children = saveDir.listFiles();
		// sort so the latest event comes first
		Arrays.sort(children, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return (int)(o2.lastModified() - o1.lastModified());
			}
		});
		// return the last 9 event list items from the sorted files
		for (int i = 0; i < Math.min(children.length, amtRecents); i++) 
			ret.add(new RecentEventListItem(getEventName(children[i].getPath()), children[i].lastModified()));
		
		return ret;
	}
	
	/**
	 *  a holder for information regarding events found from their file holdings
	 */
	private static class RecentEventListItem {
		private String name, lastModified;
		
		public RecentEventListItem(String name, long lastModifiedMillis) {
			this.name = name;
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(lastModifiedMillis);
			lastModified = (cal.get(Calendar.MONTH) + 1) + " / "
					+ cal.get(Calendar.DAY_OF_MONTH) + " / "+cal.get(Calendar.YEAR);
		}
	}
	/**
	 * a render template for RecentEventListItem in JLists
	 */
	private static class RecentEventListItemRenderer extends JPanel 
		implements ListCellRenderer<RecentEventListItem> {
		private static final long serialVersionUID = 1L;

		public RecentEventListItemRenderer() {
			super(new BorderLayout());
		}
		
		private void refresh(RecentEventListItem e, boolean isSelected) {
			removeAll();
			JLabel label = new JLabel("    "+e.name+"                 ");
			label.setForeground(getForeground());
			label.setFont(FontManager.finalFont(15f));
			label.setBorder(BorderFactory.createEmptyBorder(8, 4, 4, 4));
			add(label, BorderLayout.WEST);
			
			JPanel east = new JPanel(new BorderLayout());
//			label = new JLabel("               ");
////			label.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, Color.black));
//			label.setForeground(getForeground());
//			label.setFont(FontManager.finalFont(12));
//			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
//			east.add(label, BorderLayout.WEST);
			label = new JLabel(e.lastModified);
//			label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
			label.setForeground(getForeground());
			label.setFont(FontManager.finalFont(15f));
			label.setBorder(BorderFactory.createEmptyBorder(8, 4, 4, 4));
			label.setBackground(ColorManager.get(Themes.getCurrentTheme(), "background"));
			label.setOpaque(false);
			setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, ColorManager.get(Themes.getCurrentTheme(), "background")));
			setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorManager.get(Themes.getCurrentTheme(), "primary")));
			east.setBackground(ColorManager.get(Themes.getCurrentTheme(), "background"));
			east.add(label, BorderLayout.EAST);
			east.setOpaque(false);
			add(east, BorderLayout.EAST);
			
			if (isSelected)
				setBackground(Color.lightGray);
			else
			    setBackground(ColorManager.get(Themes.getCurrentTheme(), "background"));
		}

		@Override
		public Component getListCellRendererComponent(
				JList<? extends RecentEventListItem> list,
				RecentEventListItem value, int index, boolean isSelected,
				boolean cellHasFocus) {
			refresh(value, isSelected);
			return this;
		}
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
		t.setLayout(new BorderLayout());
		//System.out.println(ColorThemes.whiteBlue);
		Color test = (ColorManager.get(Themes.getCurrentTheme(),"text"));
		assert (test!=null);
		title.setForeground(Color.white);
		title.setBackground(ColorManager.get(Themes.getCurrentTheme(), "primary"));
		title.setOpaque(true);
		title.setFont(getFont());

		t.add(title, BorderLayout.WEST);
		return t;
	}
	
	private void rightPanelConfig() {
		// right panel config (recents)
		rightPanel = new JPanel(new BorderLayout());
//		rightPanel.setBorder(BorderFactory.createTitledBorder(
//				null, "Recent Events", TitledBorder.LEADING, TitledBorder.TOP, getFont(), Color.BLACK));
		rightPanel.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, ColorManager.get(Themes.getCurrentTheme(), "background")));
		JLabel recentLabel = new JLabel("   "+"Recent Events");
		recentLabel.setFont(FontManager.finalFont(20f));
		recentLabel.setForeground(ColorManager.get(Themes.getCurrentTheme(), "text"));
		recentLabel.setOpaque(true);
		recentLabel.setBackground(ColorManager.get(Themes.getCurrentTheme(), "background"));
		recentLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorManager.get(Themes.getCurrentTheme(), "text")));
//		recentLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 0));
		rightPanel.add(recentLabel, BorderLayout.NORTH);
		rightPanel.setBackground(ColorManager.get(Themes.getCurrentTheme(), "background"));
		DefaultListModel<RecentEventListItem> model = new DefaultListModel<RecentEventListItem>();
	
		// create jlist model for recent events
		ArrayList<RecentEventListItem> recentEvents = getRecentEvents();
		for (RecentEventListItem s : recentEvents) 
			model.addElement(s);
		// add list model to list
		JList<RecentEventListItem> recents = new JList<RecentEventListItem>(model);
		recents.setCellRenderer(new RecentEventListItemRenderer());
		recents.setBackground(ColorManager.get(Themes.getCurrentTheme(), "background"));
		rightPanel.add(recents);
		JPanel lowerRight = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		// open button, only enabled when an event is selected
		JButton openConfirmation = new JButton("Open");
//		openConfirmation.setBorder(BorderFactory.createMatteBorder(6, 0, 0, 0,ColorManager.get(Themes.whiteBlue, "primary")));
		openConfirmation.setEnabled(false);
		openConfirmation.addActionListener(e -> {
			log.info("recent event "+recents.getSelectedValue() + " opened");
			try {
				sendEventAndClose(FileHandler.load(FileHandler.SAVE_DIR
						+ Event.saveFileName(recents.getSelectedValue().name)));
			} catch (ClassNotFoundException | IOException e1) {
				com.atcs.career.program.ErrorManager.processException(e1, "Error in opening event.", true, true);
			}
		});

		recents.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				openConfirmation.setEnabled(true);
			}
		});
		lowerRight.add(openConfirmation);
		lowerRight.setBackground(ColorManager.get(Themes.getCurrentTheme(), "secondary"));
		rightPanel.add(lowerRight, BorderLayout.SOUTH);
	}
	
	// -----------------------------------END INITIAL GUI CONFIG-----------------------------------------
	
	
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
