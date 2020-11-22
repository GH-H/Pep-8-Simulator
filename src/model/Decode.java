
package model;

import model.instructionType.*;

/**
 * This class reads the input binary strings and assign them to correct
 * instruction obj
 */
public final class Decode {

	/**
	 * Decode the binary string and create the appropriate Instruction object.
	 * 
	 * @throws IllegalArgumentException if value for immediate is out of bound.
	 */
	public Instruction decodeInstruction(final String theInstructionRegister) throws IllegalArgumentException {
		Instruction instruction;

		if (theInstructionRegister.length() != 24) {
			throw new IllegalArgumentException("Entered InstructionRegister is not 24 bits long.");
		}
		String instructionSpecifier = theInstructionRegister.substring(0,8);
		String operandSpecifier = theInstructionRegister.substring(8);

		// Decode and create appropriate instruction
		switch (instructionSpecifier) {
			case "00000000": // STOP
				instruction = new Stop(instructionSpecifier, null);
				break;

			case "01110000"://i,ac
			case "01111000"://i,index
			case "01110001"://address,ac
				instruction = new Addr(instructionSpecifier, operandSpecifier);
				break;

			case "00010010":  // BRV i
				instruction = new BRV(instructionSpecifier, operandSpecifier);
				break;

			case "00010100":  // BRC i
				instruction = new BRC(instructionSpecifier, operandSpecifier);
				break;

			case "00011001":  // NOTX
			case "00011000":  // NOTA
				instruction = new NOTr(instructionSpecifier, operandSpecifier);
				break;

			case "10010010": // ANDA, n
			case "10010001": // ANDA, d
			case "10010000": // ANDA, i
				instruction = new ANDr(instructionSpecifier, operandSpecifier);
				break;

			case "10100010": // ORA, n
			case "10100001": // ORA, d
			case "10100000": // ORA, i
				instruction = new ORr(instructionSpecifier, operandSpecifier);
				break;
				
			default:
				throw new IllegalArgumentException("Instruction " + instructionSpecifier + " not supported.");
		}
		return instruction;
	}
}
			/* Old switch statement values that used to exist before reformatting
			case "01110":// instruction: add
				instruction = new Add(node[0]+ node[1],node[2]);
				break;

			case "11000":// instruction: load
				instruction = new LW(node[0]+node[1], node[2]);
				break;

			case "11100":// instruction: store
				instruction = new SW(node[0]+ node[1], node[2]);
				break;

			case "10000":// instruction: subtract
				instruction = new Sub(node[0]+node[1], node[2]);
				break;

			case "01001":// instruction: character input
				instruction = new CharIn(node[0]+node[1], node[2]);
				break;

			case "01010":// instruction: character output
				instruction = new CharOut(node[0]+ node[1], node[2]);
				break;
			*/