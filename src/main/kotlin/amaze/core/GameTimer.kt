package main.kotlin.amaze.core

const val NANOS_PER_MILLI = 1_000_000L
const val NANOS_PER_SECOND = 1_000L * NANOS_PER_MILLI

/**
 * The [GameTimer] notifies the [GameController] to update and render at a regular interval.
 */
class GameTimer(refreshRate: Int, private val controller: GameController) : Runnable {
    /** Match refresh rate */
    private val frameTime = NANOS_PER_SECOND / refreshRate

    @Volatile
    private var isRunning = true

    fun stop() {
        isRunning = false
    }

    override fun run() {
        var lastUpdateTime = System.nanoTime()

        while (isRunning) {
            syncFrameRate(lastUpdateTime)
            val currentTime = System.nanoTime()
            val timeDelta = currentTime - lastUpdateTime
            lastUpdateTime = currentTime

            controller.updateGame(timeDelta)
            controller.render()
        }
    }

    /**
     * Syncs the frame rate by delaying the update if we still have time left over in the current frame.
     */
    private fun syncFrameRate(lastUpdateTime: Long) {
        val endTime = lastUpdateTime + frameTime
        do {
            Thread.yield() // using sleep would reduce battery usage but increase frame-time variance
        } while (System.nanoTime() < endTime)
    }
}
