package main.kotlin.amaze.puzzles

import main.kotlin.amaze.LlamaAction
import main.kotlin.amaze.LlamaController
import main.kotlin.amaze.Maze
import main.kotlin.amaze.core.GameController
import main.kotlin.amaze.toMaze

fun main() {
    GameController(puzzles[3].toMaze(Puzzle4())).startGame()
}

/**
 * Going down every path is not wise, danger awaits!
 *
 * Hint:
 * You'll want to store some state in this class.
 */
class Puzzle4 : LlamaController {
    override fun getNextMove(maze: Maze): LlamaAction {
        return LlamaAction.MOVE_FORWARD
    }
}
