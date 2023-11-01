import chisel3._
import chisel3.util._


class CPUTop extends Module {
  val io = IO(new Bundle {
    val done = Output(Bool ())
    val run = Input(Bool ())
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerDataMemEnable = Input(Bool ())
    val testerDataMemAddress = Input(UInt (16.W))
    val testerDataMemDataRead = Output(UInt (32.W))
    val testerDataMemWriteEnable = Input(Bool ())
    val testerDataMemDataWrite = Input(UInt (32.W))
    //This signals are used by the tester for loading and dumping the memory content, do not touch
    val testerProgMemEnable = Input(Bool ())
    val testerProgMemAddress = Input(UInt (16.W))
    val testerProgMemDataRead = Output(UInt (32.W))
    val testerProgMemWriteEnable = Input(Bool ())
    val testerProgMemDataWrite = Input(UInt (32.W))
  })

  //Creating components
  val programCounter = Module(new ProgramCounter())
  val dataMemory = Module(new DataMemory())
  val programMemory = Module(new ProgramMemory())
  val registerFile = Module(new RegisterFile())
  val controlUnit = Module(new ControlUnit())
  val alu = Module(new ALU())

  //Connecting the modules
  //programCounter.io.run := io.run
  //programMemory.io.address := programCounter.io.programCounter

  ////////////////////////////////////////////
  //Continue here with your connections
  ////////////////////////////////////////////
  // Connect control unit signals
  controlUnit.io.opcode := programMemory.io.instructionRead(3, 0)
  controlUnit.io.writeRegister := registerFile.io.writeEnable
  controlUnit.io.stop := io.done
  controlUnit.io.immediateJump := programMemory.io.testerDataRead(3, 0)
  controlUnit.io.jump := programMemory.io.testerDataRead(7, 4) 
  controlUnit.io.aluFunc := alu.io.f
  controlUnit.io.immediateOperand := programMemory.io.testerDataRead(11, 8) 
  controlUnit.io.immediateLoad := programMemory.io.testerDataRead(15, 12) 
  controlUnit.io.loadFromMemory := programMemory.io.testerDataRead(19, 16) 
  controlUnit.io.writeToMemory := programMemory.io.testerDataRead(23, 20) 
  alu.io.immediate := programMemory.io.testerDataRead(15, 12)


  // Connect ALU signals
  alu.io.f := controlUnit.io.aluFunc
  alu.io.a := registerFile.io.a
  alu.io.b := registerFile.io.b
  alu.io.immediate := programMemory.io.testerDataRead 

  // Connect register file signals
  registerFile.io.writeEnable := controlUnit.io.writeRegister
  registerFile.io.writeData := alu.io.result
  registerFile.io.aSel := controlUnit.io.readRegister1
  registerFile.io.bSel := controlUnit.io.readRegister2    


  //This signals are used by the tester for loading the program to the program memory, do not touch
  programMemory.io.testerAddress := io.testerProgMemAddress
  io.testerProgMemDataRead := programMemory.io.testerDataRead
  programMemory.io.testerDataWrite := io.testerProgMemDataWrite
  programMemory.io.testerEnable := io.testerProgMemEnable
  programMemory.io.testerWriteEnable := io.testerProgMemWriteEnable
  //This signals are used by the tester for loading and dumping the data memory content, do not touch
  dataMemory.io.testerAddress := io.testerDataMemAddress
  io.testerDataMemDataRead := dataMemory.io.testerDataRead
  dataMemory.io.testerDataWrite := io.testerDataMemDataWrite
  dataMemory.io.testerEnable := io.testerDataMemEnable
  dataMemory.io.testerWriteEnable := io.testerDataMemWriteEnable
}