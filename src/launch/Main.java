package launch;

import static org.lwjgl.opengl.GL33.*;

import static core.Window.*;

import core.Window;

public class Main {

	public static void main(String[] args) {
		Window window = new Window(800, 600, "OpenGL");
		
		while (!window.shouldClose()) {
			glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT);
			
			window.flush();
		}
		
		windowTerminate();
	}

}
