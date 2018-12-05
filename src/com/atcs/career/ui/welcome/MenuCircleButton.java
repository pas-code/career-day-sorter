package com.atcs.career.ui.welcome;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//Edward Fominykh
//Program Description
//Nov 16, 2018
public class MenuCircleButton extends JPanel
{
    private final int strokeSize = 2;
    private String text;
    private Color background;
    private Color border;
    private Font openSans = FontManager.getOpenSans(25);
    private RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    private JLabel label1;
    private int w;
    private int h;
    
    public MenuCircleButton(String text, Color background, Color border)
    {
        super();
        this.text = text;
//        this.text2 = text2;
        this.background = background;
        this.border = border;
        
        label1 = new JLabel(text, SwingConstants.CENTER);
        setOpaque(false);
        gui();
    }
    
    private void gui()
    {
        
        label1.setFont(openSans);
        label1.setForeground(Color.white);
        label1.setAlignmentX(CENTER_ALIGNMENT);
        label1.setAlignmentY(CENTER_ALIGNMENT);
        
//        label1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        this.setLayout(new BorderLayout());
        this.add(label1, BorderLayout.CENTER);
        
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(hints);
        w = getWidth();
        h = getHeight();
        System.out.println(label1.getWidth());
//        label1.setBorder(BorderFactory.createEmptyBorder(w/2-label1.getWidth()/2,w/2-label1.getWidth()/2, h/2-label1.getWidth()/2, h/2-label1.getWidth()/2));
        int gap = 25;
//        int awh = 10;
        g2.setColor(background);
        g2.fillOval(gap, gap, Integer.min(w, h)-gap*2, Integer.min(w, h)-gap*2);
        g2.setColor(border);
        g2.setFont(openSans);
//        g2.drawString(text, 40, 65);
//        g2.drawString(text2, 40, 95);
//        g2.drawString(text, 1, 1);
    }
    
    public void mousePress()
    {
        background.darker();
    }
    
//    public static void mainn(String[] args)
//    {
//        JFrame f = new JFrame();
//        JPanel p = new JPanel();
//        f.add(p);
//        
//        p.setBackground(Color.gray);
//        MenuCircleButton mb = new MenuCircleButton("Create", "Event", Color.lightGray, Color.darkGray);
//        mb.addMouseListener(new MouseListener()
//                {
//
//                    @Override
//                    public void mouseClicked(MouseEvent e)
//                    {
//                    }
//
//                    @Override
//                    public void mousePressed(MouseEvent e)
//                    {
//                        mb.mousePress();
//                        System.out.println("Press");
//                    }
//
//                    @Override
//                    public void mouseReleased(MouseEvent e)
//                    {
//                    }
//
//                    @Override
//                    public void mouseEntered(MouseEvent e)
//                    {
//                    }
//
//                    @Override
//                    public void mouseExited(MouseEvent e)
//                    {
//                    }
//            
//                });
//        mb.setPreferredSize(new Dimension(190,120));
//        p.add(mb);
//        f.setVisible(true);
//        f.setSize(400, 500);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        f.setLayout(null);
//        
//    }
    

}
