package main.kotlin.amaze

import main.kotlin.amaze.entity.Entity
import main.kotlin.amaze.entity.Position
import main.kotlin.amaze.entity.StartBlock
import java.awt.Color
import java.awt.Graphics2D
import java.util.*

private val random = Random()
private const val MILLIS_PER_MOVE = 1000L

class Maze(private val entityGrid: Array<Array<Entity>>, private val controller: LlamaController) {
    private var gameTime = 0L
    private var lastMoveTime = -MILLIS_PER_MOVE

    val numColumns = entityGrid[0].size
    val numRows = entityGrid.size

    private val llama = Llama()
    var llamaPosition: Position = chooseRandomStartingPosition()
        private set

    fun update(elapsedTimeMillis: Long) {
        gameTime += elapsedTimeMillis

        if (llama.isDead()) return
        
        if (gameTime - lastMoveTime >= MILLIS_PER_MOVE) {
            lastMoveTime += MILLIS_PER_MOVE
            llamaPosition = llama.setCurrentAction(
                    controller.getNextMove(this),
                    llamaPosition
            )
            getEntity(llama.getNextPosition(llamaPosition)).interact(llama)
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
                llamaPosition.x * cellWidth, llamaPosition.y * cellHeight,
                cellWidth, cellHeight,
                movePercentageComplete
        )
    }

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
