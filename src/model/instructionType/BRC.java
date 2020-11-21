package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * Instruction branches to new PC location if the C flag is set to 1.
 * Includes i addressing mode.
 *
 * @version 21 November 2020
 */
public class BRC extends Instruction {

    public BRC(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    @Override
    public void execute(Controller theCon) {
        String instructionSpecifier = super.getMyInstructionSpecifier();
        String operandSpecifier = super.getMyOperandSpecifier();

        if (instructionSpecifier.charAt(7) == '0') { // Immediate Addressing: branch PC to operandSpecifier
            if (theCon.getMyCFlag() == 1) { // Set PC if C flag true.
                theCon.setMyProgramCounter(Converter.binToDec(operandSpecifier));
            }
            // Set any other relevant registers
            theCon.setMyOperand(operandSpecifier);
        }
        /* Skip x addressing mode since "i," "n," and "d" are the main 3 addressing modes focused on for this Pep/8 implementation.
         *
        else { // if (instructionSpecifier.charAt(7) == '1') - Indexing Addressing: branch PC to indexRegiseter + operandSpecifier
            int newAddress = Converter.binToDec(operandSpecifier) + Converter.binToDec(theCon.getMyIndexRegister());
            if (theCon.getMyCFlag() == 1) { // Set PC if C flag true.
                theCon.setMyProgramCounter(newAddress);
            }
            theCon.setMyOperand(decimalToBinary16BitExtender(newAddress));
        }
         */
    }

    // TODO: Make this method proper or use Converter.decToBin();
    // Temporary fix for Converter.decToBin() not returning a String.
    // Assumes that the binary address will be shorter than longer since it's unsigned memory locations that do not have math.
    // This is a bad assumption, and will either be fixed or the converter will be modified.
    private String decimalToBinary16BitExtender(int theDecimal) {
        String bitSignExtender;
        String output = "";
        if (theDecimal >= 0) {
            bitSignExtender = "0";
        } else { // if theDecimal is a negative number
            bitSignExtender = "1";
        }

        String unsignedBinary = Integer.toBinaryString(theDecimal);

        if (unsignedBinary.length() <= 15) {
            for (int i = 1; i <= 16 - unsignedBinary.length(); i++) {
                output.concat(bitSignExtender);
            }
            output.concat(unsignedBinary);
        } else {
            output = unsignedBinary;
        }
        return output;
    }
}

