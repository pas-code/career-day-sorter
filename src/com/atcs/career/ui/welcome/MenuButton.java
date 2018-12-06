package com.atcs.career.ui.welcome;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.atcs.career.resources.FontManager;

//Edward Fominykh
//Program Description
//Nov 15, 2018
public class MenuButton extends JPanel
{
	private static final long serialVersionUID = 1L;
	private RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    private final int strokeSize = 2;
    private String text;
    private Color background;
    private Color border;
    private Font openSans = FontManager.finalFont(20);
    
    public MenuButton(String text, Color background, Color border)
    {
        super();
        this.text = text;
        this.background = background;
        this.border = border;
        setOpaque(false);
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        int gap = 50;
        int awh = 10;
        g2.setRenderingHints(hints);
        g2.setColor(background);
        g2.fillRoundRect(gap, gap, w-gap*2, h - gap*2, awh, awh);
        g2.setColor(border);
        g2.setStroke(new BasicStroke(strokeSize));
        g2.drawRoundRect(gap, gap, w-gap*2, h - gap*2, awh, awh);
        g2.setFont(openSans);
        g2.drawString(text, 10, h-11);
//        g2.drawString(text, 1, 1);
    }
    
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        f.add(p);
        p.setBackground(Color.gray);
        MenuButton mb = new MenuButton("Create New Event", Color.lightGray, Color.darkGray);
        mb.setPreferredSize(new Dimension(190,40));
        p.add(mb);
        f.setVisible(true);
        f.setSize(200, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.setLayout(null);
        
    }
    
}
