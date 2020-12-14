package advent9

import java.nio.file.Files
import java.nio.file.Paths

val input: List<Long> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/9.txt")
)!!.map { it.toLong() }


fun containsAddends(number: Long, possibleAddends: List<Long>): Boolean {
    return possibleAddends.any { addend -> possibleAddends.contains(number - addend) }
}

tailrec fun checkIndex(index: Int): Long {
    val current: Long = input[index]

    return if (containsAddends(current, input.subList(index - 25, index))){
        checkIndex(index + 1)
    } else{
        current
    }
}

tailrec fun findContinuousAddends(total: Long, index: Int): List<Long>? {

    return if (continuouslySumsFromHere(total, index, index) != null){
      continuouslySumsFromHere(total, index, index)
    } else {
        findContinuousAddends(total, index + 1)
    }
}

tailrec fun continuouslySumsFromHere(total: Long, index: Int, to: Int): List<Long>? {
    val sum = input.subList(index, to).sum()
    return if (sum < total)
        continuouslySumsFromHere(total, index, to + 1)
    else if (sum > total)
        null
    else
        input.subList(index, to)
}

fun main(){

    println(checkIndex(25))
    val addends: List<Long>? = findContinuousAddends(41682220, 0)
    println(addends!!.minOrNull()!! + addends.maxOrNull()!!)

}