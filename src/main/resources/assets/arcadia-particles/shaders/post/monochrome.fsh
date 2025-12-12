#version 150

uniform sampler2D InSampler;

uniform float opacity;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(InSampler, texCoord);
    float gray = dot(color.rgb, vec3(0.3, 0.59, 0.11));
    fragColor = vec4(mix(color, vec4(gray), opacity).rgb, color.a);
}