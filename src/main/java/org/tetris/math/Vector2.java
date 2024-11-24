package org.tetris.math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vector2 {
    public float x;
    public float y;

    public Vector2(float t) {
        x = t;
        y = t;
    }

    public Vector2() {
        x = 0.0f;
        y = 0.0f;
    }
    public float length()
    {
        return (float) Math.sqrt(x * x + y * y);
    }
    public void normalize()
    {
        float length = length();

        x = x / length;
        y = y / length;
    }

    public static float dot(Vector2 left, Vector2 right) {
        return (left.x * right.x) + (left.y * right.y);
    }

    public float dot(Vector2 right) {
        return dot(this, right);
    }

    public static float cross(Vector2 left, Vector2 right) {
        return (left.x * right.y) - (left.y * right.x);
    }

    public float cross(Vector2 right) {
        return cross(this, right);
    }

    public Vector2 getRight() {
        return new Vector2(1.0f, 0.0f);
    }

    public Vector2 getUp() {
        return new Vector2(0.0f, 1.0f);
    }
}