import entity.Block
import entity.Entity
import entity.StartBlock
import entity.Walkway
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MazeInterpreterTest {
    @Test
    fun checkSimple5by5Maze() {
        val flatMap = """
            XXXXX
            XOOXX
            XXOXX
            XXOYX
            XXXXX
        """.trimIndent()

        val expectedGrid: Array<Array<Entity>> = arrayOf(
                arrayOf<Entity>(Block, Block, Block, Block, Block),
                arrayOf<Entity>(Block, Walkway, Walkway, Block, Block),
                arrayOf<Entity>(Block, Block, Walkway, Block, Block),
                arrayOf<Entity>(Block, Block, Walkway, StartBlock, Block),
                arrayOf<Entity>(Block, Block, Block, Block, Block)
        )

        val maze = createMazeFromFlatMap(flatMap)

        assertTrue(doGridsHaveMatchingEntities(expectedGrid, maze.entityGrid))
    }

    @Test
    fun checkHeightLessThan5ThrowsError() {
        val flatMap = """
            XXXXX
            XXYXX
            XXXXX
        """.trimIndent()

        assertThrows(UnsupportedGridSize::class.java) { createMazeFromFlatMap(flatMap) }
    }

    @Test
    fun checkWidthLessThan5ThrowsError() {
        val flatMap = """
            XXX
            XOX
            XOX
            XYX
            XXX
        """.trimIndent()

        assertThrows(UnsupportedGridSize::class.java) { createMazeFromFlatMap(flatMap) }
    }

    @Test
    fun checkInvalidEntityCharThrowsError() {
        val flatMap = """
            X1XXX
            X1XXX
            X1XXX
            X1XXX
            X1XXX
        """.trimIndent()

        assertThrows(InvalidMazeCharacterException::class.java) { createMazeFromFlatMap(flatMap)}
    }

    private fun doGridsHaveMatchingEntities(
            expected: Array<Array<Entity>>, actual: Array<Array<Entity>>
    ): Boolean {
        if (expected.size != actual.size || expected[0].size != actual[0].size) return false

        expected.forEachIndexed { y, row ->
            row.forEachIndexed { x, expectedEntity ->
                val actualEntity = actual[y][x]
                if (expectedEntity::class != actualEntity::class) return false
            }
        }

        return true
    }
}
