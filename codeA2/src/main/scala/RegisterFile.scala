import chisel3._
import chisel3.util._

class RegisterFile extends Module {
  val io = IO(new Bundle {
    val a = Output(UInt(32.W))
    val b = Output(UInt(32.W))
    val writeEnable = Input(Bool())
    val writeData = Input(UInt(32.W))
    val writeSel = Input(UInt(5.W))
    val aSel = Input(UInt(5.W))
    val bSel = Input(UInt(5.W))
  })

  // Define the number of registers in the file
  val numRegisters = 32

  // Create a registers
  val registers = Reg((Vec(32, UInt(32.W))))

  // Read data from the register file
  io.a := registers(io.aSel)
  io.b := registers(io.bSel)

  // Write data to the register file if writeEnable is asserted
  when(io.writeEnable) {
    registers(io.writeSel) := io.writeData
  }
}
