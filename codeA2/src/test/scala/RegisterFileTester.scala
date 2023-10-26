import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester}

class RegisterFileTester(dut: RegisterFile) extends PeekPokeTester(dut) {
  // Initialize the register file with some values
  for (i <- 0 until 32) {
    poke(dut.io.writeEnable, true.B)
    poke(dut.io.writeData, i.U)
    poke(dut.io.writeSel, i.U)
    step(1)
  }

  // Read data from specific registers and verify
  for (i <- 0 until 32) {
    poke(dut.io.writeEnable, false.B)
    poke(dut.io.aSel, i.U)
    poke(dut.io.bSel, i.U)
    step(1)
    expect(dut.io.a, i.U)
    expect(dut.io.b, i.U)
  }


  // Test for write-read consistency
  for (i <- 0 until 32) {
    val writeData = i.U

    // Write data to a register
    poke(dut.io.writeEnable, true.B)
    poke(dut.io.writeData, writeData)
    poke(dut.io.writeSel, i.U)
    step(1)

    // Read from the same register and verify consistency
    poke(dut.io.writeEnable, false.B)
    poke(dut.io.aSel, i.U)
    step(1)
    expect(dut.io.a, writeData)
  }

   // Test for read-write conflict
  // Write to a register while simultaneously attempting to read from it
  for (i <- 0 until 32) {
    val writeData = i.U

    // Start a read operation
    poke(dut.io.writeEnable, false.B)
    poke(dut.io.aSel, i.U)
    // Start a write operation to the same register
    poke(dut.io.writeEnable, true.B)
    poke(dut.io.writeData, writeData)
    poke(dut.io.writeSel, i.U)
    step(1)

    // The read should reflect the new value written
    expect(dut.io.a, writeData)
  }
}

object RegisterFileTester {
  def main(args: Array[String]): Unit = {
    println("Running the RegisterFile tester")
    iotesters.Driver.execute(
      Array("--generate-vcd-output", "on",
        "--target-dir", "generated",
        "--top-name", "RegisterFile"),
      () => new RegisterFile()) {
      c => new RegisterFileTester(c)
    }
  }
}
