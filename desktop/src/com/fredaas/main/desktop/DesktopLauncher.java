package com.fredaas.main.desktop;

import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.fredaas.main.Game;

public class DesktopLauncher {
    
	public static void main (String[] arg) {
	    int DEVICE_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	    int DEVICE_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 800;
		cfg.height = 600;
		cfg.resizable = false;
		cfg.fullscreen = false;
		new LwjglApplication(new Game(), cfg);
	}
	
}
