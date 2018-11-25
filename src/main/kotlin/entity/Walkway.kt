package entity

import Robot
import java.awt.Color
import java.awt.Graphics2D

object Walkway : Entity() {
    override fun draw(graphics: Graphics2D, topLeft: Position, width: Int, height: Int) {
        with(graphics) {
            color = Color.WHITE
            fillRect(topLeft.x, topLeft.y, width, height)
            drawBorder(topLeft, width, height)
        }
    }

    override fun interact(robot: Robot) {

    }
}
