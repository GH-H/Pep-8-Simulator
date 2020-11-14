package model.instructionType;

import controller.Controller;


public class Stop extends Instruction {

	public Stop(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	void execute(Controller theCon) {

	}
}
