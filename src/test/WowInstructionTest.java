package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.InstructionFactory;
import sml.Machine;
import sml.Registers;
import sml.instruction.WowInstruction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;

class WowInstructionTest {
  private Machine machine;

  // Setting up streams to capture output
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  void tearDown() {
    machine = null;
    System.setOut(originalOut);
  }

  @Test
  void executeTest() {
    List<String> args = new ArrayList<>();
    args.add("f0");
    args.add("5");
    Instruction instruction = InstructionFactory.getInstruction(WowInstruction.class, args);
    instruction.execute(machine);
    Assertions.assertEquals("Wow!\r\nWow!\r\nWow!\r\nWow!\r\nWow!",
            outContent.toString().trim());
  }
}