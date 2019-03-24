//Thomas Varano
//Dec 12, 2018

package com.atcs.career.ui;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import javax.swing.JFrame;

import com.atcs.career.data.Event;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.ui.home.CareerDayGUI;
import com.atcs.career.ui.welcome.WelcomeScreen;

public class MasterUI {
	private Event masterEvent;
	private CareerDayGUI mainGui;
	
	public MasterUI() {
	}
	
	public void openWelcome() {
		JFrame welcomeFrame = new JFrame("Welcome");
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // change later
		new WelcomeScreen(welcomeFrame, this);
	}
	
	public void openEventFromWelcome(Event e) {
		if (e == null)
			System.exit(0);
		this.masterEvent = e;
		FileHandler.save(e);
		System.out.println(masterEvent.infoString());
		
		openMain();	
	}
	
	public void openMain() {
		mainGui = new CareerDayGUI(masterEvent);
		configureMenuBar(mainGui.makeWindow());
	}
	
	public void setEvent(Event e) {
		this.masterEvent = e;
	}
	
	public void configureMenuBar(JFrame target) {
		MenuBar bar = new MenuBar();
		Menu menu; // the menu shown on the top level (file, edit, etc)
		MenuItem item; // the item inside the menu. only using one variable to save space.
		// File
		menu = new Menu("File");
		item = menu.add(new MenuItem("New..."));
		
		item = menu.add(new MenuItem("Save"));
		
		item = menu.add(new MenuItem("Save as..."));
		bar.add(menu);
		// Edit
		menu = new Menu("Edit");
		
		bar.add(menu);
		// Info
		menu = new Menu("Info");
		
		bar.add(menu);
		// Email
		menu = new Menu("Email");
		
		bar.add(menu);
		// Help
		menu = new Menu("Help");
		
		bar.add(menu);
		bar.setHelpMenu(menu);
		target.setMenuBar(bar);
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("java.version"));
		new MasterUI().openWelcome();
	}
}
