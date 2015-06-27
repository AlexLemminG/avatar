package com.mygdx.game.topDown;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.leftRight.GObject;

/**
 * Created by Alexander on 24.06.2015.
 */
public class PhysicsContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        GObject a = ((GObject) contact.getFixtureA().getBody().getUserData());
        GObject b = ((GObject) contact.getFixtureB().getBody().getUserData());

        if(a instanceof ReactToContacts){
            ((ReactToContacts) a).touched(b);
        }
        if(b instanceof ReactToContacts){
            ((ReactToContacts) b).touched(a);
        }
    }

    @Override
    public void endContact(Contact contact) {
        GObject a = ((GObject) contact.getFixtureA().getBody().getUserData());
        GObject b = ((GObject) contact.getFixtureB().getBody().getUserData());

        if(a instanceof ReactToContacts){
            ((ReactToContacts) a).untouched(b);
        }
        if(b instanceof ReactToContacts){
            ((ReactToContacts) b).untouched(a);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
