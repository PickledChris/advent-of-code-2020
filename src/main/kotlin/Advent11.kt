package advent10

import java.nio.file.Files
import java.nio.file.Paths

val input: List<Long> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/10.txt")
)!!.map { it.toLong() }


