//Thomas Varano
//Apr 11, 2019

package com.atcs.career.io.importexport;

public class NewCSVReader {
	public static String[][] expandTable(String compressed) {
		int numLines = 0;
		int numColumns = 0;
		int lastIndex = -1;
		do {
			lastIndex = compressed.indexOf('\n', lastIndex+1);
			numLines++;
			} while (lastIndex != -1);
		String firstLine = compressed.substring(0,compressed.indexOf('\n'));
		do {
			lastIndex = firstLine.indexOf(',', lastIndex+1);
			numColumns++;
			} while (lastIndex != -1);
		String[][] table = new String[numLines][numColumns];
		int lineNum = 0;
		int colNum = 0;
		table[0][0]="";
		boolean passiveEscape = false;
		boolean activeEscape = false;
		for (char letter : compressed.toCharArray()) {
			if (!activeEscape && letter==',') {colNum++; table[lineNum][colNum]="";}
			else if (!activeEscape && letter=='\n') {colNum=0; lineNum++; table[lineNum][colNum]="";}
			else if (letter=='\"') {
				if (!passiveEscape) {passiveEscape = true; activeEscape = true;}
				else {
					if (!activeEscape) table[lineNum][colNum]+="\"";
					activeEscape = !activeEscape;
				}
			}
			else table[lineNum][colNum] += letter;
		}
		return table;
	}
}
