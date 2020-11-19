package view;

import controller.Controller;
import model.Converter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Graphical User Interface formatting class for the Pep8 Simulator program.
 *
 * @version 5 November 2020
 */
public class Pep8GUIFrame extends JFrame implements ActionListener {
    // Class Constants
    /**
     * The Run command.
     */
    private static final String RUN_SOURCE_COMMAND = "Run Source";

    /**
     * The Step command.
     */
    private static final String RUN_OBJECT_COMMAND = "Run Object";

    /**
     * The Clear Memory command.
     */
    private static final String CLEAR_MEMORY_COMMAND = "Clear Memory";

    /**
     * The minimum rows for the smallest View.JLabeledTextArea's JTextArea size.
     */
    private static final int MIN_JTEXTAREA_ROWS = 10;

    /**
     * The minimum columns for the smallest View.JLabeledTextArea's JTextArea size.
     */
    private static final int MIN_JTEXTAREA_COLUMNS = 35;

    /**
     * The minimum columns for the initial JTextField size for the CPU bit information.
     */
    private static final int CPU_STATE_GRID_JTEXTFIELD_COLUMNS = 12;

    /**
     * The columns for the JTextField width of the NZVC flags in the CPU display area.
     */
    private static final int NZVC_JTEXTFIELD_COLUMNS = 2;

    /**
     * The number of rows needed for the GridLayout of the CPU state grid.
     */
    private static final int CPU_STATE_GRID_ROWS = 7;

    /**
     * The number of rows needed for the GridLayout of the CPU state grid.
     */
    private static final int CPU_STATE_GRID_COLUMNS = 3;

    // Instance Fields
    /**
     * A custom-formatted JTextArea for the Memory display box.
     */
    private final JLabeledTextArea myMemoryTextArea;

    /**
     * A custom-formatted JTextArea for the Source Code display box.
     */
    private final JLabeledTextArea mySourceCodeTextArea;

    /**
     * A custom-formatted JTextArea for the Object Code display box.
     */
    private final JLabeledTextArea myObjectCodeTextArea;

    /**
     * A custom-formatted JTextArea for the Input display box.
     */
    private final JLabeledTextArea myInputTextArea;

    /**
     * A custom-formatted JTextArea for the Output display box.
     */
    private final JLabeledTextArea myOutputTextArea;

    /**
     * A JTextField that represents the N flag status from the CPU.
     */
    private final JTextField myNFlagTextField;

    /**
     * A JTextField that represents the Z flag status from the CPU.
     */
    private final JTextField myZFlagTextField;

    /**
     * A JTextField that represents the V flag status from the CPU.
     */
    private final JTextField myVFlagTextField;

    /**
     * A JTextField that represents the C flag status from the CPU.
     */
    private final JTextField myCFlagTextField;

    /**
     * A JTextField that will display the hexadecimal state of the accumulator value.
     */
    private final JTextField myAccumulatorStateLeftField;

    /**
     * A JTextField that will display the decimal state of the accumulator value.
     */
    private final JTextField myAccumulatorStateRightField;

    /*
     * A JTextField that will display the hexadecimal state of the index register value.
     */
    private final JTextField myIndexRegisterStateLeftField;

    /*
     * A JTextField that will display the decimal state of the index register value.
     */
    private final JTextField myIndexRegisterStateRightField;

    /*
     * A JTextField that will display the hexadecimal state of the stack pointer value.
     */
    private final JTextField myStackPointerStateLeftField;

    /*
     * A JTextField that will display the decimal state of the stack pointer value.
     */
    private final JTextField myStackPointerStateRightField;

    /**
     * A JTextField that will display the hexadecimal state of the program counter value.
     */
    private final JTextField myProgramCounterStateLeftField;

    /**
     * A JTextField that will display the decimal state of the program counter value.
     */
    private final JTextField myProgramCounterStateRightField;

    /**
     * A JTextField that will display the binary state of the instruction specifier value.
     */
    private final JTextField myInstructionSpecifierStateLeftField;

    /**
     * A JTextField that will display the String state of the instruction specifier value.
     */
    private final JTextField myInstructionSpecifierStateRightField;

    /**
     * A JTextField that will display the hexadecimal state of the operand specifier value.
     */
    private final JTextField myOperandSpecifierStateLeftField;

    /**
     * A JTextField that will display the decimal state of the operand specifier value.
     */
    private final JTextField myOperandSpecifierStateRightField;

