package com.mygdx.game.editor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.leftRight.GObject;
import com.mygdx.game.leftRight.ShapeDrawable;

/**
 * Created by Alexander on 23.06.2015.
 */
public class SimpleBox extends GObject implements ShapeDrawable{

    public SimpleBox(float x, float y) {
        setPos(new Vector2(x, y));
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(getPos().x, getPos().y, 100,100);
    }
}
