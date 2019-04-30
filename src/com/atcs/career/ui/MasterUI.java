//Thomas Varano
//Dec 12, 2018

package com.atcs.career.ui;

import java.awt.Desktop;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.atcs.career.data.Event;
import com.atcs.career.email.EmailSender;
import com.atcs.career.io.file.FileHandler;
import com.atcs.career.io.importexport.CSVWriter;
import com.atcs.career.program.ErrorManager;
import com.atcs.career.ui.home.CareerDayGUI;
import com.atcs.career.ui.welcome.PropertiesPane;
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
//		item = menu.add(new MenuItem("New Windows"));
		
		item = menu.add(new MenuItem("Save"));
		item.setShortcut(new MenuShortcut(KeyEvent.VK_S));
		item.addActionListener(e -> {
			masterEvent.save();
		});
		
		item = menu.add(new MenuItem("Save as..."));
		item.setShortcut(new MenuShortcut(KeyEvent.VK_S, true));
		item.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("Enter a name for your new Event");
			if (name != null && name != "")
				new Event(name, masterEvent).save();
		});
		
		menu.addSeparator();
		
		item = menu.add(new MenuItem("Export..."));
		item.addActionListener(e -> {
			CSVWriter.exportEvent(masterEvent);
		});
		bar.add(menu);
		// Edit
		menu = new Menu("Edit");
		item = menu.add(new MenuItem("Edit Event"));
		item.addActionListener(e -> {
			new PropertiesPane(null, masterEvent);
		});
		
		item = menu.add(new MenuItem("Change Priority Weighting"));
		
		
		bar.add(menu);
		// Info
		menu = new Menu("Info");
		
		bar.add(menu);
		// Email
		menu = new Menu("Email");
		item = menu.add(new MenuItem("Send Student Assignments..."));
		item.addActionListener(e -> {
			EmailSender.sendAssignmentEmail(masterEvent);
		});
		
		item = menu.add(new MenuItem("Send Submission Reminders..."));
		item.addActionListener(e -> {
			EmailSender.sendReminderEmail(masterEvent);
		});
		
		bar.add(menu);
		// Help
		menu = new Menu("Help");
		item = menu.add(new MenuItem("Submit Issue"));
		item.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://gitreports.com/issue/pas-code/career-day-sorter"));
			} catch (IOException | URISyntaxException e1) {
				ErrorManager.processException(e1, "URI Error for issue submission", false, false);
			}
		});
		
		menu.addSeparator();
		
		item = menu.add(new MenuItem("Contact Mr. Uhl"));
		item.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://mail.google.com/mail/u/0/?view=cm&fs=1&tf=1&source=mailto&to=juhl@pascack.org"));
			} catch (IOException | URISyntaxException e1) {
				ErrorManager.processException(e1, "URI Error for email link create", false, false);
			}
		});
		item = menu.add(new MenuItem("Contact Tom Varano"));
		item.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://mail.google.com/mail/u/0/?view=cm&fs=1&tf=1&source=mailto&to=tvarano54@gmail.com"));
			} catch (IOException | URISyntaxException e1) {
				ErrorManager.processException(e1, "URI Error for email link create", false, false);
			}
		});
		
		
		bar.add(menu);
		bar.setHelpMenu(menu);
		target.setMenuBar(bar);
	}
}
