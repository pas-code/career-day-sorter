package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atcs.career.data.Event;
import com.atcs.career.data.GuiListable;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.logic.Algorithms;
import com.atcs.career.program.CareerDay;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.resources.FontManager;
import com.atcs.career.ui.home.info.MoreInfo;
import com.atcs.career.ui.home.info.MoreInfo.SideInfoPanel;

//Jarrett Bierman & Edward Fominykh & Thomas Varano
//9/4/18

public class CareerDayGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int PREF_W = 1000;
	public static final int PREF_H = 700;
	private byte selectedPeriod = 0;
	private byte numberOfPeriods;
	private GuiListable listed;
	private SideInfoPanel infoPanel;
	private ArrayList<JList<GuiListable>> lists;
	private SearchBar<GuiListable> centerSearch;
	private JTabbedPane tabs;
	private JPanel eastPanel;
 
	private static final BasicLogger	mainLog = BasicLogger.getLogger(CareerDayGUI.class.getName());

	private Font bigFont;
	private Font smallFont;

	private Event event;

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
		tabs = new JTabbedPane();
		tabConfig();
		layoutConfig();
	}

	public static final String REFRESH_ALL = "all";
	public void refresh(String title) {
		int previouslySelectedIndex = tabs.getSelectedIndex();
		if (title.equals(REFRESH_ALL)) {
			tabs.removeAll();
			tabConfig();
		} else {
			int tabIndexToRefresh = -1;
			for (int i = 0; i < tabs.getTabCount(); i++)
				if (tabs.getTitleAt(i).equals(title)) {
					tabIndexToRefresh = i;
					break;
				}
			ArrayList<? extends GuiListable> listInfoChanged;
			if (tabIndexToRefresh == 0)
				listInfoChanged = event.getSessions();
			else if (tabIndexToRefresh == 1)
				listInfoChanged = event.getMasterStudents();
			else
				listInfoChanged = event.getRooms();
			JScrollPane newTab = createTab(listInfoChanged, false);
//			lists.set(tabs.getSelectedIndex(), (JList<GuiListable>) newTab.getViewport().getView());
			tabs.setComponentAt(tabIndexToRefresh, newTab);
		}
		tabs.setSelectedIndex(previouslySelectedIndex);
		String previousSearchQuery = centerSearch.getText();
		centerSearch.setList(lists.get(tabs.getSelectedIndex()));
		centerSearch.setText(previousSearchQuery);
		System.out.println("list?" + lists.get(tabs.getSelectedIndex()).hashCode());
		revalidate();
	}

	private void sort() {
		// make sure they definitely want to do it.
		boolean confirmation = JOptionPane.showConfirmDialog(null,
				"Running the sorting algorithm will remove all previous assignments."
						+ "\nAre you sure you want to continue?",
				"Confirm Sort", JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE, null) == 0;
		if (confirmation) Algorithms.sort(event);

		mainLog.info("completed sort\n\n");
	}

	private void layoutConfig() {
		// title
		JPanel north = new JPanel(new BorderLayout());
		JLabel title = new JLabel("Event Scheduler", SwingConstants.CENTER);
		title.setFont(bigFont);
		north.add(title, BorderLayout.CENTER);
		JButton sort = new JButton("Run Algorithm");
		sort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sort();
				SwingUtilities.getWindowAncestor(eastPanel).requestFocus();
				System.out.println("done");
			}
		});
		north.add(sort, BorderLayout.EAST);

		JButton addElem = new JButton("+");
		addElem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainLog.info("add elem");
				JPopupMenu popupMenu = new JPopupMenu();

				JMenuItem item = new JMenuItem("Add Session");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Session s;
						if ((s = ElementCreator.createSession(event)) == null) return;
						event.getSessions().add(s);
						CareerDay.changeLog.info("Session added: " + s);
						refresh("Sessions");
					}
				});
				popupMenu.add(item);

				item = new JMenuItem("Add Student");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Student add = ElementCreator
								.createStudent(event.getNumberOfPeriods());
						if (add == null) return;
						event.getMasterStudents().add(add);
						CareerDay.changeLog.info("Student added: " + add);
						refresh("Students");
					}
				});
				popupMenu.add(item);

				item = new JMenuItem("Add Room");
				item.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Room r;
						if ((r = ElementCreator.createRoom(event.getNumberOfPeriods())) == null) return;
						event.getRooms().add(r);
						CareerDay.changeLog.info("Room added: " + r);
						refresh("Rooms");
					}
				});
				popupMenu.add(item);
				popupMenu.show(addElem, addElem.getWidth() - 25,
						addElem.getHeight() - 5);
			}
		});
		north.add(addElem, BorderLayout.WEST);
		this.add(north, BorderLayout.NORTH);

		// east panel
		eastPanel = new JPanel();
		this.add(eastPanel, BorderLayout.EAST);

		// center panel
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerSearch = new SearchBar<GuiListable>();
		centerSearch.setList(lists.get(0), true);

		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				mainLog.config("tab changed to " + tabs.getSelectedIndex());
				if (tabs.getSelectedIndex() != -1)
					centerSearch.setList(lists.get(tabs.getSelectedIndex()));
			}
		});
		centerPanel.add(centerSearch, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);

		// west panel
		JPanel west = new JPanel(new GridLayout(0, 1));
		west.setPreferredSize(new Dimension(100, 0));
		for (int i = 0; i < numberOfPeriods; i++) {
			JButton period = new JButton("Period " + (i + 1));
			// it didn't work without this. leave it
			int perInd = i;
			period.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					changePeriod((byte) perInd);
				}
			});
			period.getInsets().set(50, 50, 50, 50);
			period.setFont(smallFont);
			west.add(period);
		}
		this.add(west, BorderLayout.WEST);
		centerPanel.add(tabs, BorderLayout.CENTER);
	}

	public void tabConfig() {
		lists = new ArrayList<JList<GuiListable>>();
		tabs.setFont(smallFont);
		addTab(event.getSessions(), "Sessions");
		addTab(event.getMasterStudents(), "Students");
		addTab(event.getRooms(), "Rooms");
	}

	/** Precondition: ArrayList contents must of type Gui_Listable */
	private void addTab(ArrayList<? extends GuiListable> eventData, String title) {
		tabs.addTab(title, createTab(eventData, true));
	}
	
	private JScrollPane createTab(ArrayList<? extends GuiListable> eventData, boolean addToList) {
		JPanel scrollBackPanel = new JPanel();
		scrollBackPanel.setLayout(new BorderLayout());
		scrollBackPanel.setBackground(Color.white);
		
		GuiListable[] listData = eventData.toArray(new GuiListable[eventData.size()]);
		Arrays.sort(listData, GuiListable.listSorter());
		
		JList<GuiListable> infoList = new JList<GuiListable>(listData);
		infoList.setCellRenderer(new ListableRenderer(this));
		if (addToList)
			lists.add(infoList);

		scrollBackPanel.add(infoList, BorderLayout.NORTH);

		JScrollPane sessionScroll = new JScrollPane(scrollBackPanel);
		sessionScroll.getVerticalScrollBar().setUnitIncrement(10);
		sessionScroll.getVerticalScrollBar().setValue(1);
		return sessionScroll;
	}

	public JFrame makeWindow() {
		JFrame frame = new JFrame(CareerDay.APP_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return frame;
	}

	/**
	 * This overrides the JPanel's getPreferredSize() method It tells the JPanel
	 * to be a certain width and height
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	public Event getEvent() {
		return event;
	}

	public void setMoreInfo(GuiListable g) {
		if (listed != null && g.equals(listed)) return;
		eastPanel.removeAll();
		MoreInfo.SideInfoPanel info = MoreInfo.getInfoPanel(g, this);
		eastPanel.add(info);
		infoPanel = info;
		listed = g;
		eastPanel.revalidate();
	}

	public void changePeriod(byte periodIndex) {
		CareerDay.changeLog.info("change period to " + periodIndex);
		this.selectedPeriod = periodIndex;
		event.setCurrentPeriod(selectedPeriod);
		if (infoPanel != null) infoPanel.refresh();
		refresh(tabs.getTitleAt(tabs.getSelectedIndex()));
	}

	/**
	 * @return the selectedPeriod
	 */
	public byte getCurrentPeriod() {
		return selectedPeriod;
	}

	/**
	 * @param selectedPeriod
	 *           the selectedPeriod to set
	 */
	public void setSelectedPeriod(byte selectedPeriod) {
		this.selectedPeriod = selectedPeriod;
	}
	
	public JTabbedPane getTabs() {
		return tabs;
	}
	
	public ArrayList<JList<GuiListable>> getLists() {
		return lists;
	}

}
