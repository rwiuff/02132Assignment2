import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(4.W)) // Input Opcode
    val writeRegister = Output(UInt(1.W))
    val stop = Output(UInt(1.W))
    val immediateJump = Output(UInt(1.W))
    val jump = Output(UInt(1.W))
    val aluFunc = Output(UInt(3.W))
    val immediateOperand = Output(UInt(1.W))
    val immediateLoad = Output(UInt(1.W))
    val loadFromMemory = Output(UInt(1.W))
    val writeToMemory = Output(UInt(1.W))
  })

  // Default realisation
  io.writeRegister := 0.U
  io.stop := 0.U
  io.immediateJump := 0.U
  io.jump := 0.U
  io.aluFunc := 0.U(3.W)
  io.immediateOperand := 0.U
  io.immediateLoad := 0.U
  io.loadFromMemory := 0.U
  io.writeToMemory := 0.U

  switch(io.opcode) {
    is(1.U(4.W)) { // ADD

    }
    is(2.U(4.W)) { // SUB

    }
    is(3.U(4.W)) { // OR

    }
    is(4.U(4.W)) { // AND

    }
    is(5.U(4.W)) { // NOT

    }
    is(6.U(4.W)) { // LD

    }
    is(7.U(4.W)) { // SD

    }
    is(8.U(4.W)) { // JMP

    }
    is(9.U(4.W)) { // JEQ

    }
    is(10.U(4.W)) { // JLT

    }
    is(11.U(4.W)) { // JGT

    }
    is(12.U(4.W)) { // ADDI

    }
    is(13.U(4.W)) { // SUBI

    }
    is(14.U(4.W)) { // LI

    }
    is(0.U(4.W)) { // NOP

    }
    is(15.U(4.W)) { // END

    }
  }

}
