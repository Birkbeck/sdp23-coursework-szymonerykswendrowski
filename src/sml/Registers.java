package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the registers.
 *
 * @author Szymon Swendrowski
 */
public final class Registers {
  private final Map<Register, Integer> registers = new HashMap<>();

  /**
   * Represents the register names.
   */
  public enum Register implements RegisterName {
    EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI
  }

  /**
   * Creates a new instance of Registers.
   */
  public Registers() {
    clear(); // the class is final
  }

  /**
   * Clears the registers.
   */
  public void clear() {
    for (Register register : Register.values())
      registers.put(register, 0);
  }

  /**
   * Returns true if the given object is a registers instance
   * with equal registers.
   *
   * @param o object to compare
   * @return True or False
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Registers other) {
      return registers.equals(other.registers);
    }
    return false;
  }

  /**
   * Returns the value stored in the register.
   *
   * @param register register name
   * @return value
   */
  public int get(RegisterName register) {
    return registers.get((Register) register);
  }

  /**
   * Computes the hash value of the registers.
   *
   * @return the hash code of the registers
   */
  @Override
  public int hashCode() {
    return registers.hashCode();
  }

  /**
   * Sets the given register to the value.
   *
   * @param register register name
   * @param value   new value
   */
  public void set(RegisterName register, int value) {
    registers.put((Register) register, value);
  }

  /**
   * Computes the string representation of the registers.
   *
   * @return the string representation of the registers
   */
  @Override
  public String toString() {
    return registers.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .map(e -> e.getKey() + " = " + e.getValue())
            .collect(Collectors.joining(", ", "[", "]"));
  }
}
