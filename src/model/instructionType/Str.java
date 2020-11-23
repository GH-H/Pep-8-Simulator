
package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * This class is the SW instruction
 */

public class Str extends Instruction {
	public Str(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {
		theCon.setMyOperand(super.getMyOperandSpecifier());

		String first = (theCon.getMyAccumulatorRegister().substring(0,8));
		String second = (theCon.getMyAccumulatorRegister().substring(8));
		int address =  Converter.binToDec(getMyOperandSpecifier());

		theCon.storeInMyMemory(address,first);
		theCon.storeInMyMemory(address+1,second);

	}
}
