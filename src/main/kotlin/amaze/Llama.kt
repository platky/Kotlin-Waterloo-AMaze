package main.kotlin.amaze

import main.kotlin.amaze.LlamaState.*
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D
import java.awt.image.BufferedImage

private const val SIZE_COEFFICIENT = 0.6
private const val DEAD_MOVEMENT_CUTOFF = 0.5
private const val DEAD_IMAGE_CORRECTION_FACTOR = 0.8

class Llama {
    private var state = WAITING
    var orientation = Orientation.NORTH
        private set

    fun isDead(): Boolean = state.isDead

    fun draw(
            graphics: Graphics2D,
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            movePercentageComplete: Double
    ) {
        graphics.renderTransformed(x, y, width, height, movePercentageComplete) {
            if (state == DISAPPEARED) return

            val image = getLlamaImage(movePercentageComplete)

            val llamaHeight = if (state == FAllING) {
                height * SIZE_COEFFICIENT * (1 - movePercentageComplete)
            } else {
                height * SIZE_COEFFICIENT
            }
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
        val ratio = when {
            state == SLAUGHTERED
                    || state == CRASHED && movePercentageComplete > DEAD_MOVEMENT_CUTOFF -> {
                DEAD_MOVEMENT_CUTOFF * DEAD_IMAGE_CORRECTION_FACTOR
            }
            else -> state.speed * movePercentageComplete
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
    fun startMove(action: LlamaAction) {
        state = action.toState()
    }

    fun finishMove(lastPosition: Position): Position {
        updateOrientation()
        val updatedPosition = if (state != CRASHED) {
            getNextPosition(lastPosition)
        } else {
            lastPosition
        }
        state = state.getNextState()
        return updatedPosition
    }

    fun getNextPosition(currentPosition: Position): Position {
        return Position(
                currentPosition.column + (orientation.xDirection * state.speed).toInt(),
                currentPosition.row + (orientation.yDirection * state.speed).toInt()
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

    fun transitionToState(state: LlamaState) {
        this.state = state
    }

    private fun getLlamaImage(movePercentageComplete: Double): BufferedImage {
        return when {
            state == SLAUGHTERED -> Assets.llamaDead
            state == CRASHED && movePercentageComplete > DEAD_MOVEMENT_CUTOFF -> Assets.llamaDead
            else -> Assets.llama
        }
    }
}

enum class LlamaState(val rotation: Double, val speed: Double, val isDead: Boolean) {
    WAITING(0.0, 0.0, false),
    TURNING_LEFT(-Math.PI / 2, 0.0, false),
    TURNING_RIGHT(Math.PI / 2, 0.0, false),
    MOVING_FORWARD(0.0, 1.0, false),
    CRASHED(0.0, 1.0, true),
    SLAUGHTERED(0.0, 0.0, true),
    ENTERING_PIT(0.0, 1.0, true),
    FAllING(0.0, 0.0, true),
    DISAPPEARED(0.0, 0.0, true),
    COMPLETED(0.0, 0.0, false);

    fun getNextState(): LlamaState = when (this) {
        ENTERING_PIT -> FAllING
        FAllING -> DISAPPEARED
        CRASHED -> SLAUGHTERED
        else -> this
    }
}

enum class Orientation(val radians: Double, val xDirection: Double, val yDirection: Double) {
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
