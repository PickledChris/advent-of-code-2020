import java.nio.file.Files
import java.nio.file.Paths

data class ParsedLine(val password: String, val character: Char, val lowerBound: Int, val upperBound: Int){
    fun isValid(): Boolean {
        val occurrences = password.count{it == character}

        return occurrences in lowerBound..upperBound
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


// Laziness
val input: List<String> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/day2input.txt")
)!!

val parsedInput = input.map{parseLine(it)}
//val validInput = parsedInput.map{it to it.isValid()}
//validInput.drop(990).forEach{println(it)}

val validInput = parsedInput.filter{it.isValid()}
validInput.count()