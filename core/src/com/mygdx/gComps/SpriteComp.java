package com.mygdx.gComps;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.basics.GComp;
import com.mygdx.basics.NGObject;

/**
 * Created by Alexander on 12.07.2015.
 */
public class SpriteComp extends GComp {
    public Sprite sprite;

    @Override
    public void render(Batch batch) {
        Vector2 pos = getOwner().getPos();
        float a = getOwner().getAngleRad();
        sprite.setPosition(pos.x - sprite.getOriginX(), pos.y - sprite.getOriginY());
        sprite.setRotation(a * MathUtils.radDeg);
        sprite.draw(batch);
    }

    public SpriteComp(NGObject owner, Sprite sprite) {
        super(owner);
        this.sprite = sprite;
    }
}
