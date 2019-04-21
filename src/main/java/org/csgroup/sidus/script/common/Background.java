package org.csgroup.sidus.script.common;

import org.csgroup.sidus.entity.Entity;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.core.PrimaryTask;
import org.csgroup.sidus.core.SubTask;

public class Background extends SubTask {
    private static final int backgroundLayer = -10;
    private static final float padding = 20.0f;
    private static final float speed = 200.0f;
    private final Entity background;

    public Background(final PrimaryTask parent, final Entity background) {
        super(parent);
        this.background = background;
    }

    @Override
    public void init() {
        background.setOrigin(-(padding / 2), -background.getSize().getY() + Setting.gameHeight);
        background.setSize(Setting.gameWidth + padding, background.getSize().getY());
    }

    @Override
    public void loop() {
        background.setOrigin(background.getOrigin().getX(),
                             background.getOrigin().getY() + speed * getDelta());
        if (background.getOrigin().getY() >= 0) {
            background.setOrigin(-(padding / 2), 0);
        }
        draw(background, backgroundLayer);
    }

    @Override
    public void end() {

    }
}
