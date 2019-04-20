package org.csgroup.sidus.script.player.shot;

import org.csgroup.sidus.script.player.Player;
import org.csgroup.sidus.util.Angle;
import org.jetbrains.annotations.NotNull;

public class PlayerAShot extends PlayerShot {
    private static final float SHOT_SPEED = 3000;
    private static final int SHOT_WIDTH = 15;
    private static final int SHOT_HEIGHT = 25;
    private static final float DAMAGE = 5.0f;

    private float speed = SHOT_SPEED;
    private float damage = DAMAGE;

    public PlayerAShot(@NotNull final Player player, final float x, final float y) {
        super(player, x, y);
    }

    @Override
    public void init() {
        setTexture(getTextureSet().getTexture("shot_chara_a"));
        setOrigin(
                getPlayerOrigin().getX() + (getPlayerSize().getX() / 2) - (getSize().getX() / 8),
                getPlayerOrigin().getY() + (getSize().getY() / 4));
        moveWithOffset(getStartPosition().getX(), getStartPosition().getY());
        setSize(SHOT_WIDTH, SHOT_HEIGHT);
        setTransparency(30);
        setDrawLayer(getPlayerDrawLayer() - 1);
        super.init();
    }

    @Override
    public void loop() {
        moveToAngle(Angle.FORWARD.getAngle(), speed * getDelta());
    }

    @Override
    public void end() {

    }

    @Override
    protected void postEnd() {
        super.postEnd();
    }

    @Override
    float getDamage() {
        return damage;
    }

    @Override
    public void hit() {
        speed = 0;
        damage = 0;
        addSubTask(new PlayerAShotEffect(this));
    }
}
