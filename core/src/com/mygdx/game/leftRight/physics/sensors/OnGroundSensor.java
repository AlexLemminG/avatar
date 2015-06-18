package com.mygdx.game.leftRight.physics.sensors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.leftRight.physics.SensorFixture;

import java.util.LinkedList;

/**
 * Created by Alexander on 18.06.2015.
 */
public class OnGroundSensor extends SensorFixture{
    int grounds = 0;
    LinkedList<Body> bodiesOnGround = new LinkedList<Body>();

    public OnGroundSensor(Fixture owner) {
        super(owner);
    }

    @Override
    public void begin(Fixture b) {
        grounds++;
        bodiesOnGround.add(b.getBody());
    }

    @Override
    public void end(Fixture b) {
        grounds--;
        bodiesOnGround.remove(b.getBody());
    }

    public boolean isOnGround(){
        return grounds > 0;
    }
    public Vector2 getAvarageGroundVelocity(){
        Vector2 result = new Vector2();
        for(Body b : bodiesOnGround){
            result.add(b.getLinearVelocity());
        }

        return result;
    }
}
