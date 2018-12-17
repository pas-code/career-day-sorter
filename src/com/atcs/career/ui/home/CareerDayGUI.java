package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.atcs.career.data.Event;
import com.atcs.career.data.GuiListable;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.resources.FontManager;
import com.atcs.career.ui.ColorManager;

//Jarrett Bierman & Edward Fominykh
//9/4/18
//Default JPanel Class (Copy and Paste)
public class CareerDayGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 1000;
	public static final int PREF_H = 700;
	private JPanel east, west;
	private JLabel title;
	private JTextArea info;
	private ArrayList<JButton> periods;
	private JTabbedPane tabs;
	private Font bigFont;
	private Font smallFont;
	private Event event;
	
	private InfoPanel selectedInfoPanel;

	public CareerDayGUI(Event event) {
		this.event = event;
		gui();
	}

	private void gui() {
		setFocusable(true);
		this.setLayout(new BorderLayout());
		bigFont = FontManager.finalFont(40f);
		smallFont = FontManager.finalFont(15f);
		new ArrayList<SessionInfoUtil>();
		layoutConfig();
		tabConfig();
//		makeWindow();
	}

	private void layoutConfig() {
		// title
		title = new JLabel("Event Scheduler", SwingConstants.CENTER);
		title.setFont(bigFont);
		this.add(title, BorderLayout.NORTH);
		// east panel
		east = new JPanel();
		east.setPreferredSize(new Dimension(200, 0));
		info = new JTextArea("A Dude\nA Student\nAnother Student");
		info.setFont(smallFont);
		east.add(info);
		this.add(east, BorderLayout.EAST);
		// west panel
		west = new JPanel(new GridLayout(0, 1));
		periods = new ArrayList<JButton>();
		for (int i = 1; i <= event.getAmountOfSessions(); i++) {
			JButton period = new JButton("Period " + i);
			period.setFont(smallFont);
			periods.add(period);
		}
		for (int i = 0; i < periods.size(); i++) {
			periods.get(i).getInsets().set(50, 50, 50, 50);
			west.add(periods.get(i));
		}
		this.add(west, BorderLayout.WEST);
	}
	
	// XXX this might break continuity... changes of the returned array will not affect the original. 
	private static ArrayList<GuiListable> toListable(ArrayList<?> arr) {
		ArrayList<GuiListable> ret = new ArrayList<GuiListable>();
		for (int i = 0; i < arr.size(); i++) 
			ret.add((GuiListable)arr.get(i));
		return ret;
	}

	private void tabConfig() {
		tabs = new JTabbedPane();
		tabs.setFont(smallFont);
		addTab(toListable(event.getSessions()));
		addTab(toListable(event.getStudents()));
		addTab(toListable(event.getRooms()));
		this.add(tabs, BorderLayout.CENTER);
	}

	/** Precondition: ArrayList contents must of type Gui_Listable */
	private void addTab(ArrayList<GuiListable> eventData) {
		// sessions panel
		JPanel ScrollBackPanel = new JPanel();
		ScrollBackPanel.setLayout(new BorderLayout());
		ScrollBackPanel.setBackground(Color.white);
		JPanel sessionPanelHolder = new JPanel();
		sessionPanelHolder.setLayout(new GridLayout(0, 1));
		ScrollBackPanel.add(sessionPanelHolder, BorderLayout.NORTH);

		for (int i = 0; i < eventData.size(); i++) {
			System.out
					.println("Added " + ((GuiListable) eventData.get(i)).getType());
			sessionPanelHolder.add(new InfoPanel((GuiListable) eventData.get(i), east));

		}

		JScrollPane sessionScroll = new JScrollPane(ScrollBackPanel);
		sessionScroll.getVerticalScrollBar().setUnitIncrement(10);
		sessionScroll.getVerticalScrollBar().setValue(1);
		tabs.addTab(((GuiListable) eventData.get(0)).getType(), sessionScroll);
	}

	public void makeWindow() {
		JFrame frame = new JFrame("Default JPanel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * This overrides the JPanel's getPreferredSize() method It tells the JPanel
	 * to be a certain width and height
	 */
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	public Event getEvent() {
		return event;
	}

//	public void setSelectedInfoPanel(InfoPanel ip)
//	{
//	    selectedInfoPanel.setSelected(false);
//	    ip.setSelected(true);
//	    selectedInfoPanel = ip;
//	}
	
	/**
	 * The main method runs your entire program It has the method
	 * createAndShowGUI() and runs it. This makes your whole program work.
	 */
	public static void main(String[] args) {

		CareerDayGUI program = new CareerDayGUI(new Event());
		program.makeWindow();
	}
	
}
