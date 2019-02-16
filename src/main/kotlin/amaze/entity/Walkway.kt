package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.core.assets.Images
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.TexturePaint

/**
 * The llama likes to walk on walkways.
 */
object Walkway : Entity {
    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        val paint = TexturePaint(Images.walkway, Rectangle(0, 0, width, height))
        with(graphics) {
            setPaint(paint)
            fillRect(0, 0, width, height)
        }
    }

    override fun interact(llama: Llama) {
    }
}
