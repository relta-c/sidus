package org.csgroup.sidus.config

object Setting {
    // ratio const
    private const val windowWidthToGameWidth = 1.6623376623377
    private const val gameWidthToGameHeight = 1.1688311688312
    private const val windowWidthToGameX = 19.692307692308
    private const val gameXToGameY = 2.1666666666667

    // adjustable
    const val windowWidth = 1280
    const val windowHeight = 960
    const val windowTitle = "Avoidance"
    const val msaaLevel = 0

    // game window
    const val gameWidth = (windowWidth / windowWidthToGameWidth).toInt()
    const val gameHeight = ((windowWidth / windowWidthToGameWidth) * gameWidthToGameHeight).toInt()
    const val gameOriginX = (windowWidth / windowWidthToGameX).toInt()
    const val gameOriginY = ((windowWidth / windowWidthToGameX) / gameXToGameY).toInt()

    // normalization
    const val gameMaxX = 770
    const val gameMaxY = 900
    private const val absoluteToNormalizedX = (gameOriginX + gameWidth) / gameMaxX.toFloat()
    private const val absoluteToNormalizedY = (gameOriginY + gameHeight) / gameMaxY.toFloat()
    const val normalizedWindowWidth = windowWidth / absoluteToNormalizedX
    const val normalizedWindowHeight = windowHeight / absoluteToNormalizedY
}
