package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.fredaas.main.Game;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MovingPlatform extends B2DObject {
    
    private float timer;
    private float timerDelay;
    private float timerDiff;
    private float dy;
    private float dx;
    private float width;
    private float height;
    
    public MovingPlatform(float x, float y, World world) {
        this.x = x / PPM;
        this.y = y / PPM;
        this.world = world;
        init();
    }
    
    private void init() {
        // Fields
        timer = System.nanoTime();
        timerDelay = 9000;
        width = 96 / PPM;
        height = 16 / PPM;
        dx = 0;
        dy = -1;
        
        // Body
        bdef.type = BodyType.KinematicBody;
        bdef.position.set(x, y);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width, height);
        fdef.shape = ps;
        body = world.createBody(bdef);
        body.createFixture(fdef);
        
        // Sprite
        texture = new Texture(Gdx.files.internal("maps/platform.png"));
        sprite = new Sprite(texture);
    }
    
    @Override
    public void update() {
        timerDiff = (System.nanoTime() - timer) / 1000000;
        if (timerDiff > timerDelay) {
            timer = System.nanoTime();
            dy *= -1;
        }
        body.setLinearVelocity(dx, dy);
    }

    @Override
    public void draw() {
        sb.setProjectionMatrix(Game.cam.combined);
        updateSpritePosition(-width - 1 / PPM, -height - 1 / PPM);
        sb.begin();
        sprite.draw(sb);
        sb.end();
    }

}
