package entity

import main.kotlin.amaze.Robot
import java.awt.Color
import java.awt.Graphics2D

object Walkway : Entity() {
    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        with(graphics) {
            color = Color.WHITE
            fillRect(x, y, width, height)
            drawBorder(x, y, width, height)
        }
    }

    override fun interact(robot: Robot) {

    }
}
