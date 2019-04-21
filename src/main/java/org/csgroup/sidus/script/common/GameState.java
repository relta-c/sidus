package org.csgroup.sidus.script.common;

import org.csgroup.sidus.core.BaseTask;
import org.csgroup.sidus.core.SubTask;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("MagicNumber")
public class GameState extends SubTask {
    private int score;
    private int playerLife;
    private int bombCount;
    private float power;
    private int gaze;
    private boolean gameOver;
    private boolean gameWin;

    public GameState(@NotNull final BaseTask parent) {
        super(parent);
        score = 0;
        power = 4.0f;
        playerLife = 6;
        bombCount = 2;
    }

    public boolean isGameWin() {
        return gameWin;
    }

    public void setGameWin(final boolean gameWin) {
        this.gameWin = gameWin;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void reset() {
        score = 0;
        playerLife = 6;
        bombCount = 2;
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }

    @Override
    public void end() {

    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public void addScore(final int score) {
        this.score += score;
    }

    public int getPlayerLife() {
        return playerLife;
    }

    public void setPlayerLife(final int playerLife) {
        this.playerLife = playerLife;
    }

    public void decreasePlayerLife() {
        playerLife--;
    }

    public void addPlayerLife() {
        playerLife++;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void decreaseBomb() {
        bombCount--;
    }

    public void addBomb() {
        bombCount++;
    }

    public float getPower() {
        return power;
    }

    public void setBomb(final int bomb) {
        bombCount = bomb;
    }

    public void setPower(final float power) {
        this.power = power;
    }

    public void increasePower(final float power) {
        this.power += power;
    }

    public int getGaze() {
        return gaze;
    }

    public void setGaze(final int gaze) {
        this.gaze = gaze;
    }

    public void addGaze() {
        gaze++;
    }
}
