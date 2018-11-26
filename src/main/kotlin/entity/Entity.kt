package entity

import Robot
import java.awt.Color
import java.awt.Graphics2D

data class Position(val x: Int, val y: Int)

abstract class Entity {
    abstract fun draw(graphics: Graphics2D, topLeft: Position, width: Int, height: Int)

    protected fun Graphics2D.drawBorder(topLeft: Position, width: Int, height: Int) {
        color = Color.BLACK
        drawRect(topLeft.x, topLeft.y, width, height)
    }

    abstract fun interact(robot: Robot)
}
