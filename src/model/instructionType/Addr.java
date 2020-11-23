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
		String result;

		if (addressingMode.equals("0000")) { // Immediate value,ac
			result = Converter.decToBin(ac + operand,16);
			theCon.setMyAccumulatorRegister(result);
		} else if (addressingMode.equals("1000")) {  // Immediate value,index register
			result = Converter.decToBin(ac + operand,16);
			theCon.setMyIndexRegister(result);
		} else { //memory,direct
			int ir = Converter.binToDec(theCon.getMyIndexRegister());
			result = Converter.decToBin(ir + Converter.binToDec(theCon.getMyMemoryDataAt(operand)),16);
			theCon.setMyAccumulatorRegister(result);
		}
		theCon.setMyNFlag(NFlag(result));
		theCon.setMyZFlag(ZFlag(result));
		theCon.setMyVFlag(VFlag(ac,operand,Converter.binToDec(result)));
		theCon.setMyCFlag(CFlag(Converter.decToBin(ac,16),Converter.decToBin(operand,16),result));

	}

	private int NFlag(final String theBinary) {
		return Integer.parseInt(theBinary.substring(0,1));
	}

	private int ZFlag(final String theBinary) {
		int output = 0;
		if (theBinary.indexOf("1") == -1) { // There are no 1's in the binary string.
			output = 1;
		}
		return output;
	}

	private int VFlag(int o1, int o2,int result) {

		if((o1>0&&o2>0&&result<0)||(o1<0&&o2<0&&result>0)){
			return 1;
		}

		return 0;

	}

	private int CFlag(String o1,String o2,String result){
		int o1b = Integer.parseInt(o1.substring(0,1));
		int o2b = Integer.parseInt(o1.substring(0,1));
		int resultb = Integer.parseInt(o1.substring(0,1));
		if((o1b==1&&o1b==1&&resultb==0)||(o1b==0&&o1b==0&&resultb==1)){
			return 1;
		}
		return 0;
	}

}
