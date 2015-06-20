package com.mygdx.game.leftRight;

/**
 * Created by Alexander on 20.06.2015.
 */
public class GObject implements GOInterface{
    protected boolean active = true;
    public boolean isActive(){
        return active;
    }

    @Override
    public void setActive(boolean value) {
        active = value;
    }
}
