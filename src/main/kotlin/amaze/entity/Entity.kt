package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import java.awt.Graphics2D

/**
 * Represents an entity which exists at a location in the maze such as a [Walkway] or [Forest].
 */
abstract class Entity {
    abstract fun draw(graphics: Graphics2D, width: Int, height: Int)

    abstract fun interact(llama: Llama)
}
