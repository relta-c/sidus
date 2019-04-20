package org.csgroup.sidus.script.enemy.shot;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.script.common.AutoCleanActor;
import org.csgroup.sidus.script.common.CollisionSpace;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.enemy.Enemy;
import org.csgroup.sidus.util.Angle;
import org.csgroup.sidus.util.ShotColor;
import org.jetbrains.annotations.NotNull;

public class ShotBall extends AutoCleanActor {
    private static final float SPEED = 300.0f;
    private static final int SIZE = 15;
    private final float direction;
    private final Vector2<Float> startPosition;
    private final int drawLayer;
    private final CollisionSpace collisionSpace;
    private final TextureSet textureSet;
    private final int shotColor;

    public ShotBall(@NotNull final Enemy parent, @NotNull final ShotColor shotColor) {
        super(parent);
        direction = Angle.BACKWARD.getAngle();
        startPosition = parent.getPosition();
        drawLayer = parent.getDrawLayer() - 1;
        collisionSpace = parent.getCollisionSpace();
        textureSet = parent.getTextureSet();
        this.shotColor = shotColor.ordinal();
    }

    public ShotBall(@NotNull final Enemy parent, final float direction, @NotNull final ShotColor shotColor) {
        super(parent);
        this.direction = direction;
        startPosition = parent.getPosition();
        drawLayer = parent.getDrawLayer() - 1;
        collisionSpace = parent.getCollisionSpace();
        textureSet = parent.getTextureSet();
        this.shotColor = shotColor.ordinal();
    }

    @Override
    public void init() {
        setSpriteMap(textureSet.getSpriteMaps("shot_ball"));
        setDrawLayer(drawLayer);
        setSize(SIZE, SIZE);
        setHitSize(SIZE);
        setOrigin(startPosition);
        setFramePerSet(1);
        setFrameSet(shotColor);
        super.init();
    }

    @Override
    public void loop() {
        checkBoundary();
        moveToAngle(direction, SPEED * getDelta());
        if (collisionSpace.isCollideWithPlayer(this)) {
            collisionSpace.killPlayer();
        }
    }

    @Override
    public void end() {

    }

    private void checkBoundary() {
        final Vector2<Float> position = getOrigin();
        if (position.getX() < (Setting.gameOriginX - getSize().getX())) {
            terminate();
        } else if (position.getX() > (Setting.gameMaxX + getSize().getX())) {
            terminate();
        } else if (position.getY() < (Setting.gameOriginY - getSize().getY())) {
            terminate();
        } else if (position.getY() > (Setting.gameMaxY + getSize().getY())) {
            terminate();
        }
    }
}
