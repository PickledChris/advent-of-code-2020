package advent8

import advent8.Instruction.*
import java.nio.file.Files
import java.nio.file.Paths

val input: List<String> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/8.txt")
)!!

sealed class Instruction() {
    data class Acc(val int: Int) : Instruction()
    data class Jmp(val int: Int) : Instruction()
    data class Nop(val int: Int) : Instruction()
}

interface Execution
data class FailedExecution(val accValue: Int) : Execution
data class SuccessfulExecution(val accValue: Int) : Execution

val lineRegex = Regex("""(nop|acc|jmp) ([+\-][0-9]+)""")
fun parseInstruction(line: String): Instruction {

    val lineMatch = lineRegex.matchEntire(line)
    return if (lineMatch == null) {
        throw RuntimeException("Bad regex: ${line}")
    } else {
        when (lineMatch.groups.get(1)!!.value) {
            "nop" -> Nop(lineMatch.groups.get(2)!!.value.toInt())
            "jmp" -> Jmp(lineMatch.groups.get(2)!!.value.toInt())
            "acc" -> Acc(lineMatch.groups.get(2)!!.value.toInt())
            else -> throw RuntimeException("Failed ${lineMatch.groups.get(1)!!.value}")
        }
    }
}

fun execute(instructions: List<Instruction>): Execution {
    val maxInstructions = instructions.size

    tailrec fun nextStatement(programCounter: Int, accumulator: Int, previousInstructions: Set<Int>): Execution {
        if (programCounter >= maxInstructions) {
            return SuccessfulExecution(accumulator)
        } else if (previousInstructions.contains(programCounter)) {
            return FailedExecution(accumulator)
        }
        return when (val current = instructions[programCounter]) {
            is Acc -> nextStatement(programCounter + 1, accumulator + current.int, previousInstructions + programCounter)
            is Jmp -> nextStatement(programCounter + current.int, accumulator, previousInstructions)
            is Nop -> nextStatement(programCounter + 1, accumulator, previousInstructions + programCounter)
        }
    }
    return nextStatement(0, 0, setOf())
}

fun generateAlternativeInstructions(instructions: List<Instruction>): List<List<Instruction>> {
    val indexedInstructions: Map<Int, Instruction> = instructions.mapIndexed { index, instruction -> Pair(index, instruction) }.toMap()

    val allPermutations: List<List<Instruction>> = instructions.mapIndexedNotNull { index, instructionToReplace ->
        val newInstruction: Instruction? = when (instructionToReplace) {
            is Acc -> null
            is Jmp -> Nop(instructionToReplace.int)
            is Nop -> Jmp(instructionToReplace.int)
        }
        newInstruction?.let { newIns ->
            val thisPermutation: MutableMap<Int, Instruction> = indexedInstructions.toMutableMap()
            thisPermutation.set(index, newIns)
            thisPermutation.toList().sortedBy { it.first }.map { it.second }
        }
    }
    return allPermutations
}

fun main() {
    try {
        val instructions = input.map { parseInstruction(it) }
        println(execute(instructions))

        println(generateAlternativeInstructions(instructions).map{execute(it)}.find { it is SuccessfulExecution })


    } catch (exception: Exception) {
        exception.printStackTrace()
    }

}

