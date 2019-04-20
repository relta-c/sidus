package org.csgroup.sidus.script.stage.group;

import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.script.enemy.Enemy;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Group extends SubTask {
    private final Stage stage;
    private final List<Integer> spawnList;

    private float time;

    protected Group(@NotNull final Stage parent) {
        super(parent);
        stage = parent;
        spawnList = new ArrayList<>();
    }

    public Stage getStage() {
        return stage;
    }

    @Override
    protected void endLoop() {
        time += getDelta();
        super.endLoop();
    }

    protected float getTime() {
        return time;
    }

    protected void spawnAt(final float time, final Enemy enemy) {
        if (getTime() > time && !spawnList.contains(System.identityHashCode(enemy))) {
            spawnList.add(System.identityHashCode(enemy));
            stage.addSubTask(enemy);
        }
    }

    protected List<Integer> getSpawnList() {
        return Collections.unmodifiableList(spawnList);
    }
}
