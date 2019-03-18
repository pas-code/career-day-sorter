package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atcs.career.data.Event;
import com.atcs.career.data.GuiListable;
import com.atcs.career.logic.Algorithms;
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
	
//	public CareerDayGUI(Event event, byte numberOfPeriods) {
//        this.event = event;
//        this.numberOfPeriods = numberOfPeriods;
//        gui();
//    }
	
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
		layoutConfig();
		tabConfig();
	}

	private void refresh() {
	    
	}
	
	private void sort() {
		// make sure they definitiely want to do it.
		boolean confirmation = JOptionPane.showConfirmDialog(null, 
				"Running the sorting algorithm will remove all previous assignments."
				+ "\nAre you sure you want to continue?", "Confirm Sort", JOptionPane.YES_NO_OPTION, 
				JOptionPane.WARNING_MESSAGE, null) == 0;
		if (confirmation)
			Algorithms.sort(event);
		System.out.println("completed sort");
	}
	
	
	private void layoutConfig() {
		// title
		JPanel north = new JPanel(new BorderLayout());
		JLabel title = new JLabel("Event Scheduler", SwingConstants.CENTER);
		title.setFont(bigFont);
		north.add(title, BorderLayout.CENTER);
		JButton sort = new JButton("Run Algorithm");
		sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sort();
			}
		});
		north.add(sort, BorderLayout.EAST);
		this.add(north, BorderLayout.NORTH);
		
		// east panel
		eastPanel = new JPanel();
//		eastPanel.setPreferredSize(new Dimension(230, 0));
		this.add(eastPanel, BorderLayout.EAST);
		
		// center panel
		centerPanel = new JPanel(new BorderLayout());
		centerSearch = new SearchBar<GuiListable>();
		centerPanel.add(centerSearch, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		
		// west panel
		JPanel west = new JPanel(new GridLayout(0, 1));
		west.setPreferredSize(new Dimension(100,0));
		for (int i = 0; i < numberOfPeriods; i++) {
			JButton period = new JButton("Period " + i + 1);
			// it didn't work without this. leave it
			int perInd = i;
			period.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 changePeriod(perInd);
				}
			});
			period.getInsets().set(50, 50, 50, 50);
			period.setFont(smallFont);
			west.add(period);
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
		if (listed != null && g.equals(listed))
			return;
		eastPanel.removeAll();
		eastPanel.add(MoreInfo.getInfoPanel(g, this));
		listed = g;
		eastPanel.revalidate();
	}

	public void changePeriod(int periodIndex) {
		System.out.println("change period to "+periodIndex);
	}
	
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
