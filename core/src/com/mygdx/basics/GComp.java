package com.mygdx.basics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander on 12.07.2015.
 */
public abstract class GComp {

    public NGObject getOwner() {
        return owner;
    }

    private NGObject owner;

    public GComp(NGObject owner) {
        this.owner = owner;
    }

    public void update(float dt){}

    public void render(Batch batch){}

    public void setTransform(Vector2 pos, float aRad){}

    protected int changeOwnersTransform(Vector2 pos, float aRad){
        owner.pos.set(pos);
        owner.aRad = aRad;
        owner.lastPosChange++;
        return owner.lastPosChange;
    }

    public String getName(){
        return this.getClass().getSimpleName();
    }

}
