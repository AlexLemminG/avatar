package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander on 08.06.2015.
 */
public class Particle extends SimpleObject{
    float radius;
    final Vector2 velocity = new Vector2();

    public Particle(float radius) {
        super(new float[]{
                -radius, -radius,
                radius, -radius,
                radius,  radius,
                -radius, radius
        });
        this.radius = radius;
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        velocity.add(0, -9.8f * dt);
        sprite.translate(velocity.x * dt, velocity.y * dt);
        if(sprite.getY() < -100f)
            velocity.y = Math.abs(velocity.y);
        if(sprite.getX() < -100f)
            velocity.x = Math.abs(velocity.x);
        if(sprite.getX() > 100f)
            velocity.x = -Math.abs(velocity.x);
    }
}
