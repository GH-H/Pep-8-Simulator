package controller;

import model.Decode;
import model.MemoryUnit;
import model.instructionType.Instruction;

public class Controller {
    /**
     * The default starting memory location of the Stack Pointer.
     */
    private static final int MY_STACK_POINTER_START = 64463;

    /**
     * The default starting memory location of the Program Counter.
     */
    private static final int MY_PROGRAM_COUNTER_START = 0;

    /**
     * The default starting value for the Index Register.
     */
    private static final String MY_INDEX_REGISTER_START = "0000000000000000";

    /**
     *  The object used to read a 24-bit binary instruction to create the correct correlating Instruction-inherited object.
     */
    private final Decode myDecode;

    /**
     *  The Memory where instructions and data are loaded and read from when Pep/8 is running.
     *  Instructions are stored in consecutive locations (typically from 0x0 to 0xn),
     *  while data can be stored elsewhere (typically somewhere after instructions).
     *  (Each element is in binary format, 8 bits)
     */
    private MemoryUnit myMemory;

    /**
     * The register which stores a 16-bit value that is often used for modifying arithmetic operations on the side.
     * (binary format, 16 bits)
     */
    private String myAccumulatorRegister;

    /**
     * The index register value.
     * I think that since the accumulator is the only register that we're using, this value stays at 0x0000 for the
     * whole program (thus it is set to final to prevent accidental reassignment).
     */
    private final String myIndexRegister;

    /**
     * The current memory address location of the top of the stack.
     * (decimal format)
     */
    private int myStackPointer;

    /**
     * The current memory address location of the next instruction to be processed.
     * (decimal format)
     */
    private int myProgramCounter;

    /**
     * The register that stores the binary representation of the entire current instruction to be processed.
     * (binary format, 24-bits)
     */
    private String myInstructionRegister;

    /**
     * The immediate operand value.
     * This is different from the operand specifier in that the operand specifier can be shown in many different
     * addressing modes, while the operand is always the immediate value of that operand specifier's addressing mode.
     * (binary format, 16-bits) (Should be set to an empty string if not relevant to current instruction call).
     */
    private String myOperand;

    /**
     * Negative Flag
     * Holds a 1 if the previous instruction generated a negative value, and holds a 0 otherwise.
     */
    private int myNFlag;

    /**
     * Zero Flag
     * Holds a 1 if the previous instruction generated a zero as a value, and holds a 0 otherwise.
     */
    private int myZFlag;

    /**
     * Overflow Flag
     * Holds a 1 if the previous instruction caused a signed overflow, and holds a 0 otherwise.
     *
     * An overflow can look like a positiveNum + positiveNum = negativeNum,
     * negativeNum + negativeNum = positiveNum,
     * positiveNum - negativeNum = negativeNum,
     * or negativeNum - positiveNum = positiveNum
     */
    private int myVFlag;

    /**
     * Holds a 1 if the previous instruction produced a carry value (like borrowing on subtraction),
     * and holds a 0 otherwise.
     */
    private int myCFlag;

    /**
     * What the user wrote to the Input text area in the GUI
     */
    private String myInput;

    /**
     * What will be written in the Output text area in the GUI
     */
    private String myOutput;

    /**
     * The boolean state of if the program should be executing more instructions or should stop.
     * True means the program should continue to read instructions from memory.
     * False means the program should stop running.
     */
    private boolean myRunIsExecuting;

    public Controller(){
        myDecode = new Decode();
        myMemory = new MemoryUnit();
        myAccumulatorRegister = "";
        myIndexRegister = MY_INDEX_REGISTER_START;
        myStackPointer = MY_STACK_POINTER_START;
        myProgramCounter = MY_PROGRAM_COUNTER_START;
        myInstructionRegister = "";
        myOperand = "";
        myNFlag = 0;
        myZFlag = 0;
        myVFlag = 0;
        myCFlag = 0;
        myInput = "";
        myOutput = "";
        myRunIsExecuting = false;
    }

    public void loadObjectCodeIntoMemory(String obj){

    }

