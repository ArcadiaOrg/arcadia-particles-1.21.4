#version 150

uniform sampler2D Sampler0;
uniform float floated;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord);
    float gray = dot(color.rgb, vec3(0.3, 0.59, 0.11));
    fragColor = vec4(mix(color, vec4(gray), floated).rgb, color.a);
}