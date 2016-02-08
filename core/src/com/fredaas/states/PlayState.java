package com.fredaas.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.entities.Platform;
import com.fredaas.entities.Player;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.handlers.MyContactListener;
import com.fredaas.main.Game;

public class PlayState extends GameState {
    
    public static World world;
    private Box2DDebugRenderer dr;
    private MyContactListener cl;
    private Player player;
    private boolean debug;
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    private void init() {
        Box2D.init();
        cl = new MyContactListener();
        world = new World(new Vector2(0, -20), true);
        world.setContactListener(cl);
        dr = new Box2DDebugRenderer();
        createEntities();
    }
    
    private void createEntities() {
        player = new Player(400, 300, world);
        new Platform(400, 50, world);
    }
    
    private void handlePlayerMovement() {
        player.left(Gdx.input.isKeyPressed(Input.Keys.LEFT));
        player.right(Gdx.input.isKeyPressed(Input.Keys.RIGHT));
        player.jump(Gdx.input.isKeyJustPressed(Input.Keys.SPACE));
        player.onGround(cl.isPlayerOnGround());
    }
    
    @Override
    public void update(float dt) {
        handlePlayerMovement();
        
        player.update();
        world.step(1 / 60f, 6, 2);
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.setAlpha();
            debug = !debug;
        }
        if (debug) {
            dr.render(world, Game.b2dcam.combined);
        }
    }
    
    @Override
    public void draw(ShapeRenderer sr) {
        player.draw();
    }

}
