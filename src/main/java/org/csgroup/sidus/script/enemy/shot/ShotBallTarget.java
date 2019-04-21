package org.csgroup.sidus.script.enemy.shot;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.script.common.CollisionSpace;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.enemy.Enemy;
import org.jetbrains.annotations.NotNull;

public class ShotBallTarget extends EnemyShot {
    private static final float SPEED = 170.0f;
    private static final int SIZE = 175;
    private final Vector2<Float> startPosition;
    private final Vector2<Float> destPosition;
    private final int drawLayer;
    private final CollisionSpace collisionSpace;
    private final TextureSet textureSet;


    public ShotBallTarget(@NotNull final Enemy parent) {
        super(parent);
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
        setColor(200.0f, 0, 0);
        super.init();
    }

    @Override
    public void loop() {
        moveToward(startPosition, destPosition, SPEED * getDelta());
        if (collisionSpace.isCollideWithPlayer(this)) {
            collisionSpace.killPlayer();
        }
    }

    @Override
    public void end() {

    }

}
