import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester}

class ALUTester(c: ALU) extends PeekPokeTester(c) {
  // Test for addition
  poke(c.io.f, 0)
  poke(c.io.a, 5)
  poke(c.io.b, 3)
  expect(c.io.result, 8)
  step(1)

  // Test for subtraction
  poke(c.io.f, 1)
  poke(c.io.a, 5)
  poke(c.io.b, 3)
  expect(c.io.result, 2)
  step(1)

  // Test for OR operation
  poke(c.io.f, 2)
  poke(c.io.a, 3)  // 0011
  poke(c.io.b, 5)  // 0101
  expect(c.io.result, 7)  // 0111
  step(1)

  // Test for AND operation
  poke(c.io.f, 3)
  poke(c.io.a, 3)  // 0011
  poke(c.io.b, 5)  // 0101
  expect(c.io.result, 1)  // 0001
  step(1)

  // Test for positive overflow during addition
  poke(c.io.f, 0)
  poke(c.io.a, 2147483647)  // Two's complement representation of 2147483647 in UInt
  poke(c.io.b, 1)
  expect(c.io.overflow, true)
  step(1)

// Test for negative overflow during subtraction
  poke(c.io.f, 1)
  poke(c.io.a, 2147483648L)  // Two's complement representation of -2147483648 in UInt
  poke(c.io.b, 1)
  expect(c.io.overflow, true)
  step(1)

// Test for Comparisons returning true
  poke(c.io.f, 4)
  poke(c.io.a, 2)
  poke(c.io.b, 1)
  expect(c.io.result, 1)
  step(1)

  // Test for Comparisons returning true
  poke(c.io.f, 4)
  poke(c.io.a, 2)
  poke(c.io.b, 3)
  expect(c.io.result, 0)
  step(1)
}

object ALUTest extends App {
  Driver(() => new ALU) {
    c => new ALUTester(c)
  }
}
