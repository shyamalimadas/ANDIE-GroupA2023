package cosc202.andie;

import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * This class is used to display a loading window.
 * </p>
 * 
 * <p>
 * The loading window is used to display a loading window while the program is
 * loading.
 * </p>
 * 
 * @author Josh Lawson
 * 
 * @version 1.0
 */
public class LoadingWindow extends JDialog {

    /**
     * <p>
     * Construct a LoadingWindow object.
     * </p>
     * 
     * <p>
     * The LoadingWindow object is constructed with a JFrame object that it will use
     * to display the loading window.
     * </p>
     * 
     */
    public LoadingWindow() {
        super((Frame) null, "Loading...", true);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        getContentPane().add(progressBar);
        setSize(300, 75);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        System.out.println("New loading window");
    }

    /**
     * <p>
     * Start the loading window.
     * </p>
     */
    public void start() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("start is called");
            setVisible(true);
        });
    }

    /**
     * <p>
     * Stop the loading window.
     * </p>
     */
    public void stop() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("stop is called");
            setVisible(false);
        });
    }
}
