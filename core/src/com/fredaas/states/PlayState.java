package com.fredaas.states;

import static com.fredaas.handlers.Vars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.entities.Player;
import com.fredaas.handlers.B2DObjectProcessor;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.handlers.MyContactListener;
import com.fredaas.main.Game;

public class PlayState extends GameState {
    
    private World world;
    private TiledMapRenderer tmr;
    private TiledMap tm;
    private B2DObjectProcessor op;
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
        tm = new TmxMapLoader().load("maps/basic-map.tmx");
        tmr = new OrthogonalTiledMapRenderer(tm);
        new B2DObjectProcessor(tm, world).loadTileMap("tile-layer");
        createEntities();
    }
    
    private void createEntities() {
        for (MapObject obj : tm.getLayers().get("player").getObjects()) {
            createPlayer((EllipseMapObject) obj);
        }
    }
    
    private void createPlayer(EllipseMapObject obj) {
        player = new Player(
                obj.getEllipse().x, 
                obj.getEllipse().y,
                world);
    }
    
    private void handlePlayerMovement() {
        player.left(Gdx.input.isKeyPressed(Input.Keys.LEFT));
        player.right(Gdx.input.isKeyPressed(Input.Keys.RIGHT));
        player.jump(Gdx.input.isKeyJustPressed(Input.Keys.SPACE));
        player.onGround(cl.isPlayerOnGround());
    }
    
    @Override
    public void update(float dt) {
        Game.cam.position.set(new Vector3(player.getPosition().scl(PPM), 0));
        Game.cam.update();
        Game.b2dcam.position.set(new Vector3(player.getPosition(), 0));
        Game.b2dcam.update();
        
        handlePlayerMovement();
        
        player.update();
        
        world.step(1 / 60f, 6, 2);
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.setAlpha();
            debug = !debug;
        }
        if (debug) {
            dr.render(world, Game.b2dcam.combined);
        } else {
            tmr.setView(Game.cam);
            tmr.render();
        }
    }
    
    @Override
    public void draw(ShapeRenderer sr) {
        player.draw();
    }

}
