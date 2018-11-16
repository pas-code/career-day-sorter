//Thomas Varano
//Nov 9, 2018

package com.atcs.career.io;

import java.io.IOException;
import java.net.URL;

public class CSVReader {

	public static void main(String[] args) {
		URL export = CSVReader.class.getResource("ExportLocation.scpt");
		try {
			new ProcessBuilder("osascript", export.getPath()).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
