package com.mygdx.game.editor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.leftRight.GObject;
import com.mygdx.game.leftRight.ShapeDrawable;

/**
 * Created by Alexander on 23.06.2015.
 */
public class SimpleBox extends GObject implements ShapeDrawable{
    float x, y;

    public SimpleBox(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(x,y, 100,100);
    }
}
