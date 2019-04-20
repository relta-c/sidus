package org.csgroup.sidus.script.enemy;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.script.common.*;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

public abstract class Enemy extends AutoCleanActor {
    private static final float BOMB_INVINCIBLE_TIME = 1.5f;
    private static final int ENEMY_LAYER = 10;
    private final CollisionSpace collisionSpace;
    private final TextureSet textureSet;
    private final GameState gameState;
    private float health;
    private boolean alive;

    private float bombedTime;
    private boolean enter;

    protected Enemy(@NotNull final Stage parent) {
        super(parent);
        alive = true;
        collisionSpace = parent.getCollisionSpace();
        textureSet = parent.getTextureSet();
        gameState = parent.getGameState();
        setDrawLayer(ENEMY_LAYER);
    }

    @Override
    protected void endLoop() {
        if (enter) {
            if (!isInBoundary()) {
                terminate();
            }
        } else {
            if (isInBoundary()) {
                enter = true;
            }
        }

        if (health <= 0 && alive) {
            kill();
        }
        super.endLoop();
        bombedTime += getDelta();
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(final float health) {
        this.health = health;
    }

    public void reduceHealth(final float amount) {
        health -= amount;
    }

    public void bombed(final float rawDamage) {
        if (bombedTime > BOMB_INVINCIBLE_TIME) {
            health -= rawDamage;
            bombedTime = 0;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
        collisionSpace.removeEnemy(this);
    }

    public CollisionSpace getCollisionSpace() {
        return collisionSpace;
    }

    public TextureSet getTextureSet() {
        return textureSet;
    }

    public GameState getGameState() {
        return gameState;
    }

    private boolean isInBoundary() {
        boolean result = true;
        final Vector2<Float> position = getOrigin();
        if (position.getX() < (Setting.gameOriginX - getSize().getX())) {
            result = false;
        } else if (position.getX() > (Setting.gameMaxX + getSize().getX())) {
            result = false;
        } else if (position.getY() < (Setting.gameOriginY - getSize().getY())) {
            result = false;
        } else if (position.getY() > (Setting.gameMaxY + getSize().getY())) {
            result = false;
        }
        return result;
    }
}
