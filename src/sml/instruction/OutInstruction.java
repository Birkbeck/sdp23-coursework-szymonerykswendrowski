package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

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
   * @param label  optional label (can be null)
   * @param source the register to print the value of
   */
  public OutInstruction(String label, RegisterName source) {
    super(label, OP_CODE);
    this.source = source;
  }

  /**
   * Returns true if the given object is an output instruction with
   * the same result and source registers.
   *
   * @param o object to compare
   * @return True or False
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof OutInstruction other) {
      return Objects.equals(source, other.source);
    }
    return false;
  }

  /**
   * Executes the output instruction in the given machine.
   *
   * @param m the machine the output instruction runs on
   * @return NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
   * the instruction with the next address is to be executed
   */
  @Override
  public int execute(Machine m) {
    int value1 = m.getRegisters().get(source);
    System.out.println(value1);
    return NORMAL_PROGRAM_COUNTER_UPDATE;
  }

  /**
   * Computes the hash value of the output instruction.
   *
   * @return the hash code of the output instruction
   */
  @Override
  public int hashCode() {
    return source.hashCode();
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
