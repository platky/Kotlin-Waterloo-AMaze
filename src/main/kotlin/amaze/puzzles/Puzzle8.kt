package main.kotlin.amaze.puzzles

import main.kotlin.amaze.LlamaAction
import main.kotlin.amaze.LlamaController
import main.kotlin.amaze.Maze
import main.kotlin.amaze.core.GameController
import main.kotlin.amaze.toMaze

fun main(args: Array<String>) {
    GameController(puzzles[7].toMaze(Puzzle8())).startGame()
}

class Puzzle8 : LlamaController {
    override fun getNextMove(maze: Maze): LlamaAction {
        return LlamaAction.TURN_RIGHT
    }
}
