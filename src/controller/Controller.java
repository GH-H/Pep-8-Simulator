package controller;
import model.Converter;
import model.Decode;
import model.instructionType.Instruction;

public class Controller {
    /**
     *
     */
    private Decode myDecode;

    /**
     *
     */
    private MemoryUnit myMemory;

    /** Array of instruction memory. */
    private Instruction[] myInstructionMem;

    /**
     * Accumulator Register-----Binary String
     */
    private String myAR;

    /**
     * Index Register-----Binary String
     */
    private String myIndexRegister;

    /**
     * StackPointer
     */
    private int mySP;

    /**
     * ProgramCounter
     */
    private int myPC;

    /**
     * Instruction register-----current Instruction
     */
    private String myIR;

    /**
     *
     */
    private int myNFlag;

    /**
     *
     */
    private int myZFlag;

    /**
     *
     */
    private int myVFlag;

    /**
     *
     */
    private int myCFlag;

    /**
     * what the user wrote to the Input text area in the GUI
     */
    private String myInput;

    /**
     *what will be written in the Output text area in the GUI
     */
    private String Output;

    public Controller(){

        myInstructionMem = new Instruction[50];
        //initialize other fields......
        //
        //
        //
        //
    }

    public void loadObjectCodeIntoMemory(String obj){

    }

    public void setInput(){

    }

    /**
     *
     */
    public void run(){
        // restart PC
        myPC = 0;

        //loop through instruction memory
        execution:
        while (myPC < 50 && myInstructionMem[myPC] != null) {
            try {
                // execute current instruction
                myInstructionMem[myPC].execute(this);
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

    public MemoryUnit getMyMemory() {
        return myMemory;
    }

    public String getMyAR() {
        return myAR;
    }

    public String getMyIndexRegister() {
        return myIndexRegister;
    }

    public int getMySP() {
        return mySP;
    }

    public int getMyPC() {
        return myPC;
    }

    public String getMyIR() {
        return myIR;
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

    public String getOutput() {
        return Output;
    }


    public void setMyMemory(MemoryUnit myMemory) {
        this.myMemory = myMemory;
    }

    public void setMyAR(String myAR) {
        this.myAR = myAR;
    }

    public void setMyIndexRegister(String myIndexRegister) {
        this.myIndexRegister = myIndexRegister;
    }

    public void setMySP(int mySP) {
        this.mySP = mySP;
    }

    public void setMyPC(int myPC) {
        this.myPC = myPC;
    }

    public void setMyIR(String myIR) {
        this.myIR = myIR;
    }

    public void setMyNFlag(int myNFlag) {
        this.myNFlag = myNFlag;
    }

    public void setMyZFlag(int myZFlag) {
        this.myZFlag = myZFlag;
    }

    public void setMyVFlag(int myVFlag) {
        this.myVFlag = myVFlag;
    }

    public void setMyCFlag(int myCFlag) {
        this.myCFlag = myCFlag;
    }

    public void setMyInput(String myInput) {
        this.myInput = myInput;
    }

    public void setOutput(String output) {
        Output = output;
    }
}
