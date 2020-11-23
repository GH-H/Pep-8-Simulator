package model.instructionType;

import controller.Controller;
import model.instructionType.Instruction;

/**
 * This class was design to negate 
 * the selected binary number 
 */
public class Negate extends Instruction{
   
	/** The destination register number. */
	private String myRd;
	
   
	/** Temporary char holder. */
	public char [] holder;

	public  Negate (String myInstructionSpecifier, String operandSpecifier, String myRd) {
		super(myInstructionSpecifier,operandSpecifier);
		this.myRd = myRd;
	}
    
	@Override
	public void execute(Controller theCon) {
	 holder = myRd.toCharArray();
     holder[0] = '1';
     
     theCon.setMyIndexRegister(String.valueOf(holder));
	}


}
