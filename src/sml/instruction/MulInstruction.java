package sml.instruction;

// TODO: write a JavaDoc for the class

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

/**
 * Represents a multiply instruction.
 * @author Szymon Swendrowski
 */

public class MulInstruction extends Instruction {

    public static final String OP_CODE = "mul";
    private final RegisterName result;
    private final RegisterName source;

    /**
     * Constructor: an instruction to multiply the values in two registers.
     *
     * @param label optional label (can be null)
     * @param result the register to store the result in
     * @param source the register to multiply with the result
     */
    public MulInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    /**
     * @param m the machine the multiply instruction runs on
     * @return NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
     *          the instruction with the next address is to be executed
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, value1 * value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * Computes the string representation of the multiply instruction.
     * @return the string representation of the multiply instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }
}
