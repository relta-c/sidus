package org.csgroup.sidus.script.player.bomb;

import org.csgroup.sidus.script.common.Actor;
import org.jetbrains.annotations.NotNull;

public class BombAEffect extends Actor {
    private static final float ROTATE_SPEED = 10.0f;
    public static final int TRANSPARENCY = 70;
    private final BombA bomb;

    protected BombAEffect(@NotNull final BombA parent) {
        super(parent);
        bomb = parent;
        setTexture(parent.getTextureSet().getTexture("bomb_a_front"));
    }

    @Override
    public void init() {
        super.init();
        setDrawLayer(bomb.getDrawLayer() - 1);
        setTransparency(TRANSPARENCY);
    }

    @Override
    public void loop() {
        setPosition(bomb.getPosition());
        setSize(bomb.getSize());
        setTransparency(bomb.getTransparency());
        setRotation(getRotation() + ROTATE_SPEED);
    }

    @Override
    public void end() {

    }
}
