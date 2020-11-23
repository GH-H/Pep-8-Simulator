
package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * This class is the LW instruction
 */

public class Ldr extends Instruction {

	public Ldr(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {
		theCon.setMyOperand(super.getMyOperandSpecifier());
		int address = Converter.binToDec(super.getMyOperandSpecifier());

		theCon.setMyAccumulatorRegister(theCon.getMyMemoryDataAt(address)+ theCon.getMyMemoryDataAt(address+1));
	}
}
