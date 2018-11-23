package entity

import Robot
import java.awt.Graphics2D

data class Position(val x: Int, val y: Int)

abstract class Entity {
    abstract fun draw(graphics: Graphics2D, topLeft: Position, bottomRight: Position)

    abstract fun interact(robot: Robot)
}