package main.kotlin.amaze.entity

import main.kotlin.amaze.Llama
import main.kotlin.amaze.LlamaState
import main.kotlin.amaze.core.assets.Images
import main.kotlin.amaze.core.assets.draw
import main.kotlin.amaze.entity.VanishingWalkwayState.*
import java.awt.Graphics2D

enum class VanishingWalkwayState {
    WALKWAY,
    PIT
}

class VanishingWalkwayPattern(
    vararg state: VanishingWalkwayState
) {
    val states = listOf(*state)
}

class VanishingWalkway : ActiveEntity {
    private lateinit var states: List<VanishingWalkwayState>
    private var stateIndex = 0

    override fun draw(graphics: Graphics2D, width: Int, height: Int) {
        Images.walkway.draw(graphics, width, height)
        if (states[stateIndex] == PIT)
            Images.pit.draw(graphics, width, height)
    }

    override fun interact(llama: Llama) {
        if (states[stateIndex] == PIT)
            llama.transitionToState(LlamaState.ENTERING_PIT)
    }

    override fun transition() {
        stateIndex++
        if (stateIndex == states.size)
            stateIndex = 0
    }

    fun setStatePattern(pattern: VanishingWalkwayPattern = VanishingWalkwayPattern(WALKWAY, PIT)) {
        states = pattern.states
    }
}