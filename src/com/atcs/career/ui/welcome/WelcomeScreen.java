import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class WelcomeScreen extends JPanel{
	
	private JFrame f;
	private JPanel topPanel;
	private JPanel mainPanel;
	private JPanel leftPanel;
	private Font openSans;
	
	private MenuCircleButton cb, lb;
	
	private JLabel title;
	
	public WelcomeScreen(String programName)
	{
		f = new JFrame("PasCode");
		mainPanel = new JPanel();
		leftPanel = new JPanel();
		title = new JLabel("   " + programName);
		openSans = FontManager.getOpenSans(25f);
		topPanel = topPanelConfig();
		cb = new MenuCircleButton("New", Color.darkGray,Color.lightGray);
		lb = new MenuCircleButton("Open",  Color.darkGray, Color.lightGray);
		gui();
	}
	
	private void gui()
	{
		//Frame config
		f.setSize(600, 350);
		f.setLocationRelativeTo(null);
		f.add(this);
		
		//Class Panel config
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.setBackground(Color.gray);
		
		//Left Panel config
		leftPanel.setLayout(new GridLayout());
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.black));
		leftPanel.setPreferredSize(new Dimension(150,0));
		leftPanel.setLayout(new GridLayout(2,0));
		leftPanel.setBackground(Color.gray);
		leftPanel.add(cb);
		leftPanel.add(lb);
		
		this.add(leftPanel, BorderLayout.WEST);
		
		f.setVisible(true);
	}
	
	private JPanel topPanelConfig()
	{
		JPanel t = new JPanel();
		t.setPreferredSize(new Dimension(0,50));
		t.setBackground(Color.black);
		t.setLayout(new BorderLayout());
		
		title.setForeground(Color.white);
		title.setFont(openSans);
		
		t.add(title, BorderLayout.WEST);
		return t;
	}
	
	public static void main(String args[])
	{
		new WelcomeScreen("Event Scheduler");
	}
}
