package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.core.Assets
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.TexturePaint

object Walkway : Entity() {
    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        val paint = TexturePaint(Assets.walkway, Rectangle(0, 0, width, height))
        with(graphics) {
            setPaint(paint)
            fillRect(0, 0, width, height)
        }
    }

    override fun interact(llama: Llama) {
    }
}
