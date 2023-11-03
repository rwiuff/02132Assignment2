import chisel3._
import chisel3.util._
import chisel3.iotesters
import chisel3.iotesters.PeekPokeTester
import java.util

object Programs {
  val test0 = Array(
    "b1000_0001_0000_0000_0000_0000_1111_1111".U, // Save 255 in R1
    "b1000_0010_0000_0000_0000_0001_1001_0000".U, // Save 400 in R2
    "b1010_0000_0001_0010_0000_0000_0000_0000".U, // Save R1 in memory(R2)
    "b1111_0000_0000_0000_0000_0000_0000_0000".U  // Halt
  )
  val test1 = Array(
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
    "b1000_0100_0000_0000_0000_0001_1001_0000".U, // LOADI R4, 400
    "b1010_0000_0000_0100_0000_0000_0000_0000".U, // STORE R0, R4
    "b1111_0000_0000_0000_0000_0000_0000_0000".U  // HALT
  )
  val erosion = Array(
    "b1000_0000_0000_0000_0000_0000_0000_0000".U,
    "b1000_0001_0000_0000_0000_0000_0000_0000".U,
    "b1000_0010_0000_0000_0000_0000_0001_0011".U,
    "b1000_0011_0000_0000_0000_0000_0000_0000".U,
    "b1000_0100_0000_0000_0000_0000_1111_1111".U,
    "b1101_0000_0000_0010_0000_0000_0010_1111".U,
    "b1101_0000_0001_0010_0000_0000_0010_1100".U,
    "b0101_0101_0001_0000_0000_0000_0001_0100".U,
    "b0001_0110_0000_0101_0000_0000_0000_0000".U,
    "b0011_0101_0110_0000_0000_0001_1001_0000".U,
    "b1101_0000_0000_0011_0000_0000_0010_1000".U,
    "b1101_0000_0001_0011_0000_0000_0010_1000".U,
    "b1101_0000_0000_0100_0000_0000_0010_1000".U,
    "b1101_0000_0001_0100_0000_0000_0010_1000".U,
    "b0101_0110_0001_0000_0000_0000_0001_0100".U,
    "b0001_0111_0000_0110_0000_0000_0000_0000".U,
    "b1001_1000_0000_0111_0000_0000_0000_0000".U,
    "b1101_0000_1000_0011_0000_0000_0010_1000".U,
    "b0100_1100_0000_0000_0000_0000_0000_0001".U,
    "b0011_1101_0000_0000_0000_0000_0000_0001".U,
    "b0100_1110_0001_0000_0000_0000_0000_0001".U,
    "b0011_1111_0001_0000_0000_0000_0000_0001".U,
    "b0101_0110_0001_0000_0000_0000_0001_0100".U,
    "b0001_0111_0110_1100_0000_0000_0000_0000".U,
    "b1001_1000_0000_0111_0000_0000_0000_0000".U,
    "b0101_0110_0001_0000_0000_0000_0001_0100".U,
    "b0001_0111_0110_1101_0000_0000_0000_0000".U,
    "b1001_1001_0000_0111_0000_0000_0000_0000".U,
    "b0110_1010_1000_1001_0000_0000_0000_0000".U,
    "b0101_0110_1110_0000_0000_0000_0001_0100".U,
    "b0001_0111_0110_0000_0000_0000_0000_0000".U,
    "b1001_1000_0000_0111_0000_0000_0000_0000".U,
    "b0110_1001_1000_1010_0000_0000_0000_0000".U,
    "b0101_0110_1111_0000_0000_0000_0001_0100".U,
    "b0001_0111_0110_0000_0000_0000_0000_0000".U,
    "b1001_1010_0000_0111_0000_0000_0000_0000".U,
    "b0110_1000_1001_1010_0000_0000_0000_0000".U,
    "b1101_0000_1000_0011_0000_0000_0010_1000".U,
    "b1010_0000_0100_0101_0000_0000_0000_0000".U,
    "b1100_0000_0000_0000_0000_0000_0010_1010".U,
    "b1010_0000_0011_0101_0000_0000_0000_0000".U,
    "b1100_0000_0000_0000_0000_0000_0010_1010".U,
    "b1011_0001_0000_0000_0000_0000_0000_0000".U,
    "b1100_0000_0000_0000_0000_0000_0000_0110".U,
    "b1011_0000_0000_0000_0000_0000_0000_0000".U,
    "b1000_0001_0000_0000_0000_0000_0000_0000".U,
    "b1100_0000_0000_0000_0000_0000_0000_0101".U,
    "b1111_0000_0000_0000_0000_0000_0000_0000".U
  )
}
