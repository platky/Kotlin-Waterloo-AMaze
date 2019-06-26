package main.kotlin.amaze

import main.kotlin.amaze.LlamaOrientation.*
import main.kotlin.amaze.core.assets.Images
import main.kotlin.amaze.entity.ActiveEntity
import main.kotlin.amaze.entity.Entity
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import kotlin.math.min

private const val MILLIS_PER_MOVE = 1_000L

class Maze(
    private val entityGrid: Array<Array<Entity>>,
    private val controller: LlamaController,
    val destinationPosition: Position, // The destination where the llama is trying to get to
    startingPosition: Position,
    private val activeEntities: Set<ActiveEntity>
) {
    /** The current llama position */
    var llamaPosition: Position = startingPosition
        private set

    /** The amount of milliseconds that elapsed since the game started. */
    private var gameTime = 0L

    /** The timestamp (in milliseconds) of the last move. */
    private var lastMoveTime = -MILLIS_PER_MOVE

    val numColumns = entityGrid[0].size
    val numRows = entityGrid.size

    private val llama = Llama()

    /**
     * @return The entity at the specified grid position.
     */
    fun getEntityAt(column: Int, row: Int): Entity = entityGrid[row][column]

    /**
     * @return The entity that's in front of the llama (based on the llama's orientation).
     */
    fun getEntityInFrontOfLlama(): Entity = getEntityInDirectionFromLama(NORTH)

    /**
     * @return The entity behind the llama (based on the llama's orientation).
     */
    fun getEntityBehindLlama(): Entity = getEntityInDirectionFromLama(SOUTH)

    /**
     * @return The entity on the left side of the llama (based on the llama's orientation).
     */
    fun getEntityOnLeftSideOfLlama(): Entity = getEntityInDirectionFromLama(WEST)

    /**
     * @return The entity on the right side of the llama (based on the llama's orientation).
     */
    fun getEntityOnRightSideOfLlama(): Entity = getEntityInDirectionFromLama(EAST)

    /**
     * Gets the entity in the relative [direction] orienting as if the llama is facing [NORTH].
     */
    private fun getEntityInDirectionFromLama(
        direction: LlamaOrientation
    ): Entity {
        val effectiveDirection = when (llama.orientation) {
            NORTH -> direction
            WEST -> direction.turnLeft()
            EAST -> direction.turnRight()
            SOUTH -> direction.turnLeft().turnLeft()
        }

        return when (effectiveDirection) {
            NORTH -> getEntityAt(llamaPosition.column, llamaPosition.row - 1)
            WEST -> getEntityAt(llamaPosition.column - 1, llamaPosition.row)
            EAST -> getEntityAt(llamaPosition.column + 1, llamaPosition.row)
            SOUTH -> getEntityAt(llamaPosition.column, llamaPosition.row + 1)
        }
    }

    /**
     * Updates the maze given the [elapsedTimeMillis] since the last update.
     */
    fun update(elapsedTimeMillis: Long) {
        gameTime += elapsedTimeMillis
        if (gameTime - lastMoveTime < MILLIS_PER_MOVE) return

        lastMoveTime += MILLIS_PER_MOVE
        llamaPosition = llama.finishMove(llamaPosition)
        if (!llama.state.isReadyForAnotherUserMove()) return

        transitionActiveEntities()

        llama.startUserMove(controller.getNextMove(this))
        val nextPosition = llama.getNextPosition(llamaPosition)
        if (llamaPosition != nextPosition) {
            getEntityAt(nextPosition.column, nextPosition.row).interact(llama)
        }
    }

    fun draw(graphics: Graphics2D, width: Int, height: Int) {
        val cellWidth = Math.ceil(width.toDouble() / numColumns).toInt()
        val cellHeight = Math.ceil(height.toDouble() / numRows).toInt()

        with(graphics) {
            color = Color.GRAY
            fillRect(0, 0, width, height)

            drawEntities(cellWidth, cellHeight)
            drawLlama(cellWidth, cellHeight)

            when {
                llama.state.isDead() -> drawInCenter(Images.ouch, width, height, 0.75)
                llama.state.isVictorious() -> drawInCenter(Images.yippee, width, height, 0.75)
            }
        }
    }

    private fun Graphics2D.drawEntities(cellWidth: Int, cellHeight: Int) {
        entityGrid.forEachIndexed { y, row ->
            row.forEachIndexed { x, entity ->
                val horizontalTranslation = x * cellWidth
                val verticalTranslation = y * cellHeight

                translate(horizontalTranslation, verticalTranslation)
                entity.draw(this, cellWidth, cellHeight)
                translate(-horizontalTranslation, -verticalTranslation)
            }
        }
    }

    private fun Graphics2D.drawLlama(cellWidth: Int, cellHeight: Int) {
        val currentMoveTime = gameTime - lastMoveTime
        val movePercentageComplete = min(currentMoveTime.toDouble() / MILLIS_PER_MOVE, 1.0)

        val horizontalTranslation = llamaPosition.column * cellWidth
        val verticalTranslation = llamaPosition.row * cellHeight

        translate(horizontalTranslation, verticalTranslation)
        llama.draw(this, cellWidth, cellHeight, movePercentageComplete)
        translate(-horizontalTranslation, -verticalTranslation)
    }

    /**
     * Draws an image in the center of the screen using the [sizeRatio] percentage of the screen.
     */
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

    private fun transitionActiveEntities() {
        activeEntities.forEach {
            it.transition()
        }
    }
}
