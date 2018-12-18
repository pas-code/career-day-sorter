//Edward Fominykh
//Program Description
//Dec 3, 2018

package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.atcs.career.data.Event;
import com.atcs.career.data.GuiListable;
import com.atcs.career.data.Room;
import com.atcs.career.data.Session;
import com.atcs.career.data.Student;
import com.atcs.career.resources.FontManager;

public class InfoPanel extends JPanel implements MouseListener
{   
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JLabel titleLabel;
    private JLabel numAttending;
    private String title, info1,info2;
    private LineBorder lineBorder;
    private TitledBorder titledBorder;
    private JPanel moreInfoHolder;
    private MoreInfo.SideInfoPanel moreInfo;
    private GuiListable gl;
    private Event event;
//    private boolean isSelected;
//    private CareerDayGUI home;

    public InfoPanel(String title, String info1, String info2, Event e)
    {
        this.title = title;
        this.info1 = info1;
        this.info2 = info2;
        event = e;
        initializePanels();
    }
    
    public InfoPanel(GuiListable gl, JPanel moreInfoHolder)
    {
        this.gl = gl;
        title = gl.getTitle();
        info1 = gl.getInfo(0);
        info2 = gl.getInfo(1);
        this.moreInfoHolder = moreInfoHolder;
        initializePanels();
        initMoreInfo();
    }
    
    public void initMoreInfo()
    {
        int type = gl.getTypeNum();
       if(type == 0)
           moreInfo = new MoreInfo.SessionPanel(event, (Session) gl);
       else if(type == 1)
           moreInfo = new MoreInfo.StudentPanel(event, (Student) gl);
       else if(type == 2)
           moreInfo = new MoreInfo.RoomPanel(event, (Room) gl);
       else 
       {
           System.out.println("Did not initialize panels");
           return;
       }
       System.out.println("Initialized More info panels");
    }
    
    public void select()
    {
        System.out.println("Selected");
        moreInfoHolder.removeAll();
        moreInfoHolder.add(moreInfo);
        moreInfoHolder.setVisible(true);
        moreInfoHolder.revalidate();
        moreInfoHolder.repaint();
        Component[] c = this.getParent().getComponents();
        for(Component component : c)
        {
            InfoPanel infoPanel = (InfoPanel) component;
            infoPanel.configBorder(1);
        }
        this.configBorder(3);
    }
    
   /**Precondition: Integer Array numInfo must have a size of two*/
    public InfoPanel(String title, String info1, int[] numInfo)
    {
        this.title = title;
        this.info1 = info1;
        this.info2 = numInfo[0] + "/" + numInfo[1];
        initializePanels();
    }
    
    private void initializePanels()
    {
        titlePanel = new JPanel();
        infoPanel = new JPanel();
        titleLabel = new JLabel("     " + info1);
        numAttending = new JLabel("     " + info2);
        this.setPreferredSize(new Dimension(0, 100));
        configBorder(1);
        panelConfig();
        this.addMouseListener(this);
    }
    
    public void configBorder(int size)
    {
        lineBorder = new LineBorder(Color.gray, size, true);
        titledBorder = new TitledBorder(lineBorder, title, TitledBorder.LEFT,
                TitledBorder.DEFAULT_POSITION, FontManager.finalFont(15f));
        this.setBorder(titledBorder);
    }
    
    public void refresh()
    {
        titledBorder.setTitle(title);
        titleLabel.setText("     "+info1);
        numAttending.setText("     " + info2);
        revalidate();
        setVisible(true);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getInfo1()
    {
        return info1;
    }

    public void setInfo1(String info1)
    {
        this.info1 = info1;
    }

    public String getInfo2()
    {
        return info2;
    }

    public void setInfo2(String info2)
    {
        this.info2 = info2;
    }

    private void panelConfig()
    {
        this.setLayout(new GridLayout(0, 3));
        titlePanelConfig();
        infoPanelConfig();
        optionPanelConfig();
    }

    private void titlePanelConfig()
    {
        this.add(titlePanel);
        titlePanel.setPreferredSize(new Dimension(200, 0));
        titlePanel.setLayout(new BorderLayout());
        titleLabel.setFont(FontManager.finalFont(15f));
        titlePanel.add(titleLabel, BorderLayout.WEST);
    }

    private void infoPanelConfig()
    {
        this.add(infoPanel);
        numAttending.setFont(FontManager.finalFont(25f));
        infoPanel.setLayout(new BorderLayout());
        infoPanel.add(numAttending, BorderLayout.WEST);
    }
    
//    /**Only called from outside the class*/
//    public void setSelected(boolean s)
//    {
//        isSelected = s;
//    }

    private void optionPanelConfig()
    {
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        select();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
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
}
