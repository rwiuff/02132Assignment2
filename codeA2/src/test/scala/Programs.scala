import chisel3._
import chisel3.util._
import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester
import java.util

object Programs {
  val memTest = Array(
    "b1000_0001_0000_0000_0000_0000_1111_1111".U, // Save 255 in R1
    "b1000_0010_0000_0000_0000_0001_1001_0001".U, // Save 401 in R2
    "b1010_0000_0001_0010_0000_0000_0000_0000".U, // Save R1 in memory(R2)
    "b1001_0011_0000_0010_0000_0000_0000_0000".U, // Load memory(R2) to R3
    "b1000_0010_0000_0000_0000_0001_1001_0000".U, // Save 400 in R2
    "b1010_0000_0011_0010_0000_0000_0000_0000".U, // Save R3 in memory(R2)
    "b1111_0000_0000_0000_0000_0000_0000_0000".U  // Halt
  )
  val arithmeticTest = Array(
    "b1000_0000_0000_0000_0000_0000_0000_0000".U, // LOADI R0, 0
    "b1000_0001_0000_0000_0000_0000_0000_1010".U, // LOADI R1, 10
    "b0011_0000_0000_0000_0000_0000_0000_0010".U, // ADDI R0, R0, 2
    "b0001_0000_0001_0000_0000_0000_0000_0000".U, // ADD R0, R1, R0
    "b0101_0010_0000_0000_0000_0000_0000_0010".U, // MULT R2, R0, 2
    "b0010_0000_0010_0001_0000_0000_0000_0000".U, // SUB R0, R2, R1
    "b0100_0000_0000_0000_0000_0000_0000_0001".U, // SUBI R0, R0, 1
    "b0110_0000_0000_0001_0000_0000_0000_0000".U, // OR R0, R0, R1
    "b0111_0010_0010_0001_0000_0000_0000_0000".U, // AND R2, R2, R1
    "b0001_0000_0000_0010_0000_0000_0000_0000".U, // ADD R0, R0, R2
    "b0011_0000_0000_0000_0000_0000_0100_0010".U, // ADDI R0, R0, 66
    "b0101_0000_0000_0000_0000_0000_0000_0011".U, // MULT R0, R0, 3
    "b0100_0000_0000_0000_0000_0000_0000_1101".U, // SUBI R0 R0 13
    "b1011_0000_0000_0000_0000_0000_0000_0000".U, // INC R0
    "b1000_0100_0000_0000_0000_0001_1001_0000".U, // LOADI R4, 400
    "b1010_0000_0000_0100_0000_0000_0000_0000".U, // STORE R0, R4
    "b1111_0000_0000_0000_0000_0000_0000_0000".U  // HALT
  )
  val jumpTest = Array(
    "b1000_0000_0000_0000_0000_0000_1111_1111".U, // LOADI R0, 255
    "b1000_0001_0000_0000_0000_0001_1001_0000".U, // LOADI R1, 400
    "b1101_0000_0000_0001_0000_0000_0000_0100".U, // JEQ R0, R1, 4
    "b1000_0010_0000_0000_0000_0000_1111_1111".U, // LOADI R2, 255
    "b1101_0000_0000_0010_0000_0000_0000_0110".U, // JEQ R0, R2, 6
    "b1111_0000_0000_0000_0000_0000_0000_0000".U, // HALT
    "b1010_0000_0000_0001_0000_0000_0000_0000".U, // STORE, R0, R1
    "b1100_0000_0000_0000_0000_0000_0000_0101".U  // JMP 5
  )
  val incTest = Array(
    "b1011_0000_0000_0000_0000_0000_0000_0000".U, // INC R0
    "b1011_0000_0000_0000_0000_0000_0000_0000".U, // INC R0
    "b1011_0000_0000_0000_0000_0000_0000_0000".U, // INC R0
    "b1011_0000_0000_0000_0000_0000_0000_0000".U, // INC R0
    "b1011_0000_0000_0000_0000_0000_0000_0000".U, // INC R0
    "b1011_0001_0001_0000_0000_0000_0000_0000".U, // INC R1
    "b1011_0001_0001_0000_0000_0000_0000_0000".U, // INC R1
    "b1011_0001_0001_0000_0000_0000_0000_0000".U, // INC R1
    "b1011_0001_0001_0000_0000_0000_0000_0000".U, // INC R1
    "b1011_0001_0001_0000_0000_0000_0000_0000".U, // INC R1
    "b1111_0000_0000_0000_0000_0000_0000_0000".U  // HALT
  )
  val erosionAlgorithm = Array(
    "b1000_0000_0000_0000_0000_0000_0000_0000".U, // LOADI R0, 0;
    "b1000_0001_0000_0000_0000_0000_0000_0000".U, // LOADI R1, 0;
    "b1000_0010_0000_0000_0000_0000_0001_0011".U, // LOADI R2, 19;
    "b1000_0011_0000_0000_0000_0000_0000_0000".U, // LOADI R3, 0;
    "b1000_0100_0000_0000_0000_0000_1111_1111".U, // LOADI R4, 255;
    "b1101_0000_0000_0010_0000_0000_0010_1111".U, // JEQ R0, R2, 47;
    "b1101_0000_0001_0010_0000_0000_0010_1100".U, // JEQ R1, R2, 44;
    "b0101_0101_0001_0000_0000_0000_0001_0100".U, // MULT R5, R1, 20;
    "b0001_0110_0000_0101_0000_0000_0000_0000".U, // ADD R6, R0, R5;
    "b0011_0101_0110_0000_0000_0001_1001_0000".U, // ADDI R5, R6, 400;
    "b1101_0000_0000_0011_0000_0000_0010_1000".U, // JEQ R0, R3, 40;
    "b1101_0000_0001_0011_0000_0000_0010_1000".U, // JEQ R1, R3, 40;
    "b1101_0000_0000_0100_0000_0000_0010_1000".U, // JEQ R0, R4, 40;
    "b1101_0000_0001_0100_0000_0000_0010_1000".U, // JEQ R1, R4, 40;
    "b0101_0110_0001_0000_0000_0000_0001_0100".U, // MULT R6, R1, 20;
    "b0001_0111_0000_0110_0000_0000_0000_0000".U, // ADD R7, R0, R6;
    "b1001_1000_0000_0111_0000_0000_0000_0000".U, // LOAD R8, R7;
    "b1101_0000_1000_0011_0000_0000_0010_1000".U, // JEQ R8, R3, 40;
    "b0100_1100_0000_0000_0000_0000_0000_0001".U, // SUBI R12, R0, 1;
    "b0011_1101_0000_0000_0000_0000_0000_0001".U, // ADDI R13, R0, 1;
    "b0100_1110_0001_0000_0000_0000_0000_0001".U, // SUBI R14, R1, 1;
    "b0011_1111_0001_0000_0000_0000_0000_0001".U, // ADDI R15, R1, 1;
    "b0101_0110_0001_0000_0000_0000_0001_0100".U, // MULT R6, R1, 20;
    "b0001_0111_0110_1100_0000_0000_0000_0000".U, // ADD R7, R6, R12;
    "b1001_1000_0000_0111_0000_0000_0000_0000".U, // LOAD R8, R7;
    "b0101_0110_0001_0000_0000_0000_0001_0100".U, // MULT R6, R1, 20;
    "b0001_0111_0110_1101_0000_0000_0000_0000".U, // ADD R7, R6, R13;
    "b1001_1001_0000_0111_0000_0000_0000_0000".U, // LOAD R9, R7;
    "b0110_1010_1000_1001_0000_0000_0000_0000".U, // OR R10, R8, R9;
    "b0101_0110_1110_0000_0000_0000_0001_0100".U, // MULT R6, R14, 20;
    "b0001_0111_0110_0000_0000_0000_0000_0000".U, // ADD R7, R6, R0;
    "b1001_1000_0000_0111_0000_0000_0000_0000".U, // LOAD R8, R7;
    "b0110_1001_1000_1010_0000_0000_0000_0000".U, // OR R9, R8, R10;
    "b0101_0110_1111_0000_0000_0000_0001_0100".U, // MULT R6, R15, 20;
    "b0001_0111_0110_0000_0000_0000_0000_0000".U, // ADD R7, R6, R0;
    "b1001_1010_0000_0111_0000_0000_0000_0000".U, // LOAD R10, R7;
    "b0110_1000_1001_1010_0000_0000_0000_0000".U, // OR R8, R9, R10;
    "b1101_0000_1000_0011_0000_0000_0010_1000".U, // JEQ R8, R3, 40;
    "b1010_0000_0100_0101_0000_0000_0000_0000".U, // STORE R4, R5;
    "b1100_0000_0000_0000_0000_0000_0010_1010".U, // JMP 42;
    "b1010_0000_0011_0101_0000_0000_0000_0000".U, // STORE R3, R5;
    "b1100_0000_0000_0000_0000_0000_0010_1010".U, // JMP 42;
    "b1011_0001_0001_0000_0000_0000_0000_0000".U, // INC R1;
    "b1100_0000_0000_0000_0000_0000_0000_0110".U, // JMP 6;
    "b1011_0000_0000_0000_0000_0000_0000_0000".U, // INC R0;
    "b1000_0001_0000_0000_0000_0000_0000_0000".U, // LOADI R1, 0;
    "b1100_0000_0000_0000_0000_0000_0000_0101".U, // JMP 5;
    "b1111_0000_0000_0000_0000_0000_0000_0000".U  // END;
  )
}
