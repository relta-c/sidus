package org.csgroup.sidus.core

import kotlinx.coroutines.CoroutineScope
import net.chifumi.stellar.graphic.Display
import net.chifumi.stellar.graphic.Renderer
import net.chifumi.stellar.input.Keyboard
import org.csgroup.sidus.config.Setting


object Game {
    val display = Display(Setting.windowWidth, Setting.windowHeight, Setting.windowTitle, Setting.msaaLevel)
    val renderer = Renderer(display)
    val keyboard = Keyboard(display)
    lateinit var coroutineScope: CoroutineScope
}
