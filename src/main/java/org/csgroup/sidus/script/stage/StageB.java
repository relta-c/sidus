package org.csgroup.sidus.script.stage;

import org.csgroup.sidus.core.PrimaryTask;
import org.csgroup.sidus.core.SubTask;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StageB extends PrimaryTask {
    public StageB(@NotNull final List<SubTask> subTaskList) {
        super(subTaskList);
    }

    @Override
    public void init() {
        terminate();
    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {

    }
}
