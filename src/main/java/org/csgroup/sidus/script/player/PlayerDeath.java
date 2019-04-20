package org.csgroup.sidus.script.player;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.script.common.Actor;
import org.jetbrains.annotations.NotNull;

public class PlayerDeath extends Actor {
    private static final float INFLATION_SPEED = 7000.0f;
    private static final float DECAY_SPEED = 250.0f;
    private final Player player;
    private final Vector2<Float> position;

    protected PlayerDeath(@NotNull final Player parent, final Vector2<Float> position) {
        super(parent);
        player = parent;
        this.position = position;
    }

    @Override
    public void init() {
        setTexture(player.getTextureSet().getTexture("wave"));
        setSize(0, 0);
        setPosition(position);
        super.init();
    }

    @Override
    public void loop() {
        final float addLength = INFLATION_SPEED * getDelta();
        setSize(getSize().getX() + addLength, getSize().getY() + addLength);
        setPosition(getPosition().getX() - addLength / 2, getPosition().getY() - addLength / 2);
        setTransparency(getTransparency() - DECAY_SPEED * getDelta());
        if (getTransparency() <= 0) {
            terminate();
        }
    }

    @Override
    public void end() {

    }
}
