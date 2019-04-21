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

    private final Group group00;
    private final Group group01;
    private final Group group02;
    private final Group group03;
    private final Group group04;
    private final Group group05;
    private final Group group06;
    private final Group group07;
    private final Group group08;
    private final Group groupBoss;

    public StageA(@NotNull final List<SubTask> subTaskList, final TextureSet textureSet) {
        super(subTaskList, textureSet);
        gameState = new GameState(this);
        player = new PlayerA(this);
        collisionSpace = new CollisionSpace(this, player);

        group00 = new GroupA00(this);
        group01 = new GroupA01(this);
        group02 = new GroupA02(this);
        group03 = new GroupA03(this);
        group04 = new GroupA04(this);
        group05 = new GroupA05(this);
        group06 = new GroupA01(this);
        group07 = new GroupA00(this);
        group08 = new GroupA04(this);
        groupBoss = new GroupABoss(this);
    }

    @Override
    public void init() {
        addSubTask(gameState);
        addSubTask(backgroundTask);
        addSubTask(new UI(this));
        addSubTask(player);
        addSubTask(collisionSpace);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void loop() {
        spawnAt(0, group00);
        spawnAt(2, group01);
        spawnAt(6.0f, group02);
        spawnAt(0.0f, group04);
        spawnAt(18.0f, group05);
        spawnAt(26.0f, group03);
        spawnAt(36.0f, group06);
        spawnAt(36.0f, group07);
        spawnAt(38.0f, group08);
        spawnAt(55.0f, groupBoss);

        if (gameState.isGameOver()) {
            addSubTask(new GameOver(this));
        } else if (gameState.isGameWin()) {
            addSubTask(new GameWin(this));
        }
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
