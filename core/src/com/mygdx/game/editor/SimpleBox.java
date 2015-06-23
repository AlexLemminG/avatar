package com.mygdx.game.editor;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.leftRight.CanCreateBody;
import com.mygdx.game.leftRight.GObject;
import com.mygdx.game.leftRight.ShapeDrawable;

/**
 * Created by Alexander on 23.06.2015.
 */
public class SimpleBox extends GObject implements ShapeDrawable, CanCreateBody
{
    Body body;

    public SimpleBox(float x, float y) {
        setPos(new Vector2(x, y));
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
//        shapeRenderer.setColor(Color.BLACK);
//        shapeRenderer.rect(getPos().x, getPos().y, 100,100);
    }

    @Override
    public Body createBody(World world) {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.position.set(getPos());
        body = world.createBody(bDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50,50);
        body.createFixture(shape, 1);
        return null;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
