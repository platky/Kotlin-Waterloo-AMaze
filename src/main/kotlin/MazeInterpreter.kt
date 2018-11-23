import entity.Block
import entity.Entity
import java.lang.IllegalArgumentException

fun createMazeFromFlatmap(flatMap: String): Maze {
    val rows = flatMap.trimIndent().split("\n")
    val height = rows.size
    if (height < 5) throw UnsupportedGridSize("Maze must be at least 5 spaces tall")
    val width = rows[0].length
    if (width < 5) throw UnsupportedGridSize("Maze must be at least 5 spaces wide")

    val entityGrid: Array<Array<Entity?>> = Array(height) { arrayOfNulls<Entity?>(width) }
    rows.forEachIndexed { y, row ->
        row.forEachIndexed { x, character ->
            entityGrid[y][x] = character.createEntity()
        }
    }

    return Maze(entityGrid)
}

private fun Char.createEntity(): Entity? {
    return when(this) {
        'X' -> Block()
        'O' -> null
        else -> throw InvalidMazeCharacterException(
                "$this is not a valid representation of an entity"
        )
    }
}

class InvalidMazeCharacterException(message: String) : UnsupportedOperationException(message)
class UnsupportedGridSize(message: String) : IllegalArgumentException(message)