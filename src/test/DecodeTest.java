package test;

import controller.Controller;
import model.instructionType.ANDr;
import model.instructionType.Addr;
import model.instructionType.Instruction;
import model.instructionType.Stop;
import org.junit.jupiter.api.Test;

import model.Decode;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that the Decode object translates a 24-bit instruction register value to the correct
 * Instruction-type Object.
 *
 * @version 22 November 2020
 */
public class DecodeTest {

    /**
     * Tests that the correct Instruction-type Object is created from the method
     * reading a 24-bit-long instruction register value.
     */
    @Test
    public void decodeInstructionTest() {
        Decode decode = new Decode();
        Controller controller = new Controller();

        Instruction instr1;
        instr1 = decode.decodeInstruction(controller, "000000000000000000000000");
        assertEquals(instr1.getClass(), new Stop("00000000","0000000000000000").getClass());
        assertEquals(controller.getMyInstructionWordLabel(), "Stop");

        Instruction instr2;
        instr2 = decode.decodeInstruction(controller, "100110100000111100001111");
        assertEquals(instr2.getClass(), new ANDr("10011010","0000111100001111").getClass());
        assertEquals(controller.getMyInstructionWordLabel(), "ANDX, n");

        Instruction instr3;
        instr3 = decode.decodeInstruction(controller, "011110000000111100001111");
        assertEquals(instr3.getClass(), new Addr("01111000","0000111100001111").getClass());
        assertEquals(controller.getMyInstructionWordLabel(), "ADDX, i");
    }
}
