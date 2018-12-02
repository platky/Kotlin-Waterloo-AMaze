package main.kotlin.amaze

import main.kotlin.amaze.entity.Entity
import java.awt.Color
import java.awt.Graphics2D
import java.util.*

private const val MILLIS_PER_MOVE = 1000L

class Maze(
        private val entityGrid: Array<Array<Entity>>,
        private val controller: LlamaController,
        /** The destination where you're trying to get to */
        val destinationPosition: Position,
        startingPosition: Position
) {
    /** The current llama position */
    var llamaPosition: Position = startingPosition
        private set

    private var gameTime = 0L
    private var lastMoveTime = -MILLIS_PER_MOVE

    val numColumns = entityGrid[0].size
    val numRows = entityGrid.size

    private val llama = Llama()

    fun getEntityAt(column: Int, row: Int): Entity = entityGrid[row][column]

    fun getEntityInFrontOfLlama(): Entity {
        return when(llama.orientation) {
            Orientation.NORTH -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
            Orientation.EAST -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
            Orientation.SOUTH -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
            Orientation.WEST -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
        }
    }

    fun getEntityBehindLlama(): Entity {
        return when(llama.orientation) {
            Orientation.NORTH -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
            Orientation.EAST -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
            Orientation.SOUTH -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
            Orientation.WEST -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
        }
    }

    fun getEntityOnLeftSideOfLlama(): Entity {
        return when(llama.orientation) {
            Orientation.NORTH -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
            Orientation.EAST -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
            Orientation.SOUTH -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
            Orientation.WEST -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
        }
    }

    fun getEntityOnRightSideOfLlama(): Entity {
        return when(llama.orientation) {
            Orientation.NORTH -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
            Orientation.EAST -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
            Orientation.SOUTH -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
            Orientation.WEST -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
        }
    }

    fun update(elapsedTimeMillis: Long) {
        gameTime += elapsedTimeMillis

        if (llama.isDead()) return

        if (gameTime - lastMoveTime >= MILLIS_PER_MOVE) {
            lastMoveTime += MILLIS_PER_MOVE
            llamaPosition = llama.setCurrentAction(
                    controller.getNextMove(this),
                    llamaPosition
            )
            val nextPosition = llama.getNextPosition(llamaPosition)
            getEntityAt(nextPosition.column, nextPosition.row).interact(llama)
        }
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

        val currentMoveTime = gameTime - lastMoveTime
        val movePercentageComplete = if (currentMoveTime >= MILLIS_PER_MOVE) {
            1.0
        } else {
            currentMoveTime.toDouble() / MILLIS_PER_MOVE
        }

        llama.draw(
                graphics,
                llamaPosition.column * cellWidth, llamaPosition.row * cellHeight,
                cellWidth, cellHeight,
                movePercentageComplete
        )
    }
}
