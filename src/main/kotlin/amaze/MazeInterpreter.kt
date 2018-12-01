package main.kotlin.amaze

import main.kotlin.amaze.entity.*

fun String.toMaze(controller: LlamaController): Maze {
    val rows = split("\n")
    val height = rows.size
    require(height >= 5) { "Maze must be at least 5 spaces tall" }

    val width = rows[0].length
    require(width >= 5) { "Maze must be at least 5 spaces wide" }

    var hasStartingBlock = false
    var hasDestinationBlock = false
    val entityGrid: Array<Array<Entity>> = Array(height) { y ->
        Array(width) { x ->
            val entity = rows[y][x].toEntity()
            if (entity == StartBlock)
                hasStartingBlock = true
            if (entity == Destination)
                hasDestinationBlock = true

            entity
        }
    }

    require(hasStartingBlock) { "Maze must have at least one starting block" }
    require(hasDestinationBlock) { "Maze must have a destination block" }

    return Maze(entityGrid, controller)
}

private fun Char.toEntity(): Entity = when (this) {
    'X' -> Block()
    'S' -> StartBlock
    'O' -> Walkway
    'P' -> Pit
    'D' -> Destination
    else -> throw InvalidMazeCharacterException("$this is not a valid representation of an entity")
}

class InvalidMazeCharacterException(message: String) : UnsupportedOperationException(message)
