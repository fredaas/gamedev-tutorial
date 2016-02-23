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
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.entities.MovingPlatform;
import com.fredaas.entities.Player;
import com.fredaas.handlers.B2DObjectProcessor;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.handlers.MyContactListener;
import com.fredaas.main.Game;

public class PlayState extends GameState {
    
    private World world;
    private TiledMapRenderer tmr;
    private TiledMap tm;
    private Box2DDebugRenderer dr;
    private MyContactListener cl;
    private Player player;
    private MovingPlatform platform;
    private B2DObjectProcessor op;
    private boolean debug;
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    private void init() {
        loadBox2DWorld(0, -20);
        loadTiledStructures();
        loadEntities();
    }
    
    private void loadBox2DWorld(float x, float y) {
        Box2D.init();
        cl = new MyContactListener();
        world = new World(new Vector2(x, y), true);
        world.setContactListener(cl);
        dr = new Box2DDebugRenderer();
    }
    
    private void loadTiledStructures() {
        tm = new TmxMapLoader().load("maps/map-play.tmx");
        tmr = new OrthogonalTiledMapRenderer(tm);
        op = new B2DObjectProcessor(tm, world);
        op.loadTileMap("tile-map");
        op.loadTileMap("tile-bounce");
    }
    
    private void loadEntities() {
        createPlayer((EllipseMapObject) tm.getLayers().get("player").getObjects().get("player"));
        for (MapObject obj : tm.getLayers().get("platform").getObjects()) {
            createPlatform((EllipseMapObject) obj);
        }
    }
    
    private void createPlayer(EllipseMapObject obj) {
        player = new Player(
                obj.getEllipse().x, 
                obj.getEllipse().y,
                world);
    }
    
    private void createPlatform(EllipseMapObject obj) {
        platform = new MovingPlatform(
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
    public void update() {
        Game.cam.position.set(player.getPosition().scl(PPM), 0);
        Game.cam.update();
        Game.b2dcam.position.set(player.getPosition(), 0);
        Game.b2dcam.update();
        updateMouseCoords();
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.setAlpha();
            platform.setAlpha();
            debug = !debug;
        }
        if (debug) {
            dr.render(world, Game.b2dcam.combined);
        } else {
            tmr.setView(Game.cam);
            tmr.render();
        }
        
        player.update();
        platform.update();
        world.step(1 / 60f, 6, 2);
        handlePlayerMovement();
    }
    
    @Override
    public void draw(ShapeRenderer sr, float dt) {
        player.draw();
        platform.draw();
        sb.setProjectionMatrix(Game.hudcam.combined);
        sb.begin();
        drawMouseCoords();
        sb.end();
    }

}
