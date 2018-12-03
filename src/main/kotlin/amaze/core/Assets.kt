package main.kotlin.amaze.core

import java.awt.image.BufferedImage
import javax.sound.sampled.AudioSystem
import java.util.*

private val random = Random()

private const val BASE_PATH = "/resources"
private const val IMAGE_PATH = "$BASE_PATH/images"
private const val SOUND_PATH = "$BASE_PATH/sound"

object Assets {
    const val DYING_SOUND = "llama_dying.wav"
    const val TELEPORTING_SOUND = "teleport.wav"
    const val YIPPEE_SOUND_1 = "yippee1.wav"
    const val YIPPEE_SOUND_2 = "yippee2.wav"
    const val YIPPEE_SOUND_3 = "yippee3.wav"
    const val YIPPEE_SOUND_4 = "yippee4.wav"
    const val FALLING_SOUND = "falling.wav"

    lateinit var llama: BufferedImage
        private set

    lateinit var llamaDead: BufferedImage
        private set

    lateinit var block1: BufferedImage
        private set

    lateinit var block2: BufferedImage
        private set

    lateinit var block3: BufferedImage
        private set

    lateinit var walkway: BufferedImage
        private set

    lateinit var destination: BufferedImage
        private set

    lateinit var pit: BufferedImage
        private set

    lateinit var teleporter: BufferedImage
        private set

    lateinit var yippee: BufferedImage
        private set

    lateinit var ouch: BufferedImage
        private set

    fun loadImages(screenManager: ScreenManager) {
        with(screenManager) {
            llama = loadImage("$IMAGE_PATH/llama.png")
            llamaDead = loadImage("$IMAGE_PATH/llama_dead.png")

            block1 = loadImage("$IMAGE_PATH/block1.png")
            block2 = loadImage("$IMAGE_PATH/block2.png")
            block3 = loadImage("$IMAGE_PATH/block3.png")
            walkway = loadImage("$IMAGE_PATH/8bitgrass.png")
            destination = loadImage("$IMAGE_PATH/hay.png")
            pit = loadImage("$IMAGE_PATH/pit.png")
            teleporter = loadImage("$IMAGE_PATH/teleporter.png")

            yippee = loadImage("$IMAGE_PATH/yippee.png")
            ouch = loadImage("$IMAGE_PATH/ouch.png")
        }
    }

    fun playVictoriousSound() {
        val rnd = random.nextDouble()
        val sound = when {
            rnd < 0.25 -> YIPPEE_SOUND_1
            rnd < 0.5 -> YIPPEE_SOUND_2
            rnd < 0.75 -> YIPPEE_SOUND_3
            else -> YIPPEE_SOUND_4
        }
        playSound(sound)
    }

    fun playSound(name: String) {
        try {
            val clip = AudioSystem.getClip()
            val inputStream = AudioSystem.getAudioInputStream(
                    javaClass.getResourceAsStream("$SOUND_PATH/$name"))
            clip.open(inputStream)
            clip.start()
        } catch (e: Exception) {
            System.err.println("Cannot play $name due to: ${e.message}")
        }
    }
}
