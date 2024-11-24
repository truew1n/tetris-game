package org.tetris.texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL46;
import org.tetris.buffers.RenderObject;
import org.tetris.buffers.Shader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture extends RenderObject {

    private final String uniformName;
    private final int unit;
    private final int channels;

    public Texture(String texturePath, String uniformName, int unit) {
        this.uniformName = uniformName;
        this.unit = unit;

        BufferedImage image;
        try {
            image = ImageIO.read(new File(texturePath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load texture file: " + texturePath, e);
        }

        image = flipImageVertically(image);

        int width = image.getWidth();
        int height = image.getHeight();
        this.channels = image.getColorModel().getNumComponents();

        ByteBuffer buffer = convertImageData(image, width, height, channels);

        id = GL46.glGenTextures();
        if (id == 0) {
            throw new RuntimeException("Failed to generate texture ID.");
        }

        GL46.glActiveTexture(GL46.GL_TEXTURE0 + unit);
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, id);

        setTextureParameter(TextureParameter.MIN_FILTER, TextureParameterValue.LINEAR_MIPMAP_LINEAR);
        setTextureParameter(TextureParameter.MAG_FILTER, TextureParameterValue.LINEAR);
        setTextureParameter(TextureParameter.WRAP_U, TextureParameterValue.REPEAT);
        setTextureParameter(TextureParameter.WRAP_V, TextureParameterValue.REPEAT);

        int format;
        if (channels == 4) {
            format = GL46.GL_RGBA;
        } else if (channels == 3) {
            format = GL46.GL_RGB;
        } else if (channels == 1) {
            format = GL46.GL_RED;
        } else {
            throw new IllegalArgumentException("Unsupported number of channels: " + channels);
        }

        GL46.glTexImage2D(GL46.GL_TEXTURE_2D, 0, format, width, height, 0, format, GL46.GL_UNSIGNED_BYTE, buffer);
        GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D);

        GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0);
    }

    public static Texture generate(int width, int height) {
        return new Texture(width, height);
    }

    private Texture(int width, int height) {
        this.uniformName = null;
        this.unit = 0;
        this.channels = 1;

        id = GL46.glGenTextures();
        if (id == 0) {
            throw new RuntimeException("Failed to generate texture ID.");
        }

        GL46.glActiveTexture(GL46.GL_TEXTURE0);
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, id);

        setTextureParameter(TextureParameter.MIN_FILTER, TextureParameterValue.LINEAR_MIPMAP_LINEAR);
        setTextureParameter(TextureParameter.MAG_FILTER, TextureParameterValue.LINEAR);
        setTextureParameter(TextureParameter.WRAP_U, TextureParameterValue.REPEAT);
        setTextureParameter(TextureParameter.WRAP_V, TextureParameterValue.REPEAT);

        GL46.glTexImage2D(GL46.GL_TEXTURE_2D, 0, GL46.GL_R16F, width, height, 0, GL46.GL_RED, GL46.GL_FLOAT, (ByteBuffer) null);
        GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D);

        GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0);
    }

    public void setUniform(Shader shader) {
        if (uniformName == null) {
            throw new IllegalStateException("Uniform name is not set for this texture.");
        }

        int location = GL46.glGetUniformLocation(shader.getId(), uniformName);
        if (location == -1) {
            System.err.println("Warning: Uniform '" + uniformName + "' not found in shader.");
        } else {
            GL46.glUniform1i(location, unit);
        }
    }

    public void setTextureParameter(TextureParameter type, TextureParameterValue value) {
        GL46.glTexParameteri(GL46.GL_TEXTURE_2D, type.getGlParam(), value.getGlValue());
    }

    public void bind() {
        GL46.glActiveTexture(GL46.GL_TEXTURE0 + unit);
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, id);
    }

    public void unbind() {
        GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0);
    }

    public void delete() {
        GL46.glDeleteTextures(id);
    }

    private BufferedImage flipImageVertically(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage flipped = new BufferedImage(width, height, image.getType());
        for (int y = 0; y < height; y++) {
            flipped.setRGB(0, y, width, 1, image.getRGB(0, height - y - 1, width, 1, null, 0, width), 0, width);
        }
        return flipped;
    }

    private ByteBuffer convertImageData(BufferedImage image, int width, int height, int channels) {
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * channels);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = pixels[y * width + x];

                byte a = (byte) ((pixel >> 24) & 0xFF);
                byte r = (byte) ((pixel >> 16) & 0xFF);
                byte g = (byte) ((pixel >> 8) & 0xFF);
                byte b = (byte) (pixel & 0xFF);

                if (channels == 4) {
                    buffer.put(r).put(g).put(b).put(a);
                } else if (channels == 3) {
                    buffer.put(r).put(g).put(b);
                } else if (channels == 1) {
                    // Assuming grayscale
                    float gray = (0.299f * (r & 0xFF) + 0.587f * (g & 0xFF) + 0.114f * (b & 0xFF)) / 255.0f;
                    buffer.put((byte) (gray * 255));
                }
            }
        }

        buffer.flip();
        return buffer;
    }

}
