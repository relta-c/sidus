package org.csgroup.sidus.script.player.shot;

import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.player.Player;
import org.csgroup.sidus.script.player.PlayerProjectile;
import org.jetbrains.annotations.NotNull;

public abstract class PlayerShot extends PlayerProjectile {
    private final Player player;
    private final Vector2<Float> startPosition;

    protected PlayerShot(@NotNull final Player player, final float x, final float y) {
        super(player);
        this.player = player;
        startPosition = new ImmutableVector2<>(x, y);
    }

    abstract float getDamage();

    @Override
    protected void startLoop() {
        super.startLoop();
        checkBoundary();
        player.getEntities().checkDamageAgainstEnemies(this, getDamage());

    }

    public Vector2<Float> getPlayerPosition() {
        return player.getPosition();
    }

    public Vector2<Float> getPlayerOrigin() {
        return player.getOrigin();
    }

    public Vector2<Float> getPlayerSize() {
        return player.getSize();
    }

    public int getPlayerDrawLayer() {
        return player.getDrawLayer();
    }

    public TextureSet getTextureSet() {
        return player.getTextureSet();
    }

    public float getDelta() {
        return getParent().getDelta();
    }

    protected Vector2<Float> getStartPosition() {
        return startPosition;
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
