package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * Instruction bitwise ORs the contents of a register (which can be the Accumulator Register or the Index Register)
 * with an immediate value, direct memory location, or indirect memory location (i, d, n addressing modes).
 *
 * @version 16 November 2020
 */
public class ORr extends Instruction {

    public ORr(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    @Override
    public void execute(Controller theCon) {
        String instructionSpecifier = super.getMyInstructionSpecifier();
        String operandSpecifier = super.getMyOperandSpecifier();

        // Identifies addressing mode & gives final immediate value of operand.
        String operand1 = getOperandValue(theCon, instructionSpecifier, operandSpecifier);
        theCon.setMyOperand(operand1);

        // Pick whether to AND with the Accumulator or IndexRegister
        if (instructionSpecifier.charAt(4) == '0') { // AND with Accumulator
            String finalANDValue = bitwiseOR(operand1, theCon.getMyAccumulatorRegister());
            theCon.setMyAccumulatorRegister(finalANDValue);
            theCon.setMyNFlag(getNFlagFromBinary(finalANDValue));
            theCon.setMyZFlag(getZFlagFromBinary(finalANDValue));

        } else { // if (instructionSpecifier.charAt(7) == '1') // AND with IndexRegister
            String finalANDValue = bitwiseOR(operand1, theCon.getMyIndexRegister());
            theCon.setMyAccumulatorRegister(finalANDValue);
            theCon.setMyNFlag(getNFlagFromBinary(finalANDValue));
            theCon.setMyZFlag(getZFlagFromBinary(finalANDValue));
        }
    }

    private String bitwiseOR(final String theInput1, final String theInput2) {
        String output = "";
        try {
            if (theInput1.length() != theInput2.length()) {
                throw new IllegalArgumentException();
            }
            for (int index = 0; index < theInput1.length(); index++) {
                if (theInput1.charAt(index) == '0' && theInput2.charAt(index) == '0') {
                    output.concat("0");
                } else { // if the matching index locations are not both 0, the ORer sets the bit to 1.
                    output.concat("1");
                }
            }
        } catch (IllegalArgumentException iAE) {
            System.out.println("error occurred -- " + iAE + " ");
            iAE.printStackTrace();
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

    /**
     * Returns the operand's literal value from an instruction call by identifying it's addressing mode.
     * @return the operand's literal value from an instruction call.
     */
    public String getOperandValue(Controller theCon, final String theInstructionSpecifier, final String theOperandSpecifier) {
        String immediateValueOutput = ""; // Should be binary, 16-bits long
        String addressingMode = theInstructionSpecifier.substring(5); // Last 3 bits of instruction specifier

        if (addressingMode.equals("000")) { // Immediate value
            immediateValueOutput = theOperandSpecifier;
        } else if (addressingMode.equals("001")) {  // Direct addressing
            immediateValueOutput = getWordValueAtAddress(theCon, theOperandSpecifier); // Turn indirect address --> immediate value
        } else if (addressingMode.equals("010")) { // Indirect addressing
            String directAddress = getWordValueAtAddress(theCon, theOperandSpecifier); // Turn indirect address --> direct address
            immediateValueOutput = getWordValueAtAddress(theCon, directAddress); // Turn direct address --> immediate value
        }
        return immediateValueOutput;
    }

    /**
     * A helper method for getOperand().
     * It helps by inputting a binary representation of a direct address, then using that
     * direct address as a memory pointer to find the 16-bit-long immediate value that's stored
     * at that location.
     * @param theDirectAddress The 16-bit-long binary representation of a memory address location.
     * @return The 16-bit-long binary representation of the immediate value that was stored at the
     * direct address's pointed memory location.
     */
    private String getWordValueAtAddress(Controller theCon, String theDirectAddress) {
        // The Direct Address points to where the immediate value information is located.
        int addressLocation = Converter.binToDec(theDirectAddress);

        // Using the immediate value as a memory location pointer, retrieve the two adjacent
        // memory address bits to form the 16-bit binary representation of the immediate value.
        StringBuilder accessedImmediateValue = new StringBuilder();
        accessedImmediateValue.append(theCon.getMyMemoryDataAt(addressLocation));
        accessedImmediateValue.append(theCon.getMyMemoryDataAt(addressLocation + 1));
        return accessedImmediateValue.toString();
    }
}

