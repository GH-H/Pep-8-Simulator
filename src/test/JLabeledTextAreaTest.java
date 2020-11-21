package test;

import org.junit.jupiter.api.Test;
import view.JLabeledTextArea;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A JUnit 5 tester to see if JLabeledTextArea can be interacted with as intended.
 *
 * @version 5 November 2020
 */
public class JLabeledTextAreaTest {

    /**
     * Tests the getter method alone to see if it properly outputs a String from the View.JLabeledTextArea.
     */
    @Test
    void testGetText() {
        JLabeledTextArea testJLTextArea = new JLabeledTextArea();

        assertEquals(testJLTextArea.getText(), "");
    }

    /**
     * Tests the getter and setter methods to see if they properly input and output Strings into the View.JLabeledTextArea.
     */
    @Test
    void testSetTextThenGetText() {
        JLabeledTextArea testJLTextArea = new JLabeledTextArea();

        testJLTextArea.setText("Fish");
        testJLTextArea.setText("Dog");
        assertEquals(testJLTextArea.getText(), "Dog");
    }

    /**
     * Tests the appendText(...) and getText() methods to see if they properly modify and detect changes in JLabeledTextArea
     */
    @Test
    void testAppendTextThenGetText() {
        JLabeledTextArea testJLTextArea = new JLabeledTextArea();

        testJLTextArea.appendText("Kitty");
        testJLTextArea.appendText("Cat");
        assertEquals(testJLTextArea.getText(), "KittyCat");
    }

    /**
     * Tests to see if the toString() method displays the field values of the View.JLabeledTextArea as expected.
     */
    @Test
    void testToString() {
        JLabeledTextArea testJLTextArea = new JLabeledTextArea("test");

        assertEquals(testJLTextArea.toString(), "view.JLabeledTextArea[myTitleLabel=test,myTextArea=\"\"]");

        testJLTextArea.setText("Fish");

        assertEquals(testJLTextArea.toString(), "view.JLabeledTextArea[myTitleLabel=test,myTextArea=\"Fish\"]");
    }
}