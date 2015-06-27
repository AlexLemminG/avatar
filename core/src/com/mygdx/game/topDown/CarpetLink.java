package com.mygdx.game.topDown;

import com.mygdx.game.leftRight.GObject;

/**
 * Created by Alexander on 24.06.2015.
 */
public class CarpetLink {
    Carpet carpet;
    GObject gObject;
    protected boolean activated = false;
    public CarpetLink(Carpet carpet, GObject gObject) {
        this.carpet = carpet;
        this.gObject = gObject;
    }

    public void activate(){activated = !activated;}
    public void disactivate(){}
}
