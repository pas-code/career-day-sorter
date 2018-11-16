//Thomas Varano
//Nov 12, 2018

package com.atcs.career.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ScriptInterpreter {
	
	/**
	 * Get the response of a process
	 * @param pb a {@link ProcessBuilder} holding the process to be run.
	 * @return a String array of line responses from the script.
	 * @throws IOException 
	 * @throws InterruptedException
	 */
	public String[] getProcessValues(ProcessBuilder pb) throws IOException, InterruptedException {
		String s = null;
		Process p = pb.start();
		
		// get the response from the script 
		ArrayList<String> sb = new ArrayList<String>();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(p.getInputStream()));
		while ((s = br.readLine()) != null) 
			sb.add(s);
		
		// PRINT ERRORS
		br = new BufferedReader(
				new InputStreamReader(p.getErrorStream()));
		while ((s = br.readLine()) != null) {
			System.out.println(s);
		}
		
		p.waitFor();
		System.out.println("Script input exit at "+p.exitValue());
		return sb.toArray(new String[sb.size()]);
	}
}
