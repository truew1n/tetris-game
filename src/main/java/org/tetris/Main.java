package org.tetris;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;
import org.tetris.math.*;
import org.tetris.render.material.Mesh;
import org.tetris.render.material.Shader;
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
        GLFW.glfwSwapInterval(0);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();

        Mesh squareMesh = new Mesh(
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

        Texture texture = new Texture(fromPath("assets\\textures\\block\\block_purple.png"), "UDiffuse", 0);

        Shader defaultShader = new Shader();
        defaultShader.create();
        try {
            defaultShader.load(new File("assets\\shaders\\default.vert").getAbsolutePath(), GL46.GL_VERTEX_SHADER);
            defaultShader.load(new File("assets\\shaders\\default.frag").getAbsolutePath(), GL46.GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Texture[] textures = new Texture[10];
        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture(fromPath("assets\\textures\\font\\" + i + ".png"), "UDiffuse", 0);
        }

        Shader fpsShader = new Shader();
        fpsShader.create();
        try {
            fpsShader.load(new File("assets\\shaders\\fps.vert").getAbsolutePath(), GL46.GL_VERTEX_SHADER);
            fpsShader.load(new File("assets\\shaders\\fps.frag").getAbsolutePath(), GL46.GL_FRAGMENT_SHADER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fpsShader.activate();
//        int fpsProjectionLocation = GL46.glGetUniformLocation(defaultShader.getId(), "UProjection");
//        int fpsViewLocation = GL46.glGetUniformLocation(defaultShader.getId(), "UView");
        int fpsTransformLocation = GL46.glGetUniformLocation(fpsShader.getId(), "UTransform");

        fpsShader.deactivate();

        defaultShader.activate();
        texture.bind();

        float fheight = 8.0f;
        float fwidth = (16.0f / 9.0f) * fheight;

        float zRot = 0.0f;

        Matrix4 projection = Matrix4.getOrtographic(0.1f, 10.0f, fheight / 2.0f, -fheight / 2.0f, -fwidth / 2.0f, fwidth / 2.0f);
        Matrix4 view = Matrix4.getView(new Vector3(0.0f, 0.0f, 5.0f), -90.0f, 0.0f);
        Matrix4 transform = Matrix4.getScaling(0.5f, 0.5f, 0.5f);

        int projectionLocation = GL46.glGetUniformLocation(defaultShader.getId(), "UProjection");
        int viewLocation = GL46.glGetUniformLocation(defaultShader.getId(), "UView");
        int transformLocation = GL46.glGetUniformLocation(defaultShader.getId(), "UTransform");

        GL46.glUniformMatrix4fv(projectionLocation, false, projection.flat());
        GL46.glUniformMatrix4fv(viewLocation, false, view.flat());
        GL46.glUniformMatrix4fv(transformLocation, false, transform.flat());

        GL46.glEnable(GL46.GL_DEPTH_TEST);
        GL46.glDepthFunc(GL46.GL_LESS);

        double lastTime = 0.0f;

        float deltaSum = 0.0f;
        float fpsDeltaMax = 0.5f;
        String fps = String.format("%d", (int) (1.0f / 0.1f));

        while (running && !GLFW.glfwWindowShouldClose(window)) {
            double currentTime = GLFW.glfwGetTime();
            float deltaTime = (float) (currentTime - lastTime);
            lastTime = currentTime;

            GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
            GL46.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

//            squareMesh.draw();

            if(deltaSum >= fpsDeltaMax) {
                fps = String.format("%d", (int) (1.0f / deltaTime));
                deltaSum = 0.0f;
            }

            texture.unbind();
            defaultShader.deactivate();
            fpsShader.activate();

            float xoffset = 1.0f / 50.0f;
            float yoffset = 1.0f / 25.0f;
            float start = -1.0f;


            for (int i = 0; i < fps.length(); i++) {
                int number = fps.charAt(i) - 48;
                textures[number].bind();

                Matrix4 scaling = Matrix4.getScaling(xoffset, yoffset, 0.0f);
                Matrix4 fpsTransform = Matrix4.getTranslation((start + xoffset) + 2 * (xoffset * i), start + yoffset, 0.0f);
                Matrix4 full = Matrix4.multiply(fpsTransform, scaling);

                GL46.glUniformMatrix4fv(fpsTransformLocation, false, full.flat());

                squareMesh.draw();

                textures[number].unbind();
            }

            fpsShader.deactivate();
            defaultShader.activate();

            GL46.glUniformMatrix4fv(projectionLocation, false, projection.flat());
            GL46.glUniformMatrix4fv(viewLocation, false, view.flat());
            GL46.glUniformMatrix4fv(transformLocation, false, transform.flat());
            texture.bind();


            deltaSum += deltaTime;

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        texture.unbind();
        texture.delete();

        defaultShader.deactivate();
        defaultShader.delete();

        squareMesh.delete();

        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
