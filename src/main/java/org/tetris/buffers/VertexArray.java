package org.tetris.buffers;

import org.lwjgl.opengl.GL46;

public class VertexArray extends RenderObject {

    public VertexArray() {
        id = GL46.glGenVertexArrays();
    }

    public void link(VertexBuffer vertexBuffer, int layout, int componentCount, int type, int stride, long offset)
    {
        vertexBuffer.bind();
        GL46.glVertexAttribPointer(layout, componentCount, type, false, stride, offset);
        GL46.glEnableVertexAttribArray(layout);
        vertexBuffer.unbind();
    }

    public void bind()
    {
        GL46.glBindVertexArray(id);
    }

    public void unbind()
    {
        GL46.glBindVertexArray(0);
    }

    public void delete()
    {
        GL46.glDeleteVertexArrays(id);
    }
}
