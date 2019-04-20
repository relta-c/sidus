package org.csgroup.sidus.entity;

import net.chifumi.stellar.graphic.SpriteMap;
import net.chifumi.stellar.graphic.SpriteSet;
import net.chifumi.stellar.math.ImmutableVector2;
import net.chifumi.stellar.math.Vector2;
import org.csgroup.sidus.util.Calculate;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class AnimateEntity extends SpriteSet {
    private static final float HALF = 0.5f;

    public AnimateEntity(final SpriteMap spriteMap, final CharSequence name) {
        super(spriteMap, name);
    }

    @Override
    public Vector2<Float> getSize() {
        return Calculate.INSTANCE.absoluteToNormalizePosition(getAbsoluteSize());
    }

    @Override
    public void setSize(final float width, final float height) {
        setAbsoluteSize(Calculate.INSTANCE.normalizedToAbsolutePosition(width, height));
    }

    @Override
    public void setSize(final Vector2<Float> size) {
        setSize(size.getX(), size.getY());
    }

    public Vector2<Float> getAbsoluteSize() {
        return super.getSize();
    }

    public void setAbsoluteSize(final float width, final float height) {
        super.setSize(width, height);
    }

    public void setAbsoluteSize(final Vector2<Float> size) {
        super.setSize(size);
    }

    public Vector2<Float> getOrigin() {
        return Calculate.INSTANCE.absoluteToNormalizePosition(getAbsoluteOrigin().getX(),
                                                              getAbsoluteOrigin().getY());
    }

    public void setOrigin(final float x, final float y) {
        final Vector2<Float> position = Calculate.INSTANCE.normalizedToAbsolutePosition(x, y);
        setAbsoluteOrigin(position);
    }

    public void setOrigin(final Vector2<Float> position) {
        setOrigin(position.getX(), position.getY());
    }

    @Override
    public Vector2<Float> getPosition() {
        return new ImmutableVector2<>(getOrigin().getX() + (getSize().getX() * HALF),
                                      getOrigin().getY() + (getSize().getY() * HALF));
    }

    @Override
    public void setPosition(final float x, final float y) {
        setOrigin(x - (getSize().getX() * HALF), y - (getSize().getY() * HALF));
    }

    @Override
    public void setPosition(final Vector2<Float> position) {
        setPosition(position.getX(), position.getY());
    }

    public void moveToAngle(final float angle, final float distant) {
        final float newX = (float) (getPosition().getX() + StrictMath.cos(Math.toRadians(angle)) * distant);
        final float newY = ((float) (getPosition().getY() + StrictMath.sin(Math.toRadians(angle)) * distant));
        setPosition(newX, newY);
    }

    public void moveXToAngle(final float angle, final float distant) {
        final float newX = (float) (getPosition().getX() + StrictMath.cos(Math.toRadians(angle)) * distant);
        setPosition(newX, getPosition().getY());
    }

    public void moveYToAngle(final float angle, final float distant) {
        final float newY = ((float) (getPosition().getY() + StrictMath.sin(Math.toRadians(angle)) * distant));
        setPosition(getPosition().getX(), newY);
    }

    public void moveWithOffset(final float xOffset, final float yOffset) {
        setPosition(getPosition().getX() + xOffset, getPosition().getY() + yOffset);
    }

    public Vector2<Float> getAbsolutePosition() {
        return new ImmutableVector2<>(
                getAbsoluteOrigin().getX() + getAbsoluteSize().getX() * HALF,
                getAbsoluteOrigin().getY() + getAbsoluteSize().getY() * HALF);
    }

    public void setAbsolutePosition(final float x, final float y) {
        setAbsoluteOrigin(getAbsoluteOrigin().getX() + getAbsoluteSize().getX() * HALF,
                          getAbsoluteOrigin().getY() + getAbsoluteSize().getY() * HALF);
    }

    public Vector2<Float> getAbsoluteOrigin() {
        return super.getPosition();
    }

    public void setAbsoluteOrigin(final float x, final float y) {
        super.setPosition(x, y);
    }

    public void setAbsoluteOrigin(final Vector2<Float> position) {
        super.setPosition(position.getX(), position.getY());
    }

    @Override
    protected void updateModelMatrix() {
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix = modelMatrix.translate(new Vector3f(getAbsoluteOrigin().getX(), getAbsoluteOrigin().getY(), 0.0f));
        modelMatrix = modelMatrix.translate(new Vector3f(HALF * getAbsoluteSize().getX(), HALF * getAbsoluteSize().getY(), 0.0F));
        modelMatrix = modelMatrix.rotate(getQuaternionRotation());
        modelMatrix = modelMatrix.translate(new Vector3f(-HALF * getAbsoluteSize().getX(), -HALF * getAbsoluteSize().getY(), 0.0F));
        modelMatrix.scale(new Vector3f(getAbsoluteSize().getX(), getAbsoluteSize().getY(), 1.0F));
        setModelMatrix(modelMatrix);
    }
}
