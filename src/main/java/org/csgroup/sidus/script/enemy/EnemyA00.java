package org.csgroup.sidus.script.enemy;

import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.script.enemy.shot.ShotBall;
import org.csgroup.sidus.script.stage.Stage;
import org.csgroup.sidus.util.Angle;
import org.csgroup.sidus.util.ShotColor;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("InstanceVariableMayNotBeInitialized")
public class EnemyA00 extends Enemy {
    private static final int SCORE = 100;
    private static final float SPEED = 100.0f;
    private static final int SIZE = 75;
    private static final float SHOT_COOL_DOWN = 0.8f;
    private static final float MAX_HEALTH = 100;
    private EnemyExplosion explosion;
    private final Vector2<Float> position;
    private float direction;

    private float shotTime;

    public EnemyA00(@NotNull final Stage parent, final float x, final float y) {
        super(parent);
        position = new ImmutableVector2<>(x, y);
        direction = 0;
    }

    public EnemyA00(@NotNull final Stage parent, final Vector2<Float> position) {
        super(parent);
        this.position = position;
        direction = 0;
    }

    @Override
    public void init() {
        setSpriteMap(getTextureSet().getSpriteMaps("enemy_a"));
        setSize(SIZE, SIZE);
        setHealth(MAX_HEALTH);
        setHitSize(SIZE);
        setFrameSet(0);
        setFramePerSet(2);
        getCollisionSpace().addEnemy(this);
        explosion = new EnemyExplosion(this);
        setPosition(position);
        super.init();
    }

    @Override
    public void loop() {
        if (isAlive()) {
            moveToAngle(direction, SPEED * getDelta());
            if (shotTime >= SHOT_COOL_DOWN) {
                fire();
                shotTime = 0;
            }
            shotTime += getDelta();
        } else {
            if (!explosion.isAnimating()) {
                terminate();
            }
        }
    }

    private void fire() {
        addSubTask(new ShotBall(this, Angle.BACKWARD.getAngle(), ShotColor.ORANGE));
    }

    @Override
    public void end() {

    }

    @Override
    public void kill() {
        getGameState().addScore(SCORE);
        super.kill();
        setVisible(false);
        addSubTask(explosion);
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(final float direction) {
        this.direction = direction;
    }
}
