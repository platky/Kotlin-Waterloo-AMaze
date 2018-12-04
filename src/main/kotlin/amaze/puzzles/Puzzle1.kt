package main.kotlin.amaze.puzzles

import main.kotlin.amaze.*
import main.kotlin.amaze.core.GameController

fun main(args: Array<String>) {
    GameController(puzzles[0].toMaze(Puzzle1())).startGame()
}

/**
 * You have access to the enum [LlamaAction]
 */
class Puzzle1 : LlamaController {
    val l = listOf(LlamaAction.TURN_LEFT,LlamaAction.MOVE_FORWARD, LlamaAction.TURN_LEFT, LlamaAction.MOVE_FORWARD)
    var c = -1
    override fun getNextMove(maze: Maze): LlamaAction {
        c++
        return l[c]
    }
}
