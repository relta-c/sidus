package org.csgroup.sidus.script.common;

import net.chifumi.stellar.graphic.SpriteMap;
import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;
import net.chifumi.stellar.math.Vector3;
import net.chifumi.stellar.texture.Texture;
import org.csgroup.sidus.config.Setting;
import org.csgroup.sidus.core.BaseTask;
import org.csgroup.sidus.core.SubTask;
import org.csgroup.sidus.entity.AnimateEntity;
import org.csgroup.sidus.entity.Entity;
import org.csgroup.sidus.util.Calculate;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("InstanceVariableMayNotBeInitialized")
public abstract class Actor extends SubTask {
    private static final float DEFAULT_ANIMATION_DELAY = 0.2f;
    private static final int DEFAULT_DRAW_ROTATION = 270;
    private Entity actor;
    private AnimateEntity animateActor;

    private List<CharSequence> spriteList;

    private float hitSize;

    private int drawLayer;
    private int frameIndex;
    private int frameSet;
    private int framePerSet;
    private float animationTimer;
    private float animationDelay;
    private boolean drawing;
    private boolean animated;
    private boolean animating;
    private boolean loop;

    protected Actor(@NotNull final BaseTask parent) {
        super(parent);
        animationDelay = DEFAULT_ANIMATION_DELAY;
        animating = true;
        loop = true;
    }

    protected static int randomSpriteSet(final int number) {
        return (int) (Math.random() * (number - 2));
    }

    @Override
    public void init() {
        setRotation(DEFAULT_DRAW_ROTATION);
    }

    @Override
    protected void endLoop() {
        if (drawing) {
            if (animated) {
                if (animationTimer >= animationDelay && animating) {
                    if (frameIndex >= framePerSet) {
                        if (loop) {
                            frameIndex = 0;
                        } else {
                            frameIndex -= 1;
                            animating = false;
                        }
                    }
                    animateActor.setSprite(spriteList.get(frameIndex + (frameSet * framePerSet)));
                    animationTimer = 0;
                    frameIndex++;
                }
                animationTimer += getDelta();
                draw(animateActor, drawLayer);
            } else {
                draw(actor, drawLayer);
            }
        }
    }

    protected void turnToward(@NotNull final Vector2<Float> origin, @NotNull final Vector2<Float> destination, final float distant) {
        final double angle = Math.toDegrees(StrictMath.atan2(destination.getY() - origin.getY(), destination.getX() - origin.getX()));
        if (animated) {
            animateActor.moveXToAngle(((float) angle), distant);
        } else {
            actor.moveXToAngle(((float) angle), distant);
        }
    }

    protected void moveToward(@NotNull final Vector2<Float> origin, @NotNull final Vector2<Float> destination, final float distant) {
        final double angle = Math.toDegrees(StrictMath.atan2(destination.getY() - origin.getY(), destination.getX() - origin.getX()));
        if (animated) {
            animateActor.moveToAngle(((float) angle), distant);
        } else {
            actor.moveToAngle(((float) angle), distant);
        }
    }

    protected void moveToAngle(final float angle, final float distant) {
        if (animated) {
            animateActor.moveToAngle(angle, distant);
        } else {
            actor.moveToAngle(angle, distant);
        }
    }

    protected void moveWithOffset(final float x, final float y) {
        if (animated) {
            animateActor.moveWithOffset(x, y);
        } else {
            actor.moveWithOffset(x, y);
        }
    }

    public float getAnimationDelay() {
        return animationDelay;
    }

    public void setAnimationDelay(final float animationDelay) {
        this.animationDelay = animationDelay;
    }

    protected int getFramePerSet() {
        return framePerSet;
    }

    protected void setFramePerSet(final int framePerSet) {
        this.framePerSet = framePerSet;
    }

    public int getDrawLayer() {
        return drawLayer;
    }

    protected void setDrawLayer(final int drawLayer) {
        this.drawLayer = drawLayer;
    }

