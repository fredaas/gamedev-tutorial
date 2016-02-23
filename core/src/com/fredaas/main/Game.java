package com.fredaas.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.handlers.GameStateManager;
import static com.fredaas.handlers.Vars.PPM;

public class Game implements ApplicationListener {
	
    public static int WIDTH;
    public static int HEIGHT;
    public static OrthographicCamera cam;
    public static OrthographicCamera hudcam;
    public static OrthographicCamera b2dcam;
    private ShapeRenderer sr;
    private GameStateManager gsm;
    
	@Override
	public void create () {
	    WIDTH = Gdx.graphics.getWidth();
	    HEIGHT = Gdx.graphics.getHeight();
	    cam = new OrthographicCamera();
	    cam.setToOrtho(false, WIDTH, HEIGHT);
	    b2dcam = new OrthographicCamera();
	    b2dcam.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);
	    hudcam = new OrthographicCamera();
        hudcam.setToOrtho(false, WIDTH, HEIGHT);
	    sr = new ShapeRenderer();
	    gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update();
		gsm.draw(sr, Gdx.graphics.getDeltaTime());
	}

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
	
}
