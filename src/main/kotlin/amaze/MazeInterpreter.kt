package main.kotlin.amaze

import main.kotlin.amaze.entity.*
import java.awt.Graphics2D
import java.util.*

private const val START_BLOCK = 'S'
private const val WALKWAY = 'O'
private const val PIT = 'P'
private const val DESTINATION = 'D'
private const val BLOCK = 'X'

private const val TELEPORTER_1 = 'F'
private const val TELEPORTER_2 = 'G'
private const val TELEPORTER_3 = 'H'
private const val TELEPORTER_4 = 'I'
private const val TELEPORTER_5 = 'J'

private val random = Random()

fun String.toMaze(controller: LlamaController): Maze {
    val rows = split("\n")
    val height = rows.size
    require(height >= 5) { "Maze must be at least 5 spaces tall" }

    val width = rows[0].length
    require(width >= 5) { "Maze must be at least 5 spaces wide" }

    val possibleStartingPositions = mutableListOf<Position>()
    var destinationPosition: Position? = null
    val teleporters = mutableMapOf<Char, MutableList<TemporaryTeleporterEntity>>()

    val entityGrid: Array<Array<Entity>> = Array(height) { row ->
        Array(width) { column ->
            val char = rows[row][column]
            when (char) {
                START_BLOCK -> possibleStartingPositions.add(Position(column, row))
                DESTINATION -> destinationPosition = Position(column, row)
            }
            val entity = char.toEntity(row, column)
            if (entity is TemporaryTeleporterEntity) {
                teleporters.add(rows[row][column], entity)
            }

            entity
        }
    }

    require(possibleStartingPositions.isNotEmpty()) { "Maze must have at least one starting block" }
    require(destinationPosition != null) { "Maze must have a destination block" }

    teleporters.forEach {
        require(it.value.size == 2) { "Teleporters must exist in a pair" }
        val (teleporter1, teleporter2) = it.value
        with(teleporter1.position) {
            entityGrid[row][column] = Teleporter(teleporter2.position)
        }
        with(teleporter2.position) {
            entityGrid[row][column] = Teleporter(teleporter1.position)
        }
    }

    val startingPosition = chooseRandomStartingPosition(possibleStartingPositions)
    // We can remove the non-null assertion (!!) when we upgrade to Kotlin 1.3 due to contracts
    return Maze(entityGrid, controller, destinationPosition!!, startingPosition)
}

private fun chooseRandomStartingPosition(possiblePositions: List<Position>): Position {
    return possiblePositions[random.nextInt(possiblePositions.size)]
}

private fun Char.toEntity(row: Int, column: Int): Entity = when (this) {
    BLOCK -> Block()
    START_BLOCK -> Walkway
    WALKWAY -> Walkway
    PIT -> Pit
    DESTINATION -> Destination
    TELEPORTER_1, TELEPORTER_2, TELEPORTER_3, TELEPORTER_4, TELEPORTER_5 -> {
        TemporaryTeleporterEntity(Position(column, row))
    }
    else -> throw InvalidMazeCharacterException("$this is not a valid representation of an entity")
}

class InvalidMazeCharacterException(message: String) : UnsupportedOperationException(message)

private class TemporaryTeleporterEntity(val position: Position) : Entity() {
    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {}

    override fun interact(llama: Llama) {}
}

private fun MutableMap<Char, MutableList<TemporaryTeleporterEntity>>.add(
    char: Char, entity: TemporaryTeleporterEntity
) {
    this[char]?.add(entity) ?: put(char, mutableListOf(entity))
}
