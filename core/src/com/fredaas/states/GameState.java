package com.fredaas.states;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.handlers.GameStateManager;

public abstract class GameState {

    protected GameStateManager gsm;
    
    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }
    
    public abstract void update();
    public abstract void draw(ShapeRenderer sr);
    
}
