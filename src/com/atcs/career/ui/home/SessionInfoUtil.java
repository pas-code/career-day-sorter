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

//Edward Fominykh
//Program Description
//Nov 26, 2018
public class SessionInfoUtil extends JPanel
{
    
    
    private Session session;
    private JPanel titlePanel;
    private JPanel infoPanel;
    private JPanel optionPanel;
    private JLabel titleLabel;
    private JLabel numAttending;
    private JPanel moreInfoPanel;

    public SessionInfoUtil(Session s)
    {
        session = s;
        titlePanel = new JPanel();
        infoPanel = new JPanel();
        optionPanel = new JPanel();
        titleLabel = new JLabel("     " + session.getSpeaker());
        numAttending = new JLabel("     " + session.getStudents().size()+"/25");
        this.setPreferredSize(new Dimension(0, 100));
        LineBorder lineBorder = new LineBorder(Color.gray, 1, true);
        TitledBorder titledBorder = new TitledBorder(lineBorder, session.getTitle(), TitledBorder.LEFT,
                TitledBorder.DEFAULT_POSITION, FontManager.finalFont(15f));
        this.setBorder(titledBorder);
        panelConfig();
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
        titleLabel.setFont(FontManager.getOpenSans(15f));
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
