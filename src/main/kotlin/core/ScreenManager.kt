package main.kotlin.core

import java.awt.*
import java.awt.event.KeyListener
import java.awt.event.WindowListener
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JFrame

class ScreenManager(keyListener: KeyListener, windowListener: WindowListener) {
    private val device = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice
    private val frame = JFrame()
    private var graphics: Graphics2D? = null
    private val bufferStrategy: BufferStrategy

    init {
        with(frame) {
            addKeyListener(keyListener)
            addWindowListener(windowListener)
            focusTraversalKeysEnabled = false //allow custom actions for tab key etc.

//            setFullScreen()
            setWindowed("Kotlin Waterloo Amaze", 800, 600)
            ignoreRepaint = true
            createBufferStrategy(2)
        }
        // Kotlin 1.3 contracts will allow moving this initialization into the lambda
        bufferStrategy = frame.bufferStrategy
    }

    fun getWidth(): Int = device.displayMode.width

    fun getHeight(): Int = device.displayMode.height

    private fun JFrame.setFullScreen() {
        isUndecorated = true
        isResizable = false
        device.fullScreenWindow = this

        // Hide mouse cursor
        val toolkit = Toolkit.getDefaultToolkit()
        cursor = toolkit.createCustomCursor(toolkit.getImage(""), Point(0,0), "invisible")
    }

    private fun JFrame.setWindowed(windowTitle: String, width: Int, height: Int) {
        title = windowTitle
        size = Dimension(width, height)
        isUndecorated = false
        isVisible = true
    }

    fun restoreWindow() {
        val window = device.fullScreenWindow?.let {
            it.cursor = Cursor.getDefaultCursor()
            it.dispose()
        }
        device.fullScreenWindow = null
    }

    fun initializeFrame(): Graphics2D {
        return (bufferStrategy.drawGraphics as Graphics2D).also { graphics = it }
    }

    fun renderFrame() {
        graphics?.dispose()
        graphics = null
        if (!bufferStrategy.contentsLost()) bufferStrategy.show()

        Toolkit.getDefaultToolkit().sync()
    }

    fun loadImage(path: String): BufferedImage {
        val tempImage = ImageIO.read(javaClass.getResource(path))
        return createCompatibleImage(tempImage).also { tempImage.flush() }
    }

    private fun createCompatibleImage(image: BufferedImage): BufferedImage {
        val transparency = image.colorModel.transparency
        val result = device.defaultConfiguration.
                createCompatibleImage(image.width, image.height, transparency)

        with(result.createGraphics()) {
            drawImage(image, 0, 0, null)
            dispose()
        }
        return result
    }
}
