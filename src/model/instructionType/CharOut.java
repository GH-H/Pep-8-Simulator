package model.instructionType;

import controller.Controller;

/**
 * This class is the Character output instruction
 */

public class CharOut extends Instruction {
	public CharOut(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	void execute(Controller theCon) {

	}
}
