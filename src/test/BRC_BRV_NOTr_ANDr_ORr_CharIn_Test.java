package test;

import controller.Controller;
import model.instructionType.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *  This class has tested the validity of the following instructions:
 *  BRC, i
 *  BRV, i
 *  NOTA
 *  NOTX
 *  ANDA, i n d
 *  ORA, i n d
 *
 *  @version 21 November 2020
 */
public class BRC_BRV_NOTr_ANDr_ORr_CharIn_Test {

    /**
     * Test BRC, i (Not branching)
     */
    @Test
    public void testBRC_i_NotBranch() {
        Controller controller = new Controller();
        BRC brc = new BRC("00010100","0000000000001010"); // BRC 10,i
        brc.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 0);  // PC did not branch to location 10
        assertEquals(controller.getMyOperand(), "0000000000001010");
    }

    /**
     * Test BRC, i (Branching)
     */
    @Test
    public void testBRC_i_Branch() {
        Controller controller = new Controller();
        BRC brc = new BRC("00010100","0000000000001010"); // BRC 10,i
        controller.setMyCFlag(1); // C flag set to 1 = Branch should happen.
        brc.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 10);  // PC did branch to location 10.
        assertEquals(controller.getMyOperand(), "0000000000001010");
    }

    /**
     * Test BRV, i (Not branching)
     */
    @Test
    public void testBRV_i_NotBranch() {
        Controller controller = new Controller();
        BRV brv = new BRV("00010010","0000000000001010"); // BRV 10,i
        brv.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 0);  // PC did not branch to location 10.
        assertEquals(controller.getMyOperand(), "0000000000001010");
    }

    /**
     * Test BRV, i (Branching)
     */
    @Test
    public void testBRV_i_Branch() {
        Controller controller = new Controller();
        BRV brv = new BRV("00010010","0000000000001010"); // BRV 10,i
        controller.setMyVFlag(1); // V flag set to 1 = Branch should happen.
        brv.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 10);  // PC did branch to location 10.
        assertEquals(controller.getMyOperand(), "0000000000001010");
    }

    /**
     * Test NOTA (NOTr affecting Accumulator Register)
     */
    @Test
    public void testNOTA() {
        Controller controller = new Controller();
        NOTr notA = new NOTr("00011000","0000000000001010"); // NOTA

        // Check N and Z flags are 0 before executing.
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);

        // Invert Accumulator register once for negative value.
        notA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "1111111111111111");
        assertEquals(controller.getMyNFlag(), 1);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "");

        // Invert accumulator again to get zero value.
        notA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000000000");
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 1);
        assertEquals(controller.getMyOperand(), "");
    }

    /**
     * Test NOTX (NOTr affecting Index Register)
     */
    @Test
    public void testNOTX() {
        Controller controller = new Controller();
        NOTr notX = new NOTr("00011001","0000000000000000"); // NOTX

        // Check N and Z flags are 0 before executing.
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);

        // Invert Accumulator register once for negative value.
        notX.execute(controller);
        assertEquals(controller.getMyIndexRegister(), "1111111111111111");
        assertEquals(controller.getMyNFlag(), 1);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "");

        // Invert accumulator again to get zero value.
        notX.execute(controller);
        assertEquals(controller.getMyIndexRegister(), "0000000000000000");
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 1);
        assertEquals(controller.getMyOperand(), "");
    }

    /**
     * Test ANDA, i (ANDr affecting Accumulator Register)
     */
    @Test
    public void testANDA_i() {
        Controller controller = new Controller();
        ANDr andA = new ANDr("10010000","1000000000001010"); // ANDA 32778,i

        // Check N and Z flags are 0 before executing.
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);

        // Artificially setup Accumulator to hold a value
        controller.setMyAccumulatorRegister("1000000000001100");

        // Execute and confirm that "1010" AND "1100" = "1000" for rightmost 4 bits in Operand Specifier.
        // Also confirm that N flag was set to 1.
        andA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "1000000000001000");
        assertEquals(controller.getMyNFlag(), 1);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "1000000000001010");

        // Now AND the Accumulator Register so that you can set the Z flag to 1.
        controller.setMyAccumulatorRegister("0000000000000000");
        andA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000000000");
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 1);
        assertEquals(controller.getMyOperand(), "1000000000001010");
    }

    /**
     * Test ANDA, d (ANDr affecting Accumulator Register)
     */
    @Test
    public void testANDA_d() {
        Controller controller = new Controller();
        ANDr andA = new ANDr("10010001","0000000000001010"); // ANDA 10,d

        // Check N and Z flags are 0 before executing.
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);

        // Artificially setup Accumulator to hold a value
        controller.setMyAccumulatorRegister("1000000000001100");
        // Memory locations 10,11 stores word "1000 0000 0000 1010"
        controller.storeInMyMemory(10, "10000000");
        controller.storeInMyMemory(11, "00001010");

        // Execute and confirm that "1010" AND "1100" = "1000" for rightmost 4 bits in Operand Specifier.
        // Also confirm that N flag was set to 1.
        andA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "1000000000001000");
        assertEquals(controller.getMyNFlag(), 1);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "1000000000001010");

        // Now AND the Accumulator Register so that you can set the Z flag to 1.
        controller.setMyAccumulatorRegister("0000000000000000");
        andA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000000000");
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 1);
        assertEquals(controller.getMyOperand(), "1000000000001010");
    }

    /**
     * Test ANDA, n (ANDr affecting Accumulator Register)
     */
    @Test
    public void testANDA_n() {
        Controller controller = new Controller();
        ANDr andA = new ANDr("10010010","0000000000000101"); // ANDA 5,d

        // Check N and Z flags are 0 before executing.
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);

        // Artificially setup Accumulator to hold a value
        controller.setMyAccumulatorRegister("1000000000001100");
        // Memory locations 5,6 stores word "0000 0000 0000 1010" = 10(decimal) (Direct address stored here)
        controller.storeInMyMemory(5, "00000000");
        controller.storeInMyMemory(6, "00001010");
        // Memory locations 10,11 stores word "1000 0000 0000 1010" (immediate value stored here)
        controller.storeInMyMemory(10, "10000000");
        controller.storeInMyMemory(11, "00001010");

        // Execute and confirm that "1010" AND "1100" = "1000" for rightmost 4 bits in Operand Specifier.
        // Also confirm that N flag was set to 1.
        andA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "1000000000001000");
        assertEquals(controller.getMyNFlag(), 1);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "1000000000001010");

        // Now AND the Accumulator Register so that you can set the Z flag to 1.
        controller.setMyAccumulatorRegister("0000000000000000");
        andA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000000000");
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 1);
        assertEquals(controller.getMyOperand(), "1000000000001010");
    }

    /**
     * Test ORA, i (ANDr affecting Accumulator Register)
     */
    @Test
    public void testORA_i() {
        Controller controller = new Controller();
        ORr orA = new ORr("10100000","0000000000001010"); // ORA 10,i

        // Check N and Z flags are 0 before executing.
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);

        // Confirm that the OperandSpecifier perfectly OR'd itself into the Accumulator Register
        orA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000001010");
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "0000000000001010");

        // Now OR another value into the Accumulator Register to see that it OR'd properly.
        // Also confirm that N flag was set to 1.
        ORr orA2 = new ORr("10100000","1000000000000010"); // ORA 32770,i
        orA2.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "1000000000001010");
        assertEquals(controller.getMyNFlag(), 1);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "1000000000000010");
    }

    /**
     * Test ORA, d (ANDr affecting Accumulator Register)
     */
    @Test
    public void testORA_d() {
        Controller controller = new Controller();
        ORr orA = new ORr("10100001","0000000000001010"); // ORA 10,d

        // Check N and Z flags are 0 before executing.
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);

        // Artificially setup Accumulator to hold a value
        controller.setMyAccumulatorRegister("0000000000000000");
        // Memory locations 10,11 stores word "0000 0000 0000 1010"
        controller.storeInMyMemory(10, "00000000");
        controller.storeInMyMemory(11, "00001010");

        // Confirm that the OperandSpecifier perfectly OR'd itself into the Accumulator Register
        orA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000001010");
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "0000000000001010");    // Contents stored in Memory locations 10,11

        // Now OR another value into the Accumulator Register to see that it OR'd properly.
        // Also confirm that N flag was set to 1.
        // Memory locations 10,11 stores word "1000 0000 0000 0010"
        controller.storeInMyMemory(10, "10000000");
        controller.storeInMyMemory(11, "00000010");
        orA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "1000000000001010");
        assertEquals(controller.getMyNFlag(), 1);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "1000000000000010"); // Contents stored in Memory locations 10,11
    }

    /**
     * Test ORA, n (ANDr affecting Accumulator Register)
     */
    @Test
    public void testORA_n() {
        Controller controller = new Controller();
        ORr orA = new ORr("10100010","0000000000000101"); // ORA 5,n

        // Check N and Z flags are 0 before executing.
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);

        // Artificially setup Accumulator to hold a value
        controller.setMyAccumulatorRegister("0000000000000000");

        // Memory locations 5,6 stores word "0000 0000 0000 1010" = 10(decimal) (Direct address stored here)
        controller.storeInMyMemory(5, "00000000");
        controller.storeInMyMemory(6, "00001010");

        // Memory locations 10,11 stores word "0000 0000 0000 1010" (Immediate value stored here)
        controller.storeInMyMemory(10, "00000000");
        controller.storeInMyMemory(11, "00001010");

        // Confirm that the OperandSpecifier perfectly OR'd itself into the Accumulator Register
        orA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000001010");
        assertEquals(controller.getMyNFlag(), 0);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "0000000000001010");    // Contents stored in Memory locations 10,11

        // Now OR another value into the Accumulator Register to see that it OR'd properly.
        // Also confirm that N flag was set to 1.
        // Memory locations 10,11 stores word "1000 0000 0000 0010"
        controller.storeInMyMemory(10, "10000000");
        controller.storeInMyMemory(11, "00000010");
        orA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "1000000000001010");
        assertEquals(controller.getMyNFlag(), 1);
        assertEquals(controller.getMyZFlag(), 0);
        assertEquals(controller.getMyOperand(), "1000000000000010"); // Contents stored in Memory locations 10,11
    }

    /**
     * Test CharIn, d
     */
    @Test
    public void testCharIn_d() {
        Controller controller = new Controller();
        CharIn charIn = new CharIn("01001001","0000000000001010"); // CharIn 10,d

        // Set up user Input
        controller.setMyInput("Dog");

        // Check to see if CharIn recorded the input 'D' to location 10 in memory.
        charIn.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(10), "01000100"); // 'D' = 68 (decimal) = 01000100 (binary)
        assertEquals(controller.getMyOperand(), "0000000001000100");
        assertEquals(controller.getMyInput(), "og");

        // Check to see if CharIn recorded the input 'o' to location 10 in memory.
        charIn.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(10), "01101111"); // 'o' = 111 (decimal) = 1101111 (binary)
        assertEquals(controller.getMyOperand(), "0000000001101111");
        assertEquals(controller.getMyInput(), "g");

        // Check to see if CharIn recorded the input 'g' to location 10 in memory.
        charIn.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(10), "01100111"); // 'g' = 103 (decimal) = 01100111 (binary)
        assertEquals(controller.getMyOperand(), "0000000001100111");
        assertEquals(controller.getMyInput(), "");

        // Check to see if CharIn recorded the input "00000000" to location 10 in memory.
        charIn.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(10), "00000000"); // no char to read = 0 (decimal) = 00000000 (binary)
        assertEquals(controller.getMyOperand(), "0000000000000000");
        assertEquals(controller.getMyInput(), "");
    }

    /**
     * Test CharIn, n
     */
    @Test
    public void testCharIn_n() {
        Controller controller = new Controller();
        CharIn charIn = new CharIn("01001010","0000000000000101"); // CharIn 5,n

        // Memory locations 5,6 stores word "0000 0000 0000 1010" = 10(decimal) (Direct address stored here)
        controller.storeInMyMemory(5, "00000000");
        controller.storeInMyMemory(6, "00001010");

        // Set up user Input
        controller.setMyInput("Dog");

        // Check to see if CharIn recorded the input 'D' to location 10 in memory.
        charIn.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(10), "01000100"); // 'D' = 68 (decimal) = 01000100 (binary)
        assertEquals(controller.getMyOperand(), "0000000001000100");
        assertEquals(controller.getMyInput(), "og");

        // Check to see if CharIn recorded the input 'o' to location 10 in memory.
        charIn.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(10), "01101111"); // 'o' = 111 (decimal) = 1101111 (binary)
        assertEquals(controller.getMyOperand(), "0000000001101111");
        assertEquals(controller.getMyInput(), "g");

        // Check to see if CharIn recorded the input 'g' to location 10 in memory.
        charIn.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(10), "01100111"); // 'g' = 103 (decimal) = 01100111 (binary)
        assertEquals(controller.getMyOperand(), "0000000001100111");
        assertEquals(controller.getMyInput(), "");

        // Check to see if CharIn recorded the input "00000000" to location 10 in memory.
        charIn.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(10), "00000000"); // no char to read = 0 (decimal) = 00000000 (binary)
        assertEquals(controller.getMyOperand(), "0000000000000000");
        assertEquals(controller.getMyInput(), "");
    }
}
