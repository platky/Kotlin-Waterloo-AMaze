import entity.Destination
import entity.Entity
import entity.Position
import entity.StartBlock
import java.util.*

private val random = Random()

class Maze(val entityGrid: Array<Array<Entity>>) {
    private val width = entityGrid[0].size
    private val height = entityGrid.size

    //TODO: needs to be updatable but not directly editable by user
    var robotPosition: Position = chooseRandomStartingPosition()

    fun getEntity(position: Position): Entity = entityGrid[position.y][position.x]

    fun isPuzzleCompleted(): Boolean {
        TODO("Delete this & update Destination.interact() to mark the puzzle as completed instead")
        val entityAtRobotPosition = entityGrid[robotPosition.y][robotPosition.x]
        return entityAtRobotPosition::class == Destination::class
    }

    private fun chooseRandomStartingPosition(): Position {
        val possibleStartingPositions: MutableList<Position> = mutableListOf()
        entityGrid.forEachIndexed { y, row ->
            row.forEachIndexed { x, entity ->
                if (entity == StartBlock) possibleStartingPositions.add(Position(x, y))
            }
        }

        TODO("Delete this check and validate that it's a valid maze in the maze interpreter instead")
        require(possibleStartingPositions.size > 0) { "Maze must have a starting block" }

        return possibleStartingPositions[random.nextInt(possibleStartingPositions.size)]
    }
}
