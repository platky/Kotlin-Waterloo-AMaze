package main.kotlin.amaze

interface LlamaController {
    fun getNextMove(maze: Maze): LlamaAction
}

enum class LlamaAction {
    MOVE_FORWARD,
    TURN_LEFT,
    TURN_RIGHT
}