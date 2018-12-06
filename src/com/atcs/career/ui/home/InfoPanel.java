//Edward Fominykh
//Program Description
//Dec 3, 2018

package com.atcs.career.ui.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.atcs.career.data.Session;
import com.atcs.career.resources.FontManager;

public abstract class InfoPanel extends JPanel
{   
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel optionPanel;
    private JLabel titleLabel;
    private JLabel numAttending;
    private JPanel moreInfoPanel;
    private String title, info1,info2;
    private LineBorder lineBorder;
    private TitledBorder titledBorder;

    public InfoPanel(String title, String info1, String info2)
    {
        this.title = title;
        this.info1 = info1;
        this.info2 = info2;
        initializePanels();
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
        optionPanel = new JPanel();
        titleLabel = new JLabel("     " + info1);
        numAttending = new JLabel("     " + info2);
        this.setPreferredSize(new Dimension(0, 100));
        lineBorder = new LineBorder(Color.gray, 1, true);
        titledBorder = new TitledBorder(lineBorder, title, TitledBorder.LEFT,
                TitledBorder.DEFAULT_POSITION, FontManager.finalFont(15f));
        this.setBorder(titledBorder);
        panelConfig();
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

    private void optionPanelConfig()
    {
    }
}
