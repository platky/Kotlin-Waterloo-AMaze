package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import java.awt.Graphics2D

data class Position(val x: Int, val y: Int)

abstract class Entity {
    abstract fun draw(graphics: Graphics2D, x: Int, y: Int, width: Int, height: Int)

    abstract fun interact(llama: Llama)
}
