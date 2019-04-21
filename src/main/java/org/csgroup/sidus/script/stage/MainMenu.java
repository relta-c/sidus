package org.csgroup.sidus.script.stage;

import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.ImmutableVector3;
import net.chifumi.stellar.math.Vector2;
import net.chifumi.stellar.math.Vector3;
import net.chifumi.stellar.util.Timer;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.core.PrimaryTask;
import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.entity.Entity;
import org.csgroup.sidus.script.common.TextureSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("InstanceVariableMayNotBeInitialized")
public class MainMenu extends PrimaryTask {
    private static final Vector3<Float> INACTIVE_MENU_COLOR = new ImmutableVector3<>(255.0f, 255.0f, 255.0f);
    private static final Vector3<Float> ACTIVE_MENU_COLOR = new ImmutableVector3<>(255.0f, 100.0f, 150.0f);
    private static final Vector2<Float> MENU_SIZE = new ImmutableVector2<>(400.0f, 200.0f);
    private static final Vector2<Float> MENU_ORIGIN = new ImmutableVector2<>(370.0f, 650.0f);
    private static final float LOGO_Y = 50;
    private static final float KEY_COOL_DOWN = 0.1f;

    private Entity background;
    private Entity logo;
    private Entity startMenu;
    private Entity quitMenu;

    private final List<Entity> menuList = new ArrayList<>();
    private final int lastMenu = MainMenu.MenuItem.values().length - 1;

    private final Timer timer = new Timer();
    private final TextureSet textureSet;

    private int selectMenu;

    public MainMenu(@NotNull final List<SubTask> subTaskList, final TextureSet textureSet) {
        super(subTaskList);
        this.textureSet = textureSet;
    }

    private static float textToCenter(@NotNull final Entity entity) {
        return Setting.normalizedWindowWidth / 2 - entity.getPosition().getX();
    }

    @Override
    public void init() {
        background = addEntity(textureSet.getTexture("bg_main"));
        logo = addEntity(textureSet.getTexture("logo"));
        startMenu = addEntity(textureSet.getTexture("menu_start"));
        quitMenu = addEntity(textureSet.getTexture("menu_quit"));
        background.setAbsoluteSize(getAbsoluteScreenWidth(), getAbsoluteScreenHeight());
        logo.setOrigin(textToCenter(logo), LOGO_Y);
        initializeMenu();
        timer.start();
    }

    @Override
    public void loop() {
        disableMask();
        handleInput();
        drawAll();
    }

    @Override
    public void end() {
        enableMask();
    }

    private void initializeMenu() {
        menuList.add(startMenu);
        menuList.add(quitMenu);
        startMenu.setSize(MENU_SIZE);
        startMenu.setOrigin(MENU_ORIGIN);
        quitMenu.setSize(MENU_SIZE);
        quitMenu.setOrigin(startMenu.getOrigin().getX(), startMenu.getOrigin().getY() + quitMenu.getSize().getY() / 2);
    }

    private void drawAll() {
        draw(background, -100);
        draw(logo, 0);
        drawMenu();
    }

    private void drawMenu() {
        for (int i = 0; i < menuList.size(); i++) {
            final Entity menuItem = menuList.get(i);
            if (i == selectMenu) {
                menuItem.setColor(ACTIVE_MENU_COLOR);
            } else {
                menuItem.setColor(INACTIVE_MENU_COLOR);
            }
            draw(menuItem, 0);
        }
    }


    private void handleInput() {
        if (getKeyPressed(getKey().getKeyFire())) {
            if (getSelectedMenu() == MainMenu.MenuItem.START) {
                toNewStage(new StageA(getSubTaskList(), textureSet));
            } else if (getSelectedMenu() == MainMenu.MenuItem.EXIT) {
                terminate();
            }
        }
        if (getKeyPressed(getKey().getKeyCancel())) {
            selectMenu = lastMenu;
        }
        if (getKeyPressed(getKey().getKeyUp()) && isKeyCoolDowned()) {
            if (selectMenu == 0) {
                selectMenu = lastMenu;
            } else {
                selectMenu--;
            }
        }

        if (getKeyPressed(getKey().getKeyDown()) && isKeyCoolDowned()) {
            if (selectMenu == lastMenu) {
                selectMenu = 0;
            } else {
                selectMenu++;
            }
        }
    }

    private boolean isKeyCoolDowned() {
        final boolean result = timer.getRecordedTime() > KEY_COOL_DOWN;
        if (result) {
            timer.reset();
        }
        return result;
    }

    private MainMenu.MenuItem getSelectedMenu() {
        return MainMenu.MenuItem.values()[selectMenu];
    }

    private enum MenuItem {
        START,
        EXIT
    }
}
