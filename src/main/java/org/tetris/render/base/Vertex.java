package org.tetris.render.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.lwjgl.opengl.GL46;
import org.tetris.math.Vector2;
import org.tetris.math.Vector3;

import java.util.List;

@Data
@AllArgsConstructor
public class Vertex {
    public static final int SIZE = 32;
    public static final int TYPE = GL46.GL_FLOAT;
    public static final int TYPE_SIZE = 4;

    public static final List<VertexLinkData> vertexLinkData = List.of(
        new VertexLinkData(0, 3, GL46.GL_FLOAT, SIZE, 0),
        new VertexLinkData(1, 3, GL46.GL_FLOAT, SIZE, 3 * TYPE_SIZE),
        new VertexLinkData(2, 2, GL46.GL_FLOAT, SIZE, 6 * TYPE_SIZE)
    );

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
