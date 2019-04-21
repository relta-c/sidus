package org.csgroup.sidus.script.enemy.shot;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.script.common.CollisionSpace;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.enemy.Enemy;
import org.csgroup.sidus.util.ShotColor;
import org.jetbrains.annotations.NotNull;

public class ShotBallExplode extends EnemyShot {
    private static final float SPEED = 170.0f;
    private static final int SIZE = 100;
    private final Vector2<Float> startPosition;
    private final Vector2<Float> destPosition;
    private final int drawLayer;
    private final CollisionSpace collisionSpace;
    private final TextureSet textureSet;

    private float time;
    private final Enemy parent;

    public ShotBallExplode(@NotNull final Enemy parent) {
        super(parent);
        this.parent = parent;
        startPosition = parent.getPosition();
        destPosition = parent.getCollisionSpace().getPlayerPosition();
        drawLayer = parent.getDrawLayer() + 2;
        collisionSpace = parent.getCollisionSpace();
        textureSet = parent.getTextureSet();
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void init() {
        setTexture(textureSet.getTexture("wave"));
        setDrawLayer(drawLayer);
        setSize(SIZE, SIZE);
        setHitSize(SIZE);
        setPosition(startPosition);
        setColor(160.0f, 200.0f, 140.0f);
        super.init();
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void loop() {
        time += getDelta();
        moveToward(startPosition, destPosition, SPEED * getDelta());
        if (collisionSpace.isCollideWithPlayer(this)) {
            collisionSpace.killPlayer();
        }
        if (time > 1.5f) {
            setDisarm(true);
            for (int i = 0; i < 12; i++) {
                final EnemyShot shot = new ShotBall(parent, 45 + i * (360.0f / 48), getPosition(), ShotColor.GREEN);
                addSubTask(shot);
            }
        }
    }

    @Override
    public void end() {

    }

}
