package org.csgroup.sidus.script.common;

import net.chifumi.stellar.util.Timer;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.entity.Entity;
import org.csgroup.sidus.script.stage.Stage;
import org.jetbrains.annotations.NotNull;


public class GameWin extends SubTask {
    private final Entity light;
    private final Timer timer;

    private float time;

    public GameWin(@NotNull final Stage parent) {
        super(parent);
        light = addEntity(parent.getTextureSet().getTexture("light"));
        timer = new Timer();
    }

    @Override
    public void init() {
        light.setSize(Setting.gameWidth, Setting.gameHeight);
        light.setTransparency(2);
        timer.start();
    }

    @Override
    public void loop()
    {
        time += timer.getDeltaTime();
        draw(light, 2000);

        if (time >= 3.0f) {
            System.exit(0);
        }
    }

    @Override
    public void end() {

    }
}
