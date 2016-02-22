package com.fredaas.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.handlers.GameStateManager;

public abstract class GameState {

    protected GameStateManager gsm;
    protected BitmapFont bmf;
    protected GlyphLayout gl;
    protected SpriteBatch sb;
    
    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        init();
    }
    
    private void init() {
        sb = new SpriteBatch();
        gl = new GlyphLayout();
        bmf = loadFont("fonts/bmf.ttf", 40);
    }
    
    public abstract void update(float dt);
    public abstract void draw(ShapeRenderer sr);
    
    protected BitmapFont loadFont(String path, int size) {
        FreeTypeFontParameter par = new FreeTypeFontParameter();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(path));
        par.size = size;
        BitmapFont font = gen.generateFont(par);
        gen.dispose();
        
        return font;
    }
    
}
