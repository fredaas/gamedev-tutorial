package com.fredaas.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {
    
    private int currentFrame;
    private double timer;
    private double timerDelay = 25;
    private double timerDiff;
    private TextureRegion[] treg;
    private String type;
    
    public Animation (TextureRegion[] treg, String type) {
        this.treg = treg;
        this.type = type;
        init();
    }
    
    private void init() {
        timer = System.nanoTime();
    }
    
    private void update() {
        timerDiff = (System.nanoTime() - timer) / 1000000;
        if (timerDiff > timerDelay) {
            timer = System.nanoTime();
            timerDiff = 0;
            step();
        }
    }
    
    private void step() {
        currentFrame++;
        if (currentFrame > treg.length - 1) {
            currentFrame = 0;
        }
    }
    
    public void setFrames(TextureRegion[] treg, String type) {
        if (!this.type.equals(type)) {
            this.treg = treg;
            this.type = type;
            currentFrame = 0;
        }
    }
    
    public void setTimerDelay(float timerDelay) {
        this.timerDelay = timerDelay;
    }
    
    public boolean isLastFrame() {
        return currentFrame == treg.length - 1;
    }
    
    public TextureRegion getFrame() {
        update();
        return treg[currentFrame];
    }
    
}
