package com.mygdx.game.leftRight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alexander on 17.06.2015.
 */
public class Mob {
    float x, y;
    float speed = -10;
    private boolean dead;
    private float speedy = 0;
    float width = 0.4f;
    float height = 1f;
    float width2 = width / 2;
    float height2 = height / 2;
    Body body;
    boolean jump = false;
    boolean onGround = false;



    public Mob(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float dt){
//        if(dead)
//            speedy -= 10;
        x += dt * speed;
        y += dt * speedy;

//        body.setLinearVelocity(speed, speedy);
//        body.setLinearVelocity(speed, body.getLinearVelocity().y);
        body.applyForceToCenter(speed / 100,0, true);

        if(jump){
            jump = false;
            body.applyLinearImpulse(0, 1f, 0, 0, true);
        }

        x = body.getPosition().x;
        y = body.getPosition().y;

    }

    public void render(ShapeRenderer sr){
//        if(isDead())
//            return;
        sr.setColor(Color.BLACK);
        sr.rect(x-width2, y-height2, width, height);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        if(!this.dead) {
            jump = true;
        }

        this.dead = dead;
//        body.setActive(false);
//        body.setType(BodyDef.BodyType.KinematicBody);
    }

    public FixtureDef getFixtureDef() {
        FixtureDef result = new FixtureDef();
        result.density = 1f;
        result.friction = 1f;
        result.restitution = 0;
        PolygonShape shape = new PolygonShape();
        shape.set(new float[]{-width / 2, -height / 2,
                width / 2,  -height / 2,
                width / 2, height / 2,
                -width / 2, height / 2});
        result.shape = shape;
        return result;
    }

    public Body createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);
        body.createFixture(getFixtureDef());
        PolygonShape sensorShape = new PolygonShape();
        sensorShape.setAsBox(width2, 0.1f, new Vector2(0, -height2), 0);
        Fixture groundSensor = body.createFixture(sensorShape, 0);
        groundSensor.setSensor(true);
        this.body = body;
        body.setUserData(this);
        return body;
    }
}
