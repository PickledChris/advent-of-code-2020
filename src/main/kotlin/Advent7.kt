package advent7

import java.nio.file.Files
import java.nio.file.Paths

data class BagAmount(val bagCount: Int, val bagColour: String)

val ContainedRegex = Regex("""(\d+) (\w+ \w+) bags?""")
val EmptyRegex = Regex("""(\w+ \w+) bags contain no other bags.""")

val input: List<String> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/7.txt")
)!!

fun parseLines(): Map<String, List<BagAmount>> {
    return input.map { line ->
        val emptyMatch: MatchResult? = EmptyRegex.matchEntire(line)
        if (emptyMatch != null){
            val colour = emptyMatch.groupValues.get(1)
            Pair(colour, emptyList())
        } else {
            val colour = line.split(" ").take(2).joinToString(" ")
            val allMatches: List<BagAmount> = ContainedRegex.findAll(line).map{
                BagAmount(it.groupValues.get(1).toInt(),it.groupValues.get(2))
            }.toList()
            Pair(colour, allMatches)
        }
    }.toMap()
}

val parsedLines = parseLines()

fun String.containsBag(containeeColour: String): Boolean  {
    val containedBags: List<BagAmount>? = parsedLines[this]
    return containedBags != null && (containedBags.any { it.bagColour == containeeColour } || containedBags.any{it.bagColour.containsBag(containeeColour)})
}

fun countContainingBags(bag: String): Int {
    val containedBags: List<BagAmount>? = parsedLines[bag]
    if (containedBags == null || containedBags.isEmpty()){
        return 0
    } else {
        return containedBags.sumOf { ba: BagAmount ->
            ba.bagCount + ba.bagCount * countContainingBags(ba.bagColour)
        }
    }
}

fun main() {
    println(parsedLines.keys.count { it.containsBag("shiny gold") })
    println(countContainingBags("shiny gold"))
}






