package main.kotlin.amaze.core

import java.awt.image.BufferedImage

object Assets {
    private const val baseImagePath = "/resources/images"

    lateinit var llama: BufferedImage
        private set

    lateinit var llamaDead: BufferedImage
        private set

    lateinit var block1: BufferedImage
        private set

    lateinit var block2: BufferedImage
        private set

    lateinit var block3: BufferedImage
        private set

    lateinit var walkway: BufferedImage
        private set

    lateinit var destination: BufferedImage
        private set

    lateinit var pit: BufferedImage
        private set

    lateinit var teleporter: BufferedImage
        private set

    fun loadImages(screenManager: ScreenManager) {
        with(screenManager) {
            llama = loadImage("$baseImagePath/llama.png")
            llamaDead = loadImage("$baseImagePath/llama_dead.png")

            block1 = loadImage("$baseImagePath/block1.png")
            block2 = loadImage("$baseImagePath/block2.png")
            block3 = loadImage("$baseImagePath/block3.png")
            walkway = loadImage("$baseImagePath/grass_texture_pixel.png")
            destination = loadImage("$baseImagePath/hay.png")
            pit = loadImage("$baseImagePath/pit.png")
            teleporter = loadImage("$baseImagePath/teleporter.png")
        }
    }
}
