package com.mygdx.game.leftRight;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alexander on 20.06.2015.
 */
public class PhysPoint extends GObject implements Updatable{
    Body body;

    public PhysPoint(float x, float y, World world){

        BodyDef bDef = new BodyDef();
        bDef.fixedRotation = true;
        bDef.allowSleep = false;
        bDef.position.set(x, y);
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.gravityScale = 0;
//        bDef.gravityScale = 0;

        body = world.createBody(bDef);
        CircleShape shape = new CircleShape();
        ChainShape shape2 = new ChainShape();
        shape2.createChain(new float[]{
                -0.1f,0,0.1f,0
        });
        shape.setRadius(0.05f);
        body.createFixture(shape2, 0.001f);
        MassData mData = new MassData();
        mData.mass = 0.01f;
        body.setMassData(mData);
    }

    public void setPosition(float x, float y){
        body.setTransform(x,y,0);
    }
    public void setVelocity(float x, float y){
        body.setLinearVelocity(x, y);
    }

    public Vector2 getPosition(){
        return body.getPosition();
    }

    @Override
    public void update(float dt) {

    }

}
