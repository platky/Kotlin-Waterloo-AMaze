package entity

import main.kotlin.amaze.Robot
import main.kotlin.amaze.RobotState
import java.awt.Color
import java.awt.Graphics2D

object Block : Entity() {
    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        with(graphics) {
            color = Color.DARK_GRAY
            fillRect(x, y, width, height)
            drawBorder(x, y, width, height)
        }
    }

    override fun interact(robot: Robot) {
        robot.transitionToState(RobotState.CRASHED)
    }
}
