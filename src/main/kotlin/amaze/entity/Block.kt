package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import java.awt.Graphics2D
import java.util.*
import javax.imageio.ImageIO

class Block : Entity() {
    private val image = ImageIO.read(javaClass.getResource(randomWallImage()))

    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        graphics.drawImage(image, x, y, width, height, null)
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.CRASHED)
    }

    private fun randomWallImage(): String {
        val random = Random().nextInt(3) + 1
        return "/images/block$random.png"
    }
}
