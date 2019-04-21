package org.csgroup.sidus.script.enemy.boss;

import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.script.enemy.Enemy;
import org.csgroup.sidus.script.enemy.shot.ShotBall;
import org.csgroup.sidus.script.enemy.shot.ShotBallExplode;
import org.csgroup.sidus.script.enemy.shot.ShotBallTarget;
import org.csgroup.sidus.script.stage.Stage;
import org.csgroup.sidus.util.ShotColor;
import org.jetbrains.annotations.NotNull;

public class Boss extends Enemy {
    private static final float SPEED = 100.0f;
    private static final float SIZE = 200;
    private static final float MAX_HEALTH = 20000;


    private int life;
    private float time;
    private float speed;

    private float normalTimeA0;
    private float normalTimeA1;
    private boolean normalClearA;

    private float spellTimeA0;
    private float spellTimeA1;
    private boolean spellClearA;

    private float normalTimeB0;
    private float normalTimeB1;
    private boolean normalClearB;

    private float spellTimeB0;
    private float spellTimeB1;

    public Boss(@NotNull final Stage parent) {
        super(parent);
    }

    @SuppressWarnings("SameParameterValue")
    private static Vector2<Float> randSpawnPosition(final float yOffset) {
        final float x = (float) (Math.random() * Setting.gameMaxX);
        final float y = (float) (Setting.gameOriginY + (Math.random() * yOffset));
        return new ImmutableVector2<>(x, y);
    }

