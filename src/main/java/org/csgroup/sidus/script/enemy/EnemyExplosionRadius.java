package org.csgroup.sidus.script.enemy;

import org.csgroup.sidus.script.common.AutoCleanActor;
import org.csgroup.sidus.script.common.TextureSet;
import org.jetbrains.annotations.NotNull;

class EnemyExplosionRadius extends AutoCleanActor {
    private static final float INFLATION_SPEED = 300.0f;
    public static final int DECAY_SPEED = 300;

    private final EnemyExplosion enemyExplosion;
    private final TextureSet textureSet;

    private boolean end;

    EnemyExplosionRadius(@NotNull final EnemyExplosion parent, final TextureSet textureSet) {
        super(parent);
        enemyExplosion = parent;
        this.textureSet = textureSet;
        end = false;
    }

    @Override
    public void init() {
        setTexture(textureSet.getTexture("wave"));
        setSize(0, 0);
        setPosition(enemyExplosion.getPosition());
        super.init();
    }

    @Override
    public void loop() {
        setSize(getSize().getX() + INFLATION_SPEED * getDelta(), getSize().getY() + INFLATION_SPEED * getDelta());
        setPosition(enemyExplosion.getPosition());
        setTransparency(getTransparency() - DECAY_SPEED * getDelta());
        if (getTransparency() <= 0) {
            end = true;
            terminate();
        }
    }

    @Override
    public void end() {

    }

    public boolean isEnd() {
        return end;
    }
}
