package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains 32 registers and methods to access and change them.
 *
 * @author Szymon Swendrowski
 */
public final class Machine {

  private final Labels labels = new Labels();

  private final List<Instruction> program = new ArrayList<>();

  private final Registers registers;

  // The program counter; it contains the index (in program)
  // of the next instruction to be executed.
  private int programCounter = 0;

  public Machine(Registers registers) {
    this.registers = registers;
  }

  /**
   * Execute the program in program, beginning at instruction 0.
   * Precondition: the program and its labels have been stored properly.
   */
  public void execute() {
    programCounter = 0;
    registers.clear();
    while (programCounter < program.size()) {
      Instruction ins = program.get(programCounter);
      int programCounterUpdate = ins.execute(this);
      programCounter = (programCounterUpdate == NORMAL_PROGRAM_COUNTER_UPDATE)
              ? programCounter + 1
              : programCounterUpdate;
    }
  }

  /**
   * Get the labels present in the program.
   *
   * @return labels present in the program
   */
  public Labels getLabels() {
    return this.labels;
  }

  /**
   * Get the program under execution.
   *
   * @return program under execution
   */
  public List<Instruction> getProgram() {
    return this.program;
  }

  /**
   * Get the registers of the machine.
   *
   * @return registers of the machine
   */
  public Registers getRegisters() {
    return this.registers;
  }

  /**
   * String representation of the program under execution.
   *
   * @return pretty formatted version of the code.
   */
  @Override
  public String toString() {
    return program.stream()
            .map(Instruction::toString)
            .collect(Collectors.joining("\n"));
  }

  /**
   * Returns true if the given object is a machine with the same labels,
   * program, registers and program counter.
   *
   * @param o object to compare
   * @return True or False
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Machine other) {
      return Objects.equals(this.labels, other.labels)
              && Objects.equals(this.program, other.program)
              && Objects.equals(this.registers, other.registers)
              && this.programCounter == other.programCounter;
    }
    return false;
  }

  /**
   * Computes the hash value of the machine.
   *
   * @return the hash code of the machine
   */
  @Override
  public int hashCode() {
    return Objects.hash(labels, program, registers, programCounter);
  }
}
