package com.mygdx.game.leftRight.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * Created by Alexander on 18.06.2015.
 */
public abstract class SensorFixture {
    Fixture owner;
    public void beginContact(Contact contact){
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a == owner) {
            if(!a.getBody().getFixtureList().contains(b, true)){
                begin(b);
            }
        }else {
            if(!b.getBody().getFixtureList().contains(a, true)){
                begin(a);
            }
        }
    }

    public abstract void begin(Fixture b);
    public abstract void end(Fixture b);

    public void endContact(Contact contact) {
        if(contact.getFixtureA() == owner)
            end(contact.getFixtureB());
        else
            end(contact.getFixtureA());
    }
}
