package main.kotlin.core

import java.awt.*
import java.awt.event.KeyListener
import java.awt.event.WindowListener
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JFrame

class ScreenManager(
        title: String,
        windowWidth: Int,
        windowHeight: Int,
        keyListener: KeyListener,
        windowListener: WindowListener
) {
    private val device = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice
    private val window = JFrame()
    private var graphics: Graphics2D? = null
    private val bufferStrategy: BufferStrategy

    init {
        with(window) {
            addKeyListener(keyListener)
            addWindowListener(windowListener)
            focusTraversalKeysEnabled = false //allow custom actions for tab key etc.
            ignoreRepaint = true
            window.title = title
            size = Dimension(windowWidth, windowHeight)
            isUndecorated = false
            isVisible = true
        }
        bufferStrategy = window.setupDoubleBuffering()
    }

    private fun JFrame.setupDoubleBuffering(): BufferStrategy {
        createBufferStrategy(2)
        return bufferStrategy
    }

    fun getWidth(): Int = device.displayMode.width

    fun getHeight(): Int = device.displayMode.height

    /**
     * Called at the beginning of each frame before you can start drawing on screen.
     */
    fun initializeFrame(): Graphics2D {
        return (bufferStrategy.drawGraphics as Graphics2D).also { graphics = it }
    }

    /**
     * Called at the end of each frame to update the screen.
     */
    fun finalizeFrame() {
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
