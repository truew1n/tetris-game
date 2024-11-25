package org.tetris.render.material;

import lombok.AllArgsConstructor;
import org.lwjgl.opengl.GL46;
import org.tetris.render.base.Vertex;
import org.tetris.render.base.VertexLinkData;
import org.tetris.render.buffer.ElementBuffer;
import org.tetris.render.buffer.VertexArray;
import org.tetris.render.buffer.VertexBuffer;

import java.util.List;

@AllArgsConstructor
public class Mesh {
    private boolean usable;
    private VertexArray vertexArray;
    private int triangleCount;

    public Mesh(List<Vertex> vertices, List<Integer> indices) {
        vertexArray = new VertexArray();
        vertexArray.bind();

        VertexBuffer vertexBuffer = new VertexBuffer(vertices);
        ElementBuffer elementBuffer = new ElementBuffer(indices);

        for (VertexLinkData data : Vertex.vertexLinkData) {
            vertexArray.link(vertexBuffer, data.layout, data.componentCount, data.type, data.stride, data.offset);
        }

        vertexArray.unbind();

        triangleCount = indices.size();
        usable = true;
    }

    public void draw() {
        if(!usable) return;

        vertexArray.bind();
        GL46.glDrawElements(GL46.GL_TRIANGLES, triangleCount, GL46.GL_UNSIGNED_INT, 0);
        vertexArray.unbind();
    }

    public void delete() {
        vertexArray.delete();
    }
}
