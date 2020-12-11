package advent

import advent.Space.Empty
import advent.Space.Tree
import java.nio.file.Files
import java.nio.file.Paths

sealed class Space {
    object Tree : Space()
    object Empty : Space()
}

val input: List<String> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/day3input.txt")
)!!

val parsed: List<List<Space>> = input.map { line ->
    line.map { dotOrHash ->
        if (dotOrHash == '#') Tree else Empty
    }
}

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }

val infiniteLines: List<Sequence<Space>> = parsed.map { finiteLine ->
    finiteLine.asSequence().repeat()
}

fun isTree(right: Int, down: Int): List<Int> = infiniteLines
    .filterIndexed { index, _ -> index % down == 0 }
    .mapIndexed { index, sequence ->
        when (sequence.elementAt(index * right)) {
            Tree -> 1
            Empty -> 0
        }

    }

fun main() {
    val oneOne = isTree(1, 1).sum()
    val threeOne = isTree(3, 1).sum()
    val fiveOne = isTree(5, 1).sum()
    val sevenOne = isTree(7, 1).sum()
    val oneTwo = isTree(1, 2).sum()
    println(oneOne * threeOne * fiveOne * sevenOne * oneTwo)
}