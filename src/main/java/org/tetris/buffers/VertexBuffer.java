package org.tetris.buffers;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;
import org.tetris.math.Vertex;

import java.nio.FloatBuffer;
import java.util.List;

public class VertexBuffer extends RenderObject {

    public VertexBuffer(List<Vertex> vertices) {
        id = GL46.glGenBuffers();
        if (id == 0) {
            throw new RuntimeException("Failed to generate a new Vertex Buffer Object (VBO).");
        }

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, id);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.size() * Vertex.SIZE);
        for (Vertex vertex : vertices) {
            buffer.put(vertex.toFloatArray());
        }
        buffer.flip();

        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, buffer, GL46.GL_STATIC_DRAW);

        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
    }

    public void bind() {
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, id);
    }

    public void unbind() {
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
    }

    public void delete() {
        GL46.glDeleteBuffers(id);
    }
}
