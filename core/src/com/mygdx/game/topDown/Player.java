package com.mygdx.game.topDown;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.leftRight.CanCreateBody;
import com.mygdx.game.leftRight.GObject;

import java.util.LinkedList;

/**
 * Created by Alexander on 24.06.2015.
 */
public class Player extends GObject implements CanCreateBody, ReactToContacts{
    Body body;
    @Override
    public Body createBody(World world) {
        BodyDef bDef = new BodyDef();
        bDef.position.set(getPos());
        bDef.type = BodyDef.BodyType.DynamicBody;
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.3f);
        fixtureDef.shape = shape;
        body = world.createBody(bDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);


        return body;
    }

    @Override
    public Body getBody() {
        return body;
    }

    LinkedList<Carpet> carpets = new LinkedList<Carpet>();
    @Override
    public void touched(GObject b) {
        if(b instanceof Carpet){
            carpets.add(((Carpet) b));
        }
    }

    @Override
    public void untouched(GObject b) {
        if(b instanceof Carpet){
            carpets.remove(((Carpet) b));
        }
    }
}
