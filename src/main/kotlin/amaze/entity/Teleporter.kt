package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.Position
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D

class Teleporter(val endpoint: Position) : Entity() {
    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        with (graphics) {
            drawImage(Assets.walkway, 0, 0, width, height, null)
            drawImage(Assets.teleporter, 0, 0, width, height, null)
        }
    }

    override fun interact(llama: Llama) {
        llama.teleporterStateTransition(LlamaState.MOVING_ONTO_TELEPORTER, endpoint)
    }
}
