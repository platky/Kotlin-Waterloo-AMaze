package main.kotlin.core

class GameController {
    private val inputManager = InputManager(this)
    private val screenManager = ScreenManager(inputManager, inputManager)

    fun exitGame() {
        screenManager.restoreWindow()
        System.exit(0)
    }
}

fun main(args: Array<String>) {
    GameController()
}