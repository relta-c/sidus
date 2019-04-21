package org.csgroup.sidus.script.common;

import kotlin.Pair;
import net.chifumi.stellar.geometry.Circle;
import net.chifumi.stellar.geometry.Collision;
import net.chifumi.stellar.geometry.MutableCircle;
import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.core.BaseTask;
import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.script.enemy.Enemy;
import org.csgroup.sidus.script.enemy.shot.EnemyShot;
import org.csgroup.sidus.script.player.Player;
import org.csgroup.sidus.script.player.PlayerProjectile;
import org.csgroup.sidus.script.player.bomb.Bomb;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionSpace extends SubTask {
    private final List<Enemy> enemies;
    private final List<EnemyShot> enemyShots;
    private final Player player;

    public CollisionSpace(@NotNull final BaseTask parent, final Player player) {
        super(parent);
        this.player = player;
        enemies = new CopyOnWriteArrayList<>();
        enemyShots = new CopyOnWriteArrayList<>();
    }

    public static boolean isCollide(@NotNull final Actor actorA, @NotNull final Actor actorB) {
        final Circle actorAHitBox = new MutableCircle(actorA.getAbsolutePosition(), actorA.getAbsoluteHitSize());
        final Circle actorBHitBox = new MutableCircle(actorB.getAbsolutePosition(), actorB.getAbsoluteHitSize());
        return Collision.check(actorAHitBox, actorBHitBox);
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

    public boolean isCollideWithPlayer(final Actor actor) {
        return isCollide(player, actor);
    }

    public Pair<Boolean, List<Enemy>> checkCollideWithEnemies(final PlayerProjectile actor) {
        boolean collided = false;
        final List<Enemy> collideList = new ArrayList<>();
        for (final Enemy enemy : enemies) {
            if (isCollide(actor, enemy)) {
                collided = true;
                collideList.add(enemy);
            }
        }
        return new Pair<>(collided, collideList);
    }

    public void addEnemy(final Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(final Enemy enemy) {
        enemies.remove(enemy);
    }

    public void checkDamageAgainstEnemies(final PlayerProjectile shot, final float amount) {
        final Pair<Boolean, List<Enemy>> result = checkCollideWithEnemies(shot);
        if (result.getFirst()) {
            for (final Enemy enemy : result.getSecond()) {
                enemy.reduceHealth(amount);
            }
            shot.hit();
        }
    }

    public void checkBombAgainstEnemies(final Bomb shot, final float amount) {
        final Pair<Boolean, List<Enemy>> result = checkCollideWithEnemies(shot);
        if (result.getFirst()) {
            for (final Enemy enemy : result.getSecond()) {
                enemy.bombed(amount);
            }
            shot.hit();
        }
    }


    public void killPlayer() {
        player.kill();
    }

    public Vector2<Float> getPlayerPosition() {
        return player.getPosition();
    }

    public Vector2<Float> getPlayerOrigin() {
        return player.getOrigin();
    }

    public void addEnemyShot(final EnemyShot shot) {
        enemyShots.add(shot);
    }

    public void clearEnemyShots() {
        for (final EnemyShot shot : enemyShots) {
            shot.setDisarm(true);
            enemyShots.remove(shot);
        }
    }
}
