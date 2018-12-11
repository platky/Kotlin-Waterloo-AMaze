package main.kotlin.amaze.core.assets

import javax.sound.sampled.AudioSystem
import java.util.*

private val random = Random()

private const val SOUND_PATH = "/resources/sound"

enum class Sound(name: String) {
    DYING("llama_dying.wav"),
    TELEPORTING("teleport.wav"),
    FALLING("falling.wav"),
    VICTORY_1("yippee1.wav"),
    VICTORY_2("yippee2.wav"),
    VICTORY_3("yippee3.wav"),
    VICTORY_4("yippee4.wav");

    private val path = "$SOUND_PATH/$name"

    fun play() {
        try {
            val inputStream = AudioSystem.getAudioInputStream(
                    javaClass.getResourceAsStream(path))
            AudioSystem.getClip().run {
                open(inputStream)
                start()
            }
        } catch (e: Exception) {
            System.err.println("Cannot play $path due to: ${e.message}")
        }
    }
}

private val victorySounds = arrayOf(
        Sound.VICTORY_1,
        Sound.VICTORY_2,
        Sound.VICTORY_3,
        Sound.VICTORY_4
)

object Sounds {
    fun playVictoriousSound() {
        val index = random.nextInt(victorySounds.size)
        victorySounds[index].play()
    }
}
