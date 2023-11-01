import chisel3._
import chisel3.util._

class ControlUnit extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(4.W)) // Input Opcode
    val writeRegister = Output(Bool())
    val stop = Output(Bool())
    val immediateJump = Output(Bool())
    val jump = Output(Bool())
    val aluFunc = Output(UInt(3.W))
    val immediateOperand = Output(Bool())
    val immediateLoad = Output(Bool())
    val loadFromMemory = Output(Bool())
    val writeToMemory = Output(Bool())
  })

  // Default realisation
  io.writeRegister := false.B
  io.stop := false.B
  io.immediateJump := false.B
  io.jump := false.B
  io.aluFunc := 0.U(3.W)
  io.immediateOperand := false.B
  io.immediateLoad := false.B
  io.loadFromMemory := false.B
  io.writeToMemory := false.B

  switch(io.opcode) {
    is(1.U(4.W)) { // ADD
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(2.U(4.W)) { // SUB
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 1.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(3.U(4.W)) { // ADDI
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := true.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(4.U(4.W)) { // SUBI
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 1.U(3.W)
      io.immediateOperand := true.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(5.U(4.W)) { // MULT
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 5.U(3.W)
      io.immediateOperand := true.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(6.U(4.W)) { // OR
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 2.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(7.U(4.W)) { // AND
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 3.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(8.U(4.W)) { // LOADI
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := true.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(9.U(4.W)) { // LOAD
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := true.B
      io.writeToMemory := false.B
    }
    is(10.U(4.W)) { // STORE
      io.writeRegister := false.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := true.B
    }
    is(11.U(4.W)) { // INC
      io.writeRegister := true.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 6.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(12.U(4.W)) { // JMP
      io.writeRegister := false.B
      io.stop := false.B
      io.immediateJump := true.B
      io.jump := true.B
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(13.U(4.W)) { // JEQ
      io.writeRegister := false.B
      io.stop := false.B
      io.immediateJump := false.B
      io.jump := true.B
      io.aluFunc := 4.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
    is(15.U(4.W)) { // END
      io.writeRegister := false.B
      io.stop := true.B
      io.immediateJump := false.B
      io.jump := false.B
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := false.B
      io.immediateLoad := false.B
      io.loadFromMemory := false.B
      io.writeToMemory := false.B
    }
  }

}
