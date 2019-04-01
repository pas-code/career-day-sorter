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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atcs.career.data.Event;
import com.atcs.career.data.GuiListable;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.logic.Algorithms;
import com.atcs.career.program.MainClass;
import com.atcs.career.program.logging.BasicLogger;
import com.atcs.career.resources.FontManager;
import com.atcs.career.ui.home.info.MoreInfo;
import com.atcs.career.ui.home.info.MoreInfo.SideInfoPanel;

//Jarrett Bierman & Edward Fominykh
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

	/**
	 * 
	 */
	private static final BasicLogger mainLog;
	public static final BasicLogger changeLog;
	static {
		mainLog = BasicLogger.getLogger(CareerDayGUI.class.getName());
		changeLog = BasicLogger.getLogger("Change Log");
	}

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

	protected void refresh(String title) {
		// TODO make refresh more efficient
		int previouslySelectedIndex = tabs.getSelectedIndex();
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
		tabs.setTabComponentAt(tabIndexToRefresh, createTab(listInfoChanged));
//		tabs.removeAll();
//		tabConfig();
		tabs.setSelectedIndex(previouslySelectedIndex);
		centerSearch.setList(lists.get(tabs.getSelectedIndex()));
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
			public void actionPerformed(ActionEvent e) {
				sort();
			}
		});
		north.add(sort, BorderLayout.EAST);

		JButton addElem = new JButton("+");
		addElem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainLog.info("add elem");
				JPopupMenu popupMenu = new JPopupMenu();

				JMenuItem item = new JMenuItem("Add Session");
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Session s;
						if ((s = ElementCreator.createSession()) == null) return;
						event.getSessions().add(s);
						changeLog.info("Session added: " + s);
						refresh(s.getType());
					}
				});
				popupMenu.add(item);

				item = new JMenuItem("Add Student");
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Student add = ElementCreator
								.createStudent(event.getNumberOfPeriods());
						if (add == null) return;
						event.getMasterStudents().add(add);
						changeLog.info("Student added: " + add);
						refresh(add.getType());
					}
				});
				popupMenu.add(item);

				item = new JMenuItem("Add Room");
				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Room r;
						if ((r = ElementCreator.createRoom()) == null) return;
						event.getRooms().add(r);
						changeLog.info("Room added: " + r);
						refresh(r.getType());
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
		addTab(event.getSessions());
		addTab(event.getMasterStudents());
		addTab(event.getRooms());
	}

	/** Precondition: ArrayList contents must of type Gui_Listable */
	private void addTab(ArrayList<? extends GuiListable> eventData) {
		tabs.addTab(((GuiListable) eventData.get(0)).getType(), createTab(eventData));
	}
	
	private JScrollPane createTab(ArrayList<? extends GuiListable> eventData) {
		JPanel scrollBackPanel = new JPanel();
		scrollBackPanel.setLayout(new BorderLayout());
		scrollBackPanel.setBackground(Color.white);

		JList<GuiListable> infoList = new JList<GuiListable>(
				eventData.toArray(new GuiListable[eventData.size()]));
		infoList.setCellRenderer(new ListableRenderer(this));
		lists.add(infoList);

		scrollBackPanel.add(infoList, BorderLayout.NORTH);

		JScrollPane sessionScroll = new JScrollPane(scrollBackPanel);
		sessionScroll.getVerticalScrollBar().setUnitIncrement(10);
		sessionScroll.getVerticalScrollBar().setValue(1);
		return sessionScroll;
	}

	public JFrame makeWindow() {
		JFrame frame = new JFrame(MainClass.APP_NAME);
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
		System.out.println("change period to " + periodIndex);
		this.selectedPeriod = periodIndex;
		if (infoPanel != null) infoPanel.refresh();
	}

	/**
	 * @return the selectedPeriod
	 */
	public byte getSelectedPeriod() {
		return selectedPeriod;
	}

	/**
	 * @param selectedPeriod
	 *           the selectedPeriod to set
	 */
	public void setSelectedPeriod(byte selectedPeriod) {
		this.selectedPeriod = selectedPeriod;
	}

}
