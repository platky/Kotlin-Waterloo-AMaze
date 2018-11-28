import RobotState.WORKING
import entity.Position
import java.awt.Graphics2D

class Robot {
    var state: RobotState = WORKING

    fun draw(graphics: Graphics2D, gridPosition: Position, interpolationFactor: Double) {

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
