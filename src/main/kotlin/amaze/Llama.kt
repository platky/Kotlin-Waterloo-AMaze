package main.kotlin.amaze

import main.kotlin.amaze.LlamaState.*
import main.kotlin.amaze.core.assets.Images
import main.kotlin.amaze.core.assets.Sound
import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.image.BufferedImage

private const val SIZE_COEFFICIENT = 0.6
private const val DEAD_MOVEMENT_CUTOFF = 0.5
private const val DEAD_IMAGE_CORRECTION_FACTOR = 0.8
private const val VICTORY_MOVEMENT_CUTOFF = 0.5

class Llama {
    private var state = WAITING
    private var forcedDestination: Position? = null
    var orientation = LlamaOrientation.NORTH
        private set

    fun isDead(): Boolean = state == SLAUGHTERED || state == DISAPPEARED
    fun isVictorious(): Boolean = state == VICTORIOUS

    fun isReadyForAnotherUserMove(): Boolean = when (state) {
        WAITING, TURNING_LEFT, TURNING_RIGHT, MOVING_FORWARD -> true
        else -> false
    }

    /**
     * Sets the current action and returns the current position given the last position.
     */
    fun startUserMove(action: LlamaAction) {
        state = action.toState()
    }

    fun finishMove(lastPosition: Position): Position {
        updateOrientation()
        val updatedPosition = when {
            state == FADING_OUT -> getForcedDestination()
            state != CRASHING && state != TO_VICTORY_AND_BEYOND -> getNextPosition(lastPosition)
            else -> lastPosition
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

    private fun getForcedDestination(): Position {
        require(forcedDestination != null) { "A forced destination must be set" }

        val position = forcedDestination as Position
        forcedDestination = null
        return position
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
        if (state == CRASHING) Sound.DYING.play()

        this.state = state
    }

    fun teleporterStateTransition(state: LlamaState, position: Position) {
        if (this.state != MOVING_FORWARD) return

        transitionToState(state)
        forcedDestination = position
    }

    fun draw(
            graphics: Graphics2D,
            width: Int,
            height: Int,
            movePercentageComplete: Double
    ) {
        if (state == DISAPPEARED) return

        graphics.renderTransformed(width, height, movePercentageComplete) {
            val image = getLlamaImage(movePercentageComplete)

            val llamaHeight = if (state == FALLING) {
                height * SIZE_COEFFICIENT * (1 - movePercentageComplete)
            } else {
                height * SIZE_COEFFICIENT
            }

            val llamaWidth = (llamaHeight / image.height) * image.width
            drawImage(
                    image,
                    ((width - llamaWidth) / 2).toInt(),
                    ((height - llamaHeight) / 2).toInt(),
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
            width: Int,
            height: Int,
            movePercentageComplete: Double,
            crossinline render: Graphics2D.() -> Unit
    ) {
        val ratio = computeMovementRatio(movePercentageComplete)
        val deltaX = orientation.xDirection * width * ratio
        val deltaY = orientation.yDirection * height * ratio
        translate(deltaX, deltaY)

        val rotation = orientation.radians + state.rotation * movePercentageComplete
        rotate(rotation, width / 2.0, height / 2.0)
        setTransparency(movePercentageComplete)

        render()

        resetTransparency()
        rotate(-rotation, width / 2.0, height / 2.0)
        translate(-deltaX, -deltaY)
    }

    private fun computeMovementRatio(movePercentageComplete: Double): Double {
        return when {
            state == SLAUGHTERED
                    || state == CRASHING && movePercentageComplete > DEAD_MOVEMENT_CUTOFF -> {
                DEAD_MOVEMENT_CUTOFF * DEAD_IMAGE_CORRECTION_FACTOR
            }
            state == TO_VICTORY_AND_BEYOND && movePercentageComplete > VICTORY_MOVEMENT_CUTOFF
                    || state == VICTORIOUS -> {
                VICTORY_MOVEMENT_CUTOFF
            }
            else -> state.speed * movePercentageComplete
        }
    }

    private fun Graphics2D.setTransparency(movePercentageComplete: Double) {
        val transparency: Float = when (state) {
            FADING_OUT -> (1.0 - movePercentageComplete).toFloat()
            FADING_IN -> movePercentageComplete.toFloat()
            else -> return
        }
        this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency)
    }

    private fun Graphics2D.resetTransparency() {
        this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F)
    }

    private fun getLlamaImage(movePercentageComplete: Double): BufferedImage {
        return when {
            state == SLAUGHTERED -> Images.llamaDead
            state == CRASHING && movePercentageComplete > DEAD_MOVEMENT_CUTOFF -> Images.llamaDead
            else -> Images.llama
        }
    }
}
