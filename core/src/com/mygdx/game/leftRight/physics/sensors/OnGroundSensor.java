package com.mygdx.game.leftRight.physics.sensors;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.leftRight.physics.SensorFixture;

/**
 * Created by Alexander on 18.06.2015.
 */
public class OnGroundSensor extends SensorFixture{
    int grounds = 0;
    @Override
    public void begin(Fixture b) {
        grounds++;
    }

    @Override
    public void end(Fixture b) {
        grounds--;
    }

    public boolean isOnGround(){
        return grounds > 0;
    }
}
