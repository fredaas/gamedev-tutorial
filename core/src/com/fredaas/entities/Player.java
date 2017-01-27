package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.handlers.Animation;
import com.fredaas.main.Game;

public class Player extends B2DObject {
    
    private float dx;
    private float speed;
    private float maxSpeed;
    private float cof; // Coefficient of friction
    private float cod; // Coefficient of drag
    private float width;
    private float height;
    private boolean onGround;
    private boolean jump;
    private boolean left;
    private boolean right;
    private boolean freeFall;
    private boolean facingLeft;
    private Animation animation;
    private TextureRegion[] running;
    private TextureRegion[] idle;
    private TextureRegion[] jumping;
    private TextureRegion[] falling;
    
    public Player(float x, float y, World world) {
        this.x = x / PPM;
        this.y = y / PPM;
        this.world = world;
        init();
    }
    
    private void init() {
        // Fields
        dx = 0;
        speed = 0;
        maxSpeed = 8;
        cof = 0.75f;
        cod = 0.95f;
        width = 60 / PPM;
        height = 68 / PPM;
        
        // Body
        bdef.position.set(x, y);
        bdef.type = BodyType.DynamicBody;
        bdef.fixedRotation = true;
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(20 / PPM, 25 / PPM);
        fdef.shape = ps;
        fdef.density = 0.4f;
        fdef.friction = 0;
        body = world.createBody(bdef);
        body.createFixture(fdef);
        
        // Foot
        ps.setAsBox(15 / PPM, 5 / PPM, new Vector2(0, -25 / PPM), 0);
        fdef.shape = ps;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");
        
        // Sprites
        idle = getTextureRegion("sprites/player-idle.png", 65, 75);
        running = getTextureRegion("sprites/player-run.png", 65, 75);
        jumping = getTextureRegion("sprites/player-jump.png", 65, 75);
        falling = getTextureRegion("sprites/player-fall.png", 65, 75);
        animation = new Animation(idle, "idle");
        sprite = new Sprite(idle[0]);
        sprite.setPosition(x, y);
        sprite.setAlpha(alpha);
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
    
    private void updateSpriteState() {
        // Set direction
        if (left || right) {
            facingLeft = left;
        }
        sprite.flip(facingLeft, false);
        
        // Set frames
        if (onGround) {
            freeFall = false;
            animation.setTimerDelay(20);
            
            if (left || right) {
                animation.setFrames(running, "running");
            } else {
                animation.setFrames(idle, "idle");
            }
        } else {
            animation.setTimerDelay(50);
            
            if (!freeFall) {
                animation.setFrames(jumping, "jumping");
                freeFall = animation.isLastFrame();
            } else {
                animation.setFrames(falling, "falling");
            }
        }
    }
    
    @Override
    public void update() {
        dx = body.getLinearVelocity().x;
        
        if (onGround) {
            dx *= cof;
            speed = 1;
            if (jump) {
                body.applyForceToCenter(new Vector2(0, 45), true);
            }
        } else {
            dx *= cod;
            speed = 0.25f;
        }
        
        if (left || right) {
            dx += left ? -speed : speed;
        }
        if (Math.abs(dx) > maxSpeed) {
            dx = dx < 0 ? -maxSpeed : maxSpeed;
        }
        
        body.setLinearVelocity(dx, body.getLinearVelocity().y);
        sprite.setRegion(animation.getFrame());
        updateSpriteState();
    }
    
    @Override
    public void draw() {
        sb.setProjectionMatrix(Game.cam.combined);
        updateSpritePosition(-width / 2, -height / 2 + 5 / PPM);
        sb.begin();
        sprite.draw(sb);
        sb.end();
    }

}
