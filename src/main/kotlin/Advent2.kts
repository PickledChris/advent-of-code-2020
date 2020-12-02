import java.nio.file.Files
import java.nio.file.Paths

data class ParsedLine(val password: String, val character: Char, val leftNum: Int, val rightNum: Int){
    fun firstIsValid(): Boolean {
        val occurrences = password.count{it == character}
        return occurrences in leftNum..rightNum
    }

    fun secondIsValid(): Boolean {
        val leftMatch = password[leftNum - 1] == character
        val rightMatch = password[rightNum -1 ] == character
        return leftMatch xor rightMatch
    }
}

val lineRegex = Regex("""^(\d+)-(\d+) ([a-z]): (.*)$""")

fun parseLine(line: String): ParsedLine {
    val match: MatchResult? = lineRegex.matchEntire(line)
    if (match == null)
        throw RuntimeException("Regex failed. Bug on $line")
    else {
        fun match(n: Int): String = match.groups.get(n)!!.value
        return ParsedLine(match(4), match(3).toCharArray().get(0), match(1).toInt(), match(2).toInt())
    }
}

val input: List<String> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/day2input.txt")
)!!

val parsedInput = input.map{parseLine(it)}

val firstValid = parsedInput.filter{it.firstIsValid()}
println(firstValid.count())

val secondValid = parsedInput.filter{it.secondIsValid()}
println(secondValid.count())