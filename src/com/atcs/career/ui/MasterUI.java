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
		masterEvent = openWelcomeAndRetrieveEvent();
	}
	
	public Event openWelcomeAndRetrieveEvent() throws CancellationException{
		JFrame frame = new JFrame(MainClass.APP_NAME);
		WelcomeScreen welcome = new WelcomeScreen(frame);
		
		
	}
}
