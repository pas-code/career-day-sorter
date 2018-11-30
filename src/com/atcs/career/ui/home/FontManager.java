package com.atcs.career.ui.home;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

//Edward Fominykh
//Program Description
//Nov 16, 2018
public class FontManager
{
    public static Font finalFont(float size)
    {
        return getOpenSans(size);
    }
    
    public static Font getOpenSans(float size){
        Font x =
        null;
        try
        {
            x = Font
                    .createFont(Font.TRUETYPE_FONT,
                            ClassLoader.getSystemClassLoader().getResourceAsStream("com/atcs/career/ui/home/OpenSans-Light.ttf"))
                    .deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // register the font
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                    ClassLoader.getSystemClassLoader().getResourceAsStream("com/atcs/career/ui/home/OpenSans-Light.ttf")));
        } catch (FontFormatException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return x;
    }
}
