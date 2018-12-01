package main.kotlin.amaze.core

import java.awt.image.BufferedImage


object Assets {
    lateinit var llama: BufferedImage
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

    fun loadImages(screenManager: ScreenManager) {
        with(screenManager) {
            llama = loadImage("/images/llama.png")

            block1 = loadImage("/images/block1.png")
            block2 = loadImage("/images/block2.png")
            block3 = loadImage("/images/block3.png")
            walkway = loadImage("/images/grass_texture_pixel.png")
            destination = loadImage("/images/hay.png")
            pit = loadImage("/images/pit.png")
        }
    }
}