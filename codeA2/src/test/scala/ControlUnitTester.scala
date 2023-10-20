import chisel3._
import chisel3.iotesters.PeekPokeTester

class ControlUnitTester(dut: ControlUnit) extends PeekPokeTester(dut) {
  // Test Add
  poke(dut.io.opcode, 1.U(4.W))
  expect(dut.io.aluFunc, 0.U)
  expect(dut.io.writeRegister, 1.U)
  step(1)

}

object ControlUnitTester {
  def main(args: Array[String]): Unit = {
    println("Testing Control Unit")
    iotesters.Driver.execute(
      Array(
        "--generate-vcd-output",
        "on",
        "--target-dir",
        "generated",
        "--top-name",
        "ControlUnit"
      ),
      () => new ControlUnit()
    ) { c =>
      new ControlUnitTester(c)
    }
  }
}
