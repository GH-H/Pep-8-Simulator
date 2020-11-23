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

        theCon.setMyAccumulatorRegister(theCon.getMyMemoryDataAt(address));
    }


}
