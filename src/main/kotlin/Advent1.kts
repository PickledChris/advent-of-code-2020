import java.nio.file.Files
import java.nio.file.Paths

fun findEntriesSummingTo2020(expenses: List<Int>): Pair<Int, Int>? {

    return expenses.find { expense ->
        val complement: Int? = expenses.find { it + expense == 2020 }
        complement != null
    }?.let {
        Pair(it, 2020 - it)
    }
}
// Laziness
val input: List<Int> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/day1input.txt")
).map { it.toInt() }

val result = findEntriesSummingTo2020(input)
println(result)
if (result != null) {
    println(result.first * result.second)
}
