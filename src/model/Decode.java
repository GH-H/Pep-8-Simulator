
package model;
import model.Converter;

import model.instructionType.*;

/**
 * This class reads the input binary strings and assign them to correct
 * instruction obj
 * 
 */
public final class Decode {

	/**
	 * Decode the binary string and create the appropriate Instruction object.
	 * 
	 * @throws IllegalArgumentException if value for immediate is out of bound.
	 */
	public Instruction decodeInstruction(final String hex) throws IllegalArgumentException {
		Instruction instruction;
		String[] node;
		String theString = Converter.hexToBinary(hex).replace(" " , "");
		System.out.println(Converter.hexToBinary(hex));
		node = new String[3];
		node[0] = theString.substring(0, 5);// assign opcode to node 0
		node[1] = theString.substring(6, 8);// assign register to node 1
		
		// Decode and create appropriate instruction
		switch (node[0]) {
		case "01110":// instruction: add
			node[2] = theString.substring(8, 24);// assign operand specifier to node 2
			instruction = new Add(node[0], node[1], node[2]);
			break;
		case "11000":// instruction: load
			node[2] = theString.substring(8, 24);// assign operand specifier to node 2
			instruction = new LW(node[0], node[1], node[2]);
			break;
		case "11100":// instruction: store
			node[2] = theString.substring(8, 24);// assign operand specifier to node 2
			instruction = new SW(node[0], node[1], node[2]);
			break;
		case "10000":// instruction: subtract
			node[2] = theString.substring(8, 24);// assign operand specifier to node 2
			instruction = new Sub(node[0], node[1], node[2]);
			break;
		case "01001":// instruction: character input
			node[2] = theString.substring(8, 24);// assign operand specifier to node 2
			instruction = new CharIn(node[0], node[1], node[2]);
			break;
		case "01010":// instruction: character output
			node[2] = theString.substring(8, 24);// assign operand specifier to node 2
			instruction = new CharOut(node[0], node[1], node[2]);
			break;
		case "00000"://	instruction: stop
			instruction = new Stop(node[0], node[1]);
			break;
		
		default:
			throw new IllegalArgumentException("Instruction " + node[0] + " not supported.");
		}
		return instruction;

	}
}
