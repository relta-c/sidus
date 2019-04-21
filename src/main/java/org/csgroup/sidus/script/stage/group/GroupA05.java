package org.csgroup.sidus.script.stage.group;

import org.csgroup.sidus.script.common.Actor;
import org.csgroup.sidus.script.enemy.EnemyA04;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("MagicNumber")
public class GroupA05 extends Group {
    final EnemyA04 enemy00 = new EnemyA04(getStage(), Actor.getScreenCenter().getX(), 0);
    final EnemyA04 enemy01 = new EnemyA04(getStage(), Actor.getScreenCenter().getX() - 200, 0);
    final EnemyA04 enemy02 = new EnemyA04(getStage(), Actor.getScreenCenter().getX() + 200, 0);

    public GroupA05(@NotNull final Stage parent) {
        super(parent);
    }

    @Override
    public void init() {
        enemy00.setDirection(90);
        enemy01.setDirection(90);
        enemy02.setDirection(90);
    }

    @Override
    public void loop() {
        final float base = 0.0f;
        spawnAt(base, enemy00);
        spawnAt(base, enemy01);
        spawnAt(base, enemy02);
    }

    @Override
    public void end() {

    }
}
