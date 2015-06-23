package com.mygdx.game.leftRight;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Alexander on 20.06.2015.
 */
public class GObject implements GOInterface{
    protected boolean active = true;
    Vector2 pos = new Vector2();
    public boolean isActive(){
        return active;
    }

    @Override
    public void setActive(boolean value) {
        active = value;
    }

    public void setPos(Vector2 pos) {
        this.pos.set(pos);
        if(this instanceof CanCreateBody){
            Body body = ((CanCreateBody) this).getBody();
            if(body != null)
                body.setTransform(pos, body.getAngle());
        }
    }



    public Vector2 getPos() {
        return pos;
    }
}
