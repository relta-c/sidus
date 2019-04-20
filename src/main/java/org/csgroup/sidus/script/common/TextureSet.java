package org.csgroup.sidus.script.common;

import net.chifumi.stellar.graphic.SpriteMap;
import net.chifumi.stellar.texture.Texture;
import org.csgroup.sidus.core.PrimaryTask;
import org.csgroup.sidus.core.SubTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TextureSet extends SubTask {
    private final Map<String, Texture> textures;
    private final Map<String, SpriteMap> spriteMaps;

    public TextureSet(@NotNull final PrimaryTask parent) {
        super(parent);
        textures = new HashMap<>();
        spriteMaps = new HashMap<>();
    }

    @Override
    public void init() {
        addTexture("bg_main", "textures/bg_main.png");
        addTexture("logo", "textures/logo.png");
        addTexture("menu_start", "textures/menu_start.png");
        addTexture("menu_quit", "textures/menu_quit.png");
        addTexture("ui", "textures/ui.png");
        addTexture("ui", "textures/ui.png");
        addTexture("ui", "textures/ui.png");
        addTexture("bg_stage_a", "textures/bg_stage_a.png");
        addTexture("wave", "textures/wave.png");
        addTexture("player_hitbox", "textures/hitbox.png");
        addTexture("shot_chara_a", "textures/shot_chara_a.png");
        addTexture("bomb_a_back", "textures/bomb_a_back.png");
        addTexture("bomb_a_front", "textures/bomb_a_front.png");
        addTexture("shot_chara_a", "textures/shot_chara_a.png");
        addTexture("ui_life", "textures/ui_life.png");
        addTexture("ui_no_life", "textures/ui_no_life.png");
        addTexture("ui_bomb", "textures/ui_bomb.png");
        addTexture("ui_no_bomb", "textures/ui_no_bomb.png");
        addSpriteMap("chara_a", "textures/chara_a.png", "textures/chara_a.map");
        addSpriteMap("effect_shot_chara_a", "textures/effect_shot_chara_a.png", "textures/effect_shot_chara_a.map");
        addSpriteMap("enemy_a", "textures/enemy_a.png", "textures/enemy_a.map");
        addSpriteMap("shot_ball","textures/shot_ball.png","textures/shot_ball.map");
        addSpriteMap("enemy_explosion", "textures/enemy_explosion.png", "textures/enemy_explosion.map");
    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {

    }


    public Texture getTexture(final String name) {
        return textures.get(name);
    }

    public SpriteMap getSpriteMaps(final String name) {
        return spriteMaps.get(name);
    }

    @Override
    protected void postEnd() {

        super.postEnd();

    }

    private void addTexture(final String name, final String path) {
        @NotNull final Texture texture = loadTexture(path);
        textures.put(name, texture);
    }

    private void addSpriteMap(final String name, final String texturePath, final String mapPath) {
        spriteMaps.put(name, loadSpriteMap(loadTexture(texturePath), mapPath));
    }
}
