import java.nio.file.Files
import java.nio.file.Paths

fun findEntries(expenses: List<Int>): Pair<Int, Int> {

    val whatever: List<Pair<Int, Int>> = expenses.mapNotNull {
        val a = it
        val found: Int? = expenses.find { it + a == 2020 }
        if (found != null) {
            Pair(a, found)
        } else {
            null
        }
    }

    return whatever.first()
}
// Laziness
val input: List<Int> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/day1input.txt")
).map { it.toInt() }

val result = findEntries(input)
result.first * result.second
