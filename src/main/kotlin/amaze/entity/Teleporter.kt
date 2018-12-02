package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.Position
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D


class Teleporter(val endpoint: Position) : Entity() {
    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        graphics.drawImage(Assets.walkway, x, y, width, height, null)
        graphics.drawImage(Assets.teleporter, x, y, width, height, null)
    }

    override fun interact(llama: Llama) {
        llama.transitionToState(LlamaState.MOVING_ONTO_TELEPORTER)
    }
}