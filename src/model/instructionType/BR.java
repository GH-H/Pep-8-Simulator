package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * Branch class for BR instruction
 */
public class BR extends Instruction{

    public BR(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    /**
     * Modify PC to the jump address specified in immediate mode or index mode
     * @param theCon
     */
    @Override
    public void execute(Controller theCon) {
        if(getMyInstructionSpecifier().charAt(7) == '0') {
            int jumpAddress = Converter.binToDec(this.getMyOperandSpecifier());
            theCon.setMyProgramCounter(jumpAddress);
            theCon.setMyOperand(super.getMyOperandSpecifier());
        } else {
            int operand = Converter.binToDec(getMyOperandSpecifier());
            int dataInIndexRegister = Converter.binToDec(theCon.getMyIndexRegister());
            int targetAddress = Converter.binToDec(theCon.getMyMemoryDataAt(operand + dataInIndexRegister));
            theCon.setMyProgramCounter(targetAddress);
            theCon.setMyOperand(super.getMyOperandSpecifier());
        }
    }
}
