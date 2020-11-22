package model.instructionType;

import controller.Controller;
import model.Converter;

import static model.Converter.*;

/**
 * Instruction translates an input character from the user into an ASCII number value,
 * then stores it in a single byte location in memory (d and n addressing modes).
 *
 * @version 21 November 2020
 */

public class CharIn extends Instruction {

	public CharIn(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {
		String instructionSpecifier = super.getMyInstructionSpecifier();
		String operandSpecifier = super.getMyOperandSpecifier();

		// Convert first char from input into 8-bit binary representation.
		char inputChar = theCon.getAndRemoveFirstCharFromMyInput();
		String inputCharBinary = decToBin(inputChar, 8); // Character binary value to input in memory byte

		// Write 8-bit character to memory byte location.
		if (instructionSpecifier.substring(5).equals("001")) {	// Direct addressing mode (d)
			theCon.storeInMyMemory(Converter.binToDec(operandSpecifier), inputCharBinary);
		} else if (instructionSpecifier.substring(5).equals("010")) {	// Indirect Addressing mode (n)
			String directAddress = getWordValueAtAddress(theCon, operandSpecifier);
			theCon.storeInMyMemory(Converter.binToDec(directAddress), inputCharBinary);
		}

		// Set myOperand in Controller
		inputCharBinary = "00000000" + inputCharBinary;
		theCon.setMyOperand(inputCharBinary);
	}

	/**
	 * Retrieves a word value in binary that was stored at a memory address.
	 *
	 * @param theAddress The 16-bit-long binary representation of a memory address location.
	 * @return The 16-bit-long binary representation of the immediate value that was stored at the
	 * address's pointed memory location.
	 */
	private String getWordValueAtAddress(Controller theCon, String theAddress) {
		// The Direct Address points to where the immediate value information is located.
		int addressLocation = Converter.binToDec(theAddress);

		// Using the immediate value as a memory location pointer, retrieve the two adjacent
		// memory address bits to form the 16-bit binary representation of the immediate value.
		StringBuilder accessedImmediateValue = new StringBuilder();
		accessedImmediateValue.append(theCon.getMyMemoryDataAt(addressLocation));
		accessedImmediateValue.append(theCon.getMyMemoryDataAt(addressLocation + 1));
		return accessedImmediateValue.toString();
	}
}