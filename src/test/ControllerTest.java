package test;

import controller.Controller;
import model.Decode;
import model.MemoryUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that the methods in the Controller work as expected.
 *
 * @version 22 November 2020
 */
public class ControllerTest {

    /**
     * A string that contains 16 zeroes to mimic a 0-value binary String.
     */
    private final static String ZEROED_16_BITS = "0000000000000000";

    /**
     * A string that contains 24 zeroes to mimic a 0-value binary String.
     */
    private final static String ZEROED_24_BITS = "000000000000000000000000";

    /**
     * Tests to make sure constructor starts with correct default field values.
     */
    @Test
    public void controllerConstructorTest() {
        Controller cont = new Controller();

        // Test if starting field values are set correctly.
        assertEquals(cont.getMyAccumulatorRegister(), ZEROED_16_BITS);
        assertEquals(cont.getMyIndexRegister(), ZEROED_16_BITS);
        assertEquals(cont.getMyStackPointer(), 64463);
        assertEquals(cont.getMyProgramCounter(), 0);
        assertEquals(cont.getMyInstructionRegister(), ZEROED_24_BITS);
        assertEquals(cont.getMyOperand(), "");
        assertEquals(cont.getMyInstructionWordLabel(), "");
        assertEquals(cont.getMyNFlag(), 0);
        assertEquals(cont.getMyZFlag(), 0);
        assertEquals(cont.getMyVFlag(), 0);
        assertEquals(cont.getMyCFlag(), 0);
        assertEquals(cont.getMyInput(), "");
        assertEquals(cont.getMyOutput(), "");
        assertFalse(cont.getMyRunIsExecuting());
    }

    /**
     * Tests that the getters return the correct values of the controller.
     */
    @Test
    public void gettersTest() {
        Controller cont = new Controller();

        assertEquals(cont.getMyMemoryDataAt(0), null);
        assertEquals(cont.getMyMemoryFullDump().length, 65536);
        assertEquals(cont.getMyMemoryTotalLocations(), 65536);
        assertEquals(cont.getMyAccumulatorRegister(), ZEROED_16_BITS);
        assertEquals(cont.getMyIndexRegister(), ZEROED_16_BITS);
        assertEquals(cont.getMyStackPointer(), 64463);
        assertEquals(cont.getMyProgramCounter(), 0);
        assertEquals(cont.getMyInstructionRegister(), ZEROED_24_BITS);
        assertEquals(cont.getMyOperand(), "");
        assertEquals(cont.getMyInstructionWordLabel(), "");
        assertEquals(cont.getMyNFlag(), 0);
        assertEquals(cont.getMyZFlag(), 0);
        assertEquals(cont.getMyVFlag(), 0);
        assertEquals(cont.getMyCFlag(), 0);
        assertEquals(cont.getMyInput(), "");
        assertEquals(cont.getMyOutput(), "");
        assertFalse(cont.getMyRunIsExecuting());
    }

    /**
     * Tests the setter method of the Controller, then relies on getters to check the validity of values.
     */
    @Test
    public void settersThenGettersTest() {
        Controller cont = new Controller();

        cont.storeInMyMemory(0, "00001010");
        assertEquals(cont.getMyMemoryDataAt(0), "00001010");

        cont.setMyAccumulatorRegister("1010101010101010");
        assertEquals(cont.getMyAccumulatorRegister(), "1010101010101010");

        cont.setMyIndexRegister("0000000011110000");
        assertEquals(cont.getMyIndexRegister(), "0000000011110000");

        cont.setMyStackPointer(64470);
        assertEquals(cont.getMyStackPointer(), 64470);

        cont.setMyProgramCounter(100);
        assertEquals(cont.getMyProgramCounter(), 100);

        cont.setMyInstructionRegister("000000001111111111111111");
        assertEquals(cont.getMyInstructionRegister(), "000000001111111111111111");

        cont.setMyInstructionWordLabel("Hello I am an Instruction.");
        assertEquals(cont.getMyInstructionWordLabel(), "Hello I am an Instruction.");

        cont.setMyNFlag(1);
        cont.setMyZFlag(1);
        cont.setMyVFlag(1);
        cont.setMyCFlag(1);
        assertEquals(cont.getMyNFlag(), 1);
        assertEquals(cont.getMyZFlag(), 1);
        assertEquals(cont.getMyVFlag(), 1);
        assertEquals(cont.getMyCFlag(), 1);

        cont.setMyInput("Hello Instruction, I'm user input!");
        assertEquals(cont.getMyInput(), "Hello Instruction, I'm user input!");

        cont.setMyRunIsExecuting(true);
        assertTrue(cont.getMyRunIsExecuting());

        cont.setMyOperand("1111000011110000");
        assertEquals(cont.getMyOperand(), "1111000011110000");
    }

