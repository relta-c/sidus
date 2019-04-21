package org.csgroup.sidus.script.common;

import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.core.BaseTask;

import org.jetbrains.annotations.NotNull;

public abstract class AutoCleanActor extends Actor {

    public static final float DEFAULT_DEATH_TIME = 60.0f;
    private float deathTime;
    private float liveTime;

    protected AutoCleanActor(@NotNull final BaseTask parent) {
        super(parent);
        deathTime = DEFAULT_DEATH_TIME;
    }

    @Override
    protected void startLoop() {
        checkLiveTime();
    }

    protected void checkLiveTime() {
        liveTime += getDelta();
        if (liveTime >= deathTime) {
            terminate();
        }
    }

    public float getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(final float deathTime) {
        this.deathTime = deathTime;
    }
}
