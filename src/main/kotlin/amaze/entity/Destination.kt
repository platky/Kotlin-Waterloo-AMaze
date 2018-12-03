package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D

object Destination : Entity() {
    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        with (graphics) {
            drawImage(Assets.walkway, x, y, width, height, null)
            drawImage(Assets.destination, x, y, width, height, null)
        }
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.TO_VICTORY_AND_BEYOND)
    }
}
