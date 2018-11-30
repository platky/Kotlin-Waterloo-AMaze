package main.kotlin.amaze

import entity.Entity
import entity.Position
import entity.StartBlock
import java.awt.Color
import java.awt.Graphics2D
import java.util.*

private val random = Random()

class Maze(private val entityGrid: Array<Array<Entity>>) {
    val numColumns = entityGrid[0].size
    val numRows = entityGrid.size
    private val robot = Robot()

    fun update(elapsedTimeMillis: Long) {
        //TODO: pass percentage of the current move complete
        robot.update()
    }

    fun draw(graphics: Graphics2D, width: Int, height: Int) {
        graphics.color = Color.GRAY
        graphics.fillRect(0, 0, width, height)

        val cellWidth = width / numColumns
        val cellHeight = height / numRows

        entityGrid.forEachIndexed { y, row ->
            row.forEachIndexed { x, entity ->
                entity.draw(graphics, x * cellWidth, y * cellHeight, cellWidth, cellHeight)
            }
        }
        robot.draw(
                graphics,
                robotPosition.x * cellWidth, robotPosition.y * cellHeight,
                cellWidth, cellHeight
        )
    }

    //TODO: needs to be updatable but not directly editable by user
    var robotPosition: Position = chooseRandomStartingPosition()

    fun getEntity(position: Position): Entity = entityGrid[position.y][position.x]

    private fun chooseRandomStartingPosition(): Position {
        val possibleStartingPositions: MutableList<Position> = mutableListOf()
        entityGrid.forEachIndexed { y, row ->
            row.forEachIndexed { x, entity ->
                if (entity == StartBlock) possibleStartingPositions.add(Position(x, y))
            }
        }

        return possibleStartingPositions[random.nextInt(possibleStartingPositions.size)]
    }
}
