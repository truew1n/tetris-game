package org.tetris.render.material;

import org.lwjgl.opengl.GL46;

public enum TextureParameter {
    MIN_FILTER(GL46.GL_TEXTURE_MIN_FILTER),
    MAG_FILTER(GL46.GL_TEXTURE_MAG_FILTER),
    WRAP_U(GL46.GL_TEXTURE_WRAP_S),
    WRAP_V(GL46.GL_TEXTURE_WRAP_T),
    WRAP_T(GL46.GL_TEXTURE_WRAP_R), // Wrap mode for 3D textures
    BASE_LEVEL(GL46.GL_TEXTURE_BASE_LEVEL),
    MAX_LEVEL(GL46.GL_TEXTURE_MAX_LEVEL),
    SWIZZLE_R(GL46.GL_TEXTURE_SWIZZLE_R),
    SWIZZLE_G(GL46.GL_TEXTURE_SWIZZLE_G),
    SWIZZLE_B(GL46.GL_TEXTURE_SWIZZLE_B),
    SWIZZLE_A(GL46.GL_TEXTURE_SWIZZLE_A),
    SWIZZLE_RGBA(GL46.GL_TEXTURE_SWIZZLE_RGBA),
    COMPARE_MODE(GL46.GL_TEXTURE_COMPARE_MODE),
    COMPARE_FUNC(GL46.GL_TEXTURE_COMPARE_FUNC),
    MAX_LOD(GL46.GL_TEXTURE_MAX_LOD),
    MIN_LOD(GL46.GL_TEXTURE_MIN_LOD);

    private final int glParam;

    TextureParameter(int glParam) {
        this.glParam = glParam;
    }

    public int getGlParam() {
        return glParam;
    }
}
