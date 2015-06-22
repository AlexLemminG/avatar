package com.mygdx.game.leftRight;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander on 20.06.2015.
 */
public class GObject implements GOInterface{
    protected boolean active = true;
    Vector2 pos;
    public boolean isActive(){
        return active;
    }

    @Override
    public void setActive(boolean value) {
        active = value;
    }
}
