package org.tetris.render.material;

import lombok.Getter;
import org.lwjgl.opengl.GL46;

@Getter
public enum TextureParameterValue {

    // Filtering Modes
    LINEAR(GL46.GL_LINEAR),
    NEAREST(GL46.GL_NEAREST),
    LINEAR_MIPMAP_LINEAR(GL46.GL_LINEAR_MIPMAP_LINEAR),
    LINEAR_MIPMAP_NEAREST(GL46.GL_LINEAR_MIPMAP_NEAREST),
    NEAREST_MIPMAP_LINEAR(GL46.GL_NEAREST_MIPMAP_LINEAR),
    NEAREST_MIPMAP_NEAREST(GL46.GL_NEAREST_MIPMAP_NEAREST),

    // Wrapping Modes
    REPEAT(GL46.GL_REPEAT),
    MIRRORED_REPEAT(GL46.GL_MIRRORED_REPEAT),
    CLAMP_TO_EDGE(GL46.GL_CLAMP_TO_EDGE),
    CLAMP_TO_BORDER(GL46.GL_CLAMP_TO_BORDER),

    // Comparison Modes
    COMPARE_REF_TO_TEXTURE(GL46.GL_COMPARE_REF_TO_TEXTURE),
    NONE(GL46.GL_NONE), // Default comparison mode

    // Swizzle values (used for swizzling channels)
    SWIZZLE_ZERO(GL46.GL_ZERO),
    SWIZZLE_ONE(GL46.GL_ONE),
    SWIZZLE_RED(GL46.GL_RED),
    SWIZZLE_GREEN(GL46.GL_GREEN),
    SWIZZLE_BLUE(GL46.GL_BLUE),
    SWIZZLE_ALPHA(GL46.GL_ALPHA);

    private final int glValue;

    TextureParameterValue(int glValue) {
        this.glValue = glValue;
    }
}
