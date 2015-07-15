package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.basics.Level;

/**
 * Created by Alexander on 06.07.2015.
 */
public class UnitSpawner extends GObject{
    @Override
    public Body createBody(World world) {
        return null;
    }

    public UnitSpawner(Level level) {
        super(level);
        init();
    }

    public Vector2 position = new Vector2();

    @Override
    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    @Override
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    float lastSpawnedTime = 0f;
    public float period = 2f;

    @Override
    public void update(float dt) {
        super.update(dt);
        if(level.time >= lastSpawnedTime + period){
            lastSpawnedTime = level.time;
            Unit unit = new Unit(level);
            unit.setPosition(position);
            unit.control.maxSpeed /= MathUtils.random(4f, 6f);
            unit.control.fireDelayTime *= (MathUtils.random(7f, 10));
            unit.control.timeNextBulletFired = level.time + unit.control.fireDelayTime * ((float) Math.random());
            unit.unitType = Unit.UnitType.zombie;
            unit.control.addAction(UnitControl.FOLLOW_UNIT);
            unit.control.addAction(UnitControl.FIRE);
            unit.control.addAction(UnitControl.LOOK_AT_MOVEMENT);
            unit.control.unit = level.player;
            unit.getActor().setColor(0.3f, MathUtils.random(0.7f, 0.9f), 0.3f, 1);
        }
    }
}
