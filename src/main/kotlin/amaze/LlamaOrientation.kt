package main.kotlin.amaze

enum class LlamaOrientation(val radians: Double, val xDirection: Double, val yDirection: Double) {
    NORTH(0.0, 0.0, -1.0),
    WEST(-Math.PI / 2, -1.0, 0.0),
    SOUTH(-Math.PI, 0.0, 1.0),
    EAST(-3 * Math.PI / 2, 1.0, 0.0);

    fun turnLeft(): LlamaOrientation = when (this) {
        NORTH -> WEST
        EAST -> NORTH
        SOUTH -> EAST
        WEST -> SOUTH
    }

    fun turnRight(): LlamaOrientation = when (this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }
}
