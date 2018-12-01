package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.TexturePaint

object StartBlock : Entity() {

    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val paint = TexturePaint(Assets.walkway, Rectangle(x, y, width, height))
        with(graphics) {
            setPaint(paint)
            fillRect(x, y, width, height)
        }
    }

    override fun interact(llama: Llama) {}
}
