import entity.Block
import entity.Entity
import entity.StartBlock
import entity.Walkway
import java.lang.IllegalArgumentException

fun createMazeFromFlatMap(flatMap: String): Maze {
    val rows = flatMap.split("\n")
    val height = rows.size
    //TODO: Consider just using require(height >= 5) { "..." } here
    if (height < 5) throw UnsupportedGridSize("Maze must be at least 5 spaces tall")
    val width = rows[0].length
    if (width < 5) throw UnsupportedGridSize("Maze must be at least 5 spaces wide")

    TODO("Ensure that atleast 1 starting block & destination exists")
    val entityGrid: Array<Array<Entity>> = Array(height) { y ->
        Array(width) { x ->
            rows[y][x].createEntity()
        }
    }

    return Maze(entityGrid)
}

private fun Char.createEntity(): Entity = when (this) {
    'X' -> Block
    'Y' -> StartBlock
    'O' -> Walkway
    else -> throw InvalidMazeCharacterException("$this is not a valid representation of an entity")
}

class InvalidMazeCharacterException(message: String) : UnsupportedOperationException(message)
class UnsupportedGridSize(message: String) : IllegalArgumentException(message)
