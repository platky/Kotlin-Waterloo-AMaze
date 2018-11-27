package main.kotlin.core

import java.awt.Color

class GameController {
    private val inputManager = InputManager(this)
    private val screenManager = ScreenManager(
            title = "Kotlin Waterloo Amaze",
            windowWidth = 800,
            windowHeight = 600,
            keyListener = inputManager,
            windowListener = inputManager
    )
    private val timer = GameTimer(this)

    /** Temp for demonstration purposes */
    private var ballLocation = 0

    init {
        Thread(timer).start()
    }

    fun update(timeDelta: Long) {
        val timeDeltaMillis = timeDelta / 1_000_000.0
        val ballVellocity = 0.5 //pixels per millisecond

        ballLocation += (timeDeltaMillis * ballVellocity).toInt()
    }

    fun render() {
        val graphics = screenManager.initializeFrame()
        val windowWidth = screenManager.getWidth()
        val windowHeight = screenManager.getHeight()

        graphics.color = Color.BLACK
        graphics.fillRect(0, 0, windowWidth, windowHeight)

        graphics.color = Color.GREEN
        graphics.fillOval(ballLocation, windowHeight / 2, 20, 20)

        screenManager.finalizeFrame()
    }

    fun exitGame() {
        timer.stop()
    }

    fun cleanupAndShutDown() {
        System.exit(0)
    }
}

fun main(args: Array<String>) {
    GameController()
}
