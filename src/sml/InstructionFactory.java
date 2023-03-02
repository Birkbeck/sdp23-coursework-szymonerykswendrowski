package sml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.Map;

/**
 * Represents an instruction factory.
 *
 * @author Szymon Swendrowski
 */
public abstract class InstructionFactory {
  // Primitive type wrappers
  private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_WRAPPERS = Map.of(
          int.class, Integer.class,
          long.class, Long.class,
          boolean.class, Boolean.class,
          byte.class, Byte.class,
          char.class, Character.class,
          float.class, Float.class,
          double.class, Double.class,
          short.class, Short.class,
          void.class, Void.class);

  /**
   * Returns the instruction with the given label and arguments.
   *
   * @param instructionClass the instruction class
   * @param arguments the instruction arguments
   * @return the instruction
   */
  public static Instruction getInstruction(Class<?> instructionClass, List<String> arguments) {
    // Get the candidate constructor and the number of parameters
    Constructor<?>[] classConstructors = instructionClass.getConstructors();
    Constructor<?> Constructor = classConstructors[0];

    // Construct an array of the arguments (as strings)
    int argumentLen = arguments.toArray().length;
    String[] argumentsList = arguments.toArray(new String[argumentLen]);

    // Create an array of the correct parameter types
    Object[] parameterObjects = new Object[argumentLen];
    // Get the constructor parameters
    Class<?>[] parameterTypes = classConstructors[0].getParameterTypes();
    for (int i = 0; i < argumentLen; i++) {
      // Wrap the parameter types and store in parameterObjects
      parameterObjects[i] = toWrapper(parameterTypes[i]);
    }

    // Convert the arguments to the correct type, i.e. arguments to parameters
    for (int j = 0; j < argumentLen; j++) {
      if (parameterObjects[j].equals(Integer.class)) {
        try {
          parameterObjects[j] = Integer.parseInt(argumentsList[j]);
        } catch (NumberFormatException e) {
          throw new RuntimeException(argumentsList[j] + " is not a valid integer");
        }
      } else if (parameterObjects[j].equals(RegisterName.class)) {
        try {
          parameterObjects[j] = Registers.Register.valueOf(argumentsList[j]);
        } catch (IllegalArgumentException e) {
          throw new RuntimeException(argumentsList[j] + " is not a valid register");
        }
      } else {
        parameterObjects[j] = argumentsList[j];
      }
    }
    try {
      return (Instruction) Constructor.newInstance(parameterObjects);
    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e + "Constructor failed");
    }
  }

  /**
   * Return the correct Wrapper class if testClass is primitive.
   *
   * @param testClass class being tested
   * @return Object class or testClass
   */
  private static Class<?> toWrapper(Class<?> testClass) {
    return PRIMITIVE_TYPE_WRAPPERS.getOrDefault(testClass, testClass);
  }
}
