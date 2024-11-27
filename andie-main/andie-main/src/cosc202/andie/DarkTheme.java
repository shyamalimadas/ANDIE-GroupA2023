package cosc202.andie;

import javax.swing.plaf.metal.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.Color;

/**
 * <p>
 * Custom theme for the application.
 * </p>
 * 
 * <p>
 * Menu of themes to choose from and apply to the application.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 * 
 */
public class DarkTheme extends DefaultMetalTheme {

    private final ColorUIResource dark = new ColorUIResource(46, 46, 46); // dark grey
    private final ColorUIResource lightGrey = new ColorUIResource(98, 98, 98); // light grey
    private final ColorUIResource white = new ColorUIResource(255, 255, 255); // white

    /**
     * <p>
     * get the color ui resource for the primary 1 color.
     * </p>
     * 
     * @return the color ui resource for the primary 1 color.
     */
    public ColorUIResource getPrimary1() { 
        return lightGrey; 
    }
    
    /**
     * <p>
     * get the color ui resource for the primary 2 color.
     * </p>
     * 
     * @return the color ui resource for the primary 2 color.
     */
    public ColorUIResource getPrimary2() { 
        return lightGrey; 
    }
    
    /**
     * <p>
     * get the color ui resource for the primary 3 color.
     * </p>
     * 
     * @return the color ui resource for the primary 3 color.
     */
    public ColorUIResource getPrimary3() {         
        return lightGrey; 
    }
    
    /**
     * <p>
     * get the color ui resource for the secondary 1 color.
     * </p>
     * 
     * @return the color ui resource for the secondary 1 color.
     */
    public ColorUIResource getSecondary1() { 
        return new ColorUIResource(Color.BLACK);
    }
    
    /**
     * <p>
     * get the color ui resource for the secondary 2 color.
     * </p>
     * 
     * @return the color ui resource for the secondary 2 color.
     */
    public ColorUIResource getSecondary2() { 
        return lightGrey; 
    }
    
    /**
     * <p>
     * get the color ui resource for the secondary 3 color.
     * </p>
     * 
     * @return the color ui resource for the secondary 3 color.
     */
    public ColorUIResource getSecondary3() { 
        return dark; 
    }

    /**
     * <p>
     * get the color ui resource for the control text color.
     * </p>
     * 
     * @return the color ui resource for the control text color.
     */
    public ColorUIResource getControlTextColor() { 
        return white; 
    }

    /**
     * <p>
     * get the color ui resource for the control highlight color.
     * </p>
     * 
     * @return the color ui resource for the control highlight color.
     */
    public ColorUIResource getSystemTextColor() { 
        return white; 
    }


}

