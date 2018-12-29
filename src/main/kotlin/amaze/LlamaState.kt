package main.kotlin.amaze

import main.kotlin.amaze.core.assets.Sound
import main.kotlin.amaze.core.assets.Sounds

private const val MOVEMENT_SPEED = 1.0
private const val QUARTER_ROTATION_COUNTER_CLOCKWISE = -Math.PI / 2
private const val QUARTER_ROTATION_CLOCKWISE = Math.PI / 2

enum class LlamaState(val rotation: Double, val speed: Double) {
    WAITING,
    TURNING_LEFT(QUARTER_ROTATION_COUNTER_CLOCKWISE, 0.0),
    TURNING_RIGHT(QUARTER_ROTATION_CLOCKWISE, 0.0),
    MOVING_FORWARD(MOVEMENT_SPEED),
    CRASHING(MOVEMENT_SPEED),
    SLAUGHTERED,
    ENTERING_PIT(MOVEMENT_SPEED),
    FALLING,
    DISAPPEARED,
    TO_VICTORY_AND_BEYOND(MOVEMENT_SPEED),
    VICTORIOUS,
    WON,
    MOVING_ONTO_TELEPORTER(MOVEMENT_SPEED),
    FADING_OUT,
    FADING_IN;

    constructor() : this(0.0, 0.0)

    constructor(speed: Double) : this(0.0, speed)

    fun getNextState(): LlamaState {
        val nextState = when (this) {
            ENTERING_PIT -> FALLING
            FALLING -> DISAPPEARED
            CRASHING -> SLAUGHTERED
            MOVING_ONTO_TELEPORTER -> FADING_OUT
            FADING_OUT -> FADING_IN
            FADING_IN -> WAITING
            TO_VICTORY_AND_BEYOND -> VICTORIOUS
            else -> this
        }
        if (this != nextState) {
            nextState.playTransitionSound()
        }
        return nextState
    }

    fun playTransitionSound() {
        when (this) {
            FALLING -> Sound.FALLING.play()
            FADING_OUT -> Sound.TELEPORTING.play()
            VICTORIOUS -> Sounds.playVictoriousSound()
            CRASHING -> Sound.DYING.play()
        }
    }

    fun isReadyForAnotherUserMove(): Boolean = when (this) {
        WAITING, TURNING_LEFT, TURNING_RIGHT, MOVING_FORWARD -> true
        else -> false
    }

    fun isDead(): Boolean = this == SLAUGHTERED || this == DISAPPEARED

    fun isVictorious(): Boolean = this == VICTORIOUS
}
