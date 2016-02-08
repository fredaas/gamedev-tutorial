package com.fredaas.entities;

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
    
    public B2DObject() {
        fdef = new FixtureDef();
        bdef = new BodyDef();
    }
    
    public Body getBody() {
        return body;
    }
    
    public Vector2 position() {
        return body.getPosition();
    }
    
    public abstract void update();
    public abstract void draw();

}
