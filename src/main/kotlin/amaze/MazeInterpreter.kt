package main.kotlin.amaze

import main.kotlin.amaze.entity.*
import main.kotlin.amaze.puzzles.PuzzleDefinition
import java.awt.Graphics2D

private const val START_BLOCK = 'S'
private const val WALKWAY = 'O'
private const val PIT = 'P'
private const val DESTINATION = 'D'
private const val BLOCK = 'X'
private const val VANISHING_WALKWAY = 'V'

private const val TELEPORTER_1 = 'F'
private const val TELEPORTER_2 = 'G'
private const val TELEPORTER_3 = 'H'
private const val TELEPORTER_4 = 'I'
private const val TELEPORTER_5 = 'J'

/**
 * Interprets and constructs a [Maze] from [this] string representation.
 */
fun PuzzleDefinition.toMaze(controller: LlamaController): Maze {
    val rows = this.tileMap.lines()
    val height = rows.size
    require(height >= 5) { "Maze must be at least 5 spaces tall" }

    val width = rows[0].length
    require(width >= 5) { "Maze must be at least 5 spaces wide" }

    val possibleStartingPositions = mutableListOf<Position>()
    var destinationPosition: Position? = null
    val teleporters = mutableMapOf<Char, MutableList<TemporaryTeleporterEntity>>()
    val activeEntities = mutableSetOf<ActiveEntity>()

    val entityGrid: Array<Array<Entity>> = Array(height) { row ->
        Array(width) { column ->
            val char = rows[row][column]
            when (char) {
                START_BLOCK -> possibleStartingPositions.add(Position(column, row))
                DESTINATION -> destinationPosition = Position(column, row)
            }
            val entity = char.toEntity(row, column)
            if (entity is TemporaryTeleporterEntity) {
                teleporters.addToList(rows[row][column], entity)
            } else if(entity is ActiveEntity) {
                activeEntities.add(entity)
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

    var vanishingWalkwayIndex = 0
    activeEntities.filter { it is VanishingWalkway }.forEach {
        it as VanishingWalkway

        if (vanishingWalkwayIndex < this.vanishingWalkwayPatterns.size) {
            it.setStatePattern(this.vanishingWalkwayPatterns[vanishingWalkwayIndex])
            vanishingWalkwayIndex++
        } else {
            it.setStatePattern()
        }
    }

    val startingPosition = possibleStartingPositions.random()
    return Maze(entityGrid, controller, destinationPosition!!, startingPosition, activeEntities)
}

/**
 * Interprets and constructs an [Entity] from [this] character.
 */
private fun Char.toEntity(row: Int, column: Int): Entity = when (this) {
    BLOCK -> Forest()
    START_BLOCK -> Walkway
    WALKWAY -> Walkway
    PIT -> Pit
    DESTINATION -> Destination
    TELEPORTER_1, TELEPORTER_2, TELEPORTER_3, TELEPORTER_4, TELEPORTER_5 -> {
        TemporaryTeleporterEntity(Position(column, row))
    }
    VANISHING_WALKWAY -> VanishingWalkway()
    else -> error("$this is not a valid representation of an entity")
}

private class TemporaryTeleporterEntity(val position: Position) : Entity {
    override fun draw(graphics: Graphics2D, width: Int, height: Int) {}

    override fun interact(llama: Llama) {}
}

private fun <K : Any, V : Any> MutableMap<K, MutableList<V>>.addToList(key: K, value: V) {
    this[key]?.add(value) ?: put(key, mutableListOf(value))
}
