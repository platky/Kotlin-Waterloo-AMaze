package main.kotlin.amaze.puzzles

import main.kotlin.amaze.entity.VanishingWalkwayPattern


data class PuzzleDefinition(
    val tileMap: String,
    val vanishingWalkwayPatterns: List<VanishingWalkwayPattern> = emptyList()
)