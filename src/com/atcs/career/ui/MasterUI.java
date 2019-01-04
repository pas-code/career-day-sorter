//Thomas Varano
//Dec 12, 2018

package com.atcs.career.ui;

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
	
	public void openEvent(Event e) {
		if (e == null)
			System.exit(0);
		this.masterEvent = e;
		
		
		//just for now, so you can actually save
		FileHandler.save(e);
		
		System.out.println(masterEvent.infoString());
		
		openMain();	
	}
	
	public void openMain() {
		mainGui = new CareerDayGUI(masterEvent);
		mainGui.makeWindow();
	}
	
	public void setEvent(Event e) {
		this.masterEvent = e;
	}
	
	public static void main(String[] args) {
		new MasterUI().openWelcome();
	}
}
