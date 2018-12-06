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
		put("welcome.background", Color.GRAY);
		put("welcome.button.background", Color.DARK_GRAY);
		put("welcome.foreground", Color.LIGHT_GRAY);
		put("main.scroll.background", Color.WHITE);
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
}
