package org.csgroup.sidus.script.common;

import net.chifumi.stellar.text.FontFamily;
import net.chifumi.stellar.text.Text;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.entity.Entity;
import org.csgroup.sidus.script.stage.Stage;
import org.csgroup.sidus.util.Calculate;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;

@SuppressWarnings("InstanceVariableMayNotBeInitialized")
public class UI extends SubTask {
    private static final int FONT_SIZE = ((int) (Setting.normalizedWindowWidth / 35));
    private static final float UI_X_OFFSET = Setting.normalizedWindowWidth / 50;
    private static final int UI_X_VALUE_OFFSET = 90;
    private static final int UI_TEXT_LAYER = 1000;
    private static final int UI_BACKGROUND_LAYER = -1000;
    private static final int SCORE_Y = 150;
    private static final int LIFE_Y = 250;
    private static final int BOMB_Y = 350;
    private static final int ICON_SIZE = 20;
    private final GameState gameState;
    private final Entity uiBackground;
    private final TextureSet textureSet;
    private FontFamily cirno;
    private Text scoreText;
    private Text scoreValueText;
    private Text playerText;
    private Text playerValueText;
    private Text bombText;
    private Text bombValueText;

    public UI(@NotNull final Stage stage) {
        super(stage);
        gameState = stage.getGameState();
        textureSet = stage.getTextureSet();
        uiBackground = addEntity(stage.getTextureSet().getTexture("ui"));
        try {
            cirno = new FontFamily("fonts/cirno.fnt");
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            terminate();
        }
    }

    @Override
    public void init() {
        scoreText = new Text("Score : ", FONT_SIZE, cirno);
        scoreValueText = new Text("0", FONT_SIZE, cirno);
        playerText = new Text("Player : ", FONT_SIZE, cirno);
        playerValueText = new Text("0", FONT_SIZE, cirno);
        bombText = new Text("Bomb : ", FONT_SIZE, cirno);
        bombValueText = new Text("0", FONT_SIZE, cirno);
        scoreText.setPosition(Calculate.INSTANCE.normalizedToAbsolutePosition(Setting.gameMaxX + UI_X_OFFSET, SCORE_Y));
        scoreValueText.setPosition(Calculate.INSTANCE.normalizedToAbsolutePosition(Setting.gameMaxX + UI_X_OFFSET + UI_X_VALUE_OFFSET, SCORE_Y));

        playerText.setPosition(Calculate.INSTANCE.normalizedToAbsolutePosition(Setting.gameMaxX + UI_X_OFFSET, LIFE_Y));

        bombText.setPosition(Calculate.INSTANCE.normalizedToAbsolutePosition(Setting.gameMaxX + UI_X_OFFSET, BOMB_Y));
    }

    @Override
    public void loop() {
        final DecimalFormat scoreFormat = new DecimalFormat("00000000000000000");
        scoreValueText.setText(scoreFormat.format(gameState.getScore()));
        playerValueText.setText(String.valueOf(gameState.getPlayerLife()));
        bombValueText.setText(String.valueOf(gameState.getBombCount()));
        drawLife();
        draw(uiBackground, UI_BACKGROUND_LAYER, false);
        draw(scoreText, UI_TEXT_LAYER);
        draw(scoreValueText, UI_TEXT_LAYER);
        draw(playerText, UI_TEXT_LAYER);
        draw(bombText, UI_TEXT_LAYER);
    }

    @Override
    public void end() {

    }

    private void drawLife() {
        for (int i = 0; i < gameState.getPlayerLife(); i++) {
            final Entity life = addEntity(textureSet.getTexture("ui_life"));
            life.setSize(ICON_SIZE, ICON_SIZE);
            life.setAbsoluteOrigin(Calculate.INSTANCE.normalizedToAbsolutePosition(Setting.gameMaxX + UI_X_OFFSET + UI_X_VALUE_OFFSET + ((ICON_SIZE + 5) * i), LIFE_Y + 10));
            draw(life, UI_BACKGROUND_LAYER + 1, false);
        }
        for (int i = 0; i < 7 - gameState.getPlayerLife(); i++) {
            final Entity noLife = addEntity(textureSet.getTexture("ui_no_life"));
            final int lifeCount = i + gameState.getPlayerLife();
            noLife.setSize(ICON_SIZE, ICON_SIZE);
            noLife.setAbsoluteOrigin(Calculate.INSTANCE.normalizedToAbsolutePosition(Setting.gameMaxX + UI_X_OFFSET + UI_X_VALUE_OFFSET + ((ICON_SIZE + 5) * lifeCount), LIFE_Y + 10));
            draw(noLife, UI_BACKGROUND_LAYER + 1, false);
        }
        for (int i = 0; i < gameState.getBombCount(); i++) {
            final Entity bomb = addEntity(textureSet.getTexture("ui_bomb"));
            bomb.setSize(ICON_SIZE, ICON_SIZE);
            bomb.setAbsoluteOrigin(Calculate.INSTANCE.normalizedToAbsolutePosition(Setting.gameMaxX + UI_X_OFFSET + UI_X_VALUE_OFFSET + ((ICON_SIZE + 5) * i), BOMB_Y + 10));
            draw(bomb, UI_BACKGROUND_LAYER + 1, false);
        }
        for (int i = 0; i < 7 - gameState.getBombCount(); i++) {
            final Entity noBomb = addEntity(textureSet.getTexture("ui_no_bomb"));
            final int bombCount = i + gameState.getBombCount();
            noBomb.setSize(ICON_SIZE, ICON_SIZE);
            noBomb.setAbsoluteOrigin(Calculate.INSTANCE.normalizedToAbsolutePosition(Setting.gameMaxX + UI_X_OFFSET + UI_X_VALUE_OFFSET + ((ICON_SIZE + 5) * bombCount), BOMB_Y + 10));
            draw(noBomb, UI_BACKGROUND_LAYER + 1, false);
        }
    }
}
