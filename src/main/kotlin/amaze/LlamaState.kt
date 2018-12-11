package main.kotlin.amaze

import main.kotlin.amaze.core.assets.Sound
import main.kotlin.amaze.core.assets.Sounds

enum class LlamaState(val rotation: Double, val speed: Double) {
    WAITING(0.0, 0.0),
    TURNING_LEFT(-Math.PI / 2, 0.0),
    TURNING_RIGHT(Math.PI / 2, 0.0),
    MOVING_FORWARD(0.0, 1.0),
    CRASHING(0.0, 1.0),
    SLAUGHTERED(0.0, 0.0),
    ENTERING_PIT(0.0, 1.0),
    FALLING(0.0, 0.0),
    DISAPPEARED(0.0, 0.0),
    TO_VICTORY_AND_BEYOND(0.0, 1.0),
    VICTORIOUS(0.0, 0.0),
    MOVING_ONTO_TELEPORTER(0.0, 1.0),
    FADING_OUT(0.0, 0.0),
    FADING_IN(0.0, 0.0);

    fun getNextState(): LlamaState = when (this) {
        ENTERING_PIT -> {
            Sound.FALLING.play()
            FALLING
        }
        FALLING -> DISAPPEARED
        CRASHING -> SLAUGHTERED
        MOVING_ONTO_TELEPORTER ->  {
            Sound.TELEPORTING.play()
            FADING_OUT
        }
        FADING_OUT -> FADING_IN
        FADING_IN -> WAITING
        TO_VICTORY_AND_BEYOND -> {
            Sounds.playVictoriousSound()
            VICTORIOUS
        }
        else -> this
    }
}
