package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.states.PlayState;

public class Player extends B2DObject {
    
    private float dx;
    private float speed;
    private boolean onGround;
    private boolean jump;
    private boolean left;
    private boolean right;
    
    public Player(float x, float y, World world) {
        this.x = x / PPM;
        this.y = y / PPM;
        this.world = world;
        init();
    }
    
    private void init() {
        dx = 0;
        speed = 5;
        
        // Body
        bdef.position.set(x, y);
        bdef.type = BodyType.DynamicBody;
        bdef.fixedRotation = true;
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(20 / PPM, 20 / PPM);
        fdef.shape = ps;
        fdef.density = 0.4f;
        body = PlayState.world.createBody(bdef);
        body.createFixture(fdef);
        
        // Foot
        ps.setAsBox(15 / PPM, 5 / PPM, new Vector2(0, -20 / PPM), 0);
        fdef.shape = ps;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");
    }
    
    public void left(boolean b) {
        left = b;
    }
    public void right(boolean b) {
        right = b;
    }
    public void jump(boolean b) {
        jump = b;
    }
    public void onGround(boolean b) {
        onGround = b;
    }
    
    @Override
    public void update() {
        if (left || right) {
            dx = left ? -speed : speed;
        }
        if (jump && onGround) {
            body.applyForceToCenter(new Vector2(0, 30), true);
        }
        body.setLinearVelocity(dx, body.getLinearVelocity().y);
        dx = 0;
    }
    
    @Override
    public void draw() {
    }

}
