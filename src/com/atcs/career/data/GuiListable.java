//Edward Fominykh
//Program Description
//Dec 6, 2018

package com.atcs.career.data;

/**Ensures that class contains all necessary values to be listed in the gui list*/
public interface GuiListable
{
    public String getTitle();
    
    /**
     * returns info specific to each class, dependent on the int given.
     */
    public String getInfo(int i);
    public String getType();
    public byte getTypeNum();
}
