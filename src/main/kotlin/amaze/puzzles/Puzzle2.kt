package main.kotlin.amaze.puzzles

import main.kotlin.amaze.LlamaAction
import main.kotlin.amaze.LlamaController
import main.kotlin.amaze.Maze
import main.kotlin.amaze.core.GameController
import main.kotlin.amaze.toMaze

fun main(args: Array<String>) {
    GameController(puzzles[1].toMaze(Puzzle2())).startGame()
}

/**
 * The llama has eyes! You can utilize [Maze.getEntityInFrontOfLlama]
 *
 * Kotlin makes it easy to check the instance of something
 * https://kotlinlang.org/docs/reference/typecasts.html#is-and-is-operators
 *
 * In Kotlin if can be used as an expression like:
 * val max = if (a > b) a else b
 */
class Puzzle2 : LlamaController {
    override fun getNextMove(maze: Maze): LlamaAction {
        return LlamaAction.TURN_RIGHT
    }
}
