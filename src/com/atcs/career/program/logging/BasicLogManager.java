//Thomas Varano
//May 22, 2018

package com.atcs.career.program.logging;

import java.util.HashMap;
import java.util.logging.Level;

public class BasicLogManager {
   public static final String GLOBAL_NAME = "global";
   
   static final BasicLogManager manager;
   
   static {
      manager = new BasicLogManager();
   }
   
   public static BasicLogger getGlobal() {
      return manager.master;
   }
   
   public static final Level DEFAULT_LEVEL = Level.ALL;
      
   public static final BasicLogger.Filter DEFAULT_FILTER = new BasicLogger.Filter(DEFAULT_LEVEL);
   
   private final String name;
   private final BasicLogManager parent;
   private final HashMap<String, BasicLogger> children;

   private BasicLogger master;
   
   public BasicLogManager(String name, BasicLogManager parent) {
      this.name = name;
      children = new HashMap<String, BasicLogger>();
      this.parent = parent;
      initMaster();
   }
   
   /**
    * only for initializing the global manager
    */
   private BasicLogManager() {
      name = GLOBAL_NAME;
      children = new HashMap<String, BasicLogger>();
      parent = null;
      master = new BasicLogger(name, null);
      addLogger(master);
      master.setFilter(DEFAULT_FILTER);
      master.setOut(System.out);
   }
   
   private void initMaster() {
      master = getLogger(name);
      master.setFilter(DEFAULT_FILTER);
      master.setOut(System.out);      
   }
   
   public BasicLogManager(String name) {
      this(name, null);
   }
   
   private BasicLogger putLogger(BasicLogger l) {
      addLogger(l);
      if (parent != null) parent.putLogger(l);
      if (name != GLOBAL_NAME) manager.putLogger(l);
      return l;
   }
   
   public void addLogger(BasicLogger l) {
      children.put(l.getName(), l);      
   }

   public BasicLogger getAnonymousLogger() {
      return new BasicLogger();
   }
   
   
   BasicLogger getLogger(String name) {
      if (children.containsKey(name)) return children.get(name);
      if(manager.children.containsKey(name)) putLogger(manager.children.get(name));
      BasicLogger created =  putLogger(new BasicLogger(name, master));
      return created;
   }
   
   public BasicLogger.Filter getFilter() {
      return master.getFilter();
   }

   public void setFilter(BasicLogger.Filter filter) {
      master.setFilter(filter);
   }

   public Level getThreshold() {
      return master.getThreshold();
   }

   public String getName() {
      return name;
   }

   public BasicLogManager getParent() {
      return parent;
   }

   public HashMap<String, BasicLogger> getChildren() {
      return children;
   }

   public BasicLogger getMaster() {
      return master;
   }
}
