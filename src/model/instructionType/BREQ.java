package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * "Branch if equals to" class for BREQ instruction.
 */
public class BREQ extends Instruction {

    public BREQ(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    /**
     * Modify PC to the jump address specified in operand specifier if the value
     * stored in the accumulator is equal to 0.
     * @param theCon the controller instance
     */
    @Override
    public void execute(Controller theCon) {
        if (getMyInstructionSpecifier().charAt(7) == '0') {  // immediate mode
            if (Converter.binToDec(theCon.getMyAccumulatorRegister()) == 0) {               // Get the value in accumulator and compare to 0
                theCon.setMyProgramCounter(Converter.binToDec(this.getMyOperandSpecifier())); // Jump if the value is less than or equal
            }
        } else {
            if (Converter.binToDec(theCon.getMyAccumulatorRegister()) == 0) {
                int operand = Converter.binToDec(getMyOperandSpecifier());
                int dataInMemoryAddress =
                        Converter.binToDec(theCon.getMyIndexRegister());
                theCon.setMyProgramCounter(operand + dataInMemoryAddress);
            }
        }
    }
}
