package org.csgroup.sidus.script;

import org.csgroup.sidus.core.PrimaryTask;
import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.stage.MainMenu;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("InstanceVariableMayNotBeInitialized")
public class InitializeTask extends PrimaryTask {
    private TextureSet textureSet;

    public InitializeTask(@NotNull final List<SubTask> subTaskList) {
        super(subTaskList);
    }

    @Override
    public void init() {
        textureSet = new TextureSet(this);
        addSubTask(textureSet);
    }

    @Override
    public void loop() {
        terminateSelfOnly();
    }

    @Override
    public void end() {
        toNewStage(new MainMenu(getSubTaskList(), textureSet));
    }
}
