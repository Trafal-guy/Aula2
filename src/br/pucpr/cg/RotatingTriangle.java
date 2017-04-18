package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Util;
import br.pucpr.mage.Window;

public class RotatingTriangle implements Scene {
    private Keyboard keys = Keyboard.getInstance();
    private int vao;
	private int positions;
	private int colors;
	private int shader;

	@Override
	public void init() {		
		//Define a cor de limpeza da tela
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		//Criação do Vertex Array Object
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		//Atribuição das posições do vértice
		float[] vertexData = new float[] { 
			     0.0f,  0.5f, 
			    -0.5f, -0.5f, 
			     0.5f, -0.5f 
		};		
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(vertexData.length);
		positionBuffer.put(vertexData).flip();
		positions = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);


		//Atribuição das cores do vértice
		float[] colorData = new float[] {
				1.0f,  0.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 1.0f
		};
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colorData.length);
		colorBuffer.put(colorData).flip();
		colors = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, colors);
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);


		shader = Util.loadProgram("basic.vert", "basic.frag");
		
		//Faxina		
		glBindVertexArray(0);
	}

	@Override
	public void update(float secs) {	
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }	    
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT / GL_DEPTH_BUFFER_BIT);

		//Indica vertex array e o shader object utilizados
		glBindVertexArray(vao);
		glUseProgram(shader);
		
		
		//Associa o buffer "positions" ao atributo do shader "aPosition"
		int aPosition = glGetAttribLocation(shader, "aPosition");
		glEnableVertexAttribArray(aPosition);
		glBindBuffer(GL_ARRAY_BUFFER, positions);
		glVertexAttribPointer(aPosition, 2, GL_FLOAT, false, 0, 0);


		int aColor = glGetAttribLocation(shader, "aColor");
		glEnableVertexAttribArray(aColor);
		glBindBuffer(GL_ARRAY_BUFFER, colors);
		glVertexAttribPointer(aColor, 3, GL_FLOAT, false, 0, 0);

		//Associa os uniforms
		float angle = (float) Math.toRadians(45);
		FloatBuffer transform = BufferUtils.createFloatBuffer(16);
		new Matrix4f().rotateY(angle).get(transform);

		int uWorld = glGetUniformLocation(shader, "uWorld");
		glUniformMatrix4fv(uWorld, false, transform);
		
		//Comanda o desenho		
		glDrawArrays(GL_TRIANGLES, 0, 3);
		
		//Faxina
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(aPosition);
		glBindVertexArray(0);
		glUseProgram(0);
	}

	@Override
	public void deinit() {
	}

	public static void main(String[] args) {
		new Window(new RotatingTriangle()).show();
	}
}
