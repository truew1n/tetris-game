package org.tetris.render.material;

import org.lwjgl.opengl.GL46;
import org.tetris.math.Matrix4;
import org.tetris.render.base.RenderObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Shader extends RenderObject {

    public byte load(String filepath, int type) throws IOException {
        String content = Files.readString(Path.of(filepath));

        int shaderId = compile(content, type);

        if (shaderId == 0) {
            return 0;
        }

        GL46.glAttachShader(id, shaderId);
        GL46.glLinkProgram(id);
        GL46.glValidateProgram(id);

        GL46.glDeleteShader(shaderId);

        return 1;
    }

    private String getShaderTypeString(int type) {
        switch (type) {
            case GL46.GL_VERTEX_SHADER:
                return "Vertex Shader";
            case GL46.GL_FRAGMENT_SHADER:
                return "Fragment Shader";
            default:
                return "Unknown Shader";
        }
    }

    public int compile(String source, int type) {
        int shaderId = GL46.glCreateShader(type);

        GL46.glShaderSource(shaderId, source);
        GL46.glCompileShader(shaderId);

        int compileStatus = GL46.glGetShaderi(shaderId, GL46.GL_COMPILE_STATUS);
        if (compileStatus == GL46.GL_FALSE) {
            int infoLogLength = GL46.glGetShaderi(shaderId, GL46.GL_INFO_LOG_LENGTH);

            String infoLog = GL46.glGetShaderInfoLog(shaderId, infoLogLength);

            System.err.printf("Failed to compile %s shader!\n%s\n", getShaderTypeString(type), infoLog);

            GL46.glDeleteShader(shaderId);

            return 0;
        }

        return shaderId;
    }

    public void create()
    {
        id = GL46.glCreateProgram();
    }

    public void activate()
    {
        GL46.glUseProgram(id);
    }

    public void deactivate()
    {
        GL46.glUseProgram(0);
    }

    public void delete()
    {
        GL46.glDeleteShader(id);
    }
}
