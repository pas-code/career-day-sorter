package com.atcs.career.ui.home;

import java.awt.BorderLayout;
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
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.resources.FontManager;
import com.atcs.career.ui.ColorManager;

//Jarrett Bierman
//9/4/18
//Default JPanel Class (Copy and Paste)
public class CareerDayGUI extends JPanel 
{
    private static final long serialVersionUID = 1L;
    public static final int PREF_W = 1000;
    public static final int PREF_H = 700;
    private JPanel east, west;
    private JPanel scrollBackPanel;
    private JList<Student> studentList;
    private JList<Room> classroomList;
    private JScrollPane sessionScroll, studentScroll, classroomScroll;
    private JLabel title;
    private JTextArea info;
    private ArrayList<JButton> periods;
    private JTabbedPane tabs;
    private Font bigFont;
    private Font smallFont;
    private JPanel sessionPanelHolder; 
    private ArrayList<SessionInfoUtil> sessionPanels;
    private Event event;

    private final boolean testing = true;

    public CareerDayGUI(Event masterEvent)
    {
   	 this.event = masterEvent;
        setFocusable(true);
        this.setLayout(new BorderLayout());
        bigFont = FontManager.finalFont(40f);
        smallFont = FontManager.finalFont(15f);
        // this.event = event;
        sessionPanels = new ArrayList<SessionInfoUtil>();
        layoutConfig();
        tabConfig();
        makeWindow();
    }

    private void layoutConfig()
    {
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
        for (int i = 1; i <= event.getAmountOfSessions(); i++)
        {
            JButton period = new JButton("Period " + i);
            period.setFont(smallFont);
            periods.add(period);
        }
        for (int i = 0; i < periods.size(); i++)
        {
            periods.get(i).getInsets().set(50, 50, 50, 50);
            west.add(periods.get(i));
        }
        this.add(west, BorderLayout.WEST);
    }

    private void tabConfig()
    {
        // center panel
        tabs = new JTabbedPane();
        // sessions panel
        scrollBackPanel = new JPanel();
        scrollBackPanel.setLayout(new BorderLayout());
        scrollBackPanel.setBackground(ColorManager.get("main.scroll.background"));
        sessionPanelHolder = new JPanel();
        sessionPanelHolder.setLayout(new GridLayout(0, 1));
        scrollBackPanel.add(sessionPanelHolder, BorderLayout.NORTH);
//        for (int i = 0; i < 10; i++)
        if (testing) {
      	  final int numSessions = event.getAmountOfSessions();
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Business", "Donald Trump",numSessions)));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Investment", "Warren Buffet", numSessions)));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Military", "James Mattis", numSessions)));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Electrical Engineering", "Elon Musk", numSessions)));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Astronomy", "Albert Einstein", numSessions)));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Criminal Defense", "Robert Shapiro", numSessions)));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Intelligence", "James Comey", numSessions)));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Software Development", "Johnny Ive", numSessions)));
        }
        
        sessionScroll = new JScrollPane(scrollBackPanel);
        tabs.addTab("Sessions", sessionScroll);
        // students panel
        studentList = new JList<Student>();
        studentList.setFont(smallFont);
        studentScroll = new JScrollPane(studentList);
        tabs.addTab("Students", studentScroll);
        // classroom panel
        classroomList = new JList<Room>();
        classroomList.setFont(smallFont);
        classroomScroll = new JScrollPane(classroomList);
        tabs.addTab("Classrooms", classroomScroll);
        tabs.setFont(smallFont);
        this.add(tabs, BorderLayout.CENTER);
    }

    private void makeWindow()
    {
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
    public Dimension getPreferredSize()
    {
        return new Dimension(PREF_W, PREF_H);
    }
    
    public Event getEvent() {
   	 return event;
    }

    /**
     * The main method runs your entire program It has the method
     * createAndShowGUI() and runs it. This makes your whole program work.
     */
    public static void main(String[] args)
    {
        
        new CareerDayGUI(new Event("Career Day 2018"));
    }
}
