package entity

import main.kotlin.amaze.Robot
import main.kotlin.amaze.RobotState
import java.awt.Graphics2D
import java.io.File
import javax.imageio.ImageIO

object Mine : Entity() {
    override fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int) {
        val image = ImageIO.read(File("images/mine.png"))
        graphics.drawImage(image, x, y, width, height, null)
    }

    override fun interact(robot: Robot) {
        robot.transitionToState(RobotState.CRASHED)
    }
}