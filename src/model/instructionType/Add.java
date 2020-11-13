package model.instructionType;

import controller.Controller;

/**
 * This class is the add instruction
 */

public class Add implements Instruction {
	/** The destination register number. */
	private String myRd;

	/** The operandSpecifier value. */
	private String myOperand;

	/** The opcode of the instruction. */
	private String myOpcode;

	public Add(String myOpcode, String myRd, String operandSpecifier) {
		this.myRd = myRd;
		this.myOperand = operandSpecifier;
		this.myOpcode = myOpcode;
	}

	@Override
	public void execute(Controller theCon) {
//		int result = theCon.getAR() + Integer.parseInt(Converter.binToHex(myOperand), 16);
//
//		theCon.setRegister(result);
//		theCon.setPc(theCon.getPc()+1);


	}

	@Override
	public String getOpcode() {
		return myOpcode;
	}

	@Override
	public String getOperand() {
		return myOperand;
	}

	@Override
	public String getRegister() {
		return myRd;
	}

}
