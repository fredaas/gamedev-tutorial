package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform extends B2DObject {
    
    public Platform(float x, float y, World world) {
        this.x = x / PPM;
        this.y = y / PPM;
        this.world = world;
        init();
    }
    
    private void init() {
        bdef.type = BodyType.StaticBody;
        bdef.position.set(x, y);
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(300 / PPM, 10 / PPM);
        fdef.shape = ps;
        world.createBody(bdef).createFixture(fdef);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw() {
    }

}
