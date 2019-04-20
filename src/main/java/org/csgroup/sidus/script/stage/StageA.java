package org.csgroup.sidus.script.stage;

import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.entity.Entity;
import org.csgroup.sidus.script.common.*;
import org.csgroup.sidus.script.player.PlayerA;
import org.csgroup.sidus.script.stage.group.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StageA extends Stage {
    private final Entity background = addEntity(getTextureSet().getTexture("bg_stage_a"));
    private final Background backgroundTask = new Background(this, background);
    private final PlayerA player;
    private final CollisionSpace collisionSpace;
    private final GameState gameState;

    private final Group grout00;
    private final Group grout01;
    private final Group grout02;
    private final Group grout04;

    public StageA(@NotNull final List<SubTask> subTaskList, final TextureSet textureSet) {
        super(subTaskList, textureSet);
        gameState = new GameState(this);
        player = new PlayerA(this);
        collisionSpace = new CollisionSpace(this, player);


        grout00 = new GroupA00(this);
        grout01 = new GroupA01(this);
        grout02 = new GroupA02(this);
        grout04 = new GroupA04(this);
    }

    @Override
    public void init() {
        addSubTask(gameState);
        addSubTask(backgroundTask);
        addSubTask(new UI(this));
        addSubTask(player);
        addSubTask(collisionSpace);
        addSubTask(new GroupA00(this));
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void loop() {
        spawnAt(0, grout00);
        spawnAt(2, grout01);
        spawnAt(6.0f, grout02);
        spawnAt(12.0f, grout04);
    }

    @Override
    public void end() {
        removeAndTerminateSubTask(backgroundTask);
        terminate();
    }

    public PlayerA getPlayer() {
        return player;
    }

    public CollisionSpace getCollisionSpace() {
        return collisionSpace;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }
}
