package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;
import sml.instruction.AddInstruction;
import sml.instruction.SubInstruction;

import static sml.Registers.Register.*;

class AddInstructionTest {
  private Machine machine;
  private Registers registers;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    registers = machine.getRegisters();
  }

  @AfterEach
  void tearDown() {
    machine = null;
    registers = null;
  }

  @Test
  void addTestOne() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(11, machine.getRegisters().get(EAX));
  }

  @Test
  void addTestTwo() {
    registers.set(EAX, -5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(1, machine.getRegisters().get(EAX));
  }

  @Test
  void equalsTestOne() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    Instruction instruction2 = new AddInstruction(null, EAX, EBX);
    Assertions.assertTrue(instruction.equals(instruction2));
  }

  @Test
  void equalsTestTwo() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    registers.set(ECX, 7);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    Instruction instruction2 = new AddInstruction(null, EAX, ECX);
    Assertions.assertFalse(instruction.equals(instruction2));
  }

  @Test
  void equalsMismatchTest() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    Instruction instruction2 = new SubInstruction(null, EAX, EBX);
    Assertions.assertFalse(instruction.equals(instruction2));
  }
}
