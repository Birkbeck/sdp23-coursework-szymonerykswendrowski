package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.OutInstruction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static sml.Registers.Register.EAX;

class OutInstructionTest {
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
        registers.set(EAX, 5);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
        Assertions.assertEquals("5", outContent.toString());
    }

    @Test
    void executeValidTwo() {
        registers.set(EAX, -5);
        Instruction instruction = new OutInstruction(null, EAX);
        instruction.execute(machine);
        Assertions.assertEquals("-5", outContent.toString());
    }
}
