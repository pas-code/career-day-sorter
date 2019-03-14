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
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atcs.career.data.Event;
import com.atcs.career.data.GuiListable;
import com.atcs.career.resources.FontManager;

//Jarrett Bierman & Edward Fominykh
//9/4/18

/*
 * TODO 
 * sort button
 * sort students
 * search students
 * save button
 * session names??
 * ui between different panels
 */


public class CareerDayGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 1000;
	public static final int PREF_H = 700;
	private byte selectedPeriod = 1;
	private byte numberOfPeriods;
	private GuiListable listed;
	private ArrayList<JList<GuiListable>> lists;
	private JPanel centerPanel;
	private SearchBar<GuiListable> centerSearch;
	private JTabbedPane tabs;
	private Font bigFont;
	private JPanel eastPanel;
	private Font smallFont;
	private Event event;
	
	public CareerDayGUI(Event event, byte numberOfPeriods) {
        this.event = event;
        this.numberOfPeriods = numberOfPeriods;
        gui();
    }
	
	public CareerDayGUI(Event event) {
		this.event = event;
		this.numberOfPeriods = event.getNumberOfPeriods();
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

//	private void refresh()
//	{
//	    
//	}
	
	private void layoutConfig() {
		// title
		JLabel title = new JLabel("Event Scheduler", SwingConstants.CENTER);
		title.setFont(bigFont);
		this.add(title, BorderLayout.NORTH);
		
		// east panel
		eastPanel = new JPanel();
		eastPanel.setPreferredSize(new Dimension(200, 0));
		this.add(eastPanel, BorderLayout.EAST);
		
		// center panel
		centerPanel = new JPanel(new BorderLayout());
		centerSearch = new SearchBar<GuiListable>();
		centerPanel.add(centerSearch, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		
		// west panel
		JPanel west = new JPanel(new GridLayout(0, 1));
		west.setPreferredSize(new Dimension(100,0));
		ArrayList<JButton> periods = new ArrayList<JButton>();
		for (int i = 1; i < numberOfPeriods; i++) {
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

	public void tabConfig() {
		tabs = new JTabbedPane();
		lists = new ArrayList<JList<GuiListable>>();
		tabs.setFont(smallFont);
		addTab(event.getSessions());
		addTab(event.getStudents());
		addTab(event.getRooms());
		
		centerSearch.setList(lists.get(0), true);
		
		tabs.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            System.out.println("Tab: " + tabs.getSelectedIndex());
	            centerSearch.setList(lists.get(tabs.getSelectedIndex()));
	        }
	    });
		centerPanel.add(tabs, BorderLayout.CENTER);
	}

	/** Precondition: ArrayList contents must of type Gui_Listable */
	private void addTab(ArrayList<?> eventData) {
		// sessions panel
		JPanel scrollBackPanel = new JPanel();
		scrollBackPanel.setLayout(new BorderLayout());
		scrollBackPanel.setBackground(Color.white);

		JList<GuiListable> infoList = new JList<GuiListable>(eventData.toArray(new GuiListable[eventData.size()]));
		infoList.setCellRenderer(new ListableRenderer(this));
		lists.add(infoList);
		
		scrollBackPanel.add(infoList, BorderLayout.NORTH);

		JScrollPane sessionScroll = new JScrollPane(scrollBackPanel);
		sessionScroll.getVerticalScrollBar().setUnitIncrement(10);
		sessionScroll.getVerticalScrollBar().setValue(1);
		System.out.println(eventData);
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
	
	public void setMoreInfo(GuiListable g) {
		if (g.equals(listed))
			return;
		eastPanel.removeAll();
		eastPanel.add(MoreInfo.getInfoPanel(g, this));
	}

//	public void setSelectedInfoPanel(InfoPanel ip)
//	{
//	    selectedInfoPanel.setSelected(false);
//	    ip.setSelected(true);
//	    selectedInfoPanel = ip;
//	}
	

	/**
     * @return the selectedPeriod
     */
    public byte getSelectedPeriod() {
        return selectedPeriod;
    }

    /**
     * @param selectedPeriod the selectedPeriod to set
     */
    public void setSelectedPeriod(byte selectedPeriod) {
        this.selectedPeriod = selectedPeriod;
    }
	
}
