package main.kotlin.amaze.puzzles

import main.kotlin.amaze.Maze
import main.kotlin.amaze.LlamaAction
import main.kotlin.amaze.LlamaController
import main.kotlin.amaze.core.GameController
import main.kotlin.amaze.toMaze

private val puzzleDefinition = """
    XDXXXXXXXXXXXX
    XOOXXXXXOOOOOX
    XXOOOOOOOOXXOX
    XXOOOOXXXOXOOX
    XXXXXXXXOOXXXX
    XXXOOOSOOOXXXX
    XOOOOOXXXOOOOX
    XXXXXXXXXXXXXX
""".trimIndent()

fun main(args: Array<String>) {
    GameController(puzzleDefinition.toMaze(Puzzle1())).startGame()
}

class Puzzle1 : LlamaController {
    override fun getNextMove(maze: Maze): LlamaAction {
        val random = Math.random()
        return when {
            random < 0.3 -> LlamaAction.TURN_RIGHT
            random < 0.6 -> LlamaAction.TURN_LEFT
            else -> LlamaAction.MOVE_FORWARD
        }
    }
}