    /**
     *  Resets the registers, pointers, flags, and input and output values to default values for a new program run.
     */
    private void resetFieldsForStart() {
        myAccumulatorRegister = "";
        myStackPointer = MY_STACK_POINTER_START;
        myProgramCounter = MY_PROGRAM_COUNTER_START;
        myInstructionRegister = "";
        myOperand = "";
        myNFlag = 0;
        myZFlag = 0;
        myVFlag = 0;
        myCFlag = 0;
        myInput = "";
        myOutput = "";
        myRunIsExecuting = true;
    }

    /**
     *
     */
    public void run(){
        // Reset starting values.
        resetFieldsForStart();

        // TODO: LOAD MEMORY
        execution:
        while (myRunIsExecuting) {
            try {
                // 1) Fetch Cycle: Read current instruction to MyInstructionRegister from current PC,
                // then increment PC by 3.
                StringBuilder instructionRegister = new StringBuilder();
                instructionRegister.append(myMemory.getDataAt(myProgramCounter));
                instructionRegister.append(myMemory.getDataAt(myProgramCounter+1));
                instructionRegister.append(myMemory.getDataAt(myProgramCounter+2));
                myInstructionRegister = instructionRegister.toString();

                myProgramCounter += 3;

                // 2) Decode Cycle: The type of instruction is decoded
                Instruction currentInstruction = myDecode.decodeInstruction(myInstructionRegister);

                // 3) Access Memory, 4) Execute, and 5) Store Cycle:
                // The .execute() method modifies Memory locations and Controller fields as necessary.
                currentInstruction.execute(this);
            } catch (IllegalArgumentException e) {
                System.out.println("error occurred -- " + e + " ");
                e.printStackTrace();
                break execution;
            }
        }
    }

    public void clearMemory(){

    }

    /////////////////////////////set & get methods below//////////////////////////

    // get methods:
    public MemoryUnit getMyMemory() {
        return myMemory;
    }

    public String getMyAccumulatorRegister() {
        return myAccumulatorRegister;
    }

    public String getMyIndexRegister() {
        return myIndexRegister;
    }

    public int getMyStackPointer() {
        return myStackPointer;
    }

    public int getMyProgramCounter() {
        return myProgramCounter;
    }

    public String getMyInstructionRegister() {
        return myInstructionRegister;
    }

    public int getMyNFlag() {
        return myNFlag;
    }

    public int getMyZFlag() {
        return myZFlag;
    }

    public int getMyVFlag() {
        return myVFlag;
    }

    public int getMyCFlag() {
        return myCFlag;
    }

    public String getMyInput() {
        return myInput;
    }

    public String getMyOutput() {
        return myOutput;
    }

    public boolean getMyRunIsExecuting() {
        return myRunIsExecuting;
    }

    public String getMyOperand() {
        return myOperand;
    }

    // set methods:
    public void setMyMemory(MemoryUnit theMemory) {
        this.myMemory = theMemory;
    }

    public void setMyAccumulatorRegister(String theAccumulatorRegister) {
        this.myAccumulatorRegister = theAccumulatorRegister;
    }

    public void setMyStackPointer(int theStackPointer) {
        this.myStackPointer = theStackPointer;
    }

    public void setMyProgramCounter(int theProgramCounter) {
        this.myProgramCounter = theProgramCounter;
    }

    public void setMyInstructionRegister(String theInstructionRegister) {
        this.myInstructionRegister = theInstructionRegister;
    }

    public void setMyNFlag(int theNFlag) {
        this.myNFlag = theNFlag;
    }

    public void setMyZFlag(int theZFlag) {
        this.myZFlag = theZFlag;
    }

    public void setMyVFlag(int theVFlag) {
        this.myVFlag = theVFlag;
    }

    public void setMyCFlag(int theCFlag) {
        this.myCFlag = theCFlag;
    }

    public void setMyInput(String theInput) {
        this.myInput = theInput;
    }

    public void setMyOutput(String theOutput) {
        this.myOutput = theOutput;
    }

    public void setMyRunIsExecuting(Boolean theRunIsExecuting) {
        this.myRunIsExecuting = theRunIsExecuting;
    }

    public void setMyOperand(String theOperand) {
        this.myOperand = theOperand;
    }
}
