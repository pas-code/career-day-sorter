package com.atcs.career.ui.welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.atcs.career.resources.FontManager;
import com.atcs.career.ui.ColorManager;

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
		openSans = FontManager.finalFont(25f);
		topPanel = topPanelConfig();
		cb = new MenuCircleButton("New", ColorManager.get("welcome.button.background"), ColorManager.get("welcome.foreground"));
		lb = new MenuCircleButton("Open",ColorManager.get("welcome.button.background"), ColorManager.get("welcome.foreground"));
		configGui();
	}
	
	private void configGui()
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
		
		//Create New Event
		cb.addMouseListener(new MouseListener()
		        {

                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                    }

                    @Override
                    public void mousePressed(MouseEvent e)
                    {
                        pressed();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e)
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
		    
		        });
	}
	
	private void pressed()
	{
	    System.out.println("Pressed");
        f.removeAll();
        
        PropertiesPane p = new PropertiesPane();
        f.add(p);
        
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
