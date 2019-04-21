package org.csgroup.sidus.script.enemy.shot;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.script.common.AutoCleanActor;
import org.csgroup.sidus.script.common.CollisionSpace;
import org.csgroup.sidus.script.enemy.Enemy;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("MagicNumber")
public abstract class EnemyShot extends AutoCleanActor {
    private final CollisionSpace collisionSpace;
    private boolean disarm;

    protected EnemyShot(@NotNull final Enemy parent) {
        super(parent);
        collisionSpace = parent.getCollisionSpace();
        collisionSpace.addEnemyShot(this);
    }

    @Override
    protected void endLoop() {
        super.endLoop();
        if (!isDisarm() && collisionSpace.isCollideWithPlayer(this)) {
            collisionSpace.killPlayer();
        }

        if (isDisarm()) {
            setTransparency(getTransparency() - (500.0f * getDelta()));
        }

        checkClear();
        checkBoundary();
    }

    protected void checkClear() {
        if (isDisarm() && getTransparency() <= 0) {
            terminate();
        }
    }

    public boolean isDisarm() {
        return disarm;
    }

    public void setDisarm(final boolean disarm) {
        this.disarm = disarm;
    }

    public CollisionSpace getCollisionSpace() {
        return collisionSpace;
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
