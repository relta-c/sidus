package org.csgroup.sidus.script.stage.group;

import org.csgroup.sidus.script.enemy.EnemyA02;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("MagicNumber")
public class GroupA00 extends Group {
    final EnemyA02 enemy00 = new EnemyA02(getStage(), 0, 75);
    final EnemyA02 enemy01 = new EnemyA02(getStage(), -50, 50);
    final EnemyA02 enemy02 = new EnemyA02(getStage(), -100, 25);
    final EnemyA02 enemy03 = new EnemyA02(getStage(), -150, 0);
    final EnemyA02 enemy04 = new EnemyA02(getStage(), -200, -25);
    final EnemyA02 enemy05 = new EnemyA02(getStage(), -250, -50);

    public GroupA00(@NotNull final Stage parent) {
        super(parent);
    }

    @Override
    public void init() {
        enemy00.setDirection(35);
        enemy01.setDirection(35);
        enemy02.setDirection(35);
        enemy03.setDirection(35);
        enemy04.setDirection(35);
        enemy05.setDirection(35);
    }

    @Override
    public void loop() {
        final float base = 0.0f;
        final float dif = 0.5f;
        spawnAt(base, enemy00);
        spawnAt(base + (dif * 1), enemy01);
        spawnAt(base + (dif * 2), enemy02);
        spawnAt(base + (dif * 3), enemy03);
        spawnAt(base + (dif * 4), enemy04);
        spawnAt(base + (dif * 5), enemy05);
    }

    @Override
    public void end() {

    }
}
