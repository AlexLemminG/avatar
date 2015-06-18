package com.mygdx.game.leftRight.physics.sensors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.leftRight.physics.SensorFixture;

/**
 * Created by Alexander on 18.06.2015.
 */
public class FixtureFabric {
    public static Fixture createFixtureSensor(Body body, Shape shape, SensorFixture sensor){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0;
        fixtureDef.friction = 0;
        fixtureDef.isSensor = true;
        fixtureDef.restitution = 0;
        fixtureDef.shape = shape;
        Fixture result = body.createFixture(fixtureDef);
        result.setUserData(sensor);
        sensor.owner = result;
        return result;
    }
}
