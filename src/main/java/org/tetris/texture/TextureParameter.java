package org.tetris.texture;

import org.lwjgl.opengl.GL46;

public enum TextureParameter {
    MIN_FILTER(GL46.GL_TEXTURE_MIN_FILTER),
    MAG_FILTER(GL46.GL_TEXTURE_MAG_FILTER),
    WRAP_U(GL46.GL_TEXTURE_WRAP_S),
    WRAP_V(GL46.GL_TEXTURE_WRAP_T);

    private final int glParam;

    TextureParameter(int glParam) {
        this.glParam = glParam;
    }

    public int getGlParam() {
        return glParam;
    }
}