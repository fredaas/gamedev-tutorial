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
		cfg.width = DEVICE_WIDTH;
		cfg.height = DEVICE_HEIGHT;
		cfg.resizable = false;
		cfg.fullscreen = true;
		new LwjglApplication(new Game(), cfg);
	}
	
}
