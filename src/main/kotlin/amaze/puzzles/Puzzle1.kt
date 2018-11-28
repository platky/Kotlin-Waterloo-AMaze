package main.kotlin.amaze.puzzles

import main.kotlin.amaze.core.GameController
import main.kotlin.amaze.toMaze

private val puzzleDefinition = """
    XDXXX
    XOOXX
    XXOXX
    XXOOX
    XXSXX
""".trimIndent()

fun main(args: Array<String>) {
    val game = GameController(puzzleDefinition.toMaze())

    game.startGame()
}
