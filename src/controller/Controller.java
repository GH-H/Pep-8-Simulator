package controller;

import model.Converter;
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
     * A starting value for registers and CPU states that require 16-bit placeholders
     */
    private static final String ZEROED_24_BITS = "000000000000000000000000";

    /**
     * A starting value for registers and CPU states that require 16-bit placeholders
     */
    private static final String ZEROED_16_BITS = "0000000000000000";

    /**
     * A starting value for registers and CPU states that require 16-bit placeholders
     */
    private static final String ZEROED_8_BITS = "00000000";

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
     * (binary format, 16 bits)
     */
    private String myIndexRegister;

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
     * This is the word-label String that displays the name of the current instruction running for the GUI to display.
     * This is set in the Decoder class.
     */
    private String myInstructionWordLabel;

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
        myAccumulatorRegister = ZEROED_16_BITS;
        myIndexRegister = ZEROED_16_BITS;
        myStackPointer = MY_STACK_POINTER_START;
        myProgramCounter = MY_PROGRAM_COUNTER_START;
        myInstructionRegister = ZEROED_24_BITS;
        myOperand = "";
        myInstructionWordLabel = "";
        myNFlag = 0;
        myZFlag = 0;
        myVFlag = 0;
        myCFlag = 0;
        myInput = "";
        myOutput = "";
        myRunIsExecuting = false;
    }

    /**
     * This method takes in a raw string from the GUI that contains what the user inputted for the object code, then translates
     * the object code into binary to store into the respective MyMemory locations.
     *
     * @param theObjectCode The raw string from the GUI that contains what the user inputted for the object code. May contain
     *                      format specifiers like "\n" and "\t".
     */
    public void loadObjectCodeIntoMemory(final String theObjectCode) {
        // Remove newline and tab formatters, as well as space characters.
        String objCode = theObjectCode.replaceAll("\n","");
        objCode = objCode.replaceAll("\t","");
        objCode = objCode.replaceAll(" ","");
        objCode = objCode.toLowerCase();
        // objCode now has no formatters and is all lowercase.

        try {
            if (objCode.indexOf("zz") == -1) { // No zz cancel code present? Throw error.
                throw new IllegalArgumentException("Object code does not have a \"zz\" cancel condition. " +
                        "Object code failed to load to Memory.");
            }
            int index = 0;
            boolean zzNotReadYet = true;
            while (zzNotReadYet) {
                if (charIsLowercaseHexValue(objCode.charAt(index))
                        && charIsLowercaseHexValue(objCode.charAt(index + 1))) { // If two adjacent chars are valid hex values.
                    String twoHexChars = objCode.substring(index, index+2);
                    String binary = Converter.hexToBin(twoHexChars);
                    if (binary.length() < 8) {  // Make sure binary string is 8 length before writing to memory.
                        int binaryStartLength = binary.length();
                        for (int i = 0; i < 8 - binaryStartLength; i++) {
                            binary = "0" + binary;
                        }
                    }
                    storeInMyMemory(index/2, binary);
                    index += 2;
                } else if (objCode.charAt(index) == 'z' && objCode.charAt(index) == 'z') { // If two adjacent chars are the exit condition.
                    zzNotReadYet = false;
                } else { // If the object code is a non-valid format.
                    myMemory.clearMyMemory();
                    throw new IllegalArgumentException("Object code is not written in a valid hexadecimal format. " +
                            "Object code failed to load to Memory.");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void run(String theUserInput){
        // Reset starting values.
        resetFieldsForStart();
        setMyInput(theUserInput);

        execution:
        while (myRunIsExecuting) {
            try {
                // 1) Fetch Cycle: Read current instruction to myInstructionRegister from current PC,
                // then increment PC by 3.
                StringBuilder instructionRegister = new StringBuilder();
                // If memory contains data at locations, append it to the myInstructionRegister.
                if (myMemory.getDataAt(myProgramCounter) != null) {
                    instructionRegister.append(myMemory.getDataAt(myProgramCounter));
                } else {
                    instructionRegister.append(ZEROED_8_BITS);
                }
                if (myMemory.getDataAt(myProgramCounter + 1) != null) {
                    instructionRegister.append(myMemory.getDataAt(myProgramCounter + 1));
                } else {
                    instructionRegister.append(ZEROED_8_BITS);
                }
                if (myMemory.getDataAt(myProgramCounter + 2) != null) {
                    instructionRegister.append(myMemory.getDataAt(myProgramCounter + 2));
                } else {
                    instructionRegister.append(ZEROED_8_BITS);
                }
                myInstructionRegister = instructionRegister.toString();

                myProgramCounter += 3;

                // 2) Decode Cycle: The type of instruction is decoded
                Instruction currentInstruction = myDecode.decodeInstruction(this, myInstructionRegister);

                // 3) Access Memory, 4) Execute, and 5) Store Cycle:
                // The .execute() method modifies Memory locations and Controller fields as necessary.
                if (currentInstruction != null) {
                    currentInstruction.execute(this);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("error occurred -- " + e + " ");
                e.printStackTrace();
                break execution;
            }
        }
    }

    /**
     * A boolean checker that returns true if a char is a valid hexadecimal value. That means
     * the character ranges from 1-9, A-F, or a-f. Otherwise, returns false.
     *
     * @param theChar The character to be checked if it ranges from 1-9, A-F, or a-f.
     * @return True if the character ranges from 1-9, A-F, or a-f, false otherwise.
     */
    private boolean charIsLowercaseHexValue(char theChar) {
        boolean isHexValue = true;
        if (theChar < '0'
                || (theChar > '9' && theChar < 'A')
                || (theChar > 'F' && theChar < 'a')
                || (theChar > 'f')) {
            isHexValue = false;
        }
        return isHexValue;
    }

    /**
     *  Resets the registers, pointers, flags, and input and output values to default values for a new program run.
     */
    private void resetFieldsForStart() {
        myAccumulatorRegister = ZEROED_16_BITS;
        myIndexRegister = ZEROED_16_BITS;
        myStackPointer = MY_STACK_POINTER_START;
        myProgramCounter = MY_PROGRAM_COUNTER_START;
        myInstructionRegister = ZEROED_24_BITS;
        myOperand = "";
        myInstructionWordLabel = "";
        myNFlag = 0;
        myZFlag = 0;
        myVFlag = 0;
        myCFlag = 0;
        myInput = "";
        myOutput = "";
        myRunIsExecuting = true;
    }

    /**
     * Boolean checker for if any-length binary String is made up of only 0 and 1 chars.
     * Returns true if all characters in theBinaryInput are 0 or 1, returns false otherwise.
     *
     * @param theBinaryInput The binary string to check if all chars are either 0 or 1.
     * @return True if all characters in theBinaryInput are 0 or 1, returns false otherwise.
     */
    private boolean isBinaryString(String theBinaryInput) {
        boolean output = true;
        for (int i = 0; i < theBinaryInput.length(); i++) {
            if (theBinaryInput.charAt(i) != '0' && theBinaryInput.charAt(i) != '1') {
                output = false;
            }
        }
        return output;
    }

    /////////////////////////////set & get methods below//////////////////////////

    // get methods:
    /**
     * Gets the 8-bit, binary-format String data that's stored at a specified location in MyMemory.
     *
     * @param myMemoryIndex The MyMemory address location of the 8-bit, binary-format String data to be retrieved.
     * @return The 8-bit, binary-format String data that was stored at a MyMemory address location.
     */
    public String getMyMemoryDataAt(int myMemoryIndex) {
        String output = "";
        try {
            if (myMemoryIndex >= myMemory.getTotalMemoryLocations() || myMemoryIndex < 0) {
                throw new IllegalArgumentException("Memory location out of bounds: " +
                        "Tried to access memory location " + myMemoryIndex + " while indexes only range from " +
                        "0-" + (myMemory.getTotalMemoryLocations()-1) + ".");
            }
            output = myMemory.getDataAt(myMemoryIndex);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Returns the entire contents of the Memory in String[] form.
     *
     * @return the entire contents of the Memory in String[] form.
     */
    public String[] getMyMemoryFullDump() {
        return myMemory.getMyMemory();
    }

    /**
     * Returns the total number of memory locations in MyMemory.
     *
     * @return The total number of memory locations in MyMemory.
     */
    public int getMyMemoryTotalLocations() { return myMemory.getTotalMemoryLocations(); }

    /**
     * Returns the 16-bit value stored in myAccumulatorRegister.
     *
     * @return the 16-bit value stored in myAccumulatorRegister.
     */
    public String getMyAccumulatorRegister() {
        return myAccumulatorRegister;
    }

    /**
     * Returns the 16-bit value stored in myIndexRegister.
     *
     * @return The 16-bit value stored in myIndexRegister.
     */
    public String getMyIndexRegister() {
        return myIndexRegister;
    }

    /**
     * Returns the current location of the Stack Pointer.
     *
     * @return The current location of the Stack Pointer.
     */
    public int getMyStackPointer() {
        return myStackPointer;
    }

    /**
     * Returns the current location of the Program Counter.
     *
     * @return The current location of the Program Counter.
     */
    public int getMyProgramCounter() {
        return myProgramCounter;
    }

    /**
     * Returns the 24-bit binary value of the currently running instruction.
     *
     * @return The 24-bit binary value of the currently running instruction.
     */
    public String getMyInstructionRegister() {
        return myInstructionRegister;
    }

    /**
     * Returns a String that details in words what instruction is currently executing for the GUI to display.
     *
     * @return Returns a String that details in words what instruction is currently executing.
     */
    public String getMyInstructionWordLabel() {
        return myInstructionWordLabel;
    }

    /**
     * Returns whether the N flag is currently set to 0 or 1.
     *
     * @return Whether the N flag is currently set to 0 or 1.
     */
    public int getMyNFlag() {
        return myNFlag;
    }

    /**
     * Returns whether the Z flag is currently set to 0 or 1.
     *
     * @return Whether the z flag is currently set to 0 or 1.
     */
    public int getMyZFlag() {
        return myZFlag;
    }

    /**
     * Returns whether the V flag is currently set to 0 or 1.
     *
     * @return Whether the V flag is currently set to 0 or 1.
     */
    public int getMyVFlag() {
        return myVFlag;
    }

    /**
     * Returns whether the C flag is currently set to 0 or 1.
     *
     * @return Whether the C flag is currently set to 0 or 1.
     */
    public int getMyCFlag() {
        return myCFlag;
    }

    /**
     * Returns a string representation of the user's input characters.
     * Format specifiers such as "/n" and "/t" may be present.
     *
     * @return A string representation of the user's input characters.
     */
    public String getMyInput() {
        return myInput;
    }

    /**
     * Returns a string representation of the program's characters to output to the user.
     *
     * @return A string representation of the program's characters to output to the user.
     */
    public String getMyOutput() {
        return myOutput;
    }

    /**
     * Returns true if instructions are currently being read and ran by the program, and false otherwise.
     *
     * @return True if instructions are currently being read and ran by the program, and false otherwise.
     */
    public boolean getMyRunIsExecuting() {
        return myRunIsExecuting;
    }

    /**
     * Returns the operand value of the instruction when relevant. Can either return a 16-bit
     * binary String value or an empty String if the Operand is not relevant to the current instruction.
     *
     * @return either a 16-bit value representing an operand, or an empty string, depending on the instruction.
     */
    public String getMyOperand() {
        return myOperand;
    }

    // set methods:

    /**
     * Store a new 8-bit, binary format String value at an address location in MyMemory.
     *
     * @param theIndex The address location of where the new data should be stored.
     * @param theData The 8-bit, binary format String value that should be stored at the
     *                specified address location.
     */
    public void storeInMyMemory(int theIndex, String theData) {
        try {
            if (theIndex < 0 || theIndex >= myMemory.getTotalMemoryLocations()) {
                throw new IllegalArgumentException("Memory location out of bounds: " +
                        "Tried to store at memory location " + theIndex + " while indexes only range from " +
                        "0-" + (myMemory.getTotalMemoryLocations()-1) + ".");
            }
            if (theData.length() != 8 || !isBinaryString(theData)) {
                throw new IllegalArgumentException("Tried to store data to Memory that either " +
                        "contains non-binary characters or is not 8 bits long.");
            }
            myMemory.storeAt(theIndex, theData);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Method called by the GUI to clear the values stored in memory as well as reset the CPU states.
     */
    public void clearMyMemoryAndResetCPUFields() {
        myMemory.clearMyMemory();
        resetFieldsForStart();
        myRunIsExecuting = false; // Only difference needed from resetFieldsForStart() method.
    }

    /**
     * Sets the Accumulator Register to a new 16-bit binary value.
     *
     * @param theAccumulatorRegister The new value to override the current Accumulator Register value.
     */
    public void setMyAccumulatorRegister(String theAccumulatorRegister) {
        try {
            if (theAccumulatorRegister.length() != 16 || !isBinaryString(theAccumulatorRegister)) {
                System.out.println(theAccumulatorRegister.length() != 16);
                System.out.println(!isBinaryString(theAccumulatorRegister));
                throw new IllegalArgumentException("Tried to set the Accumulator Register to a value that contained " +
                        "non-binary characters or was not 16-bits long.");
            }
            this.myAccumulatorRegister = theAccumulatorRegister;
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Sets the Index Register to a new 16-bit binary value.
     *
     * @param theIndexRegister The new value to override the current Index Register value.
     */
    public void setMyIndexRegister(String theIndexRegister) {
        try {
            if (theIndexRegister.length() != 16 || !isBinaryString(theIndexRegister)) {
                throw new IllegalArgumentException("Tried to set the Index Register to a value that contained " +
                        "non-binary characters or was not 16-bits long.");
            }
            this.myIndexRegister = theIndexRegister;
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Sets the Stack Pointer to point to a new location.
     *
     * @param theStackPointer The new Memory location the Stack Pointer should point toward.
     */
    public void setMyStackPointer(int theStackPointer) {
        //TODO: FIND RANGES OF STACK POINTER;
        this.myStackPointer = theStackPointer;
    }

    /**
     * Sets the Program Counter to point to a new location.
     *
     * @param theProgramCounter The new Memory location the Program Counter should point toward.
     */
    public void setMyProgramCounter(int theProgramCounter) {
        try {
            if (theProgramCounter >= myMemory.getTotalMemoryLocations() || theProgramCounter < 0) {
                throw new IllegalArgumentException("Program Counter out of bounds:" +
                        "Tried to set the Program Counter to " + theProgramCounter + " while PC can only range from " +
                        "0-" + (myMemory.getTotalMemoryLocations()-1) + ".");
            }
            this.myProgramCounter = theProgramCounter;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Sets the instruction register to a new 24-bit binary value, representing the currently
     * processing binary instruction.
     *
     * @param theInstructionRegister The new Instruction Register value to represent the
     *                               currently processing binary instruction.
     */
    public void setMyInstructionRegister(String theInstructionRegister) {
        try {
            if (theInstructionRegister.length() != 24 || !isBinaryString(theInstructionRegister)) {
                throw new IllegalArgumentException("Tried to set the Index Register to a value that contained " +
                        "non-binary characters or was not 24-bits long.");
            }
            this.myInstructionRegister = theInstructionRegister;
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Sets a String that details in words what instruction is currently executing for the GUI to display.
     */
    public void setMyInstructionWordLabel(final String theInstructionWordLabel) {
        this.myInstructionWordLabel =  theInstructionWordLabel;
    }

    /**
     * Sets the N Flag to either 0 or 1.
     *
     * @param theNFlag The N Flag's new status.
     */
    public void setMyNFlag(int theNFlag) {
        try {
            if (theNFlag < 0 || theNFlag > 1) {
                throw new IllegalArgumentException("Tried to set the N Flag to a value that was not 0 or 1.");
            }
            this.myNFlag = theNFlag;
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Sets the Z Flag to either 0 or 1.
     *
     * @param theZFlag The Z Flag's new status.
     */
    public void setMyZFlag(int theZFlag) {
        try {
            if (theZFlag < 0 || theZFlag > 1) {
                throw new IllegalArgumentException("Tried to set the Z Flag to a value that was not 0 or 1.");
            }
            this.myZFlag = theZFlag;
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Sets the V Flag to either 0 or 1.
     *
     * @param theVFlag The V Flag's new status.
     */
    public void setMyVFlag(int theVFlag) {
        try {
            if (theVFlag < 0 || theVFlag > 1) {
                throw new IllegalArgumentException("Tried to set the V Flag to a value that was not 0 or 1.");
            }
            this.myVFlag = theVFlag;
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Sets the C Flag to either 0 or 1.
     *
     * @param theCFlag The C Flag's new status.
     */
    public void setMyCFlag(int theCFlag) {
        try {
            if (theCFlag < 0 || theCFlag > 1) {
                throw new IllegalArgumentException("Tried to set the C Flag to a value that was not 0 or 1.");
            }
            this.myCFlag = theCFlag;
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Sets the total string of input characters the user entered in the GUI Input text area before running the program.
     * myInput removes tabs and newlines from the input string.
     *
     * @param theInput The total string of input character the user entered in the GUI Input text area before running
     *                 the program.
     */
    public void setMyInput(final String theInput) {
        String modifiedInput = theInput.replaceAll("\t","");
        modifiedInput = modifiedInput.replaceAll("\n", "");
        this.myInput = modifiedInput;
    }

    /**
     * Returns the first char of myInput, then decrements the front of the myInput record since the character returned
     * has been processed and the program needs to work on the next character in future calls.
     *
     * @return The first char of myInput.
     */
    public char getAndRemoveFirstCharFromMyInput() {
        char output = 0;
        if (myInput.length() > 1) {
            output = myInput.charAt(0);
            myInput = myInput.substring(1);
        } else if (myInput.length() == 1) {
            output = myInput.charAt(0);
            myInput = "";
        }
        return output;
    }

    /**
     * Adds theOutput String to the end of what is currently present in myOutput String.
     *
     * @param theOutput The String to be appended to the end of the current myOutput String.
     */
    public void appendToMyOutput(String theOutput) {
        this.myOutput.concat(theOutput);
    }

    /**
     * Sets the boolean status of if the Pep/8 program is currently running and processing instructions.
     * True means the Pep/8 program is currently running, false otherwise.
     *
     * @param theRunIsExecuting The new status of if the Pep/8 program is currently reading and processing instructions.
     */
    public void setMyRunIsExecuting(Boolean theRunIsExecuting) {
        this.myRunIsExecuting = theRunIsExecuting;
    }

    /**
     * Sets a new value for the operand of the current instruction. The operand is either a 16-bit binary value or
     * an empty string, depending on if the operand value is relevant to the current instruction.
     *
     * @param theOperand The new value the Operand should display in the GUI.
     */
    public void setMyOperand(String theOperand) {
        try {
            if ((theOperand.length() != 16 && theOperand.length() != 0) || !isBinaryString(theOperand)) {
                throw new IllegalArgumentException("Tried to set the Operand to a value that contained " +
                        "non-binary characters, was not 16-bits long, or was not an empty string as denoted by \"\".");
            }
            this.myOperand = theOperand;
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