    /**
     * Tests that object code can successfully load into the Controller's memory.
     */
    @Test
    public void loadObjectCodeIntoMemoryTest() {
        Controller cont = new Controller();
        cont.loadObjectCodeIntoMemory("18\t0C af\n00 zZ");

        assertEquals(cont.getMyMemoryDataAt(0), "00011000");
        assertEquals(cont.getMyMemoryDataAt(1), "00001100");
        assertEquals(cont.getMyMemoryDataAt(2), "10101111");
        assertEquals(cont.getMyMemoryDataAt(3), "00000000");
        assertEquals(cont.getMyMemoryDataAt(4), null);
        assertEquals(cont.getMyMemoryDataAt(5), null);
    }

    /**
     * Tests that object code can successfully load into the Controller's memory.
     */
    @Test
    public void runTest() {
        Controller cont = new Controller();
        cont.loadObjectCodeIntoMemory("A0 00 0A zz"); // ORA 10,i
        cont.run("");

        // Check if the Accumulator Register was OR'd properly.
        assertEquals(cont.getMyAccumulatorRegister(), "0000000000001010");

        // Run ends on the Stop instruction.
        assertEquals(cont.getMyInstructionWordLabel(), "Stop");
        assertEquals(cont.getMyOperand(), "");
    }

    /**
     * Test that checks if the memory and CPU states have been cleared, like for
     * clearing
     */
    @Test
    public void clearMyMemoryAndResetCPUFieldsTest() {
        Controller cont = new Controller();

        // Set fields to have non-default values.
        cont.storeInMyMemory(0, "00001010");
        cont.setMyAccumulatorRegister("1010101010101010");
        cont.setMyIndexRegister("0000000011110000");
        cont.setMyStackPointer(64470);
        cont.setMyProgramCounter(100);
        cont.setMyInstructionRegister("000000001111111111111111");
        cont.setMyInstructionWordLabel("Hello I am an Instruction.");
        cont.setMyNFlag(1);
        cont.setMyZFlag(1);
        cont.setMyVFlag(1);
        cont.setMyCFlag(1);
        cont.setMyInput("Hello Instruction, I'm user input!");
        cont.setMyRunIsExecuting(true);
        cont.setMyOperand("1111000011110000");

        // Now clear memory.
        cont.clearMyMemoryAndResetCPUFields();

        // Check that fields are reset from before.
        assertEquals(cont.getMyMemoryDataAt(0), null);
        assertEquals(cont.getMyMemoryFullDump().length, 65536);
        assertEquals(cont.getMyMemoryTotalLocations(), 65536);
        assertEquals(cont.getMyAccumulatorRegister(), ZEROED_16_BITS);
        assertEquals(cont.getMyIndexRegister(), ZEROED_16_BITS);
        assertEquals(cont.getMyStackPointer(), 64463);
        assertEquals(cont.getMyProgramCounter(), 0);
        assertEquals(cont.getMyInstructionRegister(), ZEROED_24_BITS);
        assertEquals(cont.getMyOperand(), "");
        assertEquals(cont.getMyInstructionWordLabel(), "");
        assertEquals(cont.getMyNFlag(), 0);
        assertEquals(cont.getMyZFlag(), 0);
        assertEquals(cont.getMyVFlag(), 0);
        assertEquals(cont.getMyCFlag(), 0);
        assertEquals(cont.getMyInput(), "");
        assertEquals(cont.getMyOutput(), "");
        assertFalse(cont.getMyRunIsExecuting());
    }

    /**
     * Tests that the getAndRemoveFirstCharFromMyInput() returns and removes the correct
     * character from the User's input string.
     */
    @Test
    public void getAndRemoveFirstCharFromMyInputTest() {
        Controller cont = new Controller();

        cont.setMyInput("Cat");

        // Get and remove C
        char tempChar1 = cont.getAndRemoveFirstCharFromMyInput();
        assertEquals(tempChar1, 'C');
        assertEquals(cont.getMyInput(), "at");

        // Get and remove a
        char tempChar2 = cont.getAndRemoveFirstCharFromMyInput();
        assertEquals(tempChar2, 'a');
        assertEquals(cont.getMyInput(), "t");

        // Get and remove t
        char tempChar3 = cont.getAndRemoveFirstCharFromMyInput();
        assertEquals(tempChar3, 't');
        assertEquals(cont.getMyInput(), "");

        // Get and remove a character with 0 value
        char tempChar4 = cont.getAndRemoveFirstCharFromMyInput();
        assertEquals(tempChar4, 0);
        assertEquals(cont.getMyInput(), "");
    }

    /**
     * Tests that this method can add a character to the end of what will be the Output
     * results of the Pep/8 program.
     */
    @Test
    public void appendToMyOutputTest() {
        Controller cont = new Controller();

        // After appending first char
        cont.appendToMyOutput("C");
        assertEquals(cont.getMyOutput(), "C");

        // After appending multiple chars
        cont.appendToMyOutput("a");
        cont.appendToMyOutput("t");
        assertEquals(cont.getMyOutput(), "Cat");
    }
}