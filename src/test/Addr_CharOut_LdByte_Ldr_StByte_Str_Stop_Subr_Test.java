package test;
import controller.Controller;
import model.instructionType.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Addr_CharOut_LdByte_Ldr_StByte_Str_Stop_Subr_Test {
    @Test
    public void testadd() {
        Controller controller = new Controller();
        Addr a = new Addr("01110000","0000000000001010"); // AddA 10,i

        controller.setMyAccumulatorRegister("0000000000000100");


        a.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000001110");
        assertEquals(controller.getMyOperand(), "0000000000001010");

    }

    @Test
    public void testsub() {
        Controller controller = new Controller();
        Subr a = new Subr("10000000","0000000000001010"); // a 10,i


        controller.setMyAccumulatorRegister("0000000000000010");

        a.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000001000");
        assertEquals(controller.getMyOperand(), "0000000000001010");

    }

}
