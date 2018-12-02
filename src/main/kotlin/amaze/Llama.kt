package main.kotlin.amaze

import main.kotlin.amaze.LlamaState.*
import main.kotlin.amaze.core.Assets
import main.kotlin.amaze.entity.Position
import java.awt.Graphics2D
import java.awt.image.BufferedImage

private const val sizeCoefficient = 0.6
private const val deadMovementCutOff = 0.5
private const val deadImageCorrectionFactor = 0.8

class Llama {

    private var state = WAITING
    private var orientation = Orientation.NORTH

    fun isDead(): Boolean = state == CRASHED

    fun draw(
            graphics: Graphics2D,
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            movePercentageComplete: Double
    ) {
        graphics.renderTransformed(x, y, width, height, movePercentageComplete) {
            val image = getLlamaImage(movePercentageComplete)

            val llamaHeight = height * sizeCoefficient
            val llamaWidth = (llamaHeight / image.height) * image.width
            graphics.drawImage(
                    image,
                    (x + (width - llamaWidth) / 2).toInt(),
                    (y + (height - llamaHeight) / 2).toInt(),
                    llamaWidth.toInt(), llamaHeight.toInt(),
                    null
            )
        }
    }

    /**
     * Transforms the screen by translating & rotating appropriately followed by the specified
     * [render] after which the transformation is reversed to leave the graphics in a good state.
     */
    private inline fun Graphics2D.renderTransformed(
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            movePercentageComplete: Double,
            render: Graphics2D.() -> Unit
    ) {
        val ratio = if (isDead() && movePercentageComplete > deadMovementCutOff) {
            deadMovementCutOff * deadImageCorrectionFactor
        } else {
            state.speed * movePercentageComplete
        }
        val deltaX = orientation.xDirection * width * ratio
        val deltaY = orientation.yDirection * height * ratio
        translate(deltaX, deltaY)

        val rotation = orientation.radians + state.rotation * movePercentageComplete
        rotate(rotation, x + width / 2.0, y + height / 2.0)

        render()

        rotate(-rotation, x + width / 2.0, y + height / 2.0)
        translate(-deltaX, -deltaY)
    }

    /**
     * Sets the current action and returns the current position given the last position.
     */
    fun setCurrentAction(action: LlamaAction, lastPosition: Position): Position {
        val currentPosition = getNextPosition(lastPosition)
        updateOrientation()
        state = action.toState()
        return currentPosition
    }

    fun getNextPosition(currentPosition: Position): Position {
        return Position(
                currentPosition.x + (orientation.xDirection * state.speed).toInt(),
                currentPosition.y + (orientation.yDirection * state.speed).toInt()
        )
    }

    private fun updateOrientation() {
        if (state == TURNING_LEFT) {
            orientation = orientation.turnLeft()
        } else if (state == TURNING_RIGHT) {
            orientation = orientation.turnRight()
        }
    }

    private fun LlamaAction.toState(): LlamaState = when (this) {
        LlamaAction.TURN_LEFT -> TURNING_LEFT
        LlamaAction.TURN_RIGHT -> TURNING_RIGHT
        LlamaAction.MOVE_FORWARD -> MOVING_FORWARD
    }

    //TODO we may not need this function unless we want some transition validation
    fun transitionToState(state: LlamaState) {
        this.state = state
    }

    private fun getLlamaImage(movePercentageComplete: Double): BufferedImage {
        return if (state == CRASHED && movePercentageComplete > deadMovementCutOff) {
            Assets.llamaDead
        } else {
            Assets.llama
        }
    }
}

enum class LlamaState(val rotation: Double, val speed: Double) {
    WAITING(0.0, 0.0),
    TURNING_LEFT(-Math.PI / 2, 0.0),
    TURNING_RIGHT(Math.PI / 2, 0.0),
    MOVING_FORWARD(0.0, 1.0),
    CRASHED(0.0, 0.7),
    COMPLETED(0.0, 0.0)
}

private enum class Orientation(val radians: Double, val xDirection: Double, val yDirection: Double) {
    NORTH(0.0, 0.0, -1.0),
    WEST(-Math.PI / 2, -1.0, 0.0),
    SOUTH(-Math.PI, 0.0, 1.0),
    EAST(-3 * Math.PI / 2, 1.0, 0.0);

    fun turnLeft(): Orientation = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
    }

    fun turnRight(): Orientation = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}
