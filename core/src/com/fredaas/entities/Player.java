package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.fredaas.handlers.Animation;
import com.fredaas.main.Game;
import com.fredaas.states.PlayState;

public class Player extends B2DObject {
    
    private float dx;
    private float speed;
    private float width;
    private float height;
    private float alpha;
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
    private SpriteBatch sb;
    
    
    public Player(float x, float y, World world) {
        this.x = x / PPM;
        this.y = y / PPM;
        this.world = world;
        init();
    }
    
    private void init() {
        // Fields
        dx = 0;
        speed = 5;
        width = 60 / PPM;
        height = 68 / PPM;
        alpha = 1;
        
        // Body
        bdef.position.set(x, y);
        bdef.type = BodyType.DynamicBody;
        bdef.fixedRotation = true;
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(20 / PPM, 25 / PPM);
        fdef.shape = ps;
        fdef.density = 0.4f;
        fdef.friction = 0;
        body = PlayState.world.createBody(bdef);
        body.createFixture(fdef);
        
        // Foot
        ps.setAsBox(15 / PPM, 5 / PPM, new Vector2(0, -25 / PPM), 0);
        fdef.shape = ps;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("foot");
        
        // Sprites
        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("sprites/player-idle.png"));
        idle = TextureRegion.split(texture, 65, 75)[0];
        texture = new Texture(Gdx.files.internal("sprites/player-run.png"));
        running = TextureRegion.split(texture, 65, 75)[0];
        texture = new Texture(Gdx.files.internal("sprites/player-jump.png"));
        jumping = TextureRegion.split(texture, 65, 75)[0];
        texture = new Texture(Gdx.files.internal("sprites/player-fall.png"));
        falling = TextureRegion.split(texture, 65, 75)[0];
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
    public void setAlpha() {
        alpha = alpha == 1 ? 0 : 1;
        sprite.setAlpha(alpha);
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
            animation.setTimerDelay(25);
            
            if (left || right) {
                animation.setFrames(running, "running");
            }
            else {
                animation.setFrames(idle, "idle");
            }
        }
        else if (!onGround) {
            animation.setTimerDelay(50);
            
            if (!freeFall) {
                animation.setFrames(jumping, "jumping");
                freeFall = animation.isLastFrame();
            }
            else {
                animation.setFrames(falling, "falling");
            }
        }
    }
    
    private void updateSpritePosition() {
        sprite.setPosition(
                (body.getPosition().x - width / 2) * PPM,
                (body.getPosition().y - height / 2 + 5 / PPM) * PPM);
    }
    
    @Override
    public void update() {
        if (left || right) {
            dx = left ? -speed : speed;
        }
        if (jump && onGround) {
            body.applyForceToCenter(new Vector2(0, 45), true);
        }
        body.setLinearVelocity(dx, body.getLinearVelocity().y);
        dx = 0;
        
        sprite.setRegion(animation.getFrame());
        updateSpriteState();
    }
    
    @Override
    public void draw() {
        sb.setProjectionMatrix(Game.cam.combined);
        updateSpritePosition();
        sb.begin();
        sprite.draw(sb);
        sb.end();
    }

}
