package org.csgroup.sidus.script.enemy;

import org.csgroup.sidus.script.common.AutoCleanActor;
import org.jetbrains.annotations.NotNull;

public class EnemyExplosion extends AutoCleanActor {
    private static final float SIZE = 100.0f;
    private static final float ANIMATION_DELAY = 0.025f;

    private final Enemy enemy;
    private final EnemyExplosionRadius enemyExplosionRadius;

    protected EnemyExplosion(@NotNull final Enemy parent) {
        super(parent);
        enemy = parent;
        enemyExplosionRadius = new EnemyExplosionRadius(this, parent.getTextureSet());
    }

    @Override
    public void init() {
        setSpriteMap(enemy.getTextureSet().getSpriteMaps("enemy_explosion"));
        setSize(SIZE, SIZE);
        setPosition(enemy.getPosition());
        setFramePerSet(8);
        setFrameSet(0);
        setLoop(false);
        setAnimationDelay(ANIMATION_DELAY);
        super.init();
        addSubTask(enemyExplosionRadius);
    }

    @Override
    public void loop() {
        if (!isAnimating() && enemyExplosionRadius.isEnd()) {
            terminate();
        }
    }

    @Override
    public void end() {

    }
}
