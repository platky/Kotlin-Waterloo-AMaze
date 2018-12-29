package main.kotlin.amaze.core

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class InputManager(private val gameController: GameController) : KeyListener {
    override fun keyTyped(e: KeyEvent) {
        e.consume()
    }

    override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_ESCAPE) gameController.exitGame()
    }

    override fun keyReleased(e: KeyEvent) {
    }
}
