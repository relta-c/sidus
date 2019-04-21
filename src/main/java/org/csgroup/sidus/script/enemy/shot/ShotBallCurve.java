package org.csgroup.sidus.script.enemy.shot;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.enemy.Enemy;
import org.csgroup.sidus.util.Angle;
import org.csgroup.sidus.util.ShotColor;
import org.jetbrains.annotations.NotNull;

public class ShotBallCurve extends EnemyShot {
    private static final float SPEED = 80.0f;
    private static final int SIZE = 15;
    private final Vector2<Float> startPosition;
    private final int drawLayer;
    private final TextureSet textureSet;
    private final int shotColor;

    private final float angle;
    private float direction;

    private float time;

    public ShotBallCurve(@NotNull final Enemy parent, @NotNull final ShotColor shotColor) {
        super(parent);
        direction = Angle.BACKWARD.getAngle();
        startPosition = parent.getPosition();
        drawLayer = parent.getDrawLayer() - 1;
        textureSet = parent.getTextureSet();
        this.shotColor = shotColor.ordinal();
        angle = 0;
    }

    public ShotBallCurve(@NotNull final Enemy parent, final float direction, @NotNull final ShotColor shotColor, final float angle) {
        super(parent);
        this.direction = direction;
        startPosition = parent.getPosition();
        drawLayer = parent.getDrawLayer() - 1;
        textureSet = parent.getTextureSet();
        this.shotColor = shotColor.ordinal();
        this.angle = angle;
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
        direction += angle * getDelta();
        moveToAngle(direction, SPEED * getDelta());
    }

    @Override
    public void end() {

    }
}
