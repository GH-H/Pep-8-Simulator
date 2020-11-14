
package model.instructionType;

import controller.Controller;

/**
 * This class is the LW instruction
 */

public class LW extends Instruction {

	public LW(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {

	}
}
