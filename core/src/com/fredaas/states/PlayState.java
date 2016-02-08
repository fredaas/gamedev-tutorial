package com.fredaas.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.entities.Player;
import com.fredaas.handlers.GameStateManager;

public class PlayState extends GameState {
    
    private Player player;
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    private void init() {
        player = new Player(100, 100);
    }
    
    private void handlePlayerMovement() {
        player.left(Gdx.input.isKeyPressed(Input.Keys.LEFT));
        player.right(Gdx.input.isKeyPressed(Input.Keys.RIGHT));
        player.down(Gdx.input.isKeyPressed(Input.Keys.DOWN));
        player.up(Gdx.input.isKeyPressed(Input.Keys.UP));
    }
    
    @Override
    public void update(float dt) {
        handlePlayerMovement();
        player.update(dt);
    }
    
    @Override
    public void draw(ShapeRenderer sr) {
        player.draw(sr);
    }

}
