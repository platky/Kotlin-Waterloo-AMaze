package main.kotlin.amaze.puzzles

import main.kotlin.amaze.Maze
import main.kotlin.amaze.RobotAction
import main.kotlin.amaze.RobotController
import main.kotlin.amaze.core.GameController
import main.kotlin.amaze.toMaze

private val puzzleDefinition = """
    XDXXXXX
    XOOXXXX
    XXOXXXX
    XXOOOOX
    XXSXXXX
""".trimIndent()

fun main(args: Array<String>) {
    GameController(puzzleDefinition.toMaze(Puzzle1())).startGame()
}

class Puzzle1 : RobotController {
    override fun getNextMove(maze: Maze): RobotAction {
        return RobotAction.TURN_RIGHT
    }
}
