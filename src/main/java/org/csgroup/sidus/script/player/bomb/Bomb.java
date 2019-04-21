package org.csgroup.sidus.script.player.bomb;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.script.common.TextureSet;
import org.csgroup.sidus.script.player.Player;
import org.csgroup.sidus.script.player.PlayerProjectile;
import org.jetbrains.annotations.NotNull;

public abstract class Bomb extends PlayerProjectile {
    private final Player player;

    protected Bomb(@NotNull final Player player) {
        super(player);
        this.player = player;
    }

    abstract float getDamage();

    @Override
    protected void startLoop() {
        super.startLoop();
        player.getEntities().checkBombAgainstEnemies(this, getDamage());
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
}
