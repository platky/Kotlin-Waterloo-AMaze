import entity.Block
import entity.Entity
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
            XXOOX
            XXXXX
        """.trimIndent()

        val expectedGrid: Array<Array<Entity?>> = arrayOf(
                arrayOf<Entity?>(Block(), Block(), Block(), Block(), Block()),
                arrayOf<Entity?>(Block(), null, null, Block(), Block()),
                arrayOf<Entity?>(Block(), Block(), null, Block(), Block()),
                arrayOf<Entity?>(Block(), Block(), null, null, Block()),
                arrayOf<Entity?>(Block(), Block(), Block(), Block(), Block())
        )

        val maze = createMazeFromFlatmap(flatMap)

        assertTrue(doGridsHaveMatchingEntities(expectedGrid, maze.entityGrid))
    }

    @Test
    fun checkHeightLessThan5ThrowsError() {
        val flatMap = """
            XXXXX
            XXOXX
            XXXXX
        """.trimIndent()

        assertThrows(UnsupportedGridSize::class.java) { createMazeFromFlatmap(flatMap) }
    }

    @Test
    fun checkWidthLessThan5ThrowsError() {
        val flatMap = """
            XXX
            XOX
            XOX
            XOX
            XXX
        """.trimIndent()

        assertThrows(UnsupportedGridSize::class.java) { createMazeFromFlatmap(flatMap) }
    }

    @Test
    fun checkInvalidEntityCharThrowsError() {
        val flatMap = """
            XYXXX
            XYXXX
            XYXXX
            XYXXX
            XYXXX
        """.trimIndent()

        assertThrows(InvalidMazeCharacterException::class.java) { createMazeFromFlatmap(flatMap)}
    }

    private fun doGridsHaveMatchingEntities(
            expected: Array<Array<Entity?>>, actual: Array<Array<Entity?>>
    ): Boolean {
        if (expected.size != actual.size || expected[0].size != actual[0].size) return false

        expected.forEachIndexed { y, row ->
            row.forEachIndexed { x, expectedEntity ->
                val actualEntity = actual[y][x]
                if (expectedEntity != null) {
                    if (actualEntity == null) return false

                    if (expectedEntity::class != actualEntity::class) return false
                } else if (actualEntity != null) {
                    return false
                }
            }
        }

        return true
    }
}