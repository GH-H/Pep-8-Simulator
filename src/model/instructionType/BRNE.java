package model.instructionType;

import controller.Controller;
import model.Converter;

public class BRNE extends Instruction{
    public BRNE(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    /**
     * Modify PC to the jump address specified in operand specifier if the value stored in the accumulator is not equal
     * to 0.
     * @param theCon
     */
    @Override
    public void execute(Controller theCon) {
        if(getMyInstructionSpecifier().charAt(7) == '0') {  // immediate mode
            if (theCon.getMyZFlag() == 0) {               // Get the value in accumulator and compare to 0
                theCon.setMyProgramCounter(Converter.binToDec(this.getMyOperandSpecifier())); // Jump if the value is less than or equal
            }
            theCon.setMyOperand(super.getMyOperandSpecifier());
        } else {
            if (theCon.getMyZFlag() == 0){
                int operand = Converter.binToDec(getMyOperandSpecifier());
                int dataInIndexRegister = Converter.binToDec(theCon.getMyIndexRegister());
                int targetAddress = Converter.binToDec(theCon.getMyMemoryDataAt(operand + dataInIndexRegister));
                theCon.setMyProgramCounter(targetAddress);
            }
            theCon.setMyOperand(super.getMyOperandSpecifier());
        }
    }
}
