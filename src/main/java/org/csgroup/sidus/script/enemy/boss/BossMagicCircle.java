package org.csgroup.sidus.script.enemy.boss;

import org.csgroup.sidus.script.common.Actor;
import org.jetbrains.annotations.NotNull;

public class BossMagicCircle extends Actor {
    private static final float ROTATION_SPEED = 100.0f;
    private static final float SIZE = 200.0f;
    private final Boss boss;

    private float rotation;

    protected BossMagicCircle(@NotNull final Boss parent) {
        super(parent);
        boss = parent;
    }

    @Override
    public void init() {
        setTexture(boss.getTextureSet().getTexture("magic_circle"));
        setSize(SIZE, SIZE);
        setTransparency(100);
        super.init();
    }

    @Override
    public void loop() {
        setPosition(boss.getPosition());
        rotation += ROTATION_SPEED * getDelta();
        setRotation(rotation);
    }

    @Override
    public void end() {

    }
}
