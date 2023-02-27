package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * Represents a jump instruction.
 *
 * @author Szymon Swendrowski
 */
public class JnzInstruction extends Instruction{
    public static final String OP_CODE = "jnz";
    private final RegisterName source;
    private final String L;

    /**
     * Constructor: an instruction to jump to a label if the value in a register is not zero.
     *
     * @param label optional label (can be null)
     * @param source the register to check the value of
     * @param L the label to jump to
     */
    public JnzInstruction(String label, RegisterName source, String L) {
        super(label, OP_CODE);
        this.source = source;
        this.L = L;
    }

    /**
     * Returns true if the given object is a jump instruction with
     * the same result and source registers.
     *
     * @param o object to compare
     * @return True or False
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof JnzInstruction other) {
            return Objects.equals(source, other.source)
                    && Objects.equals(L, other.L);
        }
        return false;
    }

    /**
     * Executes the jump instruction in the given machine.
     *
     * @param m the machine the jump instruction runs on
     * @return the new program counter if the value in the register is not zero
     */
    @Override
    public int execute(Machine m) {
        return m.getRegisters().get(source) != 0
                ? m.getLabels().getAddress(L)
                : NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Computes the hash code of the jump instruction.
     *
     * @return the hash code of the multiply instruction
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, L);
    }

    /**
     * Computes the string representation of the jump instruction.
     *
     * @return the string representation of the jump instruction
     */
    @Override
    public String toString() {return getLabelString() + getOpcode() + " " + source + " " + L;}
}
