//Thomas Varano
//Information Team
//Nov 9, 2018

package com.atcs.career.io.importexport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVReader {

	public static void main(String[] args) {
		// URL importer = CSVReader.class.getResource("ExportLocation.scpt");
		// try {
		// System.out.println(ScriptInterpreter.getProcessValues(new
		// ProcessBuilder("osascript", importer.getPath()))[0]);
		// } catch (IOException | InterruptedException e) {
		// e.printStackTrace();
		// }

		readCSV("src/com/atcs/career/io/SampleData.csv");
		// ArrayList<String[]> read =
		// readCSV("/Users/mreineke20/Desktop/CareerSampleData.csv");
	}

	public static ArrayList<String[]> readCSV(String fileName) {
		ArrayList<String[]> lines = new ArrayList<String[]>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = br.readLine()) != null) {
			   boolean checkingForCommas = false;
			   for(int i = 0; i < line.length(); i++) {
			      if(line.charAt(i) == '"')
			         checkingForCommas = !checkingForCommas;
			      if(checkingForCommas)
			         if(line.charAt(i) == ',')
			            line = line.substring(0, i) + "/" + line.substring(i+1);
			   }
				String[] lineArr = line.split(",");
//				System.out.println(Arrays.asList(lineArr));
				lines.add(lineArr);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("File not found");
		}
		return lines;

	}
}
