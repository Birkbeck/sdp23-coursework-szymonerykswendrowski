package sml;

// TODO: write a JavaDoc for the class

/**
 * Represents a subtract instruction.
 *
 * @author Szymon Swendrowski
 */
public class SubInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "sub";

    /**
     * Constructor: an instruction to subtract the values in two registers.
     *
     * @param label optional label (can be null)
     * @param result the register to store the result in
     * @param source the register to subtract from the result
     */
    public SubInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * @param m the machine the subtract instruction runs on
     * @return NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
     *          the instruction with the next address is to be executed
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, value1 - value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Computes the string representation of the add instruction.
     *
     * @return the string representation of the add instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
