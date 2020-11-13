package src.model;

import javax.swing.*;
import java.awt.*;

/**
 * Driver class to run the View.Pep8GUIFrame.java class to open its GUI.
 *
 * @version 5 November 2020
 */
public class Pep8GUIDriver {

    /**
     * Constructs the main GUI window frame.
     *
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(() -> {
            final Pep8GUIFrame frame = new Pep8GUIFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
