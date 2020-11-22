package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * This class is the Character output instruction
 */

public class CharOut extends Instruction {
	public CharOut(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {
		theCon.setMyOperand(super.getMyOperandSpecifier());
		String operand = super.getMyOperandSpecifier();
		char character = (char) Converter.binToDec(operand);
		String result = ""+character;
		theCon.appendToMyOutput(result);
	}
}
