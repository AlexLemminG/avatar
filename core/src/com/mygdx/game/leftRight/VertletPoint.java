package com.mygdx.game.leftRight;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alexander on 20.06.2015.
 */
public class VertletPoint implements Updatable{
    Body body;

    public VertletPoint(float x, float y, World world){

        BodyDef bDef = new BodyDef();
        bDef.fixedRotation = true;
        bDef.allowSleep = false;
        bDef.position.set(x, y);
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.gravityScale = 0;
//        bDef.gravityScale = 0;

        body = world.createBody(bDef);
        CircleShape shape = new CircleShape();
        shape.setRadius(0.0005f);
        body.createFixture(shape, 0.00001f);
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
