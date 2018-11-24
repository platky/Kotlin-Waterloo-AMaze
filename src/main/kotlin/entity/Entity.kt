package entity

import Robot
import java.awt.Color
import java.awt.Graphics2D

data class Position(val x: Int, val y: Int)

abstract class Entity {
    abstract fun draw(graphics: Graphics2D, topLeft: Position, bottomRight: Position)

    protected fun drawBorder(graphics: Graphics2D, topLeft: Position, bottomRight: Position) {
        val width = bottomRight.x - topLeft.x
        val height = bottomRight.y - topLeft.y

        graphics.color = Color.BLACK
        graphics.drawRect(topLeft.x, topLeft.y, width, height)
    }

    abstract fun interact(robot: Robot)
}