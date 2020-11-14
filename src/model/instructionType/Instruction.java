package model.instructionType;

import controller.Controller;

/**
 * This class is the interface of all instructions
 *
 */

public abstract class Instruction {

	/**
	 * InstructionSpecifier values
	 */
	private String myInstructionSpecifier;

	/** The operandSpecifier value. */
	private String myOperandSpecifier;


	public Instruction(String myInstructionSpecifier, String myOperandSpecifier) {
		this.myInstructionSpecifier = myInstructionSpecifier;
		this.myOperandSpecifier = myOperandSpecifier;
	}

	abstract void execute(Controller theCon);

	public String getMyInstructionSpecifier() {
		return myInstructionSpecifier;
	}

	public String getMyOperandSpecifier() {
		return myOperandSpecifier;
	}
}
