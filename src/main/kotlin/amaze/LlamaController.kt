package main.kotlin.amaze

data class Position(val column: Int, val row: Int)

interface LlamaController {
    fun getNextMove(maze: Maze): LlamaAction
}

enum class LlamaAction {
    MOVE_FORWARD,
    TURN_LEFT,
    TURN_RIGHT;

    fun toState(): LlamaState = when (this) {
        LlamaAction.TURN_LEFT -> LlamaState.TURNING_LEFT
        LlamaAction.TURN_RIGHT -> LlamaState.TURNING_RIGHT
        LlamaAction.MOVE_FORWARD -> LlamaState.MOVING_FORWARD
    }
}
