import entity.Position
import java.awt.Graphics2D

class Robot {
    fun draw(graphics: Graphics2D, gridPosition: Position, interpolationFactor: Double) {

    }

    /**
     * InterpolationFactor will vary from 0 to 1 inclusive as the entity transitions to the next move.
     */
    fun update(interpolationFactor: Double) {}

    fun transitionToState(state: RobotState) {

    }
}

enum class RobotState {
    WORKING,
    CRASHED,
    LAZING_ABOUT
}