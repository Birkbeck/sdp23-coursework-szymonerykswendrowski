package sml;

/**
 * Represents an abstract instruction.
 *
 * @author Szymon Swendrowski
 */
public abstract class Instruction {
  public static int NORMAL_PROGRAM_COUNTER_UPDATE = -1;
  protected final String label;
  protected final String opcode;

  /**
   * Constructor: an instruction with a label and an opcode
   * (opcode must be an operation of the language)
   *
   * @param label  optional label (can be null)
   * @param opcode operation name
   */
  public Instruction(String label, String opcode) {
    this.label = label;
    this.opcode = opcode;
  }

  /**
   * Compares the instruction to the specified object.
   *
   * @param o the reference object to which the instruction is to be compared
   */
  @Override
  public abstract boolean equals(Object o);


  /**
   * Executes the instruction in the given machine.
   *
   * @param machine the machine the instruction runs on
   * @return the new program counter (for jump instructions)
   * or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
   * the instruction with the next address is to be executed
   */
  public abstract int execute(Machine machine);

  /**
   * Gets the label of the instruction.
   *
   * @return label of the instruction
   */
  public String getLabel() {
    return label;
  }

  /**
   * Gets the label of the instruction.
   *
   * @return the label of the instruction followed by a colon and a space
   * or an empty string if the instruction has no label
   */
  protected String getLabelString() {
    return (getLabel() == null) ? "" : getLabel() + ": ";
  }

  /**
   * Gets the opcode of the instruction.
   *
   * @return opcode of the instruction
   */
  public String getOpcode() {
    return opcode;
  }

  /**
   * Computes the hash values of the instruction.
   *
   * @return the hash code of the instruction
   */
  @Override
  public abstract int hashCode();

  /*
   The method below is declared abstract meaning that it must be implemented by a subclass
   (unless the subclass is abstract), but it is not implemented here in its declaration.
  */

  /**
   * Computes the string representation of the instruction.
   *
   * @return the string representation of the instruction
   */
  @Override
  public abstract String toString();
}
