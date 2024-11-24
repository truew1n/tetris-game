package org.tetris.math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vector2 {
    public float x;
    public float y;

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
}