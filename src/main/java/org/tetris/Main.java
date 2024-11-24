package org.tetris;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;
import org.tetris.buffers.ElementBuffer;
import org.tetris.buffers.Shader;
import org.tetris.buffers.VertexArray;
import org.tetris.buffers.VertexBuffer;
import org.tetris.math.Vector2;
import org.tetris.math.Vector3;
import org.tetris.math.Vertex;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    private static boolean running = true;

    private static int width = 1920;
    private static int height = 1080;

    public static void main(String[] args) {
        if (!GLFW.glfwInit()) {
            System.err.println("Failed to initialize GLFW");
            System.exit(-1);
        }

        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        long window = GLFW.glfwCreateWindow(width, height, "Tetris Game", 0, 0);
        if (window == 0) {
            System.err.println("Failed to create GLFW window");
            System.exit(-1);
        }

        GLFW.glfwSetFramebufferSizeCallback(window, (l, width, height) -> {
            GL46.glViewport(0, 0, width, height);
            Main.width = width;
            Main.height = height;
        });

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();


        VertexArray vertexArray = new VertexArray();
        vertexArray.bind();

        VertexBuffer vertexBuffer = new VertexBuffer(
            List.of(
                new Vertex(new Vector3(-0.5f, -0.5f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector2(0.0f, 0.0f)),
                new Vertex(new Vector3(0.5f, -0.5f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector2(1.0f, 0.0f)),
                new Vertex(new Vector3(0.5f, 0.5f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector2(1.0f, 1.0f)),
                new Vertex(new Vector3(-0.5f, 0.5f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector2(0.0f, 1.0f))
            )
        );

        ElementBuffer elementBuffer = new ElementBuffer(
            List.of(
                    0, 1, 2,
                    0, 2, 3
            )
        );

        vertexArray.link(vertexBuffer, 0, 3, GL46.GL_FLOAT, Vertex.SIZE, 0);
        vertexArray.link(vertexBuffer, 1, 3, GL46.GL_FLOAT, Vertex.SIZE, 3 * 4);
        vertexArray.link(vertexBuffer, 2, 2, GL46.GL_FLOAT, Vertex.SIZE, 6 * 4);
        vertexArray.unbind();

        Shader shader = new Shader();
        shader.create();
        try {
            shader.load(new File("assets\\shaders\\default.vert").getAbsolutePath(), GL46.GL_VERTEX_SHADER);
            shader.load(new File("assets\\shaders\\default.frag").getAbsolutePath(), GL46.GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        shader.activate();



        while (running && !GLFW.glfwWindowShouldClose(window)) {
            GL46.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

            vertexArray.bind();

            GL46.glDrawElements(GL46.GL_TRIANGLES, 6, GL46.GL_UNSIGNED_INT, 0);

            vertexArray.unbind();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
