package main.kotlin.amaze.puzzles

import main.kotlin.amaze.LlamaAction
import main.kotlin.amaze.LlamaController
import main.kotlin.amaze.Maze
import main.kotlin.amaze.core.GameController
import main.kotlin.amaze.toMaze

fun main() {
    GameController(puzzles[0].toMaze(Puzzle1())).startGame()
}

/**
 * The llama is experiencing rightward drunkenness!
 *
 * Fix the [getNextMove] method to make the llama move forward.
 */
class Puzzle1 : LlamaController {
    override fun getNextMove(maze: Maze): LlamaAction {
        return LlamaAction.TURN_RIGHT
    }
}
