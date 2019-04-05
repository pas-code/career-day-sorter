//Thomas Varano
//Nov 12, 2018

package com.atcs.career.io.importexport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;

import com.atcs.career.program.logging.BasicLogger;

public class ScriptInterpreter {
	private static final BasicLogger log = BasicLogger.getLogger(ScriptInterpreter.class.getName()); 
	
	
	/**
	 * runs a jar with the specified parameters
	 * @param jrePath the java runner that executes the jar. can be null if using System libs
	 * @param jarPath where the jar is (full path)
	 * @param args the arguments to give the jar. can be null
	 * @return a string array containing the responses of the jar running. 
	 * @throws IOException if any path is invalid
	 * @throws InterruptedException if the process (of running the jar) is interrupted
	 */
	public static String[] runJar(String jrePath, String jarPath, String[] args) throws IOException, InterruptedException {
		if (jrePath == null)
			jrePath = "java";
		if (args == null)
			args = new String[0];
		String[] commands = new String[args.length + 3];
		
		commands[0] = jrePath;
		commands[1] = "-jar";
		commands[2] = jarPath;
		
		for (int i = 0; i < args.length; i++) {
			commands[i + 3] = args[i];
		}
		
		System.out.println("ARG");
		for (String s : commands)
			System.out.print(s + " ");
		ProcessBuilder pb = new ProcessBuilder(commands);
		return getProcessValues(pb);
	}
	
	/**
	 * Get the response of a process
	 * @param pb a {@link ProcessBuilder} holding the process to be run.
	 * @return a String array of line responses from the script.
	 * @throws IOException 
	 * @throws InterruptedException
	 */
	public static String[] getProcessValues(ProcessBuilder pb) throws IOException, InterruptedException {
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
			System.err.println(s);
		}
		
		p.waitFor();
		log.log(Level.INFO, "Script input exit at "+p.exitValue());
		return sb.toArray(new String[sb.size()]);
	}
	
	public static void printResponse(String[] args) {
		for (String s : args) {
			System.out.println(s);
		}
		System.out.println();
	}
}
