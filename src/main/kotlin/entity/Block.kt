package entity

import Robot
import RobotState
import java.awt.Color
import java.awt.Graphics2D

class Block : Entity() {
    override fun draw(graphics: Graphics2D, topLeft: Position, bottomRight: Position) {
        val width = bottomRight.x - topLeft.x
        val height = bottomRight.y - topLeft.y
        graphics.color = Color.DARK_GRAY
        graphics.fillRect(topLeft.x, topLeft.y, width, height)

        drawBorder(graphics, topLeft, bottomRight)
    }

    override fun interact(robot: Robot) {
        robot.transitionToState(RobotState.CRASHED)
    }
}