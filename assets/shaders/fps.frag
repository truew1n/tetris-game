#version 460 core

out vec4 EFragmentColor;

in vec3 EPosition;
in vec3 ENormal;
in vec2 EUV;

uniform sampler2D UDiffuse;

void main()
{
    vec4 color = texture(UDiffuse, EUV);

    float intensity = 0.299 * color.r + 0.587 * color.g + 0.114 * color.b;

    if(intensity > 0.5) {
        discard;
    }

    color = vec4(1.0f, 1.0f, 1.0f, 1.0f);

    EFragmentColor = color;
}