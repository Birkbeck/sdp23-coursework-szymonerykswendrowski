package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.TypeVariable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

import static sml.Registers.Register;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"

    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    // TODO: Then, replace the switch by using the Reflection API

    // TODO: Next, use dependency injection to allow this machine class
    //       to work with different sets of opcodes (different CPUs)
    private Instruction getInstruction(String label) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (line.isEmpty())
            return null;

        String opcode = scan();
        String instructionName = opcode.substring(0, 1).toUpperCase() +
                opcode.substring(1).toLowerCase();
        Class<?> instructionClass = Class.forName("sml.instruction."
                + instructionName + "Instruction");

        Constructor<?>[] classConstructors = instructionClass.getConstructors();
        Constructor<?> candidateConstructor = classConstructors[0];
        int parameterCount = classConstructors[0].getParameterCount();

        List<String> arguments = new ArrayList<>();
        arguments.add(label);
        IntStream.range(0, parameterCount - 1).mapToObj(i -> scan()).forEach(arguments::add);
        System.out.println(arguments);

        int argumentLen = arguments.toArray().length;
        String[] argumentsList = arguments.toArray(new String[argumentLen]);


        Object[] parameterObjs = new Object[argumentLen];
        // get the candidate constructor parameters
        Class<?>[] parameterTypes = classConstructors[0].getParameterTypes();
        for (int i = 0; i < argumentLen; i++) {
            // attempt to type the parameters using any available string constructors
            // NoSuchMethodException will be thrown where retyping isn't possible
            Class<?> c = toWrapper(parameterTypes[i]);
            parameterObjs[i] = c;
            System.out.println(parameterObjs[i]);
        }
        System.out.println(Arrays.toString(parameterObjs));
            // return instance ob object using the successful constructor
            // and parameters of the right class types.

        for (int j = 0; j < argumentLen; j++) {
            if (parameterObjs[j].equals(Integer.class)) {
                parameterObjs[j] = Integer.parseInt(argumentsList[j]);
            } else if (parameterObjs[j].equals(RegisterName.class)) {
                parameterObjs[j] = Register.valueOf(argumentsList[j]);
            } else {
                parameterObjs[j] = argumentsList[j];
            }
        }
        System.out.println(Arrays.toString(parameterObjs));

        return (Instruction) candidateConstructor.newInstance(parameterObjs);
    }

    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
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
     * Return the correct Wrapper class if testClass is primitive
     *
     * @param testClass class being tested
     * @return Object class or testClass
     */
    private static Class<?> toWrapper(Class<?> testClass) {
        return PRIMITIVE_TYPE_WRAPPERS.getOrDefault(testClass, testClass);
    }
}
