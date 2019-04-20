package org.csgroup.sidus.core

import net.chifumi.stellar.graphic.Drawable
import net.chifumi.stellar.graphic.TexturedDrawable
import net.chifumi.stellar.text.Text

abstract class SubTask(open var parent: BaseTask) : BaseTask() {
    override fun getDelta(): Float {
        return parent.getDelta()
    }

    override fun preInit() {
        super.preInit()
        println("INFO : SUBTASK - Start by $parent @ $this")
    }

    override fun draw(entity: TexturedDrawable, layer: Int, useMask: Boolean) {
        parent.draw(entity, layer, useMask)
    }

    override fun draw(entity: Drawable, layer: Int, useMask: Boolean) {
        parent.draw(entity, layer, useMask)
    }

    override fun draw(text: Text, layer: Int) {
        parent.draw(text, layer)
    }
}
