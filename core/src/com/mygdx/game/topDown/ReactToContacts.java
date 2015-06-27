package com.mygdx.game.topDown;

import com.mygdx.game.leftRight.GObject;

/**
 * Created by Alexander on 24.06.2015.
 */
public interface ReactToContacts {
    void touched(GObject b);

    void untouched(GObject b);
}
