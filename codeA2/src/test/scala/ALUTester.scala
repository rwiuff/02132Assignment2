import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester}

class ALUTester(dut: ALU) extends PeekPokeTester(dut) {
  // Test for addition
  poke(dut.io.f, 0)
  poke(dut.io.a, 5)
  poke(dut.io.b, 3)
  expect(dut.io.result, 8)
  step(1)

  // Test for subtraction
  poke(dut.io.f, 1)
  poke(dut.io.a, 5)
  poke(dut.io.b, 3)
  expect(dut.io.result, 2)
  step(1)

  // Test for OR operation
  poke(dut.io.f, 2)
  poke(dut.io.a, 3)  // 0011
  poke(dut.io.b, 5)  // 0101
  expect(dut.io.result, 7)  // 0111
  step(1)

  // Test for AND operation
  poke(dut.io.f, 3)
  poke(dut.io.a, 3)  // 0011
  poke(dut.io.b, 5)  // 0101
  expect(dut.io.result, 1)  // 0001
  step(1)

  // Test for positive overflow during addition
  poke(dut.io.f, 0)
  poke(dut.io.a, 2147483647)  // Two's complement representation of 2147483647 in UInt
  poke(dut.io.b, 1)
  expect(dut.io.overflow, true)
  step(1)

// Test for negative overflow during subtraction
  poke(dut.io.f, 1)
  poke(dut.io.a, 2147483648L)  // Two's complement representation of -2147483648 in UInt
  poke(dut.io.b, 1)
  expect(dut.io.overflow, true)
  step(1)

// Test for Comparisons returning true
  poke(dut.io.f, 4)
  poke(dut.io.a, 2)
  poke(dut.io.b, 1)
  expect(dut.io.result, 1)
  step(1)

  // Test for Comparisons returning false
  poke(dut.io.f, 4)
  poke(dut.io.a, 2)
  poke(dut.io.b, 3)
  expect(dut.io.result, 0)
  step(1)

  // Test for MULT operation
  poke(dut.io.f, 5)
  poke(dut.io.a, 3)  // 0011
  poke(dut.io.b, 5)  // 0101
  expect(dut.io.result, 15)  // 1111
  step(1)
}

// object ALUTest extends App {
//   Driver(() => new ALU) {
//     c => new ALUTester(c)
//   }
// }
object ALUTester {
  def main(args: Array[String]): Unit = {
    println("Running the ALU tester")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "ALU"),
      () => new ALU()) {
      c => new ALUTester(c)
    }
  }
}
