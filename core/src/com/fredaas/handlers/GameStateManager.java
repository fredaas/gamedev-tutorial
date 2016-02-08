package com.fredaas.handlers;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.states.GameState;
import com.fredaas.states.PlayState;

public class GameStateManager {
    
    private GameState gs;
    
    public static enum State {
        PLAY,
        MENU,
        GAMEOVER;
    }
    
    public GameStateManager() {
        setState(State.PLAY);
    }
    
    public void setState(State state) {
        switch (state) {
            case PLAY:
                gs = new PlayState(this);
                break;
            case MENU:
                break;
            case GAMEOVER:
                break;
        }
    }
    
    public void update(float dt) {
        gs.update(dt);
    }
    
    public void draw(ShapeRenderer sr) {
        gs.draw(sr);
    }

}
