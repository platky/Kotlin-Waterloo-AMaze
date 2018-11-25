package entity

import Robot
import RobotState
import java.awt.Color
import java.awt.Graphics2D

object Block : Entity() {
    override fun draw(graphics: Graphics2D, topLeft: Position, width: Int, height: Int) {
        with(graphics) {
            color = Color.DARK_GRAY
            fillRect(topLeft.x, topLeft.y, width, height)
            drawBorder(topLeft, width, height)
        }
    }

    override fun interact(robot: Robot) {
        robot.transitionToState(RobotState.CRASHED)
    }
}
