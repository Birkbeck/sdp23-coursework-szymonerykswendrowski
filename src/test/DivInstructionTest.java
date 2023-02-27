package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.AddInstruction;
import sml.instruction.DivInstruction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static sml.Registers.Register.*;

class DivInstructionTest {
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
    void zeroDivTest() {
        registers.set(EAX, 2);
        registers.set(EBX, 0);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals("Error: Division by zero, " +
                "value of result register won't change", outContent.toString());
        Assertions.assertEquals(2, machine.getRegisters().get(EAX));
    }

    @Test
    void divTestOne() {
        registers.set(EAX, 4);
        registers.set(EBX, 2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(2, machine.getRegisters().get(EAX));
    }

    @Test
    void divTestTwo () {
        registers.set(EAX, 0);
        registers.set(EBX, 2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(0, machine.getRegisters().get(EAX));
    }

    @Test
    void negativeDivTest () {
        registers.set(EAX, 4);
        registers.set(EBX, -2);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(-2, machine.getRegisters().get(EAX));
    }

    @Test
    void remainderDivTest () {
        registers.set(EAX, 8);
        registers.set(EBX, 3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(2, machine.getRegisters().get(EAX));
    }

    @Test
    void equalsTestOne() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new DivInstruction(null, EAX, EBX);
        Assertions.assertTrue(instruction.equals(instruction2));
    }

    @Test
    void equalsTestTwo() {
        registers.set(EAX, 5);
        registers.set(EBX, 6);
        registers.set(ECX, 7);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new DivInstruction(null, EAX, ECX);
        Assertions.assertFalse(instruction.equals(instruction2));
    }

    @Test
    void equalsMismatchTest() {
        registers.set(EAX, 2);
        registers.set(EBX, 3);
        Instruction instruction = new DivInstruction(null, EAX, EBX);
        Instruction instruction2 = new AddInstruction(null, EAX, EBX);
        Assertions.assertFalse(instruction.equals(instruction2));
    }
}
