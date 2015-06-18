package com.mygdx.game.leftRight.physics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Alexander on 18.06.2015.
 */
public class ContactAdapter implements com.badlogic.gdx.physics.box2d.ContactListener{
    @Override
    public void beginContact(Contact contact) {
        if(contact.getFixtureA().getUserData() instanceof SensorFixture){
            ((SensorFixture) contact.getFixtureA().getUserData()).beginContact(contact);
        }
        if(contact.getFixtureB().getUserData() instanceof SensorFixture){
            ((SensorFixture) contact.getFixtureB().getUserData()).beginContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        if(contact.getFixtureA().getUserData() instanceof SensorFixture){
            ((SensorFixture) contact.getFixtureA().getUserData()).endContact(contact);
        }
        if(contact.getFixtureB().getUserData() instanceof SensorFixture){
            ((SensorFixture) contact.getFixtureB().getUserData()).endContact(contact);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
