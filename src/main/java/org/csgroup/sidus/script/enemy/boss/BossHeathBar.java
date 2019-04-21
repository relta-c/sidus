package org.csgroup.sidus.script.enemy.boss;

import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.script.common.Actor;
import org.csgroup.sidus.script.common.TextureSet;
import org.jetbrains.annotations.NotNull;

public class BossHeathBar extends Actor {
    private static final float MAX_SIZE = 650.0f;
    private float maxHealth;
    private final TextureSet textureSet;
    private final Boss boss;

    protected BossHeathBar(@NotNull final Boss parent) {
        super(parent);
        boss = parent;
        textureSet = parent.getTextureSet();
        maxHealth = parent.getMaxHealth();
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void init() {
        setTexture(textureSet.getTexture("red"));
        setSize(MAX_SIZE, 5);
        setOrigin(Setting.gameOriginX + 10, Setting.gameOriginY + 10);
        setDrawLayer(1000);
        setTransparency(40);
        super.init();
    }

    @Override
    public void loop() {
        maxHealth = boss.getMaxHealth();
        final float heath = boss.getHealth() / maxHealth;
        setSize(MAX_SIZE * heath, 5);
    }

    @Override
    public void end() {

    }
}
