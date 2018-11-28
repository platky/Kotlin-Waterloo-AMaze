import entity.*

fun createMazeFromFlatMap(flatMap: String): Maze {
    val rows = flatMap.split("\n")
    val height = rows.size
    require(height >= 5) { "Maze must be at least 5 spaces tall" }

    val width = rows[0].length
    require(width >= 5) { "Maze must be at least 5 spaces wide" }

    var hasStartingBlock = false
    var hasDestinationBlock = false
    val entityGrid: Array<Array<Entity>> = Array(height) { y ->
        Array(width) { x ->
            val entity = rows[y][x].createEntity()
            if (entity == StartBlock)
                hasStartingBlock = true
            if (entity == Destination)
                hasDestinationBlock = true

            entity
        }
    }

    require(hasStartingBlock) { "Maze must have at least one starting block" }
    require(hasDestinationBlock) { "Maze must have a destination block" }

    return Maze(entityGrid)
}

private fun Char.createEntity(): Entity = when (this) {
    'X' -> Block
    'S' -> StartBlock
    'O' -> Walkway
    'D' -> Destination
    else -> throw InvalidMazeCharacterException("$this is not a valid representation of an entity")
}

class InvalidMazeCharacterException(message: String) : UnsupportedOperationException(message)
