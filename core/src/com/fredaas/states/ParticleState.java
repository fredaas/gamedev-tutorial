package com.fredaas.states;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.fredaas.handlers.GameStateManager;
import com.fredaas.main.Game;

public class ParticleState extends GameState {
    
    private ParticleEffect particleStar;
    private ParticleEffect particleFlame;
    private ParticleEffectPool particlePool;
    private ArrayList<PooledEffect> effects;
    
    public ParticleState(GameStateManager gsm) {
        super(gsm);
        init();
    }
    
    private void init() {
        loadParticleEffects();
    }
    
    private void loadParticleEffects() {
        particleStar = new ParticleEffect();
        particleFlame = new ParticleEffect();
        particleStar.load(Gdx.files.internal("effects/explosion-star.p"), Gdx.files.internal("effects"));
        particleFlame.load(Gdx.files.internal("effects/explosion-flame.p"), Gdx.files.internal(""));
        particlePool = loadPool(particleStar, 0, 100);
        effects = new ArrayList<PooledEffect>();
    }
    
    private ParticleEffectPool loadPool(ParticleEffect effect, int min, int max) {
        if (particlePool != null && effects != null) {
            effects.clear();
            particlePool.clear();
        }
        return new ParticleEffectPool(effect, min, max);
    }
    
    private void drawParticleInfo() {
        gl.setText(bmf, "POS-SCREEN: " + df.format(screenCoord.x) + " " + df.format(screenCoord.y));
        bmf.draw(sb, gl, 20, Game.HEIGHT - gl.height);
        gl.setText(bmf, "ACTIVE: " + effects.size());
        bmf.draw(sb, gl, 20, Game.HEIGHT - gl.height - 30);
        gl.setText(bmf, "POOL: " + particlePool.getFree());
        bmf.draw(sb, gl, 20, Game.HEIGHT - gl.height - 60);
    }
    
    private void drawParticleEffects(float dt) {
        for (int i = 0; i < effects.size(); i++) {
            PooledEffect e = effects.get(i);
            e.draw(sb, dt);
            if (e.isComplete() || effects.size() > particlePool.max - 1) {
                effects.remove(e);
                e.free();
                i--;
            }
        }
    }
    
    private void setEffect(float x, float y) {
        PooledEffect e = particlePool.obtain();
        e.setPosition(x, y);
        effects.add(e);
    }
    
    private void updateScreenCoords() {
        screenCoord.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        Game.cam.unproject(screenCoord);
    }
    
    @Override
    public void update() {
        updateScreenCoords();
        
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            setEffect(screenCoord.x, screenCoord.y);
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            particlePool = loadPool(particleStar, 0, 100);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            particlePool = loadPool(particleFlame, 0, 100);
        }
    }

    @Override
    public void draw(ShapeRenderer sr, float dt) {
        sb.begin();
        sb.setProjectionMatrix(Game.hudcam.combined);
        drawParticleInfo();
        sb.setProjectionMatrix(Game.cam.combined);
        drawParticleEffects(dt);
        sb.end();
    }

}
