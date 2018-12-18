//Thomas Varano
//Dec 12, 2018

package com.atcs.career.ui;

import java.util.concurrent.CancellationException;

import javax.swing.JFrame;

import com.atcs.career.data.Event;
import com.atcs.career.program.MainClass;
import com.atcs.career.ui.welcome.WelcomeScreen;

public class MasterUI {
	private Event masterEvent;
	
	public MasterUI() {
	}
	
	public void openWelcome() {
		JFrame welcomeFrame = new JFrame("Welcome");
		WelcomeScreen welcome = new WelcomeScreen(welcomeFrame, this);
	}
	
	public void openEventFromWelcome(Event e) {
		this.masterEvent = e;
	}
	
}
