package main.kotlin.amaze.core

import java.awt.Dimension
import java.awt.Graphics2D
import java.awt.GraphicsEnvironment
import java.awt.Toolkit
import java.awt.event.KeyListener
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JFrame

class ScreenManager(
    title: String,
    aspectWidth: Int,
    aspectHeight: Int,
    screenRatio: Double, // percentage of the width or height to use (based on aspect ratio)
    keyListener: KeyListener
) {
    private val device = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice
    private val window = JFrame()
    private var graphics: Graphics2D? = null
    private val bufferStrategy: BufferStrategy

    init {
        require(aspectWidth > 0 && aspectHeight > 0)
        require(screenRatio > 0 && screenRatio <= 1.0)

        val windowDimensions = computeWindowDimensions(aspectWidth, aspectHeight, screenRatio)

        with(window) {
            window.title = title
            addKeyListener(keyListener)
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            ignoreRepaint = true
            contentPane.preferredSize = windowDimensions
            isResizable = false
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
        bufferStrategy = window.setupDoubleBuffering()
    }

    private fun computeWindowDimensions(
        aspectWidth: Int,
        aspectHeight: Int,
        screenRatio: Double
    ): Dimension {
        val screenWidth = device.displayMode.width
        val screenHeight = device.displayMode.height

        val aspectRatio = aspectWidth.toDouble() / aspectHeight

        val squareLength = if (aspectRatio > screenWidth.toDouble() / screenHeight) {
            (screenWidth * screenRatio / aspectWidth).toInt()
        } else {
            (screenHeight * screenRatio / aspectHeight).toInt()
        }

        return Dimension(squareLength * aspectWidth, squareLength * aspectHeight)
    }

    fun setIcon(icon: BufferedImage) {
        window.iconImage = icon
    }

    private fun JFrame.setupDoubleBuffering(): BufferStrategy {
        createBufferStrategy(2)
        return bufferStrategy
    }

    fun getRefreshRate(): Int {
        val refreshRate = device.displayMode.refreshRate

        return if (refreshRate < 30) 60 else refreshRate //Macs sometimes return 0
    }

    fun getWidth(): Int = window.width

    fun getHeight(): Int = window.contentPane.height

    private fun getTitleBarHeight(): Int = window.insets.top

    inline fun renderNewFrame(render: (Graphics2D) -> Unit) {
        val graphics = initializeFrame()
        try {
            render(graphics)
        } finally {
            finalizeFrame()
        }
    }

    /**
     * For internal use only.  Call [renderNewFrame] instead.
     */
    @PublishedApi
    internal fun initializeFrame(): Graphics2D {
        val graphics2D = bufferStrategy.drawGraphics as Graphics2D
        graphics2D.translate(0, getTitleBarHeight())

        graphics = graphics2D
        return graphics2D
    }

    /**
     * For internal use only.  Call [renderNewFrame] instead.
     */
    @PublishedApi
    internal fun finalizeFrame() {
        graphics?.dispose()
        graphics = null
        if (!bufferStrategy.contentsLost()) bufferStrategy.show()

        Toolkit.getDefaultToolkit().sync()
    }

    fun loadImage(path: String): BufferedImage {
        val tempImage = ImageIO.read(javaClass.getResource(path))
        return createCompatibleImage(tempImage).also { tempImage.flush() }
    }

    /**
     * Creates an image which is compatible with the graphics device from improved performance.
     */
    private fun createCompatibleImage(image: BufferedImage): BufferedImage {
        val transparency = image.colorModel.transparency
        val result = device.defaultConfiguration
            .createCompatibleImage(image.width, image.height, transparency)

        with(result.createGraphics()) {
            drawImage(image, 0, 0, null)
            dispose()
        }
        return result
    }
}
