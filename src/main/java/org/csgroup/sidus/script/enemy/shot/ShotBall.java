package org.csgroup.sidus.script.enemy.shot;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.enemy.Enemy;
import org.csgroup.sidus.util.Angle;
import org.csgroup.sidus.util.ShotColor;
import org.jetbrains.annotations.NotNull;

public class ShotBall extends EnemyShot {
    private static final float SPEED = 120.0f;
    private static final int SIZE = 15;
    private final float direction;
    private final Vector2<Float> startPosition;
    private final int drawLayer;
    private final TextureSet textureSet;
    private final int shotColor;
    private float configSpeed;

    private float time;

    public ShotBall(@NotNull final Enemy parent, @NotNull final ShotColor shotColor) {
        super(parent);
        direction = Angle.BACKWARD.getAngle();
        startPosition = parent.getPosition();
        drawLayer = parent.getDrawLayer() - 1;
        textureSet = parent.getTextureSet();
        this.shotColor = shotColor.ordinal();
    }

    public ShotBall(@NotNull final Enemy parent, final float direction, @NotNull final ShotColor shotColor) {
        super(parent);
        this.direction = direction;
        startPosition = parent.getPosition();
        drawLayer = parent.getDrawLayer() - 1;
        textureSet = parent.getTextureSet();
        this.shotColor = shotColor.ordinal();
    }

    public ShotBall(@NotNull final Enemy parent, final float direction, final Vector2<Float> startPosition, @NotNull final ShotColor shotColor) {
        super(parent);
        this.direction = direction;
        this.startPosition = startPosition;
        drawLayer = parent.getDrawLayer() - 1;
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
        time += getDelta();
        final float setSpeed = configSpeed == 0 ? SPEED : configSpeed;
        final float speedMod = time > 1 ? 1 : time;
        final float speed = setSpeed * (1 + (1 - (speedMod)));
        moveToAngle(direction, speed * getDelta());
    }

    @Override
    public void end() {

    }

    public float getConfigSpeed() {
        return configSpeed;
    }

    public void setConfigSpeed(final float configSpeed) {
        this.configSpeed = configSpeed;
    }
}
