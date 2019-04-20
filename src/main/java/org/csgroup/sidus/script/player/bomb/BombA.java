package org.csgroup.sidus.script.player.bomb;

import net.chifumi.stellar.math.MutableVector3;
import org.csgroup.sidus.script.common.CollisionSpace;
import org.csgroup.sidus.script.player.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BombA extends Bomb {
    private static final int BOMB_LAYER = 20;
    private static final int TRANSPARENCY = 30;
    private static final float CHARGE_STAGE = 0.5f;
    private static final float HOLD_STAGE = 0.75f;
    private static final float MOVE_STAGE = 0.5f;
    private static final float HOLD_CENTER_STAGE = 1.0f;
    private static final float EXPLODE_STAGE = 0.25f;
    private static final float FIRST_INFALTION_SPEED = 200.0f;
    private static final float SECOND_INFALTION_SPEED = 5000.0f;
    private static final float HUE_SHIFT_SPEED = 0.75f;
    private static final float DEFAULT_COLOR = 255.0f;
    private static final float MOVE_SPEED = 700.0f;
    private static final float BLINK_SPEED = 0.1f;
    private static final float BLINK_TRANSPARENCY = 20.0f;
    private static final float DECAY_SPEED = 300.0f;
    private static final float ALIVE_TIME_MAX = 4.0f;
    private final MutableVector3<Float> color;
    private final CollisionSpace collisionSpace;
    private final Player player;

    private boolean armed;
    private float aliveTime;
    private float blinkTime;

    public BombA(@NotNull final Player player) {
        super(player);
        collisionSpace = player.getEntities();
        this.player = player;
        color = new MutableVector3<>(DEFAULT_COLOR, 0.0f, 0.0f);
    }

    @Override
    protected void startLoop() {
        if (armed) {
            collisionSpace.checkDamageAgainstEnemies(this, getDamage());
        }
    }

    @Override
    float getDamage() {
        return 1.0f;
    }

    @Override
    public void hit() {

    }

    @Override
    public void init() {
        setTexture(getTextureSet().getTexture("bomb_a_back"));
        setSize(0, 0);
        setPosition(player.getPosition());
        setTransparency(TRANSPARENCY);
        setDrawLayer(BOMB_LAYER);
        super.init();
        addSubTask(new BombAEffect(this));
    }

    @Override
    public void loop() {
        setHitSize(getSize().getX());
        shiftHue();
        setColor(color);
        aliveTime += getDelta();
        if (aliveTime <= CHARGE_STAGE) {
            setPosition(player.getPosition());
            setSize(getSize().getX() + (FIRST_INFALTION_SPEED * getDelta()), getSize().getY() + (FIRST_INFALTION_SPEED * getDelta()));
        } else if (aliveTime < HOLD_STAGE + CHARGE_STAGE) {
            setPosition(player.getPosition());
        } else if (aliveTime < MOVE_STAGE + HOLD_STAGE + CHARGE_STAGE) {
            moveToward(getPosition(), getScreenCenter(), MOVE_SPEED * getDelta());
        } else if (aliveTime < HOLD_CENTER_STAGE + MOVE_STAGE + HOLD_STAGE + CHARGE_STAGE) {
            blink();
            setPosition(getScreenCenter());
            setSize(getSize().getX() + (FIRST_INFALTION_SPEED * getDelta()), getSize().getY() + (FIRST_INFALTION_SPEED * getDelta()));
        } else if (aliveTime < EXPLODE_STAGE + HOLD_CENTER_STAGE + MOVE_STAGE + HOLD_STAGE + CHARGE_STAGE) {
            armed = true;
            setPosition(getScreenCenter());
            setSize(getSize().getX() + (SECOND_INFALTION_SPEED * getDelta()), getSize().getY() + (SECOND_INFALTION_SPEED * getDelta()));
            setTransparency(getTransparency() - DECAY_SPEED * getDelta());
        } else if (aliveTime < ALIVE_TIME_MAX) {
            setTransparency(getTransparency() - DECAY_SPEED * getDelta());
        } else {
            terminate();
        }
    }

    @Override
    public void end() {

    }

    private void blink() {
        if (blinkTime > BLINK_SPEED) {
            blinkTime = 0;
            if (getTransparency() < 100) {
                setTransparency(100);
            } else {
                setTransparency(BLINK_TRANSPARENCY);
            }
        }
        blinkTime += getDelta();
    }

    private void shiftHue() {
        final float[] hsb = Color.RGBtoHSB(color.getX().intValue(), color.getY().intValue(), color.getZ().intValue(), null);
        hsb[0] += HUE_SHIFT_SPEED * getDelta();
        final Color rgb = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
        color.set(((float) rgb.getRed()), ((float) rgb.getGreen()), ((float) rgb.getBlue()));
    }
}
