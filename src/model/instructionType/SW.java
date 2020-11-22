
package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * This class is the SW instruction
 */

public class SW extends Instruction {
	public SW(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {
		theCon.setMyOperand(super.getMyOperandSpecifier());
		int address =  Converter.binToDec(getMyOperandSpecifier());
		theCon.storeInMyMemory(address,theCon.getMyAccumulatorRegister());

	}
}
