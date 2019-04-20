package org.csgroup.sidus.script.player;

import net.chifumi.stellar.graphic.TexturedDrawable;
import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;
import net.chifumi.stellar.math.Vector3;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.entity.Entity;
import org.csgroup.sidus.script.common.Actor;
import org.csgroup.sidus.script.common.CollisionSpace;
import org.csgroup.sidus.script.common.GameState;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

public abstract class Player extends Actor {
    public static final float INVINCIBLE_TIMEOUT = 3.0f;
    private static final float BLINK_TRANSPARENCY = 20.0f;
    private static final float BLINK_SPEED = 0.15f;
    private static final float SPAWN_DURATION = 1.0f;
    private static final float BOMB_ALIVE_TIME = 4.0f;
    public static final int SPAWN_MOVE_SPEED = 120;
    private final Stage stage;
    private final Entity hitbox;
    private final GameState gameState;
    private float speed;
    private float focusSpeed;
    private float sidePadding;

    private float invincibleTimeout;
    private float invincibleTime;
    private float spawnTime;
    private float blinkTime;
    private float bombTime;

    private boolean spawning;
    private boolean invincible;

    protected Player(@NotNull final Stage parent) {
        super(parent);
        stage = parent;
        gameState = parent.getGameState();
        hitbox = addEntity(stage.getTextureSet().getTexture("player_hitbox"));
        hitbox.setVisible(false);
        bombTime = BOMB_ALIVE_TIME;
    }

    abstract void drawHitbox();

    @Override
    protected void startLoop() {
        bombTime += getDelta();
        checkSpawn();
        checkInvisible();
        hitbox.setPosition(getPosition());
        handleInput();
        drawHitbox();
    }

    private void checkInvisible() {
        if (invincible) {
            invincibleTime += getDelta();
            blink();
            if (invincibleTime >= invincibleTimeout) {
                invincible = false;
                invincibleTime = 0;
                setTransparency(100);
            }
        }
    }

    private void checkSpawn() {
        if (spawning && getGameState().getPlayerLife() > 0) {
            setVisible(true);
            spawnTime += getDelta();
            final float startY = Setting.gameMaxY + (getSize().getY() / 2);
            setPosition(getScreenCenter().getX(), startY - (SPAWN_MOVE_SPEED * spawnTime));
            if (spawnTime >= SPAWN_DURATION) {
                getGameState().decreasePlayerLife();
                spawning = false;
                spawnTime = 0;
            }
        }
    }

    private void handleInput() {
        if (!spawning) {
            final float currentSpeed;
            final boolean focused;
            if (getKeyPressed(getKey().getKeyFocus())) {
                focused = true;
                currentSpeed = focusSpeed;
                hitbox.setVisible(true);
            } else {
                focused = false;
                currentSpeed = speed;
                hitbox.setVisible(false);
            }

            if (getKeyPressed(getKey().getKeyFire())) {
                fire(focused);
            }
            if (getKeyPressed(getKey().getKeyCancel())) {
                if (bombTime > BOMB_ALIVE_TIME && gameState.getBombCount() > 0) {
                    bomb();
                    setInvincible(true);
                    invincibleTimeout = INVINCIBLE_TIMEOUT;
                    bombTime = 0;
                }
            }
            if (getKeyPressed(getKey().getKeyUp())) {
                moveUp(currentSpeed);
            }
            if (getKeyPressed(getKey().getKeyDown())) {
                moveDown(currentSpeed);
            }
            if (getKeyPressed(getKey().getKeyLeft())) {
                moveLeft(currentSpeed);
            }
            if (getKeyPressed(getKey().getKeyRight())) {
                moveRight(currentSpeed);
            }

        }
    }

    private void moveUp(final float speed) {
        if ((getOrigin().getY() - (speed * getDelta())) <= Setting.gameOriginY) {
            setOrigin(getOrigin().getX(), Setting.gameOriginY);
        } else {
            moveWithOffset(0, -speed * getDelta());
        }
    }

    private void moveDown(final float speed) {
        if ((getOrigin().getY() + (speed * getDelta())) >= Setting.gameMaxY - getSize().getY()) {
            setOrigin(getOrigin().getX(), Setting.gameMaxY - getSize().getY());
        } else {
            moveWithOffset(0, speed * getDelta());
        }
    }

    private void moveLeft(final float speed) {
        if ((getOrigin().getX() - (speed * getDelta())) <= Setting.gameOriginX + sidePadding) {
            setOrigin(Setting.gameOriginX + sidePadding, getOrigin().getY());
        } else {
            moveWithOffset(-speed * getDelta(), 0);
        }
    }

    private void moveRight(final float speed) {
        if ((getOrigin().getX() + (speed * getDelta())) >= ((Setting.gameMaxX - getSize().getX()) - sidePadding)) {
            setOrigin(Setting.gameMaxX - getSize().getX() - sidePadding, getOrigin().getY());
        } else {
            moveWithOffset(speed * getDelta(), 0);
        }
    }

    protected abstract void fire(final boolean focused);

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(final float speed) {
        this.speed = speed;
    }

    public float getFocusSpeed() {
        return focusSpeed;
    }

    public void setFocusSpeed(final float focusSpeed) {
        this.focusSpeed = focusSpeed;
    }

    public float getSidePadding() {
        return sidePadding;
    }

    public void setSidePadding(final float sidePadding) {
        this.sidePadding = sidePadding;
    }

    public void setHitboxColor(final float red, final float green, final float blue) {
        hitbox.setColor(red, green, blue);
    }

    public TexturedDrawable getHitbox() {
        return hitbox;
    }

    public void setHitboxColor(final Vector3<Float> color) {
        hitbox.setColor(color);
    }

    public void setHitboxSize(final float size) {
        hitbox.setSize(size, size);
    }

    protected Vector2<Float> getStartPosition() {
        return new ImmutableVector2<>((float) Setting.gameMaxX / 2, (float) Setting.gameMaxY - (getSize().getY() / 2));
    }

    @NotNull
    @Override
    public Stage getParent() {
        return stage;
    }

    public CollisionSpace getEntities() {
        return stage.getCollisionSpace();
    }

    public TextureSet getTextureSet() {
        return stage.getTextureSet();
    }

    public boolean isSpawning() {
        return spawning;
    }

    public void setSpawning(final boolean spawning) {
        this.spawning = spawning;
    }

    public void kill() {
        if (!isInvincible()) {
            addSubTask(new PlayerDeath(this, getPosition()));
            setVisible(false);
            setInvincible(true);
            invincibleTimeout = INVINCIBLE_TIMEOUT;
            spawning = true;
        }
    }

    private void blink() {
        if (isInvincible()) {
            if (blinkTime > BLINK_SPEED) {
                blinkTime = 0;
                if (getTransparency() < 100) {
                    setTransparency(100);
                } else {
                    setTransparency(BLINK_TRANSPARENCY);
                }
            }
            blinkTime += getDelta();
        } else {
            setTransparency(100);
        }
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(final boolean invincible) {
        this.invincible = invincible;
    }

    public GameState getGameState() {
        return gameState;
    }

    protected abstract void bomb();
}
