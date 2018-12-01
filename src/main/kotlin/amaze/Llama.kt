package main.kotlin.amaze

import main.kotlin.amaze.LlamaState.*
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D

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
        val rotation = orientation.radians + state.rotation * movePercentageComplete
        graphics.rotate(rotation, x + width / 2.0, y + height / 2.0)
        val llamaHeight = height * sizeCoefficient
        val llamaWidth = (llamaHeight / Assets.llama.height) * Assets.llama.width
        graphics.drawImage(Assets.llama,
            (x + (width - llamaWidth) / 2).toInt(),
            (y + (height - llamaHeight) / 2).toInt(),
            llamaWidth.toInt(), llamaHeight.toInt(), null)
    }

    fun setCurrentAction(action: LlamaAction) {
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
    }

    //TODO we may not need this function unless we want some transition validation
    fun transitionToState(state: LlamaState) {
        this.state = state
    }
}

enum class LlamaState(val rotation: Double) {
    WAITING(0.0),
    TURNING_LEFT(-Math.PI / 2),
    TURNING_RIGHT(Math.PI / 2),
    MOVING_FORWARD(0.0),
    CRASHED(0.0),
    COMPLETED(0.0)
}

enum class Orientation(val radians: Double) {
    NORTH(0.0),
    WEST(-Math.PI / 2),
    SOUTH(-Math.PI),
    EAST(-3 * Math.PI / 2);

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