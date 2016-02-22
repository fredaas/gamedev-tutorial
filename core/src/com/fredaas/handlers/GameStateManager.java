package com.fredaas.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.states.GameState;
import com.fredaas.states.LightState;
import com.fredaas.states.PlayState;

public class GameStateManager {
    
    private GameState gs;
    private boolean pause;
    
    public static enum State {
        PLAY,
        LIGHT,
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
        }
    }
    
    private void selectNewState() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            setState(State.PLAY);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            setState(State.LIGHT);
        }
    }
    
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause = !pause;
        }
        if (!pause) {
            gs.update(dt);
            selectNewState();
        }
    }
    
    public void draw(ShapeRenderer sr) {
        gs.draw(sr);
    }

}