    /**
     * A JTextField that will display the hexadecimal state of the operand value.
     */
    private final JTextField myOperandStateLeftField;

    /**
     * A JTextField that will display the decimal state of the operand value.
     */
    private final JTextField myOperandStateRightField;

    /**
     * The controller that runs the backend calculations of the Pep/8 program.
     */
    private final Controller myController;

    /**
     * Constructs the Pep8 GUI, using the files in the current working directory (like View.JLabeledTextArea.java).
     */
    public Pep8GUIFrame() {
        // Instantiate Fields
        myMemoryTextArea = new JLabeledTextArea("Memory Dump", MIN_JTEXTAREA_ROWS, MIN_JTEXTAREA_COLUMNS);
        mySourceCodeTextArea = new JLabeledTextArea("Source Code", MIN_JTEXTAREA_ROWS, MIN_JTEXTAREA_COLUMNS);
        myObjectCodeTextArea = new JLabeledTextArea("Object Code", MIN_JTEXTAREA_ROWS, MIN_JTEXTAREA_COLUMNS);
        myInputTextArea = new JLabeledTextArea("Input", MIN_JTEXTAREA_ROWS, MIN_JTEXTAREA_COLUMNS);
        myOutputTextArea = new JLabeledTextArea("Output", MIN_JTEXTAREA_ROWS, MIN_JTEXTAREA_COLUMNS);
        myAccumulatorStateLeftField = makeUnfocusableTextField();
        myAccumulatorStateRightField = makeUnfocusableTextField();
        myIndexRegisterStateLeftField = makeUnfocusableTextField();
        myIndexRegisterStateRightField = makeUnfocusableTextField();
        myStackPointerStateLeftField = makeUnfocusableTextField();
        myStackPointerStateRightField = makeUnfocusableTextField();
        myProgramCounterStateLeftField = makeUnfocusableTextField();
        myProgramCounterStateRightField = makeUnfocusableTextField();
        myInstructionSpecifierStateLeftField = makeUnfocusableTextField();
        myInstructionSpecifierStateRightField = makeUnfocusableTextField();
        myOperandSpecifierStateLeftField = makeUnfocusableTextField();
        myOperandSpecifierStateRightField = makeUnfocusableTextField();
        myOperandStateLeftField = makeUnfocusableTextField();
        myOperandStateRightField = makeUnfocusableTextField();
        myNFlagTextField = makeUnfocusableTextField(NZVC_JTEXTFIELD_COLUMNS);
        myZFlagTextField = makeUnfocusableTextField(NZVC_JTEXTFIELD_COLUMNS);
        myVFlagTextField = makeUnfocusableTextField(NZVC_JTEXTFIELD_COLUMNS);
        myCFlagTextField = makeUnfocusableTextField(NZVC_JTEXTFIELD_COLUMNS);
        myController = new Controller();

        // Build GUI Visuals
        setTitle("Pep/8");
        JPanel basePanel = new JPanel(new BorderLayout());
        JPanel topButtonPanel = new JPanel(new FlowLayout());
        JPanel cpuBasePanel = new JPanel(new BorderLayout());
        JPanel cpuNZVC = new JPanel(new FlowLayout());
        JPanel cpuStateGrid = new JPanel(new GridLayout(CPU_STATE_GRID_ROWS,CPU_STATE_GRID_COLUMNS));

        // Formatting NZVC flags for the CPU
        cpuNZVC.add(new JLabel("N"));
        cpuNZVC.add(myNFlagTextField);
        cpuNZVC.add(new JLabel("Z"));
        cpuNZVC.add(myZFlagTextField);
        cpuNZVC.add(new JLabel("V"));
        cpuNZVC.add(myVFlagTextField);
        cpuNZVC.add(new JLabel("C"));
        cpuNZVC.add(myCFlagTextField);

        // Formatting the CPU's state information
        cpuStateGrid.add(new JLabel("Accumulator"));
        cpuStateGrid.add(myAccumulatorStateLeftField);
        cpuStateGrid.add(myAccumulatorStateRightField);
        cpuStateGrid.add(new JLabel("Index Register"));
        cpuStateGrid.add(myIndexRegisterStateLeftField);
        cpuStateGrid.add(myIndexRegisterStateRightField);
        cpuStateGrid.add(new JLabel("Stack Pointer"));
        cpuStateGrid.add(myStackPointerStateLeftField);
        cpuStateGrid.add(myStackPointerStateRightField);
        cpuStateGrid.add(new JLabel("Program Counter"));
        cpuStateGrid.add(myProgramCounterStateLeftField);
        cpuStateGrid.add(myProgramCounterStateRightField);
        cpuStateGrid.add(new JLabel("Instruction Specifier"));
        cpuStateGrid.add(myInstructionSpecifierStateLeftField);
        cpuStateGrid.add(myInstructionSpecifierStateRightField);
        cpuStateGrid.add(new JLabel("Operand Specifier"));
        cpuStateGrid.add(myOperandSpecifierStateLeftField);
        cpuStateGrid.add(myOperandSpecifierStateRightField);
        cpuStateGrid.add(new JLabel("(Operand)"));
        cpuStateGrid.add(myOperandStateLeftField);
        cpuStateGrid.add(myOperandStateRightField);

        // Finalizing the formatting for the CPU state information
        JPanel centerCPULabel = new JPanel();
        centerCPULabel.add(new JLabel("CPU"));
        cpuBasePanel.add(centerCPULabel, BorderLayout.NORTH);
        cpuBasePanel.add(cpuNZVC, BorderLayout.CENTER);
        cpuBasePanel.add(cpuStateGrid, BorderLayout.SOUTH);

        // Apply JSplitPanes for each content square
        JSplitPane splitSourceCodeAndObjectCode = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                mySourceCodeTextArea, myObjectCodeTextArea);
        JSplitPane splitInputAndOutput = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                myInputTextArea, myOutputTextArea);
        JSplitPane splitCPUStatesAndInputOutput = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                cpuBasePanel, splitInputAndOutput);
        JSplitPane splitLeftAndMiddleColumn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                splitSourceCodeAndObjectCode, splitCPUStatesAndInputOutput);
        JSplitPane splitLeftMiddleAndRightColumn = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                splitLeftAndMiddleColumn, myMemoryTextArea);

        // Apply buttons to the top of the window and the content squares to the rest of the window
        topButtonPanel.add(makeButton(RUN_SOURCE_COMMAND));
        topButtonPanel.add(makeButton(RUN_OBJECT_COMMAND));
        topButtonPanel.add(makeButton(CLEAR_MEMORY_COMMAND));
        basePanel.add(topButtonPanel, BorderLayout.NORTH);
        basePanel.add(splitLeftMiddleAndRightColumn, BorderLayout.CENTER);
        add(basePanel);
        pack();
    }

    /**
     * Returns a new JButton with the specified text and an ActionListener.
     *
     * @param theText The text.
     * @return a new JButton.
     */
    private JButton makeButton(final String theText) {
        final JButton button = new JButton(theText);
        button.addActionListener(this);
        return button;
    }

    /**
     * Returns a new JTextField that cannot be focused (typed on) by the user.
     * This makes the JTextField display-only for information.
     * @param theColumns The width of the JTextField
     * @return A new JTextField that cannot be focused by the user.
     */
    private JTextField makeUnfocusableTextField(int theColumns) {
        JTextField textField = new JTextField(theColumns);
        textField.setFont(new Font("monospaced", Font.PLAIN, 12));
        textField.setHorizontalAlignment(JTextField.CENTER); //TODO: Fix this not centering text as expected.
        textField.setFocusable(false);
        return textField;
    }

    /**
     * Returns a new JTextField that cannot be focused (typed on) by the user.
     * This makes the JTextField display-only for information.
     *
     * @return A new JTextField that cannot be focused by the user.
     */
    private JTextField makeUnfocusableTextField() {
        JTextField textField = new JTextField(CPU_STATE_GRID_JTEXTFIELD_COLUMNS);
        textField.setFocusable(false);
        return textField;
    }

    private void updateCPUFieldsView() {
        // Generate left states (mostly hexadecimal)
        String hexAccumulator = Converter.binToHexSetLength(myController.getMyAccumulatorRegister(),4).toUpperCase();
        String hexIndexRegister = Converter.binToHexSetLength(myController.getMyIndexRegister(),4).toUpperCase();
        String hexStackPointer = Converter.decToHexSetLength(myController.getMyStackPointer(),4).toUpperCase();
        String hexProgramCounter = Converter.decToHexSetLength(myController.getMyProgramCounter(),4).toUpperCase();
        String binInstructionSpecifier = myController.getMyInstructionRegister().substring(0,8);
        String hexOperandSpecifier = Converter.binToHexSetLength(myController.getMyInstructionRegister().substring(8),4).toUpperCase();
        String hexOperand = Converter.binToHexSetLength(myController.getMyOperand(),4).toUpperCase();

        // Generate right states (mostly decimal)
        int decAccumulator = Converter.binToDec(myController.getMyAccumulatorRegister());
        int decIndexRegister = Converter.binToDec(myController.getMyIndexRegister());
        int decStackPointer = myController.getMyStackPointer();
        int decProgramCounter = myController.getMyProgramCounter();
        // TODO: FIND KEYWORD FOR INSTRUCTION SPECIFIER
        int decOperandSpecifier = Converter.binToDec(myController.getMyInstructionRegister().substring(8));
        int decOperand = Converter.binToDec(myController.getMyOperand());

        // Populate left JTextFields
        myAccumulatorStateLeftField.setText("0x" + hexAccumulator);
        myIndexRegisterStateLeftField.setText("0x" + hexIndexRegister);
        myStackPointerStateLeftField.setText("0x" + hexStackPointer);
        myProgramCounterStateLeftField.setText("0x" + hexProgramCounter);
        myInstructionSpecifierStateLeftField.setText(binInstructionSpecifier);
        myOperandSpecifierStateLeftField.setText("0x" + hexOperandSpecifier);
        myOperandStateLeftField.setText("0x" + hexOperand);

        // Populate right JTextFields
        myAccumulatorStateRightField.setText(Integer.toString(decAccumulator));
        myIndexRegisterStateRightField.setText(Integer.toString(decIndexRegister));
        myStackPointerStateRightField.setText(Integer.toString(decStackPointer));
        myProgramCounterStateRightField.setText(Integer.toString(decProgramCounter));
        // TODO: PRINT KEYWORD FOR INSTRUCTION SPECIFIER
        myOperandSpecifierStateRightField.setText(Integer.toString(decOperandSpecifier));
        myOperandStateRightField.setText(Integer.toString(decOperand));

        // Populate NZVC Flag JTextFields
        myNFlagTextField.setText(Integer.toString(myController.getMyNFlag()));
        myZFlagTextField.setText(Integer.toString(myController.getMyZFlag()));
        myVFlagTextField.setText(Integer.toString(myController.getMyVFlag()));
        myCFlagTextField.setText(Integer.toString(myController.getMyCFlag()));
    }

    private void updateMemoryDumpView() {
        myMemoryTextArea.setText("");
        String[] fullMemoryDump = myController.getMyMemoryFullDump();
        for (int fakePC = 0; fakePC < myController.getMyMemoryTotalLocations(); fakePC += 8) { // Loop for each newline of the Memdump
            // For each row:
            myMemoryTextArea.appendText(Converter.decToHexSetLength(fakePC, 4).toUpperCase() + " | ");
            for (int row = 0; row < 8; row++) {
                String rawBinary = myController.getMyMemoryDataAt(fakePC + row);
                if (rawBinary == null) {
                    myMemoryTextArea.appendText("00 ");
                } else {
                    myMemoryTextArea.appendText(Converter.binToHexSetLength(rawBinary, 2).toUpperCase() + " ");
                }
            }
            myMemoryTextArea.appendText("\n");
        }
    }

    //TODO: Finalize proper button actions.
    /**
     * A notification method called in response to action events in this window.
     *
     * @param theEvent The action event that triggered the call.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final String command = theEvent.getActionCommand().intern();
        switch (command) {
            case RUN_SOURCE_COMMAND:
                System.out.println("\"Run Source\" Steps Running");
                //TODO: Call assembleSourceCodeToObjectCode() to translate the Source Code to Object Code,
                // then write the object code to myObjectTextArea
                //Let action follow the RUN_OBJECT_COMMAND step after. No "break;" needed here
            case RUN_OBJECT_COMMAND:
                System.out.println("\"Run Object\" Steps Running");
                myController.loadObjectCodeIntoMemory(myObjectCodeTextArea.getText());
                //TODO: add myController.run();
                updateMemoryDumpView();
                updateCPUFieldsView();
                break;
            case CLEAR_MEMORY_COMMAND:
                System.out.println("\"Clear Memory\" Steps Running");
                myController.clearMyMemoryAndResetCPUFields();
                updateMemoryDumpView();
                updateCPUFieldsView();
                break;
            default:
                throw new IllegalArgumentException("Unknown action.");
        }
    }
}