package main.kotlin.amaze

interface RobotController {
    fun getNextMove(maze: Maze): RobotAction
}

enum class RobotAction {
    MOVE_FORWARD,
    TURN_LEFT,
    TURN_RIGHT
}