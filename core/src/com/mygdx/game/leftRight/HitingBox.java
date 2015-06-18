package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Alexander on 17.06.2015.
 */
public class HitingBox {
    float x, y;
    float width = 50, height = 100;
    double timeCreated;
    boolean dead;
    private double lifeTime = 2;

    public HitingBox(float x, float y, double lifeTime) {
        this.x = x;
        this.y = y;
        this.lifeTime = lifeTime;
        timeCreated = LeftRight.instance.time;
    }

    public void update(float dt){
        if(dead)
            return;

        for(Mob mob : LeftRight.instance.mobs){
            if(mob.x >= x && mob.x <= x + width &&
                    mob.y >= y && mob.y <= y + height){
                mob.setDead(true);
            }
        }
        if(LeftRight.instance.time >= timeCreated + lifeTime)
            dead = true;

    }

    public void render(ShapeRenderer sr){
        if(dead)
            return;
        sr.setColor(Color.RED);
        sr.rect(x,y, width, height);
    }
}
