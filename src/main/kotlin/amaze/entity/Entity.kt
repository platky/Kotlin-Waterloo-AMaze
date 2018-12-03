package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import java.awt.Graphics2D

abstract class Entity {
    abstract fun draw(graphics: Graphics2D, width: Int, height: Int)

    abstract fun interact(llama: Llama)
}
