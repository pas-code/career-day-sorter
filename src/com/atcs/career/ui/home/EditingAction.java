//Thomas Varano
//Mar 22, 2019

package com.atcs.career.ui.home;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * an interface used for editing items using a textfield. If the action is performed (hitting enter) or focus is 
 * lost, the edit() function will be called
 * @author Thomas Dante Varano
 *
 */
public interface EditingAction extends ActionListener, FocusListener {
	void edit(AWTEvent e);
	
	default void actionPerformed(ActionEvent e) {
		edit(e);
	}
	
	default void focusGained(FocusEvent e) {}

   default void focusLost(FocusEvent e) {
   	edit(e);
   }
}