    protected int getFrameSet() {
        return frameSet;
    }

    protected void setFrameSet(final int frameSet) {
        this.frameSet = frameSet;
        animateActor.setSprite(spriteList.get(frameIndex + frameSet));
    }

    protected void setTexture(final Texture texture) {
        actor = addEntity(texture);
        drawing = true;
        animated = false;
        setRotation(0);
    }

    protected void setSpriteMap(final SpriteMap spriteMap) {
        animateActor = addAnimateEntity(spriteMap, spriteMap.getSpriteList().get(0));
        drawing = true;
        animated = true;
        setRotation(0);
        spriteList = spriteMap.getSpriteList();
    }

    public void setColor(final float red, final float green, final float blue) {
        if (animated) {
            animateActor.setColor(red, green, blue);
        } else {
            actor.setColor(red, green, blue);
        }
    }

    public void setColor(final Vector3<Float> color) {
        setColor(color.getX(), color.getY(), color.getZ());
    }

    public Vector2<Float> getSize() {
        return animated ? animateActor.getSize() : actor.getSize();
    }

    public void setSize(final Vector2<Float> size) {
        setSize(size.getX(), size.getY());
    }

    public void setSize(final float x, final float y) {
        if (animated) {
            animateActor.setSize(x, y);
        } else {
            actor.setSize(x, y);
        }
    }

    public Vector2<Float> getOrigin() {
        return animated ? animateActor.getOrigin() : actor.getOrigin();
    }

    public void setOrigin(final Vector2<Float> position) {
        if (animated) {
            animateActor.setOrigin(position);
        } else {
            actor.setOrigin(position);
        }
    }

    public void setOrigin(final float x, final float y) {
        setOrigin(new ImmutableVector2<>(x, y));
    }

    public Vector2<Float> getPosition() {
        return animated ? animateActor.getPosition() : actor.getPosition();
    }

    public void setPosition(final Vector2<Float> position) {
        setPosition(position.getX(), position.getY());

    }

    public void setPosition(final float x, final float y) {
        if (animated) {
            animateActor.setPosition(x, y);
        } else {
            actor.setPosition(x, y);
        }
    }

    public Vector2<Float> getAbsolutePosition() {
        return animated ? animateActor.getAbsolutePosition() : actor.getAbsolutePosition();
    }

    public float getRotation() {
        return animated ? animateActor.getRotation() : actor.getRotation();
    }

    public void setRotation(final float rotation) {
        final float setRotation = rotation + 90;
        if (animated) {
            animateActor.setRotation(setRotation);
        } else {
            actor.setRotation(setRotation);
        }
    }

    public float getHitSize() {
        return hitSize;
    }

    public void setHitSize(final float hitSize) {
        this.hitSize = hitSize;
    }

    public float getAbsoluteHitSize() {
        return Calculate.INSTANCE.normalizedToAbsolutePosition(hitSize, hitSize).getX() / 2;
    }

    public boolean isVisible() {
        return animated ? animateActor.isVisible() : actor.isVisible();
    }

    public void setVisible(final boolean visible) {
        if (animated) {
            animateActor.setVisible(visible);
        } else {
            actor.setVisible(visible);
        }
    }

    public float getTransparency() {
        return animated ? animateActor.getTransparency() : actor.getTransparency();
    }

    public void setTransparency(final float transparency) {
        if (animated) {
            animateActor.setTransparency(transparency);
        } else {
            actor.setTransparency(transparency);
        }
    }

    public boolean isAnimating() {
        return animating;
    }

    public void setAnimating(final boolean animating) {
        this.animating = animating;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(final boolean loop) {
        this.loop = loop;
    }

    public static Vector2<Float> getScreenCenter() {
        final float x = Setting.gameMaxX / 2;
        final float y = Setting.gameMaxY / 2;
        return new ImmutableVector2<>(x, y);
    }

    @Override
    public void terminate() {
        super.terminate();
        getParent().removeSubTask(this);
    }
}
