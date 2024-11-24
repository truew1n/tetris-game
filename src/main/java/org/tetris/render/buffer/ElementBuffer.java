package org.tetris.render.buffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;
import org.tetris.render.base.RenderObject;

import java.nio.IntBuffer;
import java.util.List;

public class ElementBuffer extends RenderObject {

    public ElementBuffer(List<Integer> indices) {
        id = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, id);

        IntBuffer buffer = BufferUtils.createIntBuffer(indices.size() * 4);
        for (Integer index : indices) {
            buffer.put(index);
        }
        buffer.flip();

        GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, buffer, GL46.GL_STATIC_DRAW);
    }

    public void bind()
    {
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public void unbind()
    {
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void delete()
    {
        GL46.glDeleteBuffers(id);
    }
}
