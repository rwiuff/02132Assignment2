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

  val reg = Reg(UInt(16.W))
  when(io.stop | ~io.run) {
    reg := reg
  }.elsewhen(io.jump) {
    reg := io.programCounterJump
  }.otherwise {
    reg := reg + 1.U(16.W)
  }
  io.programCounter := reg

}
