package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * Represents a divide instruction.
 *
 * @author Szymon Swendrowski
 */
public class DivInstruction extends Instruction {
  public static final String OP_CODE = "div";
  private final RegisterName result;
  private final RegisterName source;

  /**
   * Constructor: an instruction to divide the values in two registers.
   *
   * @param label  optional label (can be null)
   * @param result the register to store the result in
   * @param source the register to divide into the result
   */
  public DivInstruction(String label, RegisterName result, RegisterName source) {
    super(label, OP_CODE);
    this.result = result;
    this.source = source;
  }

  /**
   * Returns true if the given object is a divide instruction with
   * the same result and source registers.
   *
   * @param o object to compare
   * @return True or False
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof DivInstruction other) {
      return Objects.equals(result, other.result)
              && Objects.equals(source, other.source);
    }
    return false;
  }

  /**
   * Executes the divide instruction in the given machine.
   *
   * @param m the machine the divide instruction runs on
   * @return NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
   * the instruction with the next address is to be executed
   */
  @Override
  public int execute(Machine m) {
    int value1 = m.getRegisters().get(result);
    int value2 = m.getRegisters().get(source);
    try {
      m.getRegisters().set(result, value1 / value2);
    } catch (ArithmeticException e) {
      System.out.print("Error: Division by zero, " +
              "value of result register won't change");
    }
    return NORMAL_PROGRAM_COUNTER_UPDATE;
  }

  /**
   * Computes the hash code of the divide instruction.
   *
   * @return the hash code of the divide instruction
   */
  @Override
  public int hashCode() {
    return Objects.hash(result, source);
  }

  /**
   * Computes the string representation of the divide instruction.
   *
   * @return the string representation of the divide instruction
   */
  @Override
  public String toString() {
    return getLabelString() + getOpcode() + " " + result + " " + source;
  }
}
