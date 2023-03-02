package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents the labels in the program.
 * (Note: this is not to be confused with the Strings that are used
 * to represent the labels in instructions.)
 *
 * @author Szymon Swendrowski
 */
public final class Labels {
  private final Map<String, Integer> labels = new HashMap<>();

  /**
   * Adds a label with the associated address to the map.
   *
   * @param label   the label
   * @param address the address the label refers to
   */
  public void addLabel(String label, int address) {
    Objects.requireNonNull(label);
    /* Check for duplicate labels, have to do this before
     * the label is put in as put() replaces duplicate
     * keys in HashMaps. */
    if (labels.containsKey(label))
      throw new IllegalArgumentException("Duplicate label: " + label);
    labels.put(label, address);
  }

  /**
   * Returns the address associated with the label.
   *
   * @param label the label
   * @return the address the label refers to
   */
  public int getAddress(String label) {
    /* A NullPointerException could be thrown here if getAddress is used
     * on a label that wasn't assigned to a given instruction using
     * the addLabel method, but instead, was only used as a parameter
     * in the constructor of a given instruction. */
    if (!labels.containsKey(label))
      throw new IllegalArgumentException("Label does not exist: " + label);
    return labels.get(label);
  }

  /**
   * representation of this instance,
   * in the form "[label -> address, label -> address, ..., label -> address]"
   *
   * @return the string representation of the labels map
   */
  @Override
  public String toString() {
    return labels.entrySet().stream()
            .map(entry -> entry.getKey() + " -> " + entry.getValue())
            .collect(Collectors.joining(", ", "[", "]"));
  }

  /**
   * Returns true if the given object is a labels instance
   *
   * @param o object to compare
   * @return True or False
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Labels other) {
      return labels.equals(other.labels);
    }
    return false;
  }

  /**
   * Computes the hash value of the labels.
   *
   * @return the hash code of the labels
   */
  @Override
  public int hashCode() {
    return labels.hashCode();
  }

  /**
   * Removes the labels
   */
  public void reset() {
    labels.clear();
  }
}
