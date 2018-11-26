package main.kotlin.core

class GameController {
    private val inputManager = InputManager(this)
    private val screenManager = ScreenManager(
            title = "Kotlin Waterloo Amaze",
            windowWidth = 800,
            windowHeight = 600,
            keyListener = inputManager,
            windowListener = inputManager
    )

    fun exitGame() {
        System.exit(0)
    }
}

fun main(args: Array<String>) {
    GameController()
}
