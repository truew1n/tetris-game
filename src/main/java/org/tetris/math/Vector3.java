package org.tetris.math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vector3 {
    public float x;
    public float y;
    public float z;

    public float length()
    {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
    public void normalize()
    {
        float length = length();

        x = x / length;
        y = y / length;
        z = z / length;
    }
}
