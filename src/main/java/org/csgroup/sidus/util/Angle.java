package org.csgroup.sidus.util;

public enum Angle {
    BACKWARD(90),
    FORWARD(270);

    private final float angle;

    Angle(final float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }
}
