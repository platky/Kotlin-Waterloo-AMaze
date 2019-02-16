package main.kotlin.amaze.puzzles

import main.kotlin.amaze.LlamaAction
import main.kotlin.amaze.LlamaController
import main.kotlin.amaze.Maze
import main.kotlin.amaze.core.GameController
import main.kotlin.amaze.toMaze

fun main() {
    GameController(puzzles[9].toMaze(Puzzle10())).startGame()
}

/**
 * Achieve enlightenment by being one with the llama.  Good Luck!
 */
class Puzzle10 : LlamaController {
    override fun getNextMove(maze: Maze): LlamaAction {
        return LlamaAction.MOVE_FORWARD
    }
}
