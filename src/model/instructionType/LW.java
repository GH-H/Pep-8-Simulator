
package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * This class is the LW instruction
 */

public class LW extends Instruction {

	public LW(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {
		theCon.setMyOperand(super.getMyOperandSpecifier());
		int address = Converter.binToDec(super.getMyOperandSpecifier());

		theCon.setMyAccumulatorRegister(theCon.getMyMemoryDataAt(address));
	}
}
