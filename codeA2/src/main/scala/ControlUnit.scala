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
    val readRegister1 = Output(UInt(5.W))
    val readRegister2 = Output(UInt(5.W))
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
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(2.U(4.W)) { // SUB
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 1.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(3.U(4.W)) { // ADDI
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := 1.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(4.U(4.W)) { // SUBI
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 1.U(3.W)
      io.immediateOperand := 1.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(5.U(4.W)) { // MULT
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 5.U(3.W)
      io.immediateOperand := 1.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(6.U(4.W)) { // OR
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 2.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(7.U(4.W)) { // AND
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 3.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(8.U(4.W)) { // LOADI
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 1.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(9.U(4.W)) { // LOAD
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 1.U
      io.writeToMemory := 0.U
    }
    is(10.U(4.W)) { // STORE
      io.writeRegister := 0.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 1.U
    }
    is(11.U(4.W)) { // INC
      io.writeRegister := 1.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 6.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(12.U(4.W)) { // JMP
      io.writeRegister := 0.U
      io.stop := 0.U
      io.immediateJump := 1.U
      io.jump := 1.U
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(13.U(4.W)) { // JEQ
      io.writeRegister := 0.U
      io.stop := 0.U
      io.immediateJump := 0.U
      io.jump := 1.U
      io.aluFunc := 4.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
    is(15.U(4.W)) { // END
      io.writeRegister := 0.U
      io.stop := 1.U
      io.immediateJump := 0.U
      io.jump := 0.U
      io.aluFunc := 0.U(3.W)
      io.immediateOperand := 0.U
      io.immediateLoad := 0.U
      io.loadFromMemory := 0.U
      io.writeToMemory := 0.U
    }
  }

}
