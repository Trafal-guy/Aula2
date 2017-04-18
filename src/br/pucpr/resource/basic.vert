#version 330

in vec2 aPosition;
in vec3 aColor;

uniform mat4 uWorld;

out vec4 vColor;

void main(){
    gl_Position = vec4(aPosition, 0.0, 1.0) * uWorld;
    vColor = vec4(aColor, 1.0);
}