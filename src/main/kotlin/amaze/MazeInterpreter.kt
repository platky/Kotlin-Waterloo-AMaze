package main.kotlin.amaze

import main.kotlin.amaze.entity.*
import java.util.*

private const val START_BLOCK = 'S'
private const val WALKWAY = 'O'
private const val PIT = 'P'
private const val DESTINATION = 'D'
private const val BLOCK = 'X'

private val random = Random()

fun String.toMaze(controller: LlamaController): Maze {
    val rows = split("\n")
    val height = rows.size
    require(height >= 5) { "Maze must be at least 5 spaces tall" }

    val width = rows[0].length
    require(width >= 5) { "Maze must be at least 5 spaces wide" }

    val possibleStartingPositions = mutableListOf<Position>()
    var destinationPosition: Position? = null

    val entityGrid: Array<Array<Entity>> = Array(height) { row ->
        Array(width) { column ->
            val char = rows[row][column]
            when (char) {
                START_BLOCK -> possibleStartingPositions.add(Position(column, row))
                DESTINATION -> destinationPosition = Position(column, row)
            }
            char.toEntity()
        }
    }

    require(possibleStartingPositions.isNotEmpty()) { "Maze must have at least one starting block" }
    require(destinationPosition != null) { "Maze must have a destination block" }

    val startingPosition = chooseRandomStartingPosition(possibleStartingPositions)
    // We can remove the non-null assertion (!!) when we upgrade to Kotlin 1.3 due to contracts
    return Maze(entityGrid, controller, destinationPosition!!, startingPosition)
}

private fun chooseRandomStartingPosition(possiblePositions: List<Position>): Position {
    return possiblePositions[random.nextInt(possiblePositions.size)]
}

private fun Char.toEntity(): Entity = when (this) {
    BLOCK -> Block()
    START_BLOCK -> Walkway
    WALKWAY -> Walkway
    PIT -> Pit
    DESTINATION -> Destination
    else -> throw InvalidMazeCharacterException("$this is not a valid representation of an entity")
}

class InvalidMazeCharacterException(message: String) : UnsupportedOperationException(message)
