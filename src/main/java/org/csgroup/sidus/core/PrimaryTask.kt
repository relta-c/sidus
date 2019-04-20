package org.csgroup.sidus.core

import net.chifumi.stellar.graphic.Drawable
import net.chifumi.stellar.graphic.TexturedDrawable
import net.chifumi.stellar.math.ImmutableVector2
import net.chifumi.stellar.text.Text
import net.chifumi.stellar.util.Timer
import org.csgroup.sidus.config.Setting

abstract class PrimaryTask(val subTaskList: MutableList<SubTask>) : BaseTask() {
    private val timer: Timer = Timer()
    private val drawList = HashMap<TexturedDrawable, Pair<Int, Boolean>>()
    private val drawSolidList = HashMap<Drawable, Pair<Int, Boolean>>()
    private val drawTextList = HashMap<Text, Int>();
    private var deltaTime: Float = 0.0f

    override fun preInit() {
        println("INFO : PRIMARY_TASK - start @ $javaClass -> subtasks :: $subTaskList")
        for (subTask in subTaskList) {
            subTask.parent = this;
        }
    }

    override fun startLoop() {
        if (!timer.isStarted) {
            timer.start()
        }
        deltaTime = timer.deltaTime
        enableMask()
    }

    fun <T : PrimaryTask> toNewStage(task: PrimaryTask) {
        newTask(task)
        terminateSelfOnly()
    }

    override fun endLoop() {
        drawLayered()
        disableMask()
        refresh()
    }

    override fun <T : SubTask> addSubTask(task: T) {
        this.subTaskList.add(task)
        newTask(task)
    }


    override fun draw(entity: TexturedDrawable, layer: Int, useMask: Boolean) {
        this.drawList[entity] = Pair(layer, useMask)
    }

    override fun draw(entity: Drawable, layer: Int, useMask: Boolean) {
        this.drawSolidList[entity] = Pair(layer, useMask)
    }

    override fun draw(text: Text, layer: Int) {
        this.drawTextList[text] = layer;
    }


    fun enableMask() {
        Game.renderer.enableMask(
                ImmutableVector2(Setting.gameOriginX, Setting.gameOriginY),
                ImmutableVector2(Setting.gameWidth, Setting.gameHeight))
    }

    fun disableMask() {
        Game.renderer.disableMask()
    }

    fun terminateSelfOnly() {
        super.terminate()
    }

    override fun terminate() {
        while (subTaskList.isNotEmpty()) {
            try {
                subTaskList.forEach { e ->
                    e.terminate()
                    subTaskList.remove(e)
                }
            } catch (e: ConcurrentModificationException) {

            }
        }
        super.terminate()
    }

    private fun drawLayered() {
        val sortedDrawList = drawList.toList().stream().sorted { o1, o2 -> o1.second.first.compareTo(o2.second.first) }
        sortedDrawList.forEach { (e, v) ->
            if (v.second) {
                Game.renderer.draw(e)
            } else {
                disableMask()
                Game.renderer.draw(e)
                enableMask()
            }
        }
        val sortedSolidDrawList = drawSolidList.toList().stream().sorted { o1, o2 -> o1.second.first.compareTo(o2.second.first) }
        sortedSolidDrawList.forEach { (e, v) ->
            if (v.second) {
                Game.renderer.draw(e)
            } else {
                disableMask()
                Game.renderer.draw(e)
                enableMask()
            }
        }
        val sortedTextList = drawTextList.toList().stream().sorted { o1, o2 -> o1.second.compareTo(o2.second) }
        sortedTextList.forEach { (e, _) ->
            disableMask()
            Game.renderer.draw(e)
            enableMask()
        }
        drawTextList.clear()
        drawList.clear()
        drawSolidList.clear()
    }

    private fun refresh() {
        Game.display.update()
    }

    override fun getDelta(): Float {
        return deltaTime
    }
}
