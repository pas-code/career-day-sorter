//Thomas Varano
//Dec 13, 2018

package com.atcs.career.ui.welcome;

import java.util.concurrent.CancellationException;

import com.atcs.career.data.Event;
import com.atcs.career.io.importexport.CSVReader;

/**
 * open an old event
 */
public class OpenScreen {
	public static Event open() throws CancellationException {
		CSVReader.getFileLocation(".event");
	}
}
