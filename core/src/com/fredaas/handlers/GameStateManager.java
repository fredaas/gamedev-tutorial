package com.fredaas.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.states.GameState;
import com.fredaas.states.LightState;
import com.fredaas.states.ParticleState;
import com.fredaas.states.PlayState;

public class GameStateManager {
    
    private GameState gs;
    private boolean pause;
    
    public static enum State {
        PLAY,
        LIGHT,
        PARTICLE;
    }
    
    public GameStateManager() {
        setState(State.PLAY);
    }
    
    public void setState(State state) {
        switch (state) {
            case PLAY:
                gs = new PlayState(this);
                break;
            case LIGHT:
                gs = new LightState(this);
                break;
            case PARTICLE:
                gs = new ParticleState(this);
        }
    }
    
    private void selectNewState() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            setState(State.PLAY);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            setState(State.LIGHT);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            setState(State.PARTICLE);
        }
    }
    
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
        if (!pause) {
            gs.update();
            selectNewState();
        }
    }
    
    public void draw(ShapeRenderer sr, float dt) {
        gs.draw(sr, dt);
    }

}
