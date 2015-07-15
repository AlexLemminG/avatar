package com.mygdx.gComps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.basics.GComp;
import com.mygdx.basics.NGObject;

/**
 * Created by Alexander on 12.07.2015.
 */
public class PhysicsBodyComp extends GComp {
    Body body;
    int lastPosChange = 0;

    public PhysicsBodyComp(NGObject owner, Body body) {
        super(owner); this.body = body;
    }

    @Override
    public void update(float dt) {
        lastPosChange = changeOwnersTransform(body.getPosition(), body.getAngle());
    }

    @Override
    public void setTransform(Vector2 pos, float aRad) {
        if(lastPosChange != getOwner().lastPosChange)
            body.setTransform(pos, aRad);
    }
}
