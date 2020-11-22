package model.instructionType;


import controller.Controller;
import model.Converter;

public class CPr extends Instruction{


    public CPr(String myInstructionSpecifier, String myOperandSpecifier) {
        super(myInstructionSpecifier, myOperandSpecifier);
    }

    @Override
    public void execute(Controller theCon) {
        final int smallestDecimal = Converter.binToDec("1000000000000000");
        int valueInAccumulator = Converter.binToDec(theCon.getMyAccumulatorRegister());
        int valueInOperand = Converter.binToDec(getMyOperandSpecifier());
        int temp = valueInAccumulator - valueInOperand;

        if (temp < 0) {     // Set N flag to 1 if r - Oprnd < 0
            theCon.setMyNFlag(1);
            if (valueInAccumulator < 0 && (0 - valueInOperand) < 0 && temp >= smallestDecimal) {
                theCon.setMyCFlag(1);
            }
            if (temp < smallestDecimal) {
                theCon.setMyCFlag(1);      // If the result is less than the smallest number that can be
                theCon.setMyVFlag(1);      // represented by the signed 16-bit integer, set C, V flag to 1
            }
        } else if (temp == 0) {     // Set Z flag to 1 if r - Oprnd == 0
            theCon.setMyZFlag(1);
        }
        if (theCon.getMyNFlag() == 0 ^ theCon.getMyVFlag() == 0) {  // N is set based on N XOR V
            theCon.setMyNFlag(1);
        } else {
            theCon.setMyNFlag(0);
        }
    }
}
