//Thomas Varano
//Dec 19, 2018

package com.atcs.career.program;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class MenuBarHandler {
	
	public static MenuBar configMenuBar() {
		
		/*
		 * XXX IS ONLY SUPPORTED IN JAVA 9
		 * 
		 * configures preferences and "about" calls from the menu bar.
		 * 
		if (Desktop.isDesktopSupported()) {
         Desktop.getDesktop().setAboutHandler(new AboutHandler() {
            @Override
            public void handleAbout(AboutEvent arg0) {
               UIHandler.showAbout();
            }
         });
         Desktop.getDesktop().setPreferencesHandler(new PreferencesHandler() {
            @Override
            public void handlePreferences(PreferencesEvent arg0) {
               showPreferences(age);
            } 
         });
      }
      */
		MenuBar bar = new MenuBar();
		// the current menu being worked on
		Menu menu;
		// the current item in the menu being worked on
		MenuItem item;
		
		// ------------ FILE BAR ------------
		menu = new Menu("File");
		
		// heres how to add stuff although Im likely the only one whos going to put stuff in
		item = menu.add(new MenuItem("New Event..."));
		item.addActionListener(e -> {
			//	open prompt
			// if yes, prompt for old save if not done so
		});
		
		item = menu.add(new MenuItem("Open"));
		item.addActionListener(e -> {
			// prompt saving and open new.
			// expect cancel
		});
		item.setShortcut(new MenuShortcut(KeyEvent.VK_O));
		
		item = menu.add(new MenuItem("Save"));
		item.addActionListener(e -> {
			// save the event
		});
		item.setShortcut(new MenuShortcut(KeyEvent.VK_S));
		
		item = menu.add(new MenuItem("Save as..."));
		item.addActionListener(e -> {
			// if the user wants to export the event as a .event file for some reason. 
			// super easy to do, but work on wording
		});
		
		item = menu.add(new MenuItem("Export..."));
		item.addActionListener(e -> {
			// export csv
		});
		bar.add(menu);
		
		
		
		
		return bar;
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame("menu");
		f.setMenuBar(configMenuBar());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
	}
}
