package advent5

import java.nio.file.Files
import java.nio.file.Paths

val input: List<String> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/day5input.txt")
)!!

data class SeatNo(val row: Int, val seat: Int) {
    val id = row * 8 + seat
}

fun parseRow(seat: String): SeatNo {

    val binaryRow: String = seat.take(7).mapNotNull {
        when (it) {
            'F' -> '0'
            'B' -> '1'
            else -> null
        }
    }.joinToString("")
    val rowAsInt = Integer.parseInt(binaryRow, 2)


    val binarySeat: String = seat.takeLast(3).mapNotNull {
        when (it) {
            'L' -> '0'
            'R' -> '1'
            else -> null
        }
    }.joinToString("")
    val seatAsInt = Integer.parseInt(binarySeat, 2)

    return SeatNo(rowAsInt, seatAsInt)
}

fun main() {
    try {
        val rows = input.map { parseRow(it).id}.toSet()
        val min = rows.minOrNull()
        val max = rows.maxOrNull()

        val range = (min!! .. max!!).toSet()

        println(range.subtract(rows))



    } catch (e: Exception) {
        e.printStackTrace()
    }
}
