package main.kotlin.amaze

import main.kotlin.amaze.LlamaState.*
import main.kotlin.amaze.core.Assets
import main.kotlin.amaze.entity.Position
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Llama {
    private val sizeCoefficient = 0.6

    private var state = WAITING
    private var orientation = Orientation.NORTH

    fun draw(
            graphics: Graphics2D,
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            movePercentageComplete: Double
    ) {
        val image = selectStateImageAsset(state)
        val ratio = state.speed * movePercentageComplete
        val deltaX = orientation.xDirection * width * ratio
        val deltaY = orientation.yDirection * height * ratio
        graphics.translate(deltaX, deltaY)

        val rotation = orientation.radians + state.rotation * movePercentageComplete
        graphics.rotate(rotation, x + width / 2.0, y + height / 2.0)
        val llamaHeight = height * sizeCoefficient
        val llamaWidth = (llamaHeight / image.height) * image.width
        graphics.drawImage(image,
            (x + (width - llamaWidth) / 2).toInt(),
            (y + (height - llamaHeight) / 2).toInt(),
            llamaWidth.toInt(), llamaHeight.toInt(), null)
    }

    /**
     * Sets the current action and returns the current position given the last position.
     */
    fun setCurrentAction(action: LlamaAction, lastPosition: Position): Position {
        val currentPosition = Position(
                lastPosition.x + (orientation.xDirection * state.speed).toInt(),
                lastPosition.y + (orientation.yDirection * state.speed).toInt()
        )

        if (state == TURNING_LEFT) {
            orientation = orientation.turnLeft()
        } else if (state == TURNING_RIGHT) {
            orientation = orientation.turnRight()
        }
        state = when (action) {
            LlamaAction.TURN_LEFT -> TURNING_LEFT
            LlamaAction.TURN_RIGHT -> TURNING_RIGHT
            LlamaAction.MOVE_FORWARD -> MOVING_FORWARD
        }
        return currentPosition
    }

    //TODO we may not need this function unless we want some transition validation
    fun transitionToState(state: LlamaState) {
        this.state = state
    }

    private fun selectStateImageAsset(state: LlamaState): BufferedImage {
        return when(state) {
            CRASHED -> Assets.llamaDead
            else -> Assets.llama
        }
    }
}

enum class LlamaState(val rotation: Double, val speed: Double) {
    WAITING(0.0, 0.0),
    TURNING_LEFT(-Math.PI / 2, 0.0),
    TURNING_RIGHT(Math.PI / 2, 0.0),
    MOVING_FORWARD(0.0, 1.0),
    CRASHED(0.0, 0.0),
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
