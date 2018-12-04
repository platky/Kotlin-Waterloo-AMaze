package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.util.*

class Forest : Entity() {
    private val image by lazy { randomBlockImage() }

    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        with (graphics) {
            drawImage(Assets.walkway, 0, 0, width, height, null)
            drawImage(image, 0, 0, width, height, null)
        }
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.CRASHING)
    }

    private fun randomBlockImage(): BufferedImage {
        return when(Random().nextInt(3) + 1) {
            1 -> Assets.block1
            2 -> Assets.block2
            else -> Assets.block3
        }
    }
}
