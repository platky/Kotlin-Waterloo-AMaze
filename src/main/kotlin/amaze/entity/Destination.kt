package entity

import main.kotlin.amaze.Robot
import main.kotlin.amaze.RobotState
import java.awt.Graphics2D

object Destination : Entity() {
    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {

    }

    override fun interact(robot: Robot) {
        robot.transitionToState(RobotState.COMPLETED)
    }
}
