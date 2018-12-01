package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import java.awt.Graphics2D
import java.awt.Rectangle
import java.awt.TexturePaint
import javax.imageio.ImageIO

object Walkway : Entity() {
    private val image = ImageIO.read(javaClass.getResource("/images/grass_texture_pixel.png"))

    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val paint = TexturePaint(image, Rectangle(x, y, width, height))
        with(graphics) {
            setPaint(paint)
            fillRect(x, y, width, height)
        }
    }

    override fun interact(llama: Llama) {

    }
}
