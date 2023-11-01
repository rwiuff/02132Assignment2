import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val f = Input(UInt(3.W))
    val a = Input(UInt(32.W))
    val b = Input(UInt(32.W))
    val result = Output(UInt(32.W))
    //probably not needed. we will never overflow this 32 bit ALU with a 20x20 pixel picture.
    val overflow = Output(Bool())
    val immediate = Output(UInt(4.W))

  })

  val result = Wire(UInt(32.W))
  //default value
  result := 0.U
  io.immediate := 0.U


  //ALU functionallity Selection 
  switch (io.f) {
    is(0.U) {result := io.a + io.b}
    is(1.U) {result := io.a - io.b}
    is(2.U) {result := io.a | io.b}
    is(3.U) {result := io.a & io.b}
    is(4.U) {result := io.a === io.b}
    is(5.U) {result := io.a * io.b}
    is(6.U) {result := io.a + (1.U)}
  }

    //output
  io.result := result

   // Overflow detection
  io.overflow := (io.f === 0.U && ((io.a(31) === 0.U && io.b(31) === 0.U && io.result(31) === 1.U) || 
                 (io.a(31) === 1.U && io.b(31) === 1.U && io.result(31) === 0.U))) ||
                 (io.f === 1.U && ((io.a(31) === 0.U && io.b(31) === 1.U && io.result(31) === 1.U) || 
                 (io.a(31) === 1.U && io.b(31) === 0.U && io.result(31) === 0.U)))



}