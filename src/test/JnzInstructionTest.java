package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

import static sml.Registers.Register.*;

class JnzInstructionTest {
    private Machine machine;
    private Registers registers;

    // Setting up streams to capture output
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
        System.setOut(originalOut);
    }

    @Test
    void executeValid() {
        Instruction instruction1 = new MovInstruction(null, EAX, 10);
        Instruction instruction2 = new MovInstruction(null, EBX, 5);
        Instruction instruction3 = new SubInstruction("f1", EAX, EBX);
        Instruction instruction4 = new JnzInstruction(null, EAX, "f1");

        List<Instruction> instructions = new ArrayList<>();
        instructions.add(instruction1);
        instructions.add(instruction2);
        instructions.add(instruction3);
        instructions.add(instruction4);

        machine.getProgram().addAll(instructions);
        machine.getLabels().addLabel("f1", 2);

        machine.execute();

        Assertions.assertEquals(0, machine.getRegisters().get(EAX));
    }

    @Test
    void executeValidTwo () {
        Instruction instruction1 = new MovInstruction(null, EAX, 6);
        Instruction instruction2 = new MovInstruction(null, EBX, 1);
        Instruction instruction3 = new MovInstruction(null, ECX, 1);
        Instruction instruction4 = new MulInstruction("f3", EBX, EAX);
        Instruction instruction5 = new SubInstruction(null, EAX, ECX);
        Instruction instruction6 = new JnzInstruction(null, EAX, "f3");
        Instruction instruction7 = new OutInstruction(null, EBX);

        List<Instruction> factorial = new ArrayList<>();
        factorial.add(instruction1);
        factorial.add(instruction2);
        factorial.add(instruction3);
        factorial.add(instruction4);
        factorial.add(instruction5);
        factorial.add(instruction6);
        factorial.add(instruction7);

        machine.getProgram().addAll(factorial);
        machine.getLabels().addLabel("f3", 3);

        machine.execute();

        Assertions.assertEquals(720, machine.getRegisters().get(EBX));
        Assertions.assertEquals("720", outContent.toString());
    }
}
