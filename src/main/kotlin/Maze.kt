import entity.Destination
import entity.Entity
import entity.Position
import entity.StartBlock
import java.lang.IllegalArgumentException
import java.util.*

private val random = Random()

class Maze(val entityGrid: Array<Array<Entity>>) {
    private val width = entityGrid[0].size
    private val height = entityGrid.size

    //TODO: needs to be updatable but not directly editable by user
    var robotPosition: Position = chooseRandomStartingPosition()

    fun getEntity(position: Position): Entity {
        return entityGrid[position.y][position.x]
    }

    fun isPuzzleCompleted(): Boolean {
        val entityAtRobotPosition = entityGrid[robotPosition.y][robotPosition.x]
        return entityAtRobotPosition::class == Destination::class
    }

    private fun chooseRandomStartingPosition(): Position {
        val possibleStartingPositions: MutableList<Position> = mutableListOf()
        entityGrid.forEachIndexed { y, row ->
            row.forEachIndexed { x, entity ->
                if (entity::class == StartBlock::class)
                    possibleStartingPositions.add(Position(x, y))
            }
        }
        if (possibleStartingPositions.size == 0)
            throw IllegalArgumentException("Maze must have a starting block")

        return possibleStartingPositions[random.nextInt(possibleStartingPositions.size)]
    }
}