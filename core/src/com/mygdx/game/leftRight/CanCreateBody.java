package com.mygdx.game.leftRight;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alexander on 20.06.2015.
 */
public interface CanCreateBody {
    public Body createBody(World world);
    public Body getBody();
}
