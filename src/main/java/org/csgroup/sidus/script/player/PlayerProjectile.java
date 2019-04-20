package org.csgroup.sidus.script.player;

import org.csgroup.sidus.core.BaseTask;
import org.csgroup.sidus.script.common.AutoCleanActor;
import org.jetbrains.annotations.NotNull;

public abstract class PlayerProjectile extends AutoCleanActor {
    protected PlayerProjectile(@NotNull final BaseTask parent) {
        super(parent);
    }

    public abstract void hit();
}
