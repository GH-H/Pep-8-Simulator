package model.instructionType;

import controller.Controller;
import model.Converter;

public class LdByte extends Instruction{

    public LdByte(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    @Override
    public void execute(Controller theCon) {
        theCon.setMyOperand(super.getMyOperandSpecifier());
        int address = Converter.binToDec(super.getMyOperandSpecifier());
        String result = theCon.getMyMemoryDataAt(address);
        theCon.setMyAccumulatorRegister(result);
        theCon.setMyNFlag(NFlag(result));
        theCon.setMyZFlag(ZFlag(result));

    }

    private int NFlag(final String theBinary) {
        return Integer.parseInt(theBinary.substring(0,1));
    }

    private int ZFlag(final String theBinary) {
        int output = 0;
        if (theBinary.indexOf("1") == -1) { // There are no 1's in the binary string.
            output = 1;
        }
        return output;
    }
}
