package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;

public class Triangle implements Scene {
    private Keyboard keys = Keyboard.getInstance();

	@Override
	public void init() {		
		//Define a cor de limpeza da tela
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
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
		glClear(GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void deinit() {
	}

	public static void main(String[] args) {
		new Window(new Triangle()).show();
	}
}
