package org.csgroup.sidus.core

import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import net.chifumi.stellar.graphic.Drawable
import net.chifumi.stellar.graphic.SpriteMap
import net.chifumi.stellar.graphic.TexturedDrawable
import net.chifumi.stellar.input.Key
import net.chifumi.stellar.input.KeyState
import net.chifumi.stellar.text.Text
import net.chifumi.stellar.texture.Texture
import net.chifumi.stellar.texture.TextureLoader
import org.csgroup.sidus.entity.AnimateEntity
import org.csgroup.sidus.entity.Entity
import org.csgroup.sidus.config.KeyMap

abstract class BaseTask {
    private var running = false
    private val textureList: MutableList<Texture> = ArrayList()
    private val subTaskList: MutableList<SubTask> = mutableListOf()

    suspend fun start() {
        running = true
        preInit()
        init()
        while (running) {
            startLoop()
            loop()
            if (Game.display.isTerminating) {
                running = false
            }
            endLoop()
            yield()
        }
        end()
        postEnd()
    }

    open fun preInit() {}

    fun loadTexture(path: String): Texture {
        val newTexture = TextureLoader().load(path)!!
        textureList.add(newTexture)
        println("INFO : TEXTURE - Add by $this :: \"$path\" -> [${newTexture.hashCode()}]")
        return newTexture
    }

    fun loadSpriteMap(texture: Texture, path: String): SpriteMap {
        return SpriteMap(texture, path)
    }

    fun addEntity(texture: Texture) = Entity(texture)

    fun addEntity(spriteMap: SpriteMap, name: CharSequence) = Entity(spriteMap, name)

    fun addAnimateEntity(spriteMap: SpriteMap, name: CharSequence) = AnimateEntity(spriteMap, name)

    fun getAbsoluteScreenWidth() = Game.display.resolution.x!!

    fun getAbsoluteScreenHeight() = Game.display.resolution.y!!

    fun getKey(): KeyMap = KeyMap

    fun getKeyPressed(key: Key): Boolean {
        return Game.keyboard.getKey(key) == KeyState.PRESS
    }

    open fun terminate() {
        running = false
        println("INFO : TASK - Terminate @ " + javaClass.name)
    }

    @JvmOverloads
    open fun draw(entity: TexturedDrawable, layer: Int = 0, useMask: Boolean = true) {

    }

    @JvmOverloads
    open fun draw(entity: Drawable, layer: Int = 0, useMask: Boolean = true) {

    }

    @JvmOverloads
    open fun draw(text: Text, layer: Int = 0) {

    }

    protected fun <T : BaseTask> newTask(task: T) {
        Game.coroutineScope.launch {
            task.start()
        }
    }

    protected open fun postEnd() {
        textureList.forEach { e ->
            println("INFO : TEXTURE - Delete [" + e.hashCode() + "] @ " + javaClass)
            e.delete()
        }
    }

    open fun <T : SubTask> addSubTask(task: T) {
        this.subTaskList.add(task)
        newTask(task)
    }

    open fun <T : SubTask> removeSubTask(task: T) {
        this.subTaskList.remove(task)
    }

    open fun <T : SubTask> removeAndTerminateSubTask(task: T) {
        this.subTaskList.remove(task)
        task.terminate()
    }

    fun isRunning(): Boolean {
        return running;
    }

    abstract fun getDelta(): Float

    protected open fun startLoop() {}

    protected open fun endLoop() {}

    abstract fun init()

    abstract fun loop()

    abstract fun end()
}
