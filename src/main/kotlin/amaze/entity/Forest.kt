package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.core.assets.Images
import main.kotlin.amaze.core.assets.draw
import java.awt.Graphics2D

private val FORREST_IMAGES = listOf(
    lazy { Images.forrest1 },
    lazy { Images.forrest2 },
    lazy { Images.forrest3 }
)

/**
 * The llama is afraid to walk through a [Forest] in fear of being hunted and slaughtered.
 */
class Forest : Entity {
    private val forestImage by FORREST_IMAGES.random()

    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        Images.walkway.draw(graphics, width, height)
        forestImage.draw(graphics, width, height)
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.CRASHING)
    }
}
