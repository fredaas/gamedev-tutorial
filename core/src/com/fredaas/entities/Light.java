package com.fredaas.entities;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Light {
    
    private float x;
    private float y;
    private PointLight light;
    private RayHandler rh;
    private Color color;

    public Light(float x, float y, Color color, RayHandler rh) {
        super();
        this.rh = rh;
        this.x = x;
        this.y = y;
        this.color = color;
        init();
    }
    
    private void init() {
        light = new PointLight(
                rh, 2000, color,
                MathUtils.random(1000, 1000) / PPM,
                x, y);
    }
    
    public void attachTo(Body body) {
        light.attachToBody(body);
    }

}
