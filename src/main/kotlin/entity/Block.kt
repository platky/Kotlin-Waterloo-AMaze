package main.kotlin.entity

import main.kotlin.Robot
import main.kotlin.RobotState
import java.awt.Color
import java.awt.Graphics2D

class Block : Entity() {
    override fun draw(graphics: Graphics2D, topLeft: Position, bottomRight: Position) {
        val width = bottomRight.x - topLeft.x
        val height = bottomRight.y - topLeft.y
        graphics.color = Color.RED
        graphics.fillRect(topLeft.x, topLeft.y, width, height)
    }

    override fun interact(robot: Robot) {
        robot.transitionToState(RobotState.CRASHED)
    }

}