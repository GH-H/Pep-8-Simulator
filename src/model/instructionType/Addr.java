package model.instructionType;

import model.Converter;
import controller.Controller;

/**
 * This class is the add instruction
 */

public class Addr extends Instruction {

	public Addr(String myInstructionSpecifier, String operandSpecifier) {
		super(myInstructionSpecifier, operandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {

		String addressingMode = super.getMyInstructionSpecifier().substring(4);
		int ac =  Converter.binToDec(theCon.getMyAccumulatorRegister());
		int operand = Converter.binToDec(super.getMyOperandSpecifier());
		theCon.setMyOperand(super.getMyOperandSpecifier());

		if (addressingMode.equals("0000")) { // Immediate value,ac
			int result = ac + operand;
			theCon.setMyAccumulatorRegister(Converter.decToBin(result,16));
		} else if (addressingMode.equals("1000")) {  // Immediate value,index register
			int result = ac + operand;
			theCon.setMyIndexRegister(Converter.decToBin(result,16));
		} else { //memory,direct
			int ir = Converter.binToDec(theCon.getMyIndexRegister());
			int result = ir + Converter.binToDec(theCon.getMyMemoryDataAt(operand));
			theCon.setMyAccumulatorRegister(Converter.decToBin(result,16));
		}

	}
}
