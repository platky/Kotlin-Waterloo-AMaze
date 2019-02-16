package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.core.assets.Images
import main.kotlin.amaze.core.assets.draw
import java.awt.Graphics2D

/**
 * The destination where the llama is trying to get to.
 */
object Destination : Entity {
    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        Images.walkway.draw(graphics, width, height)
        Images.destination.draw(graphics, width, height)
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.TO_VICTORY_AND_BEYOND)
    }
}