    @Override
    public void init() {
        setSpriteMap(getTextureSet().getSpriteMaps("boss"));
        setSize(SIZE, SIZE);
        setHealth(MAX_HEALTH);
        setHitSize(SIZE);
        setFrameSet(0);
        setFramePerSet(2);
        getCollisionSpace().addEnemy(this);
        setPosition(getScreenCenter().getX(), 0);
        super.init();
        speed = SPEED;
        addSubTask(new BossHeathBar(this));
        addSubTask(new BossMagicCircle(this));

        life = 3;
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void loop() {
        if (life == 0 && getHealth() <= 0) {
            getGameState().setGameWin(true);
        }

        time += getDelta();

        if (time > 2.0f) {
            speed = 0.0f;
        }
        moveToAngle(90.0f, speed * getDelta());


        if (life == 3) {
            normalA();
        } else if (life == 2) {
            if (normalClearA) {
                spellA();
            } else {
                getCollisionSpace().clearEnemyShots();
                normalClearA = true;
            }
        } else if (life == 1) {
            if (spellClearA) {
                normalB();
            } else {
                getCollisionSpace().clearEnemyShots();
                spellClearA = true;
            }
        } else if (getHealth() > 0){
            if (normalClearB) {
                spellB();
            } else {
                getCollisionSpace().clearEnemyShots();
                normalClearB = true;
            }
        }
    }

    @Override
    public void end() {

    }

    @Override
    public void kill() {
        if (life == 0) {
            super.kill();
        } else {
            setHealth(MAX_HEALTH);
            life--;
        }
    }

    @Override
    protected void checkLiveTime() {

    }

    @Override
    public void bombed(final float rawDamage) {
        super.bombed(rawDamage / 8);
    }

    @SuppressWarnings("MagicNumber")
    private void normalA() {
        normalTimeA0 += getDelta();
        normalTimeA1 += getDelta();
        if (normalTimeA0 >= 1.0f) {
            normalTimeA0 = 0.0f;
            final float randNum = ((float) Math.random() * 360);
            for (int i = 0; i < 48; i++) {
                addSubTask(new ShotBall(this, randNum + (360.0f / 48) * i, ShotColor.VIOLET));
            }

        }
        if (normalTimeA1 >= 3.0f) {
            normalTimeA1 = 0.0f;
            addSubTask(new ShotBallTarget(this));
        }
    }

    @SuppressWarnings("MagicNumber")
    private void normalB() {
        normalTimeB0 += getDelta();
        normalTimeB1 += getDelta();
        if (normalTimeB0 > 1.0f) {
            normalTimeB0 = 0;
            final float randNum = ((float) Math.random() * 360);
            for (int i = 0; i < 12; i++) {
                final ShotBall shot = new ShotBall(this, randNum + (360.0f / 12) * i, ShotColor.VIOLET);
                shot.setConfigSpeed(400.0f);
                addSubTask(shot);
            }
            for (int i = 0; i < 12; i++) {
                final ShotBall shot = new ShotBall(this, 45 + (360.0f / 48) * i, ShotColor.RED);
                shot.setConfigSpeed(400.0f);
                addSubTask(shot);
            }
        }
        if (normalTimeB1 > 0.5) {
            normalTimeB1 = 0;
            final float randNum = ((float) Math.random() * 360);
            for (int i = 0; i < 12; i++) {
                final ShotBall shot = new ShotBall(this, randNum + (360.0f / 12) * i, ShotColor.BLUE);
                shot.setConfigSpeed(100.0f);
                addSubTask(shot);
            }
        }
    }

    @SuppressWarnings("MagicNumber")
    private void spellA() {
        spellTimeA0 += getDelta();
        spellTimeA1 += getDelta();
        final float yOffset = 50.0f;
        if (spellTimeA1 >= 0.6f) {
            spellTimeA1 = 0.0f;
            final float randX = (float) (Math.random() * Setting.gameMaxX);
            addSubTask(new ShotBall(this, 90.0f, new ImmutableVector2<>(randX, ((float) Setting.gameOriginY)), ShotColor.GREY));
            if (spellTimeA0 >= 2.0f) {
                for (int i = 0; i < 2; i++) {
                    final ShotBall shot = new ShotBall(this, 90.0f, randSpawnPosition(yOffset), ShotColor.SKY);
                    shot.setConfigSpeed(140);
                    addSubTask(shot);
                }
            }
            if (spellTimeA0 >= 4.0f) {
                for (int i = 0; i < 12; i++) {
                    final ShotBall shot = new ShotBall(this, 90.0f, randSpawnPosition(yOffset), ShotColor.BLUE);
                    shot.setConfigSpeed(145);
                    addSubTask(shot);
                }
            }
            if (spellTimeA0 >= 6.0f) {
                for (int i = 0; i < 4; i++) {
                    final ShotBall shot = new ShotBall(this, 90.0f, randSpawnPosition(yOffset), ShotColor.VIOLET);
                    shot.setConfigSpeed(170);
                    addSubTask(shot);
                }
            }
            if (spellTimeA0 >= 8.0f) {
                for (int i = 0; i < 6; i++) {
                    final ShotBall shot = new ShotBall(this, 90.0f, randSpawnPosition(yOffset), ShotColor.PINK);
                    shot.setConfigSpeed(205);
                    addSubTask(shot);
                }
            }
            if (spellTimeA0 >= 10.0f) {
                for (int i = 0; i < 4; i++) {
                    final ShotBall shot = new ShotBall(this, 90.0f, randSpawnPosition(yOffset), ShotColor.RED);
                    shot.setConfigSpeed(300);
                    addSubTask(shot);
                }
            }
        }
    }

    @SuppressWarnings("MagicNumber")
    private void spellB() {
        spellTimeB0 += getDelta();
        spellTimeB1 += getDelta();
        if (spellTimeB0 > 5.0f) {
            spellTimeB0 = 0.0f;
            addSubTask(new ShotBallExplode(this));
        }

        if (spellTimeB1 > 0.5f) {
            spellTimeB1 = 0.0f;
            for (int i = 0; i < Setting.gameMaxY / 12; i++) {
                final ShotBall shot = new ShotBall(this, 0.0f, new ImmutableVector2<>(100.0f, (Setting.gameMaxY / 12.0f) * i), ShotColor.BLUE);
                shot.setConfigSpeed(60);
                addSubTask(shot);
            }
            for (int i = 0; i < 6; i++) {
                final ShotBall shot = new ShotBall(this, 90.0f, randSpawnPosition(50), ShotColor.VIOLET);
                shot.setConfigSpeed(100);
                addSubTask(shot);

            }
        }
    }
}
