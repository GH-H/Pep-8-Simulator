package model.instructionType;

import controller.Controller;

/**
 * This class is the Character input instruction
 */

public class CharIn extends Instruction {

	public CharIn(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	void execute(Controller theCon) {

	}
}