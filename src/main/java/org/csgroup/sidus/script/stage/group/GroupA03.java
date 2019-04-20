package org.csgroup.sidus.script.stage.group;

import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.script.enemy.EnemyA03;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("MagicNumber")
public class GroupA03 extends Group {
    final EnemyA03 enemy00 = new EnemyA03(getStage(), Setting.gameMaxX, 50);
    final EnemyA03 enemy01 = new EnemyA03(getStage(), Setting.gameMaxX + 50, 50);
    final EnemyA03 enemy02 = new EnemyA03(getStage(), Setting.gameMaxX + 100, 50);
    final EnemyA03 enemy03 = new EnemyA03(getStage(), Setting.gameMaxX + 150, 50);
    final EnemyA03 enemy04 = new EnemyA03(getStage(), Setting.gameMaxX + 200, 50);
    final EnemyA03 enemy05 = new EnemyA03(getStage(), Setting.gameMaxX + 250, 50);

    public GroupA03(@NotNull final Stage parent) {
        super(parent);
    }

    @Override
    public void init() {
        enemy00.setDirection(180);
        enemy01.setDirection(180);
        enemy02.setDirection(180);
        enemy03.setDirection(180);
        enemy04.setDirection(180);
        enemy05.setDirection(180);
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
