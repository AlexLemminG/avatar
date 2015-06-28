package com.keepCalmAndDoItRight.gObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.keepCalmAndDoItRight.ReactToContacts;
import com.keepCalmAndDoItRight.basics.GObject;
import com.keepCalmAndDoItRight.quick.ShapesCreator;

/**
 * Created by Alexander on 27.06.2015.
 */
public class ActionBox implements ReactToContacts {
    private Fixture fixture;

    public FixtureDef getFixtureDef(){
        FixtureDef result = new FixtureDef();
        PolygonShape ps = ShapesCreator.box(0.05f,0.05f);
        ps.setAsBox(0.05f, 0.05f, new Vector2(0, 0.7f), 0);
        result.shape = ps;
        result.isSensor = true;
        result.density = 0;
        return result;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
        fixture.setUserData(this);
    }

    Array<Activatable> activatables = new Array<Activatable>();
    public void activate() {
        for(Activatable a : activatables){
            a.activate();
        }
    }

    @Override
    public void touched(GObject b) {
        if(b instanceof Activatable){
            activatables.add((Activatable) b);
        }
    }

    @Override
    public void untouched(GObject b) {
        if(b instanceof Activatable){
            activatables.removeValue(((Activatable) b),true);
        }
    }
}
