package com.fredaas.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.handlers.GameStateManager;

public class Game implements ApplicationListener {
	
    private static int WIDTH;
    private static int HEIGHT;
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private GameStateManager gsm;
    
	@Override
	public void create () {
	    WIDTH = Gdx.graphics.getWidth();
	    HEIGHT = Gdx.graphics.getHeight();
	    cam = new OrthographicCamera(WIDTH, HEIGHT);
	    cam.setToOrtho(true);
	    sr = new ShapeRenderer();
	    gsm = new GameStateManager();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update();
		gsm.draw(sr);
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
