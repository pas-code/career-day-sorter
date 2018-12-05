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

//Edward Fominykh
//Program Description
//Nov 26, 2018
public class SessionInfoUtil extends InfoPanel
{

    public SessionInfoUtil(Session s)
    {
        super(s.getTitle(), s.getSpeaker(), new int[]{s.getStudents().size(), 25});
    }
    
}
