package model.instructionType;

import controller.Controller;

/**
 * Instruction bitwise inverts the value in a register, which can include the Accumulator Register or the Index Register.
 *
 * @version 16 November 2020
 */
public class NOTr extends Instruction {

    public NOTr(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    @Override
    public void execute(Controller theCon) {
        String instructionSpecifier = super.getMyInstructionSpecifier();
        String operandSpecifier = super.getMyOperandSpecifier();

        if (instructionSpecifier.charAt(7) == '0') { // Invert Accumulator
            String invertedAccumulator = bitwiseInvert(theCon.getMyAccumulatorRegister());
            theCon.setMyAccumulatorRegister(invertedAccumulator);
            theCon.setMyNFlag(getNFlagFromBinary(invertedAccumulator));
            theCon.setMyZFlag(getZFlagFromBinary(invertedAccumulator));

        } else { // if (instructionSpecifier.charAt(7) == '1') // Invert Index Register
            String invertedInstructionRegister = bitwiseInvert(theCon.getMyInstructionRegister());
            theCon.setMyInstructionRegister(invertedInstructionRegister);
            theCon.setMyNFlag(getNFlagFromBinary(invertedInstructionRegister));
            theCon.setMyZFlag(getZFlagFromBinary(invertedInstructionRegister));
        }
    }

    private String bitwiseInvert(final String theInput) {
        String output = "";
        for(int index = 0; index < theInput.length(); index++) {
            if (theInput.charAt(index) == '1') {
                output.concat("0");
            } else { // if (theInput.charAt(index) == 0)
                output.concat("1");
            }
        }
        return output;
    }

    private int getNFlagFromBinary(final String theBinary) {
        return Integer.parseInt(theBinary.substring(0,1));
    }

    private int getZFlagFromBinary(final String theBinary) {
        int output = 0;
        if (theBinary.indexOf("1") == -1) { // There are no 1's in the binary string.
            output = 1;
        }
        return output;
    }
}

