package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Alexander on 17.06.2015.
 */
public class Player extends Mob{
    public float width = 0.4f;
    public float height = 1;


    public Player(float x, float y) {
        super(x, y);
        color = Color.YELLOW;
    }
}
