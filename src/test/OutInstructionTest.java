package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.AddInstruction;
import sml.instruction.OutInstruction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static sml.Registers.Register.*;

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
  void outTestOne() {
    registers.set(EAX, 5);
    Instruction instruction = new OutInstruction(null, EAX);
    instruction.execute(machine);
    Assertions.assertEquals("5", outContent.toString().trim());
  }

  @Test
  void outTestTwo() {
    registers.set(EAX, -5);
    Instruction instruction = new OutInstruction(null, EAX);
    instruction.execute(machine);
    Assertions.assertEquals("-5", outContent.toString().trim());
  }

  @Test
  void equalsTestOne() {
    registers.set(EAX, 5);
    Instruction instruction = new OutInstruction(null, EAX);
    Instruction instruction2 = new OutInstruction(null, EAX);
    Assertions.assertTrue(instruction.equals(instruction2));
  }

  @Test
  void equalsTestTwo() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new OutInstruction(null, EAX);
    Instruction instruction2 = new OutInstruction(null, EBX);
    Assertions.assertFalse(instruction.equals(instruction2));
  }

  @Test
  void equalsMismatchTest() {
    registers.set(EAX, 2);
    registers.set(EBX, 3);
    Instruction instruction = new OutInstruction(null, EAX);
    Instruction instruction2 = new AddInstruction(null, EAX, EBX);
    Assertions.assertFalse(instruction.equals(instruction2));
  }

  @Test
  void hashCodeTest() {
    registers.set(EAX, 5);
    Instruction instruction = new OutInstruction(null, EAX);
    Instruction instruction2 = new OutInstruction(null, EAX);
    Assertions.assertEquals(instruction.hashCode(), instruction2.hashCode());
  }
}
