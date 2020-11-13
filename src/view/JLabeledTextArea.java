package view;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel that acts as a labeled JTextArea with horizontal and vertical scroll bars.
 *
 * @version 5 November 2020
 */
public class JLabeledTextArea extends JPanel {

    /**
     * The default title JLabel contents if constructed without a title parameter.
     */
    private static final String DEFAULT_TITLE_TEXT = "";

    /**
     * The default number of rows that the JTextArea should have when created without size parameters.
     */
    private static final int DEFAULT_JTEXTAREA_ROWS = 5;

    /**
     * The default number of columns that the JTextArea should have when created without size parameters.
     */
    private static final int DEFAULT_JTEXTAREA_COLUMNS = 10;

    /**
     * The label that will sit centered on top of the main JTextArea.
     */
    private JLabel myTitleLabel;

    /**
     * The text area where users will either input information or see displayed information.
     */
    private JTextArea myTextArea;

    /**
     * Constructs a custom JTextArea that scrolls horizontally, vertically, and has a centered
     * label above it.
     */
    public JLabeledTextArea(){
        this(DEFAULT_TITLE_TEXT, DEFAULT_JTEXTAREA_ROWS,DEFAULT_JTEXTAREA_COLUMNS);
    }

    /**
     * Constructs a custom JTextArea that scrolls horizontally, vertically, and has a centered
     * label above it.
     *
     * @param theTitle The label that will sit above the JTextArea.
     */
    public JLabeledTextArea(String theTitle){
        this(theTitle, DEFAULT_JTEXTAREA_ROWS,DEFAULT_JTEXTAREA_COLUMNS);
    }

    /**
     * Constructs a custom JTextArea that scrolls horizontally, vertically, and has a centered
     * label above it.
     *
     * @param theTitle The label that will sit above the JTextArea.
     * @param theRows The minimum number of rows the JTextArea should have.
     * @param theCols The minimum number of columns the JTextArea should have.
     */
    public JLabeledTextArea(String theTitle, int theRows, int theCols){
        // Instantiate Fields
        myTitleLabel = new JLabel(theTitle);
        myTextArea = new JTextArea(theRows, theCols);

        // Build Panel Format
        setLayout(new BorderLayout());
        JPanel centerLabelPanel = new JPanel();
        JScrollPane textAreaScrollPane = new JScrollPane(myTextArea);
        textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textAreaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        centerLabelPanel.add(myTitleLabel);
        add(centerLabelPanel, BorderLayout.NORTH);
        add(textAreaScrollPane, BorderLayout.CENTER);
    }

    /**
     * Returns all the contents of the JTextArea in a String. Formatting is included.
     *
     * @return All of the JTextArea's contents in a String. Formatting is included.
     */
    public String getText() {
        return myTextArea.getText();
    }

    /**
     * Replaces all the contents of the JTextArea with the new input string.
     */
    public void setText(String theNewText) {
        myTextArea.setText(theNewText);
    }

    /**
     * Returns a String representation of all the field values of the View.JLabeledTextArea.
     *
     * @return A String representation of all the field values of the View.JLabeledTextArea.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(getClass().getName());
        output.append("[myTitleLabel=");
        output.append(myTitleLabel.getText());
        output.append(",myTextArea=");
        output.append(myTextArea.getText());
        output.append("]");
        return output.toString();
    }
}
