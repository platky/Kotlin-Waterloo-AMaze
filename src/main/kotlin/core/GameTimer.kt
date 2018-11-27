package main.kotlin.core

const val NANOS_PER_MILLI = 1_000_000L
const val NANOS_PER_SECOND = 1_000L * NANOS_PER_MILLI
const val FRAME_TIME = NANOS_PER_SECOND / 60L // 60 frames per second

class GameTimer(private val controller: GameController): Runnable {
    @Volatile
    private var isRunning = true

    private var overSleep = 0L

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
        val currentTime = System.nanoTime()
        val endTime = lastUpdateTime + FRAME_TIME
        val timeLeft = endTime - currentTime
        if(timeLeft > 0) {
            try {
                Thread.sleep(timeLeft / NANOS_PER_MILLI)
            } catch (e: InterruptedException) {
                //ignore
            }
        }

        do {
            Thread.yield()
        } while (System.nanoTime() < endTime)
    }

    private fun sleep(nanoseconds: Long) {

    }
}