//Edward Fominykh
//Program Description
//Apr 10, 2019

package com.atcs.career.ui;

import java.util.HashMap;

public class Themes
{
    
    private static HashMap<String, MutableColor> currentTheme;
    
    public static HashMap<String, MutableColor> getCurrentTheme()
    {
        return currentTheme;
    }
    
    public static void setCurrentTheme(HashMap<String, MutableColor> theme)
    {
        currentTheme = theme;
    }
    
    public static HashMap<String, MutableColor> whiteBlue;
    
    public static void initializeColors()
    {
        whiteBlue = new HashMap<String, MutableColor>();
        // put colors according to keys such as background, foreground, stuff like that.
        whiteBlue.put("text", new MutableColor(0, 107, 166));
        whiteBlue.put("background", new MutableColor(230 ,230, 230));
        whiteBlue.put("primary", new MutableColor(0, 107, 166));
        whiteBlue.put("secondary", new MutableColor(185, 185, 205));
        whiteBlue.put("tertiary", new MutableColor(4, 150, 255));
    }
    
}
