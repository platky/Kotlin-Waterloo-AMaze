package main.kotlin.amaze.puzzles

import main.kotlin.amaze.entity.VanishingWalkwayPattern
import main.kotlin.amaze.entity.VanishingWalkwayState.PIT
import main.kotlin.amaze.entity.VanishingWalkwayState.WALKWAY

val puzzles = listOf(
    PuzzleDefinition(
        """
        XXXXXXX
        XXXDXXX
        XXXOXXX
        XXXOXXX
        XXXOXXX
        XXXSXXX
        XXXXXXX
    """.trimIndent()
    ),
    PuzzleDefinition(
        """
        XXXXXXX
        XOOOODX
        XXXOXXX
        XXXSXXX
        XXXXXXX
    """.trimIndent()
    ),
    PuzzleDefinition(
        """
        XXXXXXX
        XVXDXVX
        XOXOXOX
        XOXOXOX
        XXXOXXX
        XSOOOSX
        XXXXXXX
    """.trimIndent()
    ),
    PuzzleDefinition(
        """
        XXXXXXX
        XXXDXXX
        XXXVXXX
        XXXVXXX
        XXXVXXX
        XXXVXXX
        XXXVXXX
        XXXSXXX
        XXXXXXX
    """.trimIndent(),
        listOf(
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT, PIT),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT, PIT, PIT),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT, PIT, PIT, PIT, PIT),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT, PIT, PIT, PIT, PIT, PIT),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, PIT, PIT, PIT, PIT, PIT, PIT, PIT, PIT)
        )
    ),
    PuzzleDefinition(
        """
        XXXXXXXXXXX
        XOOOOOOOOOX
        XOXOXOXOXOX
        XOXOXOXOXOX
        XOXVXDXVXOX
        XSXOOOOOXSX
        XXXXXXXXXXX
    """.trimIndent()
    ),
    PuzzleDefinition(
        """
        XXXXXXXXXXXXXX
        XDOXXXOOOOOOOX
        XXOOOOOXOOXXOX
        XXOOVOXXXOXOOX
        XXXXXXVXOOXXXX
        XXXOOOOOOOXOOX
        XVOOOOSXXOOOOX
        XXXXXXXXXXXXXX
    """.trimIndent(),
        listOf(
            VanishingWalkwayPattern(PIT, WALKWAY, WALKWAY),
            VanishingWalkwayPattern(WALKWAY, PIT, WALKWAY),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, PIT)
        )
    ),
    PuzzleDefinition(
        """
        XXXXXXXXXXXXXXXXXXXX
        XDOOOOOOOOPOOOOOOOOX
        XVXOXXOXPOXOXXXXOXOX
        XOXXOOOXXXOXOOOOXOOX
        XOOOXXOOOOOXOXXOXXOX
        XOXXXXXXXXOOXOOOOOOX
        XOOOOOOXOOXOOOXOXPOX
        XXXXXOXOOOXXXXXOXXOX
        XOOOXOOOXXXOXXXOOOXX
        XOXOXXXOOOOOOOXXXOOX
        XOOOOOOOXXSXXOOOOOXX
        XXXXXXXXXXXXXXXXXXXX
    """.trimIndent(),
        listOf(
            VanishingWalkwayPattern(PIT, WALKWAY, WALKWAY)
        )
    ),
    PuzzleDefinition(
        """
        XXXXXXXXXXXXXX
        XXOOOOOOOOOVFX
        XXDXXOVOOXOOOX
        XXXOXXXXXXXXXX
        XXXOXOOOOOOXFX
        XSOOOOXOXXOOOX
        XXXXXXXXXXXXXX
    """.trimIndent()
    ),
    PuzzleDefinition(
        """
        XXXXXXXXXXXXXX
        XXXXXVOOOOOFXX
        XOOOOXXXXXXXXX
        XXOGOOOOOOOXOX
        XOODXVVXXXFOOX
        XXXXXVVOOOOXOX
        XSSSOOOOOXXXGX
        XXXXXXXXXXXXXX
    """.trimIndent(),
        listOf(
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT, PIT),
            VanishingWalkwayPattern(WALKWAY, PIT),
            VanishingWalkwayPattern(PIT, WALKWAY),
            VanishingWalkwayPattern(WALKWAY, PIT),
            VanishingWalkwayPattern(PIT, WALKWAY)
        )
    ),
    PuzzleDefinition(
        """
        XXXXXXX
        XXXSXXX
        XXXVXXX
        XXXVOXX
        XXXVXXX
        XXOVXXX
        XXXVXXX
        XXXVOXX
        XXXVXXX
        XXXDXXX
        XXXXXXX
    """.trimIndent(),
        listOf(
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT, WALKWAY),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT, WALKWAY, WALKWAY),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, WALKWAY, PIT, WALKWAY, WALKWAY, WALKWAY),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, WALKWAY, PIT, WALKWAY, WALKWAY, WALKWAY, WALKWAY),
            VanishingWalkwayPattern(WALKWAY, WALKWAY, PIT, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY),
            VanishingWalkwayPattern(WALKWAY, PIT, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY, WALKWAY)
        )
    ),
    PuzzleDefinition(
        """
        XXXXXXXXXXXXXXXX
        XSSOOSOXOOSOOSSX
        XOOXOOOSOOOOXOOX
        XSXXXXXXXXXXXXOX
        XOOXOOOOOOOOXOOX
        XOOOOXXOOXXOOOSX
        XXOOOXFOGPXOOSXX
        XSSOOXDGPFXOOOOX
        XOXXXXXXXXXXXXOX
        XOOXOSOOOOSOXOOX
        XSSOOOOSOOOOOSSX
        XSSOOXOSOOXOOSSX
        XXXXXXXXXXXXXXXX
    """.trimIndent()
    )
)
