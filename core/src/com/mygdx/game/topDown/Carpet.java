package com.mygdx.game.topDown;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.leftRight.CanCreateBody;
import com.mygdx.game.leftRight.GObject;

import java.util.LinkedList;

/**
 * Created by Alexander on 24.06.2015.
 */
public class Carpet extends GObject implements ReactToContacts, CanCreateBody{
    Body body;
    String name = "Carpet";
    LinkedList<CarpetLink> links = new LinkedList<CarpetLink>();

    private PolygonShape shape;


    public Carpet(PolygonShape shape) {
        this.shape = shape;
    }

    @Override
    public void touched(GObject b) {
        TopDown.instance.view.ui.putInfo(name + " touched ", b != null ? b.toString() : "null");
    }

    @Override
    public void untouched(GObject b) {
        TopDown.instance.view.ui.putInfo(name + " untouched", b != null ? b.toString() : "null");
    }

    @Override
    public Body createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = shape;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        body.setUserData(this);

        return body;
    }

    public void activate(){
        for(CarpetLink link : links)
            link.activate();
    }

    @Override
    public Body getBody() {
        return body;
    }
}
