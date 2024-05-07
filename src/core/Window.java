package core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.opengl.GL33.*;

import org.lwjgl.opengl.GL;

import java.awt.Dimension;

public class Window {
	
	private static long windowCount = 0;
	private static boolean callouts;
	
	public static long windowGetCount() {
		return windowCount;
	}
	public static void windowEnableCallouts() {
		callouts = true;
	}
	public static void windowDisableCallouts() {
		callouts = false;
	}
	
	@SuppressWarnings("unused")
	private String title;
	private long address;
	
	public Window(int width, int height, String title) {
		this.title = title;
		

		if (callouts) {
			System.out.println("Creating window " + title);
		}

		glfwInit();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

		address = glfwCreateWindow(width, height, title, NULL, NULL);

		if (address == NULL) {
			System.out.println("Failed to create window " + title);
			if (windowCount < 1) {
				glfwTerminate();
				System.out.println("Terminating GLFW");
			}
		}

		glfwMakeContextCurrent(address);
		
		GL.createCapabilities();
		
		glViewport(0, 0, width, height);

		glfwSetFramebufferSizeCallback(address, (long fb_window, int fb_width, int fb_height) -> {
			glViewport(0, 0, fb_width, fb_height);
		});
		glfwSwapInterval(1);
		glfwSetInputMode(address, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		glfwShowWindow(address);
		
		windowCount++;
	}
	
	public void enableVSync() {
		glfwSwapInterval(1);
	}
	public void disableVSync() {
		glfwSwapInterval(0);
	}
	public void showMouse() {
		glfwSetInputMode(address, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}
	public void hideMouse() {
		glfwSetInputMode(address, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}
	public void show() {
		glfwShowWindow(address);
	}
	public void hide() {
		glfwHideWindow(address);
	}
	public Dimension getSize() {
		int width[] = {0};
		int height[] = {0};
		glfwGetWindowSize(address, width, height);
		return new Dimension(width[0], height[0]);
	}
	public long getAddress() {
		return address;
	}
	public void makeCurrentContext() {
		glfwMakeContextCurrent(address);
	}
	public boolean shouldClose() {
		return glfwWindowShouldClose(address);
	}
	public boolean keyPressed(short key) {
		return (glfwGetKey(address, key) == GLFW_PRESS);
	}
	public boolean mousePressed(short button) {
		return (glfwGetMouseButton(address, button) == GLFW_PRESS);
	}
	
	public void flush() {
		glfwSwapBuffers(address);
		glfwPollEvents();
	}
	
	public static void windowTerminate() {
		glfwTerminate();
	}
}
