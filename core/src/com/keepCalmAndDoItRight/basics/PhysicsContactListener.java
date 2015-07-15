package com.keepCalmAndDoItRight.basics;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.keepCalmAndDoItRight.gObjects.ActionBox;

/**
 * Created by Alexander on 24.06.2015.
 */
public class PhysicsContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {
        Object a = ( contact.getFixtureA().getUserData());

        if(a instanceof ReactToContacts && !(contact.getFixtureB().getUserData() instanceof ActionBox)){
            Object b = ( contact.getFixtureB().getBody().getUserData());
            ((ReactToContacts) a).touched(((GObject) b));
        }

        Object b = (contact.getFixtureB().getUserData());
        if(b instanceof ReactToContacts && !(contact.getFixtureA().getUserData() instanceof ActionBox)){
            a = ( contact.getFixtureA().getBody().getUserData());

            ((ReactToContacts) b).touched(((GObject) a));
        }
        contact.setEnabled(false);
    }

    @Override
    public void endContact(Contact contact) {
        Object a = ( contact.getFixtureA().getUserData());

        if(a instanceof ReactToContacts && !(contact.getFixtureB().getUserData() instanceof ActionBox)){
            Object b = ( contact.getFixtureB().getBody().getUserData());
            ((ReactToContacts) a).untouched(((GObject) b));
        }

        Object b = (contact.getFixtureB().getUserData());
        if(b instanceof ReactToContacts && !(contact.getFixtureA().getUserData() instanceof ActionBox)){
            a = ( contact.getFixtureA().getBody().getUserData());

            ((ReactToContacts) b).untouched(((GObject) a));
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
