import chisel3._
import chisel3.util._

class ProgramCounter extends Module {
  val io = IO(new Bundle {
    val stop = Input(Bool())
    val jump = Input(Bool())
    val run = Input(Bool())
    val programCounterJump = Input(UInt(16.W))
    val programCounter = Output(UInt(16.W))
  })

  val notRun = ~io.run // Not gate
  val stopOrRun = notRun | io.stop // Or gate
  val countRegister = RegInit(0.U) // Register init
  val incremented = 1.U + countRegister // Adder
  val jumpOrInc = Mux(io.jump, incremented, io.programCounterJump) // First mux
  val toReg = Mux(stopOrRun, jumpOrInc, countRegister) // Second mux
  countRegister := toReg // Update reg

  io.programCounter := countRegister // Output

}
