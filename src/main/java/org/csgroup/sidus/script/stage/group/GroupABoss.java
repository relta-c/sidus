package org.csgroup.sidus.script.stage.group;

import org.csgroup.sidus.script.enemy.boss.Boss;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class GroupABoss extends Group {
    final Boss boss = new Boss(getStage());

    public GroupABoss(@NotNull final Stage parent) {
        super(parent);
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        final float base = 0.0f;
        spawnAt(base, boss);
    }

    @Override
    public void end() {

    }
}
