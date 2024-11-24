package org.tetris.math;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Vector4 {
    public float x;
    public float y;
    public float z;

    public float w;

    public Vector4() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
    }
    public Vector4(float t) {
        x = t;
        y = t;
        z = t;
        w = t;
    }

    public Vector4(Vector3 vector) {
        x = vector.x;
        y = vector.y;
        z = vector.z;
        w = 1.0f;
    }

    public float length()
    {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }
    public void normalize()
    {
        float length = length();

        x = x / length;
        y = y / length;
        z = z / length;
        w = w / length;
    }
}