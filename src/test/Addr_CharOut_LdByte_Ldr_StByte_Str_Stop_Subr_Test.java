package test;
import controller.Controller;
import model.instructionType.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Addr_CharOut_LdByte_Ldr_StByte_Str_Stop_Subr_Test {
    @Test
    public void testAdd() {
        Controller controller = new Controller();
        Addr a = new Addr("01110000","0000000000001010"); // AddA 10,i

        controller.setMyAccumulatorRegister("0000000000000100");

        a.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000001110");
        assertEquals(controller.getMyOperand(), "0000000000001010");
    }

    @Test
    public void testSub() {
        Controller controller = new Controller();
        Subr a = new Subr("10000000","0000000000001010"); // a 10,i

        controller.setMyAccumulatorRegister("0000000000000010");

        a.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000001000");
        assertEquals(controller.getMyOperand(), "0000000000001010");
    }

    @Test
    public void testStop() {
        Controller controller = new Controller();
        Stop a = new Stop("00000000",null);

        a.execute(controller);
        assertEquals(controller.getMyRunIsExecuting(), false);
    }

    @Test
    public void testCharOut() {
        Controller controller = new Controller();
        CharOut a = new CharOut("01001000","0000000001100001");//a

        a.execute(controller);
        assertEquals(controller.getMyOutput(), "a");
    }

    @Test
    public void testStr() {
        Controller controller = new Controller();
        Str a = new Str("11100001","0000000000000001");//a
        controller.setMyAccumulatorRegister("0000000000000001");
        a.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(1), "00000000");
        assertEquals(controller.getMyMemoryDataAt(2), "00000001");
    }

    @Test
    public void testStByte() {
        Controller controller = new Controller();
        StByte a = new StByte("11100001","0000000000000001");//a
        controller.setMyAccumulatorRegister("0000000000000001");
        a.execute(controller);
        assertEquals(controller.getMyMemoryDataAt(1), "00000000");
        assertEquals(controller.getMyMemoryDataAt(2), "00000000");
    }

//    @Test
//    public void testLdr() {
//        Controller controller = new Controller();
//        StByte a = new StByte("11100001","0000000000000001");//a
//        Ldr b = new Ldr("11000001","0000000000000001");//a
//
//        controller.setMyAccumulatorRegister("0000000000000001");
//        a.execute(controller);
//
//        b.execute(controller);
//        assertEquals(controller.getMyAccumulatorRegister(), "0000000000000001");
//
//    }
}
