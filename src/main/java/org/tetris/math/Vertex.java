package org.tetris.math;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vertex {
    public static final int SIZE = 32;
    Vector3 position;
    Vector3 normal;
    Vector2 uv;

    public float[] toFloatArray() {
        return new float[]{
            position.x, position.y, position.z,
            normal.x, normal.y, normal.z,
            uv.x, uv.y
        };
    }
}
