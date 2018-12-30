package main.kotlin.amaze.core

import main.kotlin.amaze.Maze
import main.kotlin.amaze.core.assets.Images

/**
 * The [GameController] connects everything together.  It configures the input management, sets up the screen,
 * establishes the game timer for animation, and notifies the maze when to update and render.
 */
class GameController(private val maze: Maze) {
    private val inputManager = InputManager(this)
    private val screenManager = ScreenManager(
        title = "Kotlin Waterloo Amaze",
        aspectWidth = maze.numColumns,
        aspectHeight = maze.numRows,
        screenRatio = 0.75,
        keyListener = inputManager
    )

    private val timer = GameTimer(screenManager.getRefreshRate(), this)

    /** Keep track of sub-millisecond left-overs to reduce stutter */
    private var leftOverTime = 0L

    init {
        Images.loadImages(screenManager)
        screenManager.setIcon(Images.destination)
    }

    fun updateGame(timeDeltaNanos: Long) {
        val fullTimeDelta = timeDeltaNanos + leftOverTime
        val timeDeltaMillis = fullTimeDelta / NANOS_PER_MILLI
        leftOverTime = fullTimeDelta - timeDeltaMillis * NANOS_PER_MILLI

        maze.update(timeDeltaMillis)
    }

    fun render() {
        screenManager.renderNewFrame { graphics ->
            maze.draw(graphics, screenManager.getWidth(), screenManager.getHeight())
        }
    }

    fun startGame() {
        Thread(timer).start()
    }

    fun exitGame() {
        timer.stop()
        System.exit(0)
    }
}
