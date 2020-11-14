
package model.instructionType;

import controller.Controller;

/**
 * This class is the SW instruction
 */

public class SW extends Instruction {
	public SW(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {

	}
}
