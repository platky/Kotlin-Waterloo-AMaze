package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.core.assets.Images
import main.kotlin.amaze.core.assets.draw
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.util.*

class Forest : Entity() {
    private val forestImage by lazy { randomBlockImage() }

    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        Images.walkway.draw(graphics, width, height)
        forestImage.draw(graphics, width, height)
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.CRASHING)
    }

    private fun randomBlockImage(): BufferedImage = when (Random().nextInt(3)) {
        0 -> Images.forrest1
        1 -> Images.forrest2
        else -> Images.forrest3
    }
}
