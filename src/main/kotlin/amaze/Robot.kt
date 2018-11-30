package main.kotlin.amaze

import entity.Position
import main.kotlin.amaze.RobotState.*
import java.awt.Color
import java.awt.Graphics2D

class Robot {
    private var state= WAITING
    private var orientation = Orientation.NORTH

    fun draw(
            graphics: Graphics2D,
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            movePercentageComplete: Double
    ) {
        val rotation = orientation.radians + state.rotation * movePercentageComplete
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

    fun setCurrentAction(action: RobotAction) {
        if (state == TURNING_LEFT) {
            orientation = orientation.turnLeft()
        } else if (state == TURNING_RIGHT) {
            orientation = orientation.turnRight()
        }
        state = when (action) {
            RobotAction.TURN_LEFT -> TURNING_LEFT
            RobotAction.TURN_RIGHT -> TURNING_RIGHT
            RobotAction.MOVE_FORWARD -> MOVING_FORWARD
        }
    }

    //TODO we may not need this function unless we want some transition validation
    fun transitionToState(state: RobotState) {
        this.state = state
    }
}

enum class RobotState(val rotation: Double) {
    WAITING(0.0),
    TURNING_LEFT(-Math.PI / 2),
    TURNING_RIGHT(Math.PI / 2),
    MOVING_FORWARD(0.0),
    CRASHED(0.0),
    COMPLETED(0.0)
}

enum class Orientation(val radians: Double) {
    NORTH(0.0),
    WEST(-Math.PI / 2),
    SOUTH(-Math.PI),
    EAST(-3 * Math.PI / 2);

    fun turnLeft(): Orientation = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
    }

    fun turnRight(): Orientation = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}