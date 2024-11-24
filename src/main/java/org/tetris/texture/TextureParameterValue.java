package org.tetris.texture;

import lombok.Getter;
import org.lwjgl.opengl.GL46;

@Getter
public enum TextureParameterValue {

    LINEAR(GL46.GL_LINEAR),
    NEAREST(GL46.GL_NEAREST),
    LINEAR_MIPMAP_LINEAR(GL46.GL_LINEAR_MIPMAP_LINEAR),
    REPEAT(GL46.GL_REPEAT),
    CLAMP_TO_EDGE(GL46.GL_CLAMP_TO_EDGE);

    private final int glValue;

    TextureParameterValue(int glValue) {
        this.glValue = glValue;
    }

}