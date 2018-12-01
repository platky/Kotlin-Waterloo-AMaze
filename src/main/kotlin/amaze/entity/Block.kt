package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.util.*

class Block : Entity() {
    private val image by lazy {
        randomBlockImage()
    }

    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        graphics.drawImage(image, x, y, width, height, null)
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.CRASHED)
    }

    private fun randomBlockImage(): BufferedImage {
        val random = Random().nextInt(3) + 1
        return when(random) {
            1 -> Assets.block1
            2 -> Assets.block2
            else -> Assets.block3
        }
    }
}
