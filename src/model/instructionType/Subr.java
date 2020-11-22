package model.instructionType;

import controller.Controller;
import model.Converter;

/**
 * This class is the subtract instruction
 */

public class Subr extends Instruction {
	public Subr(String myInstructionSpecifier, String myOperandSpecifier) {
		super(myInstructionSpecifier, myOperandSpecifier);
	}

	@Override
	public void execute(Controller theCon) {
		String addressingMode = super.getMyInstructionSpecifier().substring(4);
		int ac =  Converter.binToDec(theCon.getMyAccumulatorRegister());
		int operand = Converter.binToDec(super.getMyOperandSpecifier());
		theCon.setMyOperand(super.getMyOperandSpecifier());

		if (addressingMode.equals("0000")) { // Immediate value,ac
			int result = ac - operand;
			theCon.setMyAccumulatorRegister(Converter.decToBin(result,16));
		} else if (addressingMode.equals("1000")) {  // Immediate value,index
			int result = ac - operand;
			theCon.setMyIndexRegister(Converter.decToBin(result,16));
		} else if (addressingMode.equals("0001")) { // ac addressing
			int ir = Converter.binToDec(theCon.getMyIndexRegister());
			int result = ir + Converter.binToDec(theCon.getMyMemoryDataAt(operand));
			theCon.setMyAccumulatorRegister(Converter.decToBin(result,16));
		}else{
			int ir = Converter.binToDec(theCon.getMyIndexRegister());
			int result = ir + Converter.binToDec(theCon.getMyMemoryDataAt(operand));
			theCon.setMyIndexRegister(Converter.decToBin(result,16));
		}
	}
}
