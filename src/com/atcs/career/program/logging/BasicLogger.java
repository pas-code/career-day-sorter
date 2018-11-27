//Thomas Varano
//May 22, 2018

package com.atcs.career.program.logging;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class BasicLogger {
   private PrintStream out;
   private Filter filter;
   private BasicLogger master;
   private Level threshold;
   private final String name;
   private boolean verbose;
   
   BasicLogger(String name, BasicLogger master) {
      this.name = name; this.master = master;
      verbose = true;
   }
   
   BasicLogger(String name) {
      this(name, BasicLogManager.getGlobal());
   }
   
   private static final String ANON_NAME = "anon";
   BasicLogger() {
      this(ANON_NAME);
   }
   
   
   public static BasicLogger getLogger(String name) {
      return BasicLogManager.manager.getLogger(name);
   }
   
   //-------------------------------LOGGING--------------------------------
   public void log(LogRecord l) {
      if (!vetLog(l)) return;
      if (verbose) {
         getOut().println(LocalDate.now() + " " + LocalTime.now() + " "+ getCallerMethod() + " - " + name + ":");
         getOut().println(l.getLevel().getName() + ": "+l.getMessage());
      } else {
         getOut().println(name + " -- "+l.getLevel().getName() + ": "+l.getMessage());
      }
   }
   
   public void log(Level l, String msg) {
      log(new LogRecord(l, msg));
   }
   
   public void severe(String msg) {
      log(Level.SEVERE, msg);
   }
   
   public void warning(String msg) {
      log(Level.WARNING, msg);
   }
   
   public void info(String msg) {
      log(Level.INFO, msg);
   }
   
   public void config(String msg) {
      log(Level.CONFIG, msg);
   }
   
   public void fine(String msg) {
      log(Level.FINE, msg);
   }
   
   public void finer(String msg) {
      log(Level.FINER, msg);
   }
   
   public void finest(String msg) {
      log(Level.FINEST, msg);
   }
   
   
   //----------------------------------------------------------------------
   
   public static String getCallerMethod() { 
      StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
      for (int i=1; i<stElements.length; i++) {
          StackTraceElement ste = stElements[i];
          if (!ste.getClassName().equals(BasicLogger.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
              return ste.getClassName() + " " + ste.getMethodName() +  " ln " + ste.getLineNumber();
          }
      }
      return "";
   }
   
   private boolean vetLog(LogRecord l) {
      return (l.getLevel().intValue() >= getThreshold().intValue());
   }
   
   public PrintStream getOut() {
      if (out != null) return out;
      return master.getOut();
   }
   public void setOut(PrintStream out) {
      this.out = out;
   }
   public Filter getFilter() {
      if (filter != null) return filter; 
      if (master == null) return BasicLogManager.DEFAULT_FILTER;
      return master.getFilter();
   }
   public void setFilter(Filter filter) {
      this.filter = filter;
   }
   public BasicLogger getMaster() {
      return master;
   }
   public void setMaster(BasicLogger master) {
      this.master = master;
   }
   public Level getThreshold() {
      if (threshold != null) return threshold;
      if (master == null) return BasicLogManager.DEFAULT_LEVEL;
      return master.getThreshold();
   }
   public void setThreshold(Level threshold) {
      this.threshold = threshold;
   }
   public String getName() {
      return name;
   }
   public boolean isVerbose() {
      return verbose;
   }
   public void setVerbose(boolean v) {
      this.verbose = v;
   }
}
