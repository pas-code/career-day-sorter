//Thomas Varano
//Dec 12, 2018

package com.atcs.career.ui;

import javax.swing.JFrame;

import com.atcs.career.data.Event;
import com.atcs.career.ui.welcome.WelcomeScreen;

public class MasterUI {
	private Event masterEvent;
	
	public MasterUI() {
	}
	
	public void openWelcome() {
		JFrame welcomeFrame = new JFrame("Welcome");
		new WelcomeScreen(welcomeFrame, this);
	}
	
	public void openEventFromWelcome(Event e) {
		if (e == null)
			System.exit(0);
		this.masterEvent = e;
	}
	
	public void openMain() {
		
	}
	
	public void setEvent() {
		
	}
	
	public static void main(String[] args) {
		new MasterUI().openWelcome();
	}
}
