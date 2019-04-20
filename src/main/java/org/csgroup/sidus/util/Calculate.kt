package org.csgroup.sidus.util

import net.chifumi.stellar.math.ImmutableVector2
import net.chifumi.stellar.math.Vector2
import org.csgroup.sidus.config.Setting as Conf

object Calculate {
    fun normalizedToAbsolutePosition(x: Float, y: Float): Vector2<Float> {
        val absoluteX = convertOffset(x, Conf.normalizedWindowWidth, Conf.windowWidth.toFloat())
        val absoluteY = convertOffset(y, Conf.normalizedWindowHeight, Conf.windowHeight.toFloat())
        return ImmutableVector2(absoluteX, absoluteY)
    }

    fun absoluteToNormalizePosition(x: Float, y: Float): Vector2<Float> {
        val normalizedX = convertOffset(x, Conf.windowWidth.toFloat(), Conf.normalizedWindowWidth)
        val normalizedY = convertOffset(y, Conf.windowHeight.toFloat(), Conf.normalizedWindowHeight)
        return ImmutableVector2(normalizedX, normalizedY)
    }

    fun absoluteToNormalizePosition(vector2: Vector2<Float>): Vector2<Float> {
        return absoluteToNormalizePosition(vector2.x, vector2.y)
    }

    private fun convertOffset(offset: Float, maxOffset: Float, newMaxOffset: Float): Float {
        return ((offset / maxOffset) * newMaxOffset)
    }
}
