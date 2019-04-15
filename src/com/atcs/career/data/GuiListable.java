//Edward Fominykh
//Program Description
//Dec 6, 2018

package com.atcs.career.data;

import java.util.Comparator;

import com.atcs.career.ui.home.Searchable;

/**Ensures that class contains all necessary values to be listed in the gui list*/
public interface GuiListable extends Searchable
{
    public String getTitle();
    
    /**
     * returns info specific to each class, dependent on the int given.
     */
    public String getInfo(int i);
    public String getInfoTitle(int i);
    public String getType();
    
    default int compareToList(GuiListable o) {
   	 return getIdentifier().compareTo(o.getIdentifier());
    }
    
    public static Comparator<GuiListable> listSorter() {
   	 return new Comparator<GuiListable>() {
			public int compare(GuiListable o1, GuiListable o2) {
				return o1.compareToList(o2);
			}
   	 };
    }
}
