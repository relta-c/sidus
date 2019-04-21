package org.csgroup.sidus.core

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.csgroup.sidus.script.InitializeTask


class Launcher {
    fun start() = runBlocking {
        Game.coroutineScope = this
        Game.coroutineScope.launch { InitializeTask(mutableListOf<SubTask>()).start() }
    }
}
