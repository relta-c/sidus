package org.csgroup.sidus.script.stage;

import org.csgroup.sidus.core.PrimaryTask;
import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.script.common.CollisionSpace;
import org.csgroup.sidus.script.common.GameState;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.stage.group.Group;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Stage extends PrimaryTask {
    private final TextureSet textureSet;
    private final List<Integer> spawnList;

    private float time;

    protected Stage(@NotNull final List<SubTask> subTaskList, final TextureSet textureSet) {
        super(subTaskList);
        this.textureSet = textureSet;
        spawnList = new ArrayList<>();
    }

    @Override
    protected void startLoop() {
        super.startLoop();
        time += getDelta();
    }

    public abstract CollisionSpace getCollisionSpace();

    public abstract GameState getGameState();

    public TextureSet getTextureSet() {
        return textureSet;
    }

    public float getTime() {
        return time;
    }

    protected void spawnAt(final float time, final Group enemy) {
        if (getTime() > time && !spawnList.contains(System.identityHashCode(enemy))) {
            spawnList.add(System.identityHashCode(enemy));
            addSubTask(enemy);
        }
    }
}
