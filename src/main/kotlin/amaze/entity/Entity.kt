package entity

import main.kotlin.amaze.Robot
import java.awt.Color
import java.awt.Graphics2D

data class Position(val x: Int, val y: Int)

abstract class Entity {
    abstract fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int)

    protected fun Graphics2D.drawBorder(x: Int, y: Int, width: Int, height: Int) {
        color = Color.BLACK
        drawRect(x, y, width, height)
    }

    abstract fun interact(robot: Robot)
}
