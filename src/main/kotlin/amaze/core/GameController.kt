package main.kotlin.amaze.core

import main.kotlin.amaze.Maze

class GameController(private val maze: Maze) {
    private val inputManager = InputManager(this)
    private val screenManager = ScreenManager(
            title = "Kotlin Waterloo Amaze",
            aspectRatio = maze.numColumns.toDouble() / maze.numRows,
            screenRatio = 0.75,
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
        maze.draw(graphics, screenManager.getWidth(), screenManager.getHeight())
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
