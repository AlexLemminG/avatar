package com.mygdx.gComps;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.basics.GComp;
import com.mygdx.basics.NGObject;

/**
 * Created by Alexander on 12.07.2015.
 */
public class TransformChangerComp extends GComp {
    public TransformChangerComp(NGObject owner) {
        super(owner);
    }


    public void setTransform(Vector2 pos, float aRad){
        changeOwnersTransform(pos, aRad);
    }
}
