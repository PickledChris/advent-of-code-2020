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

fun execute(instructions: List<Instruction>): Int {

    tailrec fun nextStatement(programCounter: Int, accumulator: Int, previousInstructions: Set<Int>): Int {
        if (previousInstructions.contains(programCounter)){
            return accumulator
        }
        return when (val current = instructions[programCounter]) {
            is Acc -> nextStatement(programCounter + 1, accumulator + current.int, previousInstructions + programCounter)
            is Jmp -> nextStatement(programCounter + current.int, accumulator, previousInstructions)
            is Nop -> nextStatement(programCounter + 1, accumulator, previousInstructions + programCounter)
        }
    }
    return nextStatement(0, 0, setOf())
}

fun main() {
    try {
        val instructions = input.map { parseInstruction(it) }
        val result = execute(instructions)
        println(result)
    } catch (exception: Exception) {
        exception.printStackTrace()
    }

}

