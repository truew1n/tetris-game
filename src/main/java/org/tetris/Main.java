package org.tetris;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;
import org.tetris.math.Matrix4;
import org.tetris.render.material.Mesh;
import org.tetris.render.material.Shader;
import org.tetris.math.Vector2;
import org.tetris.math.Vector3;
import org.tetris.render.base.Vertex;
import org.tetris.render.material.Texture;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    private static boolean running = true;

    private static int width = 1920;
    private static int height = 1080;

    private static String fromPath(String filepath) {
        return new File(filepath).getAbsolutePath();
    }
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

        Mesh blockMesh = new Mesh(
            List.of(
                    new Vertex(new Vector3(-1.0f, -1.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector2(0.0f, 1.0f)),
                    new Vertex(new Vector3(1.0f, -1.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector2(1.0f, 1.0f)),
                    new Vertex(new Vector3(1.0f, 1.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector2(1.0f, 0.0f)),
                    new Vertex(new Vector3(-1.0f, 1.0f, 0.0f), new Vector3(0.0f, 0.0f, 1.0f), new Vector2(0.0f, 0.0f))
            ),
            List.of(
                    0, 1, 2,
                    2, 3, 0
            )
        );

        Texture texture = new Texture(fromPath("assets\\textures\\block_purple.png"), "UDiffuse", 0);

        Shader shader = new Shader();
        shader.create();
        try {
            shader.load(new File("assets\\shaders\\default.vert").getAbsolutePath(), GL46.GL_VERTEX_SHADER);
            shader.load(new File("assets\\shaders\\default.frag").getAbsolutePath(), GL46.GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        shader.activate();
        texture.bind();

        float fheight = 8.0f;
        float fwidth = (16.0f / 9.0f) * fheight;

        Matrix4 projection = Matrix4.getOrtographic(0.1f, 10.0f, -fheight / 2.0f, fheight / 2.0f, -fwidth / 2.0f, fwidth / 2.0f);
        Matrix4 view = Matrix4.getView(new Vector3(0.0f, 0.0f, 5.0f), -90.0f, 0.0f);
        Matrix4 transform = Matrix4.getScaling(0.5f, 0.5f, 0.5f);

        System.out.println(projection);
        System.out.println(view);
        System.out.println(transform);

        int projectionLocation = GL46.glGetUniformLocation(shader.getId(), "UProjection");
        int viewLocation = GL46.glGetUniformLocation(shader.getId(), "UView");
        int transformLocation = GL46.glGetUniformLocation(shader.getId(), "UTransform");

        GL46.glUniformMatrix4fv(projectionLocation, false, projection.flat());
        GL46.glUniformMatrix4fv(viewLocation, false, view.flat());
        GL46.glUniformMatrix4fv(transformLocation, false, transform.flat());

        while (running && !GLFW.glfwWindowShouldClose(window)) {
            GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
            GL46.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

            blockMesh.draw();

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        texture.unbind();
        texture.delete();

        shader.deactivate();
        shader.delete();

        blockMesh.delete();

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
