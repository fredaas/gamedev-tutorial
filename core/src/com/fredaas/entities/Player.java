package com.fredaas.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Player {
    
    private float dx;
    private float dy;
    private float speed;
    private float maxSpeed;
    private float inertia;
    private float x;
    private float y;
    
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    
    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        init();
    }
    
    private void init() {
        speed = 50;
        maxSpeed = 500;
        inertia = 0.95f;
    }
    
    public void update(float dt) {
        if (left || right) {
            dx += left ? -speed : speed;
        }
        if (up || down) {
            dy += down ? -speed : speed;
        }
        
        if (Math.abs(dx) > maxSpeed) {
            dx = dx < 0 ? -maxSpeed : maxSpeed;
        }
        if (Math.abs(dy) > maxSpeed) {
            dy = dy < 0 ? -maxSpeed : maxSpeed;
        }
        dx = Math.abs(dx) < 1 ? 0 : dx;
        dy = Math.abs(dy) < 1 ? 0 : dy;
        dx *= inertia;
        dy *= inertia;
        
        x += dx * dt;
        y += dy * dt;
    }
    
    public void left(boolean b) {
        left = b;
    }
    public void right(boolean b) {
        right = b;
    }
    public void up(boolean b) {
        up = b;
    }
    public void down(boolean b) {
        down = b;
    }
    
    public void draw(ShapeRenderer sr) {
        sr.setColor(Color.GREEN);
        sr.begin(ShapeType.Line);
        sr.circle(x, y, 20);
        sr.end();
    }

}
