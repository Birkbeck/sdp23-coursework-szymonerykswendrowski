package sml.instruction;

// TODO: write a JavaDoc for the class

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * Represents an output instruction.
 *
 * @author Szymon Swendrowski
 */
public class OutInstruction extends Instruction {
    public static final String OP_CODE = "out";
    private final RegisterName source;

    /**
     * Constructor: an instruction to print the value in a register.
     *
     * @param label optional label (can be null)
     * @param source the register to print the value of
     */
    public OutInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    /**
     * Executes the output instruction in the given machine.
     *
     * @param m the machine the output instruction runs on
     * @return NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
     *          the instruction with the next address is to be executed
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(source);
        System.out.print(value1);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Computes the string representation of the output instruction.
     *
     * @return the string representation of the output instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }
}
