package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import java.awt.Graphics2D

/**
 * Represents an entity (such as a [Walkway] or [Forest]) which exists at a location in the maze.
 */
interface Entity {
    fun draw(graphics: Graphics2D, width: Int, height: Int)

    fun interact(llama: Llama)
}
