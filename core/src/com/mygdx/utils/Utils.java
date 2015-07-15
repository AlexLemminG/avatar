package com.mygdx.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.basics.SimplePath;

/**
 * Created by Alexander on 13.07.2015.
 */
public class Utils {
    public SimplePath movementPathToForcePath(SimplePath path){
        Array<Vector2> v = new Array<>();
        Vector2 currentVel = path.getPoints()[1].cpy().sub(path.getPoints()[0]).scl(path.getPoints().length-1);

        for (int i = 1; i < path.getPoints().length; i++) {
            Vector2 vel = path.getPoints()[i].cpy().sub(path.getPoints()[i-1]).scl(path.getPoints().length-1);

            v.add( vel.cpy().sub(currentVel));
            currentVel = vel;
        }
        return new SimplePath(v);
    }
}
