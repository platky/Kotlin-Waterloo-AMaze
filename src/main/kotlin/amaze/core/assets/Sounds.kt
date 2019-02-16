package main.kotlin.amaze.core.assets

import main.kotlin.amaze.core.assets.Sound.*
import javax.sound.sampled.AudioSystem

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
            val inputStream = AudioSystem.getAudioInputStream(javaClass.getResourceAsStream(path))
            AudioSystem.getClip().run {
                open(inputStream)
                start()
            }
        } catch (e: Exception) {
            System.err.println("Cannot play $path due to: ${e.message}")
        }
    }
}

object Sounds {
    private val victorySounds = arrayOf(VICTORY_1, VICTORY_2, VICTORY_3, VICTORY_4)

    fun playVictoriousSound() {
        victorySounds.random().play()
    }
}
