package test;
import controller.Controller;
import model.instructionType.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for BR, BREQ, BRGE, BRLE, BRLT, BRNE instructions
 */
public class BR_BREQ_BRGE_BRLE_BRLT_BRNE_Test {

    @Test
    public void testBR() {
        Controller controller = new Controller();
        BR brInstruction = new BR("00001000","0000000000000010");
        brInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 2);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBREQ() {
        Controller controller = new Controller();
        BREQ breqInstruction = new BREQ("00001010","0000000000000010");
        controller.setMyZFlag(1);
        breqInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 2);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBREQ_NotBranch() {
        Controller controller = new Controller();
        BREQ breqInstruction = new BREQ("00001010","0000000000000010");
        controller.setMyZFlag(0);
        breqInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 0);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBRGE() {
        Controller controller = new Controller();
        BRGE brgeInstruction = new BRGE("00001110","0000000000000010");
        controller.setMyNFlag(0);
        brgeInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 2);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBRGE_NotBranch() {
        Controller controller = new Controller();
        BRGE brgeInstruction = new BRGE("00001110","0000000000000010");
        controller.setMyNFlag(1);
        brgeInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 0);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBRLE() {
        Controller controller = new Controller();
        BRLE brleInstruction = new BRLE("00000110","0000000000000010");
        controller.setMyNFlag(1);
        controller.setMyZFlag(1);
        brleInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 2);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBRLE_NotBranch() {
        Controller controller = new Controller();
        BRLE brleInstruction = new BRLE("00000110","0000000000000010");
        controller.setMyNFlag(0);
        controller.setMyZFlag(0);
        brleInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 0);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBRLT() {
        Controller controller = new Controller();
        BRLT brltInstruction = new BRLT("00001000","0000000000000010");
        controller.setMyNFlag(1);
        brltInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 2);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBRLT_NotBranch() {
        Controller controller = new Controller();
        BRLT brltInstruction = new BRLT("00001000","0000000000000010");
        controller.setMyNFlag(0);
        brltInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 0);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBRNE() {
        Controller controller = new Controller();
        BRNE brneInstruction = new BRNE("00001000","0000000000000010");
        controller.setMyZFlag(0);
        brneInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 2);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }

    @Test
    public void testBRNE_NotBranch() {
        Controller controller = new Controller();
        BRNE brneInstruction = new BRNE("00001000","0000000000000010");
        controller.setMyZFlag(1);
        brneInstruction.execute(controller);

        assertEquals(controller.getMyProgramCounter(), 0);
        assertEquals(controller.getMyOperand(), "0000000000000010");
    }
}
