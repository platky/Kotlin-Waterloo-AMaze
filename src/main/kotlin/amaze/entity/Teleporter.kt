package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.Position
import main.kotlin.amaze.core.assets.Images
import main.kotlin.amaze.core.assets.draw
import java.awt.Graphics2D

/**
 * A [Teleporter] is a quantum mechanical device which transports the llama by taking advantage of
 * quantum entanglement.
 */
class Teleporter(private val endpoint: Position) : Entity {
    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        Images.walkway.draw(graphics, width, height)
        Images.teleporter.draw(graphics, width, height)
    }

    override fun interact(llama: Llama) {
        llama.teleporterStateTransition(LlamaState.MOVING_ONTO_TELEPORTER, endpoint)
    }
}
