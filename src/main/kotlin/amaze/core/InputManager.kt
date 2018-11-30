package main.kotlin.amaze.core

import java.awt.event.*

class InputManager(private val gameController: GameController) : WindowAdapter(), KeyListener {
    override fun windowClosing(e: WindowEvent?) {
        gameController.exitGame()
    }

    override fun keyTyped(e: KeyEvent) {
        e.consume()
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_ESCAPE -> gameController.exitGame()
        }
    }

    override fun keyReleased(e: KeyEvent) {
    }
}
