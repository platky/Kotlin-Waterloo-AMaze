package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import java.awt.Graphics2D
import javax.imageio.ImageIO

object Pit : Entity() {
    private val image = ImageIO.read(javaClass.getResource("/images/pit.png"))

    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        graphics.drawImage(image, x, y, width, height, null)
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.CRASHED)
    }
}