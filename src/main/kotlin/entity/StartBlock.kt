package entity

import Robot
import java.awt.Color
import java.awt.Graphics2D


class StartBlock : Entity() {
    override fun draw(graphics: Graphics2D, topLeft: Position, bottomRight: Position) {
        val width = bottomRight.x - topLeft.x
        val height = bottomRight.y - topLeft.y
        graphics.color = Color.WHITE
        graphics.fillRect(topLeft.x, topLeft.y, width, height)

        drawBorder(graphics, topLeft, bottomRight)
    }

    override fun interact(robot: Robot) {}
}