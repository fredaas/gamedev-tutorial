package com.fredaas.states;

import static com.fredaas.handlers.Vars.PPM;
import java.text.DecimalFormat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.main.Game;

public abstract class GameState {

    protected GameStateManager gsm;
    protected BitmapFont bmf;
    protected GlyphLayout gl;
    protected SpriteBatch sb;
    protected Vector3 worldCoord;
    protected Vector3 screenCoord;
    protected DecimalFormat df;
    
    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }
    
    private void init() {
        sb = new SpriteBatch();
        gl = new GlyphLayout();
        bmf = loadFont("fonts/bmf.ttf", 25);
        worldCoord = new Vector3();
        screenCoord = new Vector3();
        df = new DecimalFormat("#");
    }
    
    public abstract void update();
    public abstract void draw(ShapeRenderer sr, float dt);
    
    protected void updateMouseCoords() {
        worldCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        screenCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Game.b2dcam.unproject(worldCoord);
    }
    
    protected void drawMouseCoords() {
        gl.setText(bmf, "POS-WORLD: " + df.format(worldCoord.x * PPM) + " " + df.format(worldCoord.y * PPM));
        bmf.draw(sb, gl, 20, Game.HEIGHT - gl.height);
        gl.setText(bmf, "POS-SCREEN: " + df.format(screenCoord.x) + " " + df.format(screenCoord.y));
        bmf.draw(sb, gl, 20, Game.HEIGHT - gl.height - 30);
    }
    
    private BitmapFont loadFont(String path, int size) {
        FreeTypeFontParameter par = new FreeTypeFontParameter();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
        par.size = size;
        BitmapFont font = gen.generateFont(par);
        gen.dispose();
        
        return font;
    }
    
}
