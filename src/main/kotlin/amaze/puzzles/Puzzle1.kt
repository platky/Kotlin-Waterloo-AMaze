package main.kotlin.amaze.puzzles

import main.kotlin.amaze.*
import main.kotlin.amaze.core.GameController

fun main(args: Array<String>) {
    GameController(puzzles[0].toMaze(Puzzle1())).startGame()
}

class Puzzle1 : LlamaController {
    override fun getNextMove(maze: Maze): LlamaAction {
        return LlamaAction.TURN_RIGHT
    }
}
