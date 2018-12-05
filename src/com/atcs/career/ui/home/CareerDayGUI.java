package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
import javax.swing.SwingUtilities;

import com.atcs.career.data.Event;
import com.atcs.career.data.Session;
import com.atcs.career.resources.FontManager;

//Jarrett Bierman
//9/4/18
//Default JPanel Class (Copy and Paste)
public class CareerDayGUI extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
    private static final long serialVersionUID = 1L;
    public static final int PREF_W = 1000;
    public static final int PREF_H = 700;
    public JPanel east, west;
    public JPanel ScrollBackPanel;
    public JList studentList, classroomList;
    public JScrollPane sessionScroll, studentScroll, classroomScroll;
    public JLabel title;
    public JTextArea info;
    public ArrayList<JButton> periods;
    public JTabbedPane tabs;
    public int numOfPeriods = 3;
    private Font bigFont;
    private Font smallFont;
    // public ArrayList<String> exampleSessions;
    // public ArrayList<String> exampleStudents;
    // public ArrayList<String> exampleClassrooms;
    private JPanel sessionPanelHolder;
    private ArrayList<SessionInfoUtil> sessionPanels;
    private static Event event;

    public CareerDayGUI()
    {
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
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
        for (int i = 1; i <= numOfPeriods; i++)
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
        ScrollBackPanel = new JPanel();
        ScrollBackPanel.setLayout(new BorderLayout());
        ScrollBackPanel.setBackground(Color.white);
        sessionPanelHolder = new JPanel();
        sessionPanelHolder.setLayout(new GridLayout(0, 1));
        ScrollBackPanel.add(sessionPanelHolder, BorderLayout.NORTH);
//        for (int i = 0; i < 10; i++)
        
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Business", "Donald Trump")));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Investment", "Warren Buffet")));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Military", "James Mattis")));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Electrical Engineering", "Elon Musk")));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Astronomy", "Albert Einstein")));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Criminal Defense", "Robert Shapiro")));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Intelligence", "James Comey")));
            sessionPanelHolder.add(new SessionInfoUtil(new Session("Software Development", "Johnny Ive")));
            
        sessionScroll = new JScrollPane(ScrollBackPanel);
        tabs.addTab("Sessions", sessionScroll);
        // students panel
        studentList = new JList();
        studentList.setFont(smallFont);
        studentScroll = new JScrollPane(studentList);
        tabs.addTab("Students", studentScroll);
        // classroom panel
        classroomList = new JList();
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
     * The paintComponent is a method that is inherited from the JPanel class.
     * Writing code in here overrides the method This is where you draw shapes,
     * pictures, and objects
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
    }

    /**
     * keyPressed does something when you press a key on a keyboard Every key
     * has a unique int value (and can access with e.getKeyCode() you can choose
     * which specific key with an if statement (shown below)
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_E)
            System.out.println("I am pressing the 'E' key");
    }

    /**
     * keyReleased is the same as keyPressed, but it does something when you
     * release a key
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * mousePressed does a think when you press your mouse down you can also
     * access the location of the mouse through the methods e.getX() and
     * e.getY()
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
    }

    /**
     * This overrides the JPanel's getPreferredSize() method It tells the JPanel
     * to be a certain width and height
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(PREF_W, PREF_H);
    }

    /**
     * The main method runs your entire program It has the method
     * createAndShowGUI() and runs it. This makes your whole program work.
     */
    public static void main(String[] args)
    {
//        event = new Event("Career Day 2018");
        
        new CareerDayGUI();
    }
}
