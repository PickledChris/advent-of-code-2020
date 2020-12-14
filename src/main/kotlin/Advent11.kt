package advent11

import java.nio.file.Files
import java.nio.file.Paths

val input: List<List<Space>> = Files.readAllLines(
    Paths.get("/home/chris/code/advent/src/main/resources/11.txt")
)!!.map { s: String? -> s!!.map { parseSeat(it) } }

interface Space {
    val occupants: Int
}

object Floor : Space {
    override val occupants = 0
    override fun toString() = "."
}

object EmptySeat : Space {
    override val occupants = 0
    override fun toString() = "L"
}

object OccupiedSeat : Space {
    override val occupants = 1
    override fun toString() = "#"
}

fun parseSeat(c: Char): Space =
    when (c) {
        'L' -> EmptySeat
        '.' -> Floor
        else -> OccupiedSeat
    }

data class Room(val spaces: List<List<Space>>) {
    private fun adjacentSpaceSum(spaceCoordinates: Pair<Int, Int>): Int {
        val offsets = listOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))
        val adjacentSeatCoords = offsets.map { Pair(it.first + spaceCoordinates.first, it.second + spaceCoordinates.second) }
        val adjacentSeats: List<Space> = adjacentSeatCoords.mapNotNull { spaces.getOrNull(it.first)?.getOrNull(it.second) }
        return adjacentSeats.sumOf { it.occupants }
    }

    private fun transform(): Room {
        val newSpaces = spaces.mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, space ->
                val adjacentSpaceSum: Int = adjacentSpaceSum(Pair(rowIndex, columnIndex))
                if (space is EmptySeat && adjacentSpaceSum == 0) {
                    OccupiedSeat
                } else if (space is OccupiedSeat && adjacentSpaceSum >= 4) {
                    EmptySeat
                } else {
                    space
                }
            }
        }
        return Room(newSpaces)
    }

    fun occupiedSeats(): Int = spaces.sumOf { it.sumOf { it.occupants } }

    fun simulateUntilStable(): Room {

        val nextState = transform()
        if (nextState == this) {
            return this
        } else {
            return nextState.simulateUntilStable()
        }
    }

    override fun toString(): String {
        return spaces.joinToString("\n") { it.joinToString("") }
    }
}

fun main() {
    val initialRoom = Room(input)

    val stableRoom = initialRoom.simulateUntilStable()
    println(stableRoom.occupiedSeats())
}



