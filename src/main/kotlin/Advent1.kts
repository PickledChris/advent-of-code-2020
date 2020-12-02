import java.nio.file.Files
import java.nio.file.Paths

// First Kotlin script, trying to be idiomatic not optimal

fun find2EntriesSummingToN(expenses: List<Int>, n: Int): Pair<Int, Int>? {
    return expenses.find { expense ->
        val complement: Int? = expenses.find { it + expense == n }
        complement != null
    }?.let {
        Pair(it, n - it)
    }
}

fun find3EntriesSummingTo2020(expenses: List<Int>): Triple<Int, Int, Int>? {
    val firstExpense = expenses.find { expense ->
        val expenseComplement = expenses.find { find2EntriesSummingToN(expenses, 2020 - expense) != null }
        expenseComplement != null
    }

    return firstExpense?.let { fe ->
        val secondExpense: Pair<Int, Int>? = find2EntriesSummingToN(expenses, 2020 - fe)
        return secondExpense?.let {
            return Triple(fe, it.first, it.second)
        }
    }
}

// Laziness
val input: List<Int> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/day1input.txt")
).map { it.toInt() }

val result2 = find2EntriesSummingToN(input, 2020)
println(result2)
if (result2 != null) {
    println(result2.first * result2.second)
}

val result3 = find3EntriesSummingTo2020(input)
println(result3)
if (result3 != null) {
    println(result3.first * result3.second * result3.third)
}