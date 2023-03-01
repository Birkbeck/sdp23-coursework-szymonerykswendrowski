package sml;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

import static sml.Registers.Register;

/**
 * This class is responsible for reading the SML program from a file and
 * translating it into a program that can be executed by the machine.
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author Szymon Swendrowski
 */
public final class Translator {
    private static Translator instance; // singleton instance
    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

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

    private Translator(String fileName) {
        this.fileName =  fileName;
    }

    /**
     * Returns the singleton instance of the translator.
     *
     * @param fileName the name of the file containing the SML code
     * @return the singleton instance of the translator
     */
    public static Translator getInstance(String fileName) {
        if (instance == null) {
            instance = new Translator(fileName);
        }
        return instance;
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    // TODO: Next, use dependency injection to allow this machine class
    //       to work with different sets of opcodes (different CPUs)
    private Instruction getInstruction(String label) {
        if (line.isEmpty())
            return null;

        // Extract the opcode from the line and get instruction type
        String opcode = scan();
        String instructionName = opcode.substring(0, 1).toUpperCase() +
                opcode.substring(1).toLowerCase();
        Class<?> instructionClass;
        try {
            instructionClass = Class.forName("sml.instruction."
                    + instructionName + "Instruction");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e + " is not a valid instruction");
        }

        // Get the candidate constructor and the number of parameters
        Constructor<?>[] classConstructors = instructionClass.getConstructors();
        Constructor<?> Constructor = classConstructors[0];
        int parameterCount = classConstructors[0].getParameterCount();

        // Get the arguments from the line
        List<String> arguments = new ArrayList<>();
        arguments.add(label);
        IntStream.range(0, parameterCount - 1).mapToObj(i -> scan()).forEach(arguments::add);

        // Construct an array of the arguments (as strings)
        int argumentLen = arguments.toArray().length;
        String[] argumentsList = arguments.toArray(new String[argumentLen]);

        // Create an array of the correct parameter types
        Object[] parameterObjs = new Object[argumentLen];
        // Get the constructor parameters
        Class<?>[] parameterTypes = classConstructors[0].getParameterTypes();
        for (int i = 0; i < argumentLen; i++) {
            // Wrap the parameter types and store in parameterObjs
            parameterObjs[i] = toWrapper(parameterTypes[i]);
        }

        // Convert the arguments to the correct type, i.e. arguments to parameters
        for (int j = 0; j < argumentLen; j++) {
            if (parameterObjs[j].equals(Integer.class)) {
                parameterObjs[j] = Integer.parseInt(argumentsList[j]);
            } else if (parameterObjs[j].equals(RegisterName.class)) {
                parameterObjs[j] = Register.valueOf(argumentsList[j]);
            } else {
                parameterObjs[j] = argumentsList[j];
            }
        }
        // Return new instance of the instruction using the successful constructor
        // and parameters of the right class types.
        try {
            return (Instruction) Constructor.newInstance(parameterObjs);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e + "Constructor failed");
        }
    }

    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    /**
     * Reads the SML code from the file and translates it into a list of instructions.
     *
     * @param labels the labels of the instructions
     * @param program the program (list of instructions)
     * @throws IOException if the file cannot be read
     * @throws ClassNotFoundException if the instruction class cannot be found
     * @throws InvocationTargetException if the instruction constructor cannot be invoked
     * @throws InstantiationException if the instruction cannot be instantiated
     * @throws IllegalAccessException if the instruction constructor is not accessible
     */
    public void readAndTranslate(Labels labels, List<Instruction> program)
            throws IOException, ClassNotFoundException,
            InvocationTargetException, InstantiationException,
            IllegalAccessException {
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
    }

    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     *
     * @return the first word of line or "" if there is no word
     */
    private String scan() {
        line = line.trim();

        for (int i = 0; i < line.length(); i++)
            if (Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }

        return line;
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
