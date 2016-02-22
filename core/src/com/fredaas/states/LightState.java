package com.fredaas.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.entities.Light;
import com.fredaas.entities.LightBulb;
import com.fredaas.handlers.B2DObjectProcessor;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.main.Game;

import box2dLight.RayHandler;

public class LightState extends GameState {
    
    private TiledMap tm;
    private RayHandler rh;
    private LightBulb lb;
    private Light light;
    private World world;
    private B2DObjectProcessor op;
    private Box2DDebugRenderer dr;
    
    public LightState(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    private void init() {
        loadBox2DWorld(0, 0);
        loadTiledStructures();
        loadLight();
    }
    
    private void loadBox2DWorld(float x, float y) {
        Box2D.init();
        world = new World(new Vector2(x, y), true);
        dr = new Box2DDebugRenderer();
    }
    
    private void loadTiledStructures() {
        tm = new TmxMapLoader().load("maps/map-light.tmx");
        op = new B2DObjectProcessor(tm, world);
        op.loadObjects();
    }
    
    private void loadLight() {
        rh = new RayHandler(world);
        lb = new LightBulb(world);
        light = new Light(0, 0, new Color(0, 100, 0, 1), rh);
        light.attachTo(lb.getBody());
    }
    
    public void update(float dt) {
        rh.setCombinedMatrix(Game.b2dcam);
        rh.updateAndRender();
        
        Game.b2dcam.position.set(lb.getPosition(), 0);
        Game.b2dcam.update();
        
        updateMouseCoords();
        
        world.step(1 / 60f, 6, 2);
        
        dr.render(world, Game.b2dcam.combined);
        
        lb.setPosition(worldCoord.x, worldCoord.y);
    }

    public void draw(ShapeRenderer sr) {
        sb.begin();
        drawMouseCoords();
        sb.end();
    }

}
