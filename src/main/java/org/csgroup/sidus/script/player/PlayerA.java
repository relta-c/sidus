package org.csgroup.sidus.script.player;

import net.chifumi.stellar.math.ImmutableVector3;
import net.chifumi.stellar.math.Vector3;
import org.csgroup.sidus.script.player.bomb.BombA;
import org.csgroup.sidus.script.player.shot.PlayerAShot;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class PlayerA extends Player {
    private static final int DRAW_LAYER = 0;
    private static final float ANIMATION_DELAY = 0.1f;
    private static final float PLAYER_SIZE = 125.0f;
    private static final float HITBOX_SIZE = 10.0f;
    private static final float PLAYER_SPEED = 500.0f;
    private static final float FOCUSED_SPEED = 250.0f;
    private static final float SIDE_PADDING = -35.0f;
    private static final float SHOT_COOL_DOWN = 0.03f;

    private static final Vector3<Float> HITBOX_COLOR = new ImmutableVector3<>(10.0f, 100.0f, 190.0f);

    private float shotTime;

    public PlayerA(@NotNull final Stage parent) {
        super(parent);
    }

    @Override
    void drawHitbox() {
        draw(getHitbox(), DRAW_LAYER + 1);
    }

    @Override
    public void init() {
        setSpriteMap(getParent().getTextureSet().getSpriteMaps("chara_a"));
        setSize(PLAYER_SIZE, PLAYER_SIZE);
        setPosition(getStartPosition());
        setFramePerSet(3);
        setSpeed(PLAYER_SPEED);
        setFocusSpeed(FOCUSED_SPEED);
        setSidePadding(SIDE_PADDING);
        setAnimationDelay(ANIMATION_DELAY);
        setDrawLayer(DRAW_LAYER);
        setHitSize(HITBOX_SIZE);
        setHitboxSize(HITBOX_SIZE);
        setHitboxColor(HITBOX_COLOR);
        super.init();
    }

    @Override
    public void loop() {
    }

    @Override
    public void end() {

    }

    @Override
    protected void fire(final boolean focused) {
        if (shotTime > SHOT_COOL_DOWN) {
            if (focused) {
                fireFocus();
            } else {
                fireNormal();
            }
            shotTime = 0;
        }
        shotTime += getDelta();
    }

    @SuppressWarnings("MagicNumber")
    private void fireNormal() {
        addSubTask(new PlayerAShot(this, -15, 0));
        addSubTask(new PlayerAShot(this, 15, 0));
        addSubTask(new PlayerAShot(this, 40, 50));
        addSubTask(new PlayerAShot(this, -40, 50));
    }

    @SuppressWarnings("MagicNumber")
    private void fireFocus() {
        addSubTask(new PlayerAShot(this, -10, -25));
        addSubTask(new PlayerAShot(this, -5, -25));
        addSubTask(new PlayerAShot(this, 5, -25));
        addSubTask(new PlayerAShot(this, 10, -25));
    }

    @Override
    protected void bomb() {
        addSubTask(new BombA(this));
        getGameState().decreaseBomb();
    }
}
