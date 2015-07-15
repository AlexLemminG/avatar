package com.keepCalmAndDoItRight.quick;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Filter;

/**
 * Created by Alexander on 03.07.2015.
 */
public class Consts {
    public static Filter WALL_FILTER = new Filter();
    static{
        WALL_FILTER.categoryBits = 0b0010;
        WALL_FILTER.maskBits = 0b1101;
    }
    public static Filter BULLET_FILTER = new Filter();
    static{
        BULLET_FILTER.categoryBits = 0b0100;
        BULLET_FILTER.maskBits = 0b1011;
    }

    public static int FIRE_BUTTON = Input.Keys.F;

}
