import entity.Block
import entity.Entity
import entity.StartBlock
import entity.Walkway
import java.lang.IllegalArgumentException

fun createMazeFromFlatMap(flatMap: String): Maze {
    val rows = flatMap.trimIndent().split("\n")
    val height = rows.size
    if (height < 5) throw UnsupportedGridSize("Maze must be at least 5 spaces tall")
    val width = rows[0].length
    if (width < 5) throw UnsupportedGridSize("Maze must be at least 5 spaces wide")

    val entityGrid: Array<Array<Entity>> = Array(height) { y ->
        Array(width) { x ->
            rows[y][x].createEntity()
        }
    }

    return Maze(entityGrid)
}

private fun Char.createEntity(): Entity {
    return when(this) {
        'X' -> Block()
        'Y' -> StartBlock()
        'O' -> Walkway()
        else -> throw InvalidMazeCharacterException(
                "$this is not a valid representation of an entity"
        )
    }
}

class InvalidMazeCharacterException(message: String) : UnsupportedOperationException(message)
class UnsupportedGridSize(message: String) : IllegalArgumentException(message)