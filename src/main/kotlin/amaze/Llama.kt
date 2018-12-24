package main.kotlin.amaze

import main.kotlin.amaze.LlamaState.*
import main.kotlin.amaze.core.assets.Images
import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.image.BufferedImage

private const val SIZE_COEFFICIENT = 0.6
private const val DEAD_MOVEMENT_CUTOFF = 0.5
private const val DEAD_IMAGE_FACTOR = 0.8
private const val VICTORY_MOVEMENT_CUTOFF = 0.5

class Llama {
    var state = WAITING
        private set

    var orientation = LlamaOrientation.NORTH
        private set

    private var forcedDestination: Position? = null

    /**
     * Start obeying the specified user [action]
     */
    fun startUserMove(action: LlamaAction) {
        state = action.toState()
        state.playTransitionSound()
    }

    /**
     * The current move has completed.  Transition to any animations if applicable.
     */
    fun finishMove(lastPosition: Position): Position {
        updateOrientation()
        val updatedPosition = when (state) {
            FADING_OUT -> getForcedDestination()
            CRASHING, TO_VICTORY_AND_BEYOND -> lastPosition
            else -> getNextPosition(lastPosition)
        }
        state = state.getNextState()
        return updatedPosition
    }

    private fun updateOrientation() {
        when (state) {
            TURNING_LEFT -> orientation = orientation.turnLeft()
            TURNING_RIGHT -> orientation = orientation.turnRight()
        }
    }

    /**
     * Teleporters can move the llama to a different location even if that llama doesn't want to.
     */
    private fun getForcedDestination(): Position {
        require(forcedDestination != null) { "A forced destination must be set" }

        val position = forcedDestination as Position
        forcedDestination = null
        return position
    }

    /**
     * @return applies the llama's intentions on the [currentPosition] to determine where
     * it's going.
     */
    fun getNextPosition(currentPosition: Position): Position = Position(
        currentPosition.column + (orientation.xDirection * state.speed).toInt(),
        currentPosition.row + (orientation.yDirection * state.speed).toInt()
    )

    /**
     * The maze entities can impose their will on the llama.
     */
    fun transitionToState(state: LlamaState) {
        state.playTransitionSound()
        this.state = state
    }

    /**
     * Llama just entered the a teleporter zone is being radiated with intense quantum
     * entanglement particles.
     */
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

            val llamaHeight = when (state) {
                FALLING -> height * SIZE_COEFFICIENT * (1 - movePercentageComplete)
                else -> height * SIZE_COEFFICIENT
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
     * Transforms the screen by translating & rotating it followed by the specified [render]
     * after which the transformation is reversed to leave the graphics in the previous state.
     */
    private inline fun Graphics2D.renderTransformed(
        width: Int,
        height: Int,
        movePercentageComplete: Double,
        render: Graphics2D.() -> Unit
    ) {
        val ratio = computeMovementRatio(movePercentageComplete)
        val deltaX = orientation.xDirection * width * ratio
        val deltaY = orientation.yDirection * height * ratio
        translate(deltaX, deltaY)

        val rotation = orientation.radians + state.rotation * movePercentageComplete
        rotate(rotation, width / 2.0, height / 2.0)
        setTransparency(movePercentageComplete)

        try {
            render()
        } finally {
            resetTransparency()
            rotate(-rotation, width / 2.0, height / 2.0)
            translate(-deltaX, -deltaY)
        }
    }

    /**
     * Sometimes we need to stop the llama movement partway through the move.
     */
    private fun computeMovementRatio(movePercentageComplete: Double): Double = when {
        shouldDisplayDeadLlama(movePercentageComplete) -> DEAD_MOVEMENT_CUTOFF * DEAD_IMAGE_FACTOR
        arrivedAtDestination(movePercentageComplete) -> VICTORY_MOVEMENT_CUTOFF
        else -> state.speed * movePercentageComplete
    }

    private fun shouldDisplayDeadLlama(movePercentageComplete: Double): Boolean = when {
        state == SLAUGHTERED -> true
        else -> state == CRASHING && movePercentageComplete > DEAD_MOVEMENT_CUTOFF
    }

    private fun arrivedAtDestination(movePercentageComplete: Double): Boolean = when {
        state == VICTORIOUS -> true
        else -> state == TO_VICTORY_AND_BEYOND && movePercentageComplete > VICTORY_MOVEMENT_CUTOFF
    }

    /**
     * Possible quantum mechanical entanglement in progress.
     */
    private fun Graphics2D.setTransparency(movePercentageComplete: Double) {
        val transparency = when (state) {
            FADING_OUT -> 1.0 - movePercentageComplete
            FADING_IN -> movePercentageComplete
            else -> return
        }
        this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency.toFloat())
    }

    private fun Graphics2D.resetTransparency() {
        this.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F)
    }

    private fun getLlamaImage(movePercentageComplete: Double): BufferedImage = when {
        state == SLAUGHTERED -> Images.llamaDead
        state == CRASHING && movePercentageComplete > DEAD_MOVEMENT_CUTOFF -> Images.llamaDead
        else -> Images.llama
    }
}
