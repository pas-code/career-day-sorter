//Thomas Varano
//Dec 5, 2018

package com.atcs.career.ui;

import java.awt.Color;
import java.util.HashMap;

public class ColorManager {
	private static HashMap<String, MutableColor> colors;
	
	private static void initColorMap() {
		colors = new HashMap<String, MutableColor>();
		// put colors according to keys such as background, foreground, stuff like that.
		put("text", new MutableColor(48, 52, 63));
		put("background", new MutableColor(250 ,250, 255));
//		put("primary", new MutableColor(21, 27, 71));
//		put("secondary", new MutableColor(39, 52, 105));
//		put("tertiary", new MutableColor(228, 217, 255));
		put("primary", new MutableColor(0, 107, 166));
		put("secondary", new MutableColor(209, 104, 0));
		put("tertiary", new MutableColor(4, 150, 255));
	}
	
	private static void put(String key, Color val) {
		colors.put(key, new MutableColor(val));
	}
	
	private static void put(String key, MutableColor val) {
		colors.put(key, val);
	}
	
	public static MutableColor get(String key) {
		if (colors == null) 
			initColorMap();
		return colors.get(key);
	}
	
	public static MutableColor get(HashMap<String, MutableColor> colors, String key) {
	    assert (key!=null && colors!=null);
	    assert (colors.containsKey(key));
	    System.out.println(colors.size());
        Object ret = colors.get(key);
        assert (ret!=null);
        return (MutableColor) ret;
    }
}
