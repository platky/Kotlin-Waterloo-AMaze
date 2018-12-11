package main.kotlin.amaze.core.assets

import main.kotlin.amaze.core.ScreenManager
import java.awt.Graphics2D
import java.awt.image.BufferedImage

private const val IMAGE_PATH = "/resources/images"

object Images {
    lateinit var llama: BufferedImage
        private set

    lateinit var llamaDead: BufferedImage
        private set

    lateinit var forrest1: BufferedImage
        private set

    lateinit var forrest2: BufferedImage
        private set

    lateinit var forrest3: BufferedImage
        private set

    lateinit var walkway: BufferedImage
        private set

    lateinit var destination: BufferedImage
        private set

    lateinit var pit: BufferedImage
        private set

    lateinit var teleporter: BufferedImage
        private set

    lateinit var yippee: BufferedImage
        private set

    lateinit var ouch: BufferedImage
        private set

    fun loadImages(screenManager: ScreenManager) {
        with(screenManager) {
            llama = loadImage("$IMAGE_PATH/llama.png")
            llamaDead = loadImage("$IMAGE_PATH/llama_dead.png")

            forrest1 = loadImage("$IMAGE_PATH/forrest1.png")
            forrest2 = loadImage("$IMAGE_PATH/forrest2.png")
            forrest3 = loadImage("$IMAGE_PATH/forrest3.png")
            walkway = loadImage("$IMAGE_PATH/8bitgrass.png")
            destination = loadImage("$IMAGE_PATH/hay.png")
            pit = loadImage("$IMAGE_PATH/pit.png")
            teleporter = loadImage("$IMAGE_PATH/teleporter.png")

            yippee = loadImage("$IMAGE_PATH/yippee.png")
            ouch = loadImage("$IMAGE_PATH/ouch.png")
        }
    }
}

fun BufferedImage.draw(graphics: Graphics2D, width: Int, height: Int) {
    graphics.drawImage(this, 0, 0, width, height, null)
}
