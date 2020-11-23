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
}
