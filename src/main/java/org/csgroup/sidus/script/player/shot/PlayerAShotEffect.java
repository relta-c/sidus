package org.csgroup.sidus.script.player.shot;

import org.csgroup.sidus.script.common.Actor;
import org.csgroup.sidus.script.common.AutoCleanActor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class PlayerAShotEffect extends Actor {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 40;
    public static final int HIT_EFFECT_DRAW_LAYER = 11;
    private static final float TIME_ALIVE = 0.1f;

    private float timeAlive;

    final PlayerShot playerShot;

    public PlayerAShotEffect(@NotNull final PlayerShot parent) {
        super(parent);
        playerShot = parent;
    }

    @Override
    public void init() {
        setSpriteMap(playerShot.getTextureSet().getSpriteMaps("effect_shot_chara_a"));
        setFramePerSet(1);
        setFrameSet(randomSpriteSet(4));
        setOrigin(
                playerShot.getOrigin().getX() + (playerShot.getSize().getX() / 2) - (getSize().getX() / 8),
                playerShot.getOrigin().getY() - playerShot.getSize().getY() - 10);
        setDrawLayer(HIT_EFFECT_DRAW_LAYER);
        setSize(WIDTH, HEIGHT);
        setTransparency(1);
        super.init();
    }

    @Override
    public void loop() {
        timeAlive += getDelta();
        if (timeAlive > TIME_ALIVE) {
            terminate();
            playerShot.terminate();
        }
    }

    @Override
    public void end() {

    }

    @Override
    protected void postEnd() {
        super.postEnd();
    }
}
