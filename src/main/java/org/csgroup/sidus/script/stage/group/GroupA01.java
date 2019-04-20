package org.csgroup.sidus.script.stage.group;

import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.script.enemy.EnemyA01;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("MagicNumber")
public class GroupA01 extends Group {
    final EnemyA01 enemy00 = new EnemyA01(getStage(), Setting.gameMaxX, 75);
    final EnemyA01 enemy01 = new EnemyA01(getStage(), Setting.gameMaxX + 50, 50);
    final EnemyA01 enemy02 = new EnemyA01(getStage(), Setting.gameMaxX + 100, 25);
    final EnemyA01 enemy03 = new EnemyA01(getStage(), Setting.gameMaxX + 150, 0);
    final EnemyA01 enemy04 = new EnemyA01(getStage(), Setting.gameMaxX + 200, -25);
    final EnemyA01 enemy05 = new EnemyA01(getStage(), Setting.gameMaxX + 250, -50);

    public GroupA01(@NotNull final Stage parent) {
        super(parent);
    }

    @Override
    public void init() {
        enemy00.setDirection(145);
        enemy01.setDirection(145);
        enemy02.setDirection(145);
        enemy03.setDirection(145);
        enemy04.setDirection(145);
        enemy05.setDirection(145);
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
