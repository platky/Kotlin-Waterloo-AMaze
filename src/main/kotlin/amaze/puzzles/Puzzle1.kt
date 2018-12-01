package main.kotlin.amaze.puzzles

import main.kotlin.amaze.Maze
import main.kotlin.amaze.LlamaAction
import main.kotlin.amaze.LlamaController
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

class Puzzle1 : LlamaController {
    override fun getNextMove(maze: Maze): LlamaAction {
        return if (Math.random() > 0.5) LlamaAction.TURN_RIGHT else LlamaAction.TURN_LEFT
    }
}
