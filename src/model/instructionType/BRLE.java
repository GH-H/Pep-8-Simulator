package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * "Branch if less than or equals to" class for BRLE instruction.
 */
public class BRLE extends Instruction{

    public BRLE(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    /**
     * Modify PC to the jump address specified in operand specifier if the value stored in the accumulator is less
     * than or equal to 0.
     * @param theCon the controller instance
     */
    @Override
    public void execute(Controller theCon) {
        if (getMyInstructionSpecifier().charAt(7) == '0') {  // immediate mode
            if (theCon.getMyNFlag() == 1 && theCon.getMyZFlag() == 1) {
                theCon.setMyProgramCounter(Converter.binToDec(this.getMyOperandSpecifier()));
            }
        } else {
            if (theCon.getMyNFlag() == 1 && theCon.getMyZFlag() == 1) {
                int operand = Converter.binToDec(getMyOperandSpecifier());
                int dataInIndexRegister = Converter.binToDec(theCon.getMyIndexRegister());
                int targetAddress = Converter.binToDec(theCon.getMyMemoryDataAt(operand + dataInIndexRegister));
                theCon.setMyProgramCounter(targetAddress);
            }
        }
    }
}