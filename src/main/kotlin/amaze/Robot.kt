package main.kotlin.amaze

import main.kotlin.amaze.RobotState.WORKING
import entity.Position
import java.awt.Color
import java.awt.Graphics2D

class Robot {
    var state: RobotState = WORKING
    var rotation = 0.0

    fun update() {
        rotation += 0.1
    }

    fun draw(
            graphics: Graphics2D,
            x: Int,
            y: Int,
            width: Int,
            height: Int
    ) {
        graphics.rotate(rotation, x + width / 2.0, y + height / 2.0)

        graphics.color = Color.RED

        val robotWidth = width / 5
        val robotHeight = height / 3
        graphics.fillRect(
                x + (width - robotWidth) / 2, y + (height - robotHeight) / 2,
                robotWidth, robotHeight
        )
        graphics.color = Color.BLUE
        graphics.fillOval(
                x + (width - robotWidth) / 2, y + (height - robotWidth / 2) / 2
                , robotWidth, robotWidth
        )
    }

    /**
     * InterpolationFactor will vary from 0 to 1 inclusive as the entity transitions to the next move.
     */
    fun update(interpolationFactor: Double) {}

    //TODO we may not need this function unless we want some transition validation
    fun transitionToState(state: RobotState) {
        this.state = state
    }
}

enum class RobotState {
    WORKING,
    CRASHED,
    COMPLETED
}
