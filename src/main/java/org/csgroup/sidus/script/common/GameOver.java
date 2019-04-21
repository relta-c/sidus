package org.csgroup.sidus.script.common;

import net.chifumi.stellar.util.Timer;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.entity.Entity;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class GameOver extends SubTask {
    private final Entity gameOver;
    private final Entity black;
    private final Timer timer;

    private float time;

    public GameOver(@NotNull final Stage parent) {
        super(parent);
        gameOver = addEntity(parent.getTextureSet().getTexture("ui_game_over"));
        black = addEntity(parent.getTextureSet().getTexture("black"));
        timer = new Timer();
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public void init() {
        black.setSize(Setting.gameWidth, Setting.gameHeight);
        black.setTransparency(2);
        gameOver.setSize(1000, 700.0f);
        gameOver.setPosition(400.0f, 300.0f);
        timer.start();
    }

    @Override
    public void loop()
    {
        time += timer.getDeltaTime();
        draw(black, 2000);
        draw(gameOver, 2001);

        if (time >= 3.0f) {
            System.exit(0);
        }
    }

    @Override
    public void end() {

    }
}
