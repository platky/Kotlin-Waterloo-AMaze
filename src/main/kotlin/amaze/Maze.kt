package main.kotlin.amaze

import main.kotlin.amaze.core.Assets
import main.kotlin.amaze.entity.Entity
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

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
            LlamaOrientation.NORTH -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
            LlamaOrientation.EAST -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
            LlamaOrientation.SOUTH -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
            LlamaOrientation.WEST -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
        }
    }

    fun getEntityBehindLlama(): Entity {
        return when(llama.orientation) {
            LlamaOrientation.NORTH -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
            LlamaOrientation.EAST -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
            LlamaOrientation.SOUTH -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
            LlamaOrientation.WEST -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
        }
    }

    fun getEntityOnLeftSideOfLlama(): Entity {
        return when(llama.orientation) {
            LlamaOrientation.NORTH -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
            LlamaOrientation.EAST -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
            LlamaOrientation.SOUTH -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
            LlamaOrientation.WEST -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
        }
    }

    fun getEntityOnRightSideOfLlama(): Entity {
        return when(llama.orientation) {
            LlamaOrientation.NORTH -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
            LlamaOrientation.EAST -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
            LlamaOrientation.SOUTH -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
            LlamaOrientation.WEST -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
        }
    }

    fun update(elapsedTimeMillis: Long) {
        gameTime += elapsedTimeMillis

        if (gameTime - lastMoveTime >= MILLIS_PER_MOVE) {
            lastMoveTime += MILLIS_PER_MOVE

            llamaPosition = llama.finishMove(llamaPosition)
            if (!llama.isReadyForAnotherUserMove()) return

            llama.startUserMove(controller.getNextMove(this))
            val nextPosition = llama.getNextPosition(llamaPosition)
            if (llamaPosition != nextPosition) {
                getEntityAt(nextPosition.column, nextPosition.row).interact(llama)
            }
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

        if (llama.isDead()) {
            graphics.drawInCenter(Assets.ouch, width, height, 0.75)
        } else if (llama.isVictorious()) {
            graphics.drawInCenter(Assets.yippee, width, height, 0.75)
        }
    }

    private fun Graphics2D.drawInCenter(
            image: BufferedImage,
            screenWidth: Int,
            screenHeight: Int,
            sizeRatio: Double
    ) {
        val imageWidth = screenWidth * sizeRatio
        val imageHeight = image.height * imageWidth / image.width

        drawImage(
                image,
                ((screenWidth - imageWidth) / 2).toInt(),
                ((screenHeight - imageHeight) / 2).toInt(),
                imageWidth.toInt(), imageHeight.toInt(),
                null
        )
    }
}
