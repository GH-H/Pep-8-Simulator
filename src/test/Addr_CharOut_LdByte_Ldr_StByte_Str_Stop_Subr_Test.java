package test;
import controller.Controller;
import model.instructionType.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Addr_CharOut_LdByte_Ldr_StByte_Str_Stop_Subr_Test {
    @Test
    public void testadd() {
        Controller controller = new Controller();
        Addr addA = new Addr("01110000","0000000000001010"); // AddA 32778,i

        // Artificially setup Accumulator to hold a value
        controller.setMyAccumulatorRegister("0000000000000100");

        // Execute and confirm that "1010" AND "1100" = "1000" for rightmost 4 bits in Operand Specifier.
        // Also confirm that N flag was set to 1.
        addA.execute(controller);
        assertEquals(controller.getMyAccumulatorRegister(), "0000000000001110");
        assertEquals(controller.getMyOperand(), "0000000000001010");

    }

}
