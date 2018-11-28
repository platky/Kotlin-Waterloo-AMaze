package main.kotlin.amaze.core

import main.kotlin.amaze.Maze

class GameController(private val maze: Maze) {
    private val inputManager = InputManager(this)
    private val screenManager = ScreenManager(
            title = "Kotlin Waterloo Amaze",
            windowWidth = 600,
            windowHeight = 600,
            keyListener = inputManager,
            windowListener = inputManager
    )
    private val timer = GameTimer(screenManager.getRefreshRate(), this)
    /** Keep track of sub-millisecond left-overs to reduce stutter */
    private var leftOverTime = 0L

    fun update(timeDelta: Long) {
        val fullTimeDelta = timeDelta + leftOverTime
        val timeDeltaMillis = fullTimeDelta / NANOS_PER_MILLI
        leftOverTime = fullTimeDelta - timeDeltaMillis * NANOS_PER_MILLI

        maze.update(timeDeltaMillis)
    }

    fun render() {
        val graphics = screenManager.initializeFrame()

        val width = screenManager.getWidth()
        val height = screenManager.getHeight()

        graphics.translate(0, screenManager.getTitleBarHeight())
        maze.draw(graphics, width, height)

        screenManager.finalizeFrame()
    }

    fun startGame() {
        Thread(timer).start()
    }

    fun exitGame() {
        timer.stop()
    }

    fun cleanupAndShutDown() {
        System.exit(0)
    }
}
