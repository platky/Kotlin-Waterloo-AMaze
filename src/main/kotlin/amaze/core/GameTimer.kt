package main.kotlin.amaze.core

const val NANOS_PER_MILLI = 1_000_000L
const val NANOS_PER_SECOND = 1_000L * NANOS_PER_MILLI

class GameTimer(refreshRate: Int, private val controller: GameController): Runnable {
    /** Match refresh rate */
    private val frameTime = NANOS_PER_SECOND / refreshRate

    @Volatile
    private var isRunning = true

    fun stop() {
        isRunning = false
    }

    override fun run() {
        try {
            runGameLoop()
        } finally {
            controller.cleanupAndShutDown()
        }
    }

    private fun runGameLoop() {
        var lastNanoTime = System.nanoTime()

        while (isRunning) {
            syncFrameRate(lastNanoTime)
            val currentTime = System.nanoTime()
            val timeDelta = currentTime - lastNanoTime
            lastNanoTime = currentTime

            controller.update(timeDelta)
            controller.render()
        }
    }

    private fun syncFrameRate(lastUpdateTime: Long) {
        val endTime = lastUpdateTime + frameTime
        do {
            Thread.yield()
        } while (System.nanoTime() < endTime)
    }
}
