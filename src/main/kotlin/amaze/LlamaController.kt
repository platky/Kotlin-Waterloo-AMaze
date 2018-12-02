package main.kotlin.amaze

data class Position(val column: Int, val row: Int)

interface LlamaController {
    fun getNextMove(maze: Maze): LlamaAction
}

enum class LlamaAction {
    MOVE_FORWARD,
    TURN_LEFT,
    TURN_RIGHT
}
