package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class B2DObject {
    
    protected float x;
    protected float y;
    protected World world;
    protected Body body;
    protected FixtureDef fdef;
    protected BodyDef bdef;
    protected Sprite sprite;
    protected Texture texture;
    protected SpriteBatch sb;
    protected float alpha;
    
    public B2DObject() {
        fdef = new FixtureDef();
        bdef = new BodyDef();
        sb = new SpriteBatch();
        alpha = 1;
    }
    
    public Body getBody() {
        return body;
    }
    
    public Vector2 getPosition() {
        return body.getPosition();
    }
    
    protected void updateSpritePosition(float xOffset, float yOffset) {
        sprite.setPosition(
                (body.getPosition().x + xOffset) * PPM,
                (body.getPosition().y + yOffset) * PPM);
    }
    
    public void setAlpha() {
        alpha = alpha == 1 ? 0 : 1;
        sprite.setAlpha(alpha);
    }
    
    protected TextureRegion[] getTextureRegion(String path, int width, int height) {
        return TextureRegion.split(
                new Texture(Gdx.files.internal(path)), 
                width, 
                height)[0];
    }
    
    public abstract void update();
    public abstract void draw();

}
