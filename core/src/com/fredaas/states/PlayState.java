package com.fredaas.states;

import static com.fredaas.handlers.Vars.PPM;
import java.text.DecimalFormat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private Vector3 worldCoord;
    private Vector3 screenCoord;
    private DecimalFormat df;
    private boolean debug;
    
    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    private void init() {
        loadB2DWorld();
        loadTiledStructures();
        loadEntities();
        loadFonts();
        worldCoord = new Vector3();
        screenCoord = new Vector3();
        df = new DecimalFormat("#");
    }
    
    private void loadB2DWorld() {
        Box2D.init();
        cl = new MyContactListener();
        world = new World(new Vector2(0, -20), true);
        world.setContactListener(cl);
        dr = new Box2DDebugRenderer();
    }
    
    private void loadTiledStructures() {
        tm = new TmxMapLoader().load("maps/basic-map.tmx");
        tmr = new OrthogonalTiledMapRenderer(tm);
        op = new B2DObjectProcessor(tm, world);
        op.loadTileMap("tile-map");
        op.loadTileMap("tile-bounce");
    }
    
    private void loadFonts() {
        sb = new SpriteBatch();
        gl = new GlyphLayout();
        bmf = loadFont("fonts/bmf.ttf", 40);
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
    
    private void updateMouseCoords() {
        worldCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        screenCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Game.b2dcam.unproject(worldCoord);
    }
    
    private void drawMouseCoords() {
        gl.setText(bmf, df.format(worldCoord.x * PPM) + " " + df.format(worldCoord.y * PPM));
        bmf.draw(sb, gl, 20, Game.HEIGHT - gl.height);
        gl.setText(bmf, df.format(screenCoord.x) + " " + df.format(screenCoord.y));
        bmf.draw(sb, gl, 20, Game.HEIGHT - gl.height - 30);
    }
    
    @Override
    public void update(float dt) {
        Game.cam.position.set(new Vector3(player.getPosition().scl(PPM), 0));
        Game.cam.update();
        Game.b2dcam.position.set(new Vector3(player.getPosition(), 0));
        Game.b2dcam.update();
        
        handlePlayerMovement();
        updateMouseCoords();
        
        player.update();
        platform.update();
        
        world.step(1 / 60f, 6, 2);
        
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
    }
    
    @Override
    public void draw(ShapeRenderer sr) {
        player.draw();
        platform.draw();
        
        sb.begin();
        drawMouseCoords();
        sb.end();
    }

}
