package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class LightBulb extends B2DObject {
    
    private float radius;
    private float speed;
    
    public LightBulb(World world) {
        this.world = world;
        init();
    }
    
    private void init()  {
        radius = 10 / PPM;
        speed = 0;
        bdef.type = BodyType.DynamicBody;
        CircleShape cs = new CircleShape();
        cs.setRadius(radius);
        fdef.shape = cs;
        body = world.createBody(bdef);
        body.createFixture(fdef);
    }
    
    public void setPosition(float x, float y) {
        Vector2 pos = new Vector2(x, y);
        pos.sub(body.getPosition());
        speed = pos.len() < radius ? 0 : 2;
        body.setLinearVelocity(pos.nor().scl(speed));
    }

    @Override
    public void update() {
    }

    @Override
    public void draw() {
    }
    
}
