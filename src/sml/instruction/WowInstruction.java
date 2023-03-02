package sml.instruction;

import sml.Instruction;
import sml.Machine;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Represents a wow instruction.
 *
 * @author Szymon Swendrowski
 */
public class WowInstruction extends Instruction {
    public static final String OP_CODE = "out";
    private final int t;

    /**
     * Constructor: an instruction to print the "Wow"! t times.
     *
     * @param label optional label (can be null)
     * @param t the amount of times to print "Wow!"
     */
    public WowInstruction(String label, int t) {
        super(label, OP_CODE);
        this.t = t;
    }

    /**
     * Returns true if the given object is a wow instruction with
     * the same t value.
     *
     * @param o object to compare
     * @return True or False
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof WowInstruction other) {
            return Objects.equals(t, other.t);
        }
        return false;
    }

    /**
     * Executes the wow instruction in the given machine.
     *
     * @param m the machine the wow instruction runs on
     * @return NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
     *          the instruction with the next address is to be executed
     */
    @Override
    public int execute(Machine m) {
        IntStream.range(0, t).mapToObj(i -> "Wow!").forEach(System.out::println);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Computes the hash value of the wow instruction.
     *
     * @return the hash code of the wow instruction
     */
    @Override
    public int hashCode() {
        return Objects.hash(t);
    }

    /**
     * Computes the string representation of the wow instruction.
     *
     * @return the string representation of the wow instruction
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + t;
    }
}