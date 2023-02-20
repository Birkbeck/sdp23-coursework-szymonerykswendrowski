package sml.instruction;

// TODO: write a JavaDoc for the class

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * Represents a move instruction.
 *
 * @author Szymon Swendrowski
 */

public class MovInstruction extends Instruction {
    public static final String OP_CODE = "mov";
    private final RegisterName result;
    private final int x;

    /**
     * Constructor: an instruction to store an integer value in a register.
     *
     * @param label optional label (can be null)
     * @param result the register to store the integer in
     * @param x the integer to store into the result
     */
    public MovInstruction(String label, RegisterName result, int x) {
        super(label, OP_CODE);
        this.result = result;
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    /**
     * Executes the move instruction in the given machine.
     *
     * @param m the machine the move instruction runs on
     * @return NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
     *          the instruction with the next address is to be executed
     */
    @Override
    public int execute(Machine m) {
        m.getRegisters().set(result, x);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Computes the string representation of the move instruction.
     *
     * @return the string representation of the move instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + x;
    }
}