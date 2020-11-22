package model.instructionType;

import controller.Controller;
import model.Converter;

public class StByte extends Instruction{
    public StByte(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    @Override
    public void execute(Controller theCon) {
        theCon.setMyOperand(super.getMyOperandSpecifier());

        String first = (theCon.getMyAccumulatorRegister().substring(0,9));
        int address =  Converter.binToDec(getMyOperandSpecifier());

        theCon.storeInMyMemory(address,first);
    }
}
